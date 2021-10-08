package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@TeleOp(group = "drive")
public class CarouselTestSpinner extends LinearOpMode {
    private DcMotor carouselMotor;

    @Override
    public void runOpMode() throws InterruptedException {
        carouselMotor = (DcMotor)hardwareMap.get("carouselMotor");

        waitForStart();

        while (!isStopRequested()) {
//            if(gamepad1.a) {
//                carouselMotor.setPower(1.0);
//            }
//            else if(gamepad1.a) {
//                carouselMotor.setPower(1.0);
//            }
//            else{
//                carouselMotor.setPower(0.0);
//            }


            if (gamepad1.left_trigger > 0){
                carouselMotor.setPower(gamepad1.left_trigger);
                telemetry.addData("power", gamepad1.left_trigger);
            }
            else{
                carouselMotor.setPower(0);
            }

            telemetry.update();
        }
    }
}

