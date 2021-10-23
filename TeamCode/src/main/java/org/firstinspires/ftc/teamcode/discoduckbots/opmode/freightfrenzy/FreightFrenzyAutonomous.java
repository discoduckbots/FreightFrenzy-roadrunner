package org.firstinspires.ftc.teamcode.discoduckbots.opmode.freightfrenzy;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.discoduckbots.hardware.HardwareStore;
import org.firstinspires.ftc.teamcode.discoduckbots.hardware.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode.discoduckbots.opmode.RingStackDetector;
import org.firstinspires.ftc.teamcode.discoduckbots.sensors.TensorFlow;
public class FreightFrenzyAutonomous {



    @com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="ffautonomous", group="Linear Opmode")
    public class AutonomousLowVoltage extends LinearOpMode {

        private ElapsedTime runtime = new ElapsedTime();
        private MecanumDrivetrain mecanumDrivetrain = null;


        TensorFlow tensorFlow = null;
        RingStackDetector ringStackDetector = null;

        private static final double AUTONOMOUS_SPEED = 0.4;
        private static final double STRAFE_SPEED = 0.5;
        private static final double ROTATION_SPEED = 0.4;
        private static final int WOBBLE_GRABBER_REVOLUTIONS = 6250;

        @Override
        public void runOpMode() throws InterruptedException {
            // initialize hardware
            HardwareStore hardwareStore = new HardwareStore(hardwareMap, telemetry, this);
            mecanumDrivetrain = hardwareStore.getMecanumDrivetrain();
            DcMotor carouselSpinner = hardwareStore.getCarouselMotor();

        /* tensorFlow = new TensorFlow();
        ringStackDetector = new RingStackDetector();
        tensorFlow.initTensorflow(telemetry, hardwareMap); */

            // wait for start
            waitForStart();
            runtime.reset();

        /*int number = ringStackDetector.getSlot(tensorFlow.detect(), telemetry);
        telemetry.addData("Tensorflow saw " + number + " rings", number);
        telemetry.update();*/
//start
            mecanumDrivetrain.driveByGyro(6, mecanumDrivetrain.DIRECTION_FORWARD, AUTONOMOUS_SPEED, 0);
            //tensorflow
            sleep(300);
            mecanumDrivetrain.driveByGyro(18, mecanumDrivetrain.DIRECTION_STRAFE_RIGHT, AUTONOMOUS_SPEED, 0);
            //spin carousel
            sleep(200);
            //carouselSpinner.setPower(0.7);
            sleep(1000);
            //carouselSpinner.setPower(0);
            mecanumDrivetrain.driveByGyro(36, mecanumDrivetrain.DIRECTION_STRAFE_LEFT, AUTONOMOUS_SPEED, 0);
            mecanumDrivetrain.driveByGyro(10, mecanumDrivetrain.DIRECTION_FORWARD, AUTONOMOUS_SPEED, 0);
            //drop cube
            sleep(200);
            /*This distance depends on if we are going over the barrier or through the gap*/
            mecanumDrivetrain.driveByGyro(6, mecanumDrivetrain.DIRECTION_REVERSE, AUTONOMOUS_SPEED, 0);
            mecanumDrivetrain.driveByGyro(42, mecanumDrivetrain.DIRECTION_STRAFE_LEFT, AUTONOMOUS_SPEED, 0);
        }
    }
}

