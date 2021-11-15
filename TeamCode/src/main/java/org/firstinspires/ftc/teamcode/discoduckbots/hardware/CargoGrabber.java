package org.firstinspires.ftc.teamcode.discoduckbots.hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class CargoGrabber {

    private DcMotor cargoMotor;
    private Servo cargoGrabber;

    public CargoGrabber(DcMotor wobbleMoverMotor, Servo wobbleGrabber) {
        this.cargoMotor = wobbleMoverMotor;
        this.cargoGrabber = wobbleGrabber;

    }
    public void drop(LinearOpMode opmode) {
        cargoMotor.setPower(-1.0);
        opmode.sleep(2000);
        cargoMotor.setPower(0);
        release();
    }
    public void dropByEncoder(int revolutions){
        cargoMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        cargoMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        cargoMotor.setTargetPosition(cargoMotor.getCurrentPosition() + revolutions);

        while (cargoMotor.getTargetPosition() > cargoMotor.getCurrentPosition()){
            cargoMotor.setPower(1.0);
        }
        cargoMotor.setPower(0.0);
        release();
    }

    public void lowerByEncoder(int revolutions){
        cargoMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        cargoMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        cargoMotor.setTargetPosition(cargoMotor.getCurrentPosition() + revolutions);

        while (cargoMotor.getTargetPosition() > cargoMotor.getCurrentPosition()){
            cargoMotor.setPower(1.0);
        }
        cargoMotor.setPower(0.0);
    }

    public void grabAndLiftByEncoder(int revolutions, LinearOpMode opMode){
        grab();
        opMode.sleep(500);
        cargoMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        cargoMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        cargoMotor.setTargetPosition(cargoMotor.getCurrentPosition() + revolutions);

        while (cargoMotor.getTargetPosition() > cargoMotor.getCurrentPosition()){
            cargoMotor.setPower(1.0);
        }

        cargoMotor.setPower(0.0);

    }

    public void liftByEncoder(int revolutions){
        cargoMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        cargoMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        cargoMotor.setTargetPosition(cargoMotor.getCurrentPosition() + revolutions);

        while (cargoMotor.getTargetPosition() > cargoMotor.getCurrentPosition()){
            cargoMotor.setPower(1.0);
        }

        cargoMotor.setPower(0.0);

    }

    public void liftInch(LinearOpMode opmode) {
        cargoMotor.setPower(1.0);
        opmode.sleep(325);
        cargoMotor.setPower(0);
    }
    public void dropLift(LinearOpMode opmode) {
        cargoMotor.setPower(-1.0);
        opmode.sleep(2000);
        cargoMotor.setPower(0);
        release();
        cargoMotor.setPower(1.0);
        opmode.sleep(1000);
        cargoMotor.setPower(0);
    }

    public void lower(double speed) {
        cargoMotor.setPower(-1 * speed);
    }

    public void lift(double speed) {
        cargoMotor.setPower(speed);
    }

    public void stop() {
        cargoMotor.setPower(0);
    }

    public void grab() {
        cargoGrabber.setDirection(Servo.Direction.REVERSE);
        cargoGrabber.setPosition(0);
    }

    public void release() {
        cargoGrabber.setDirection(Servo.Direction.REVERSE);
        cargoGrabber.setPosition(0.4);

    }
}