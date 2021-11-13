package org.firstinspires.ftc.teamcode.discoduckbots.hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class CarouselSpinner {

    private final DcMotorEx carouselMotor;
    private final LinearOpMode opMode;

    private static final double MAX_ROTATIONS_PER_SECOND = 100;
    private static final double ENCODER_CYCLES_PER_ROTATION = 28;

    private static final double POWER = .8;



    public CarouselSpinner(DcMotorEx carouselMotor, LinearOpMode opMode) {
        this.carouselMotor = carouselMotor;
        this.opMode = opMode;

        carouselMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        carouselMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    private double getVelocity(double power) {
        return MAX_ROTATIONS_PER_SECOND * ENCODER_CYCLES_PER_ROTATION * power;
    }

    public void getOneDuckInAutonomous() {
        //carouselMotor.setVelocity(getVelocity(POWER));
        carouselMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        carouselMotor.setPower(POWER);
        opMode.sleep(1500);
        stop();
    }
    public void start() {
        carouselMotor.setPower(.6);
    }
    public void setForward() {
        carouselMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void setBackward() {
        carouselMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }


    public void stop(){
        carouselMotor.setPower(0);
    }

    public void getOneDuckInAutonomous2() {
        carouselMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        carouselMotor.setPower(POWER);
        opMode.sleep(1500);
        stop();
    }

}