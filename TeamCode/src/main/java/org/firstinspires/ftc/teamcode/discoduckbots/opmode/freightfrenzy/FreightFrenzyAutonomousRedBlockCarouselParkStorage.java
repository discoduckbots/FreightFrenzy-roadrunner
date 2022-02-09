/* Copyright (c) 2019 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.discoduckbots.opmode.freightfrenzy;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.discoduckbots.hardware.CargoGrabber;
import org.firstinspires.ftc.teamcode.discoduckbots.hardware.CarouselSpinner;
import org.firstinspires.ftc.teamcode.discoduckbots.hardware.DuckDetector;
import org.firstinspires.ftc.teamcode.discoduckbots.hardware.HardwareStore;
import org.firstinspires.ftc.teamcode.discoduckbots.hardware.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode.discoduckbots.opmode.RingStackDetector;
import org.firstinspires.ftc.teamcode.discoduckbots.sensors.TensorFlow;

/**
 * This 2020-2021 OpMode illustrates the basics of using the TensorFlow Object Detection API to
 * determine the position of the Freight Frenzy game elements.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 *
 * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
 * is explained below.
 */
@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "FFRedBlockCarouselParkStorage", group = "drive")

public class FreightFrenzyAutonomousRedBlockCarouselParkStorage extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private MecanumDrivetrain mecanumDrivetrain = null;
    private CargoGrabber cargoGrabber = null;
    private CarouselSpinner carouselSpinner = null;


    TensorFlow tensorFlow = null;
    RingStackDetector ringStackDetector = null;

    private static final double AUTONOMOUS_SPEED = 0.45;
    private static final double STRAFE_SPEED = 0.35;
    private static final double ROTATION_SPEED = 0.4;
    private static final int WOBBLE_GRABBER_REVOLUTIONS = 6250;
    private static final int LEVEL_1 = 0; //3230
    private static final int LEVEL_2 = -3700; //4485
    private static final int LEVEL_3 = 0; //5205


    /* Note: This sample uses the all-objects Tensor Flow model (FreightFrenzy_BCDM.tflite), which contains
     * the following 4 detectable objects
     *  0: Ball,
     *  1: Cube,
     *  2: Duck,
     *  3: Marker (duck location tape marker)
     *
     *  Two additional model assets are available which only contain a subset of the objects:
     *  FreightFrenzy_BC.tflite  0: Ball,  1: Cube
     *  FreightFrenzy_DM.tflite  0: Duck,  1: Marker
     */
    private static final String TFOD_MODEL_ASSET = "FreightFrenzy_BCDM.tflite";
    private static final String[] LABELS = {
            "Ball",
            "Cube",
            "Duck",
            "Marker"
    };

    /*
     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
     * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
     * web site at https://developer.vuforia.com/license-manager.
     *
     * Vuforia license keys are always 380 characters long, and look as if they contain mostly
     * random data. As an example, here is a example of a fragment of a valid key:
     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
     * Once you've obtained a license key, copy the string from the Vuforia web site
     * and paste it in to your code on the next line, between the double quotes.
     */
    private static final String VUFORIA_KEY =
            "AYSk32L/////AAABmbhg8GZe7kWfmZUNbwUuIPIF4dklwa5nY6Be4MuPWPpva8SYxSc/pUq/kc9kdl8Bh7w7t8PjWaJGfLRGug7l/wswCDj2V2Ag+hsG2zUDnAY55qbbiTzIjyt2qJzfYIK5Ipojsz7KmEiAWC7DUf9C64jez6LEDJEYYwtR+W2RrTl0DRRYpVmMGk31aF5ZbHC77dTEvpT5xCGAC35F2R53bYW9eUbDMiQWnfKTKOxLA8oEsA5pI42IJhZvFqfSFYsTaLp7DymS8b3QVwn4jOvWMh+sdloU9f1fE14yolR4wcIzbiFcSA2eJTGYfwUcopLlpZsE4A3XdKRx/AIExFADF5qAaAW02wMILYxWQYXDBQ4m";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the TensorFlow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    @Override
    public void runOpMode() {
        HardwareStore hardwareStore = new HardwareStore(hardwareMap, telemetry, this);
        mecanumDrivetrain = hardwareStore.getMecanumDrivetrain();
        cargoGrabber = hardwareStore.getCargoGrabber();
        carouselSpinner = hardwareStore.getCarouselSpinner();
        DuckDetector duckDetector = new DuckDetector(hardwareStore.getDistanceSensor(),
                hardwareStore.getDistanceSensor2());

        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first
        //initVuforia();
        //initTfod();

        /**
         * Activate TensorFlow Object Detection before we wait for the start command.
         * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
         **/
       /* if (tfod != null) {
            tfod.activate();

            // The TensorFlow software will scale the input images from the camera to a lower resolution.
            // This can result in lower detection accuracy at longer distances (> 55cm or 22").
            // If your target is at distance greater than 50 cm (20") you can adjust the magnification value
            // to artificially zoom in to the center of image.  For best results, the "aspectRatio" argument
            // should be set to the value of the images used to create the TensorFlow Object Detection model
            // (typically 16/9).
            tfod.setZoom(2.5, 16.0/9.0);
        }
*/
        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        waitForStart();

        if (opModeIsActive()) {

            //tensorflow
            //sleep(300);
            //cargoGrabber.grab();
            //mecanumDrivetrain.driveByGyro(3, mecanumDrivetrain.DIRECTION_REVERSE, AUTONOMOUS_SPEED,0);
            //sleep(300);
            mecanumDrivetrain.driveByGyro(17.5, MecanumDrivetrain.DIRECTION_REVERSE, AUTONOMOUS_SPEED, 0);
            sleep(300);
            Log.d("FTC", "Checking for ducks");
            int level = duckDetector.getLevel();
            mecanumDrivetrain.driveByGyro(2, MecanumDrivetrain.DIRECTION_FORWARD, AUTONOMOUS_SPEED, 0);
            sleep(300);
            mecanumDrivetrain.gyroTurn(90, 0.45, this );
            //mecanumDrivetrain.driveByGyro(3, mecanumDrivetrain.DIRECTION_STRAFE_LEFT, STRAFE_SPEED, 0);
            sleep(300);
            mecanumDrivetrain.driveByGyro(33, mecanumDrivetrain.DIRECTION_FORWARD, AUTONOMOUS_SPEED, 90);
            sleep(300);
            mecanumDrivetrain.driveByGyro(17.5, mecanumDrivetrain.DIRECTION_STRAFE_LEFT, AUTONOMOUS_SPEED, 90);
            sleep(300);
            carouselSpinner.getOneDuckInAutonomous();
            sleep(500);
            mecanumDrivetrain.driveByGyro(40.5, mecanumDrivetrain.DIRECTION_STRAFE_RIGHT, STRAFE_SPEED, 90);
            sleep(300);
            Log.d("FTC", "level " + level);
            cargoGrabber.liftByEncoder(level);
            //mecanumDrivetrain.driveByGyro(3, mecanumDrivetrain.DIRECTION_STRAFE_RIGHT, STRAFE_SPEED, 0);
           // mecanumDrivetrain.driveByGyro(4, mecanumDrivetrain.DIRECTION_STRAFE_RIGHT, STRAFE_SPEED, 0);
            sleep(300);
            //mecanumDrivetrain.forwardByTime(this, AUTONOMOUS_SPEED, 0.5);
            //sleep(500);
            //mecanumDrivetrain.driveByGyro(18, mecanumDrivetrain.DIRECTION_STRAFE_RIGHT, STRAFE_SPEED, 0);
            //sleep(500);
            mecanumDrivetrain.driveByGyro(26.5, mecanumDrivetrain.DIRECTION_REVERSE, AUTONOMOUS_SPEED, 90);
            //sleep(1500);
            sleep(300);
            cargoGrabber.release();
            sleep(300);
            Log.d("FTC", "Drive back");
            mecanumDrivetrain.driveByGyro(36, mecanumDrivetrain.DIRECTION_FORWARD, AUTONOMOUS_SPEED, 90);
            sleep(300);
            //Log.d("FTC", "Strafing after lifting");
            Log.d("FTC", "Strafing left 1");
            mecanumDrivetrain.driveByGyro(23, mecanumDrivetrain.DIRECTION_STRAFE_LEFT, STRAFE_SPEED, 90);
            /*sleep(200);
            Log.d("FTC", "Drive back");
            mecanumDrivetrain.driveByGyro(37, mecanumDrivetrain.DIRECTION_REVERSE, AUTONOMOUS_SPEED, 90);
            sleep(200);
            Log.d("FTC", "Strafing left 2");
            mecanumDrivetrain.driveByGyro(5, mecanumDrivetrain.DIRECTION_STRAFE_LEFT, STRAFE_SPEED, 90);
            //sleep(300);
            Log.d("FTC", "Strafing left by time");
            // Don't know why this is actually strafing left
            mecanumDrivetrain.forwardByTime(this, AUTONOMOUS_SPEED, 0.7);
            sleep(200);
            Log.d("FTC", "All the way back ");
            mecanumDrivetrain.driveByGyro(64, mecanumDrivetrain.DIRECTION_REVERSE, AUTONOMOUS_SPEED, 90);
            */
            cargoGrabber.resetToLydiasFavoritePosition();

            //sleep(1500);
            //Log.d("FTC", "Releasing freight");
            //cargoGrabber.release();
            //sleep(1000);


            /*Log.d("FTC", "Before coming back");
            mecanumDrivetrain.driveByGyro(9, mecanumDrivetrain.DIRECTION_FORWARD, AUTONOMOUS_SPEED,90);
            sleep(300);
            Log.d("FTC", "Before turning");
            mecanumDrivetrain.gyroTurn(90, 0.45, this );
            sleep(500);
            Log.d("FTC", "Before hitting wall");
            mecanumDrivetrain.strafeLeftByTime(this, AUTONOMOUS_SPEED, 1.8);
            sleep(300);
            Log.d("FTC", "After hitting wall");
            mecanumDrivetrain.driveByGyro(75, mecanumDrivetrain.DIRECTION_FORWARD, AUTONOMOUS_SPEED,90);
            sleep(300);
            //mecanumDrivetrain.driveByGyro(10, mecanumDrivetrain.DIRECTION_STRAFE_RIGHT, AUTONOMOUS_SPEED,0);
            /*mecanumDrivetrain.driveByGyro(9, mecanumDrivetrain.DIRECTION_STRAFE_LEFT, AUTONOMOUS_SPEED, 0);
            mecanumDrivetrain.driveByGyro(20, mecanumDrivetrain.DIRECTION_REVERSE, AUTONOMOUS_SPEED, 0);
            //drop cube
            mecanumDrivetrain.driveByGyro(18, mecanumDrivetrain.DIRECTION_STRAFE_RIGHT, AUTONOMOUS_SPEED, 0);
            mecanumDrivetrain.driveByGyro(5, mecanumDrivetrain.DIRECTION_REVERSE, AUTONOMOUS_SPEED, 0);
           
            sleep(200);
            /*This distance depends on if we are going over the barrier or through the gap*/
            /*mecanumDrivetrain.driveByGyro(3, mecanumDrivetrain.DIRECTION_FORWARD, AUTONOMOUS_SPEED, 0);
            sleep(200);
            mecanumDrivetrain.gyroTurn(90,0.3,this);
            sleep (200);
            mecanumDrivetrain.driveByGyro(21, mecanumDrivetrain.DIRECTION_STRAFE_RIGHT, AUTONOMOUS_SPEED, 0);

            telemetry.addData("end","");
            telemetry.update();
            telemetry.addData("end","");
            telemetry.update();

             */
        }


    }

    /**
     * Initialize the Vuforia localization engine.
     **/

    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 320;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
    }
}
