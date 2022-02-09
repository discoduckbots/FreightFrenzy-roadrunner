package org.firstinspires.ftc.teamcode.discoduckbots.hardware;

import android.util.Log;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;

import static org.opencv.imgproc.Imgproc.COLOR_BGR2GRAY;
import static org.opencv.imgproc.Imgproc.COLOR_RGB2GRAY;
import static org.opencv.imgproc.Imgproc.THRESH_BINARY;

public class BlockDetector {

    private static final int MAX_THRESHOLD = 50;
    private static final String TAG = "ftc-opencv";
    OpenCvCamera webcam;
    BlockDetectorListener listener;
    DistanceSensor distanceSensor;
    double BLOCK_SENSOR = 9;

    public BlockDetector(WebcamName webcamName, HardwareMap hardwareMap, BlockDetectorListener listener
                         ) {
        this.listener = listener;
        //this.distanceSensor = distanceSensor;
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());


        webcam = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);
        Log.d(TAG, "open webcam " + webcam);
        // OR...  Do Not Activate the Camera Monitor View
        //webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"));

        /*
         * Specify the image processing pipeline we wish to invoke upon receipt
         * of a frame from the camera. Note that switching pipelines on-the-fly
         * (while a streaming session is in flight) *IS* supported.
         */
        webcam.setPipeline(new SamplePipeline());

        /*
         * Open the connection to the camera device. New in v1.4.0 is the ability
         * to open the camera asynchronously, and this is now the recommended way
         * to do it. The benefits of opening async include faster init time, and
         * better behavior when pressing stop during init (i.e. less of a chance
         * of tripping the stuck watchdog)
         *
         * If you really want to open synchronously, the old method is still available.
         */
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                /*
                 * Tell the webcam to start streaming images to us! Note that you must make sure
                 * the resolution you specify is supported by the camera. If it is not, an exception
                 * will be thrown.
                 *
                 * Keep in mind that the SDK's UVC driver (what OpenCvWebcam uses under the hood) only
                 * supports streaming from the webcam in the uncompressed YUV image format. This means
                 * that the maximum resolution you can stream at and still get up to 30FPS is 480p (640x480).
                 * Streaming at e.g. 720p will limit you to up to 10FPS and so on and so forth.
                 *
                 * Also, we specify the rotation that the webcam is used in. This is so that the image
                 * from the camera sensor can be rotated such that it is always displayed with the image upright.
                 * For a front facing camera, rotation is defined assuming the user is looking at the screen.
                 * For a rear facing camera or a webcam, rotation is defined assuming the camera is facing
                 * away from the user.
                 */
                Log.d(TAG, "starting streaming");
                webcam.startStreaming(160, 120, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
            Log.d(TAG, "found an error " + errorCode);
            }
        });
    }

    class SamplePipeline extends OpenCvPipeline {
        boolean viewportPaused;

        /*
         * NOTE: if you wish to use additional Mat objects in your processing pipeline, it is
         * highly recommended to declare them here as instance variables and re-use them for
         * each invocation of processFrame(), rather than declaring them as new local variables
         * each time through processFrame(). This removes the danger of causing a memory leak
         * by forgetting to call mat.release(), and it also reduces memory pressure by not
         * constantly allocating and freeing large chunks of memory.
         */

        @Override
        public Mat processFrame(Mat input) {
            Mat bw1 = new Mat();
            Mat bw2 = new Mat();
            Mat grayimage = new Mat();
            Size size = new Size();
            boolean blockDetectedZone1 = false; // cargo in grabber side
            boolean blockDetectedZone2 = false; // cargo in intake side
           // Imgproc.cvtColor(input, grayimage, COLOR_RGB2GRAY);
            Imgproc.cvtColor(input, grayimage, COLOR_BGR2GRAY);
            Log.d("ftc-opencv", "int height = " + input.height()); // print values for debugging
            Log.d("ftc-opencv", "int width = " + input.width()); // print values for debugging

           // Rect roi_zone1= new Rect(55, 40, 60, 35);
            //Rect roi_zone2= new Rect(55, 75, 60, 40);
            Rect roi_zone1= new Rect(75, 17, 36, 50);
            Rect roi_zone2= new Rect(70, 70, 45, 40);
            Mat im_zone1 = grayimage.submat(roi_zone1);
            Mat im_zone2 = grayimage.submat(roi_zone2);
            Imgproc.threshold(im_zone1, bw1, 180, 255, THRESH_BINARY);
            Imgproc.threshold(im_zone2, bw2, 180, 255, THRESH_BINARY);

            int cargo1 = Core.countNonZero(bw1);
            int cargo2 = Core.countNonZero(bw2);
            Log.d("ftc-opencv", "cargo1 = " + cargo1); // print values for debugging
            Log.d("ftc-opencv", "cargo2 = " + cargo2); // print values for debugging

            if (cargo1 > MAX_THRESHOLD) {
                blockDetectedZone1 = true;//grabber
            }
            if (cargo2 > MAX_THRESHOLD) {
                blockDetectedZone2 = true;//intake
            }

           // checkBlockDetected(blockDetected);


            /**
             * NOTE: to see how to get data from your pipeline to your OpMode as well as how
             * to change which stage of the pipeline is rendered to the viewport when it is
             * tapped, please see {@link PipelineStageSwitchingExample}
             */

            return input;
        }

        @Override
        public void onViewportTapped() {
            /*
             * The viewport (if one was specified in the constructor) can also be dynamically "paused"
             * and "resumed". The primary use case of this is to reduce CPU, memory, and power load
             * when you need your vision pipeline running, but do not require a live preview on the
             * robot controller screen. For instance, this could be useful if you wish to see the live
             * camera preview as you are initializing your robot, but you no longer require the live
             * preview after you have finished your initialization process; pausing the viewport does
             * not stop running your pipeline.
             *
             * Here we demonstrate dynamically pausing/resuming the viewport when the user taps it
             */

            viewportPaused = !viewportPaused;

            if (viewportPaused) {
                webcam.pauseViewport();
            } else {
                webcam.resumeViewport();
            }
        }
    }

  /*  private boolean checkBlockDetected(boolean cameraDetectedBlock) {
        boolean distanceSensorDetectedBlock = distanceSensor.getDistance(double distance) {
            return distance < BLOCK_SENSOR;
       } */

    }



