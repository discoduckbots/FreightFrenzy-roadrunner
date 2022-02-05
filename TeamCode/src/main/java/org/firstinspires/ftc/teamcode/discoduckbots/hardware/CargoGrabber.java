package org.firstinspires.ftc.teamcode.discoduckbots.hardware;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class CargoGrabber {

    private DcMotor cargoMotor;
    private Servo cargoGrabber;
    private boolean resetInProgress = false;

    public CargoGrabber(DcMotor wobbleMoverMotor, Servo wobbleGrabber) {
        this.cargoMotor = wobbleMoverMotor;
        this.cargoGrabber = wobbleGrabber;
        cargoMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        cargoMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        cargoMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }
    public void drop(LinearOpMode opmode) {
        cargoMotor.setPower(-0.5);
        opmode.sleep(2000);
        cargoMotor.setPower(0);
        release();
    }

    public void print() {
        Log.d("ftc2", "cargoMotor " + cargoMotor.getCurrentPosition());
    }

    public void dropByEncoder(int revolutions){
        cargoMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        cargoMotor.setTargetPosition(cargoMotor.getCurrentPosition() + revolutions);
        cargoMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        while (cargoMotor.getTargetPosition() > cargoMotor.getCurrentPosition()){
            cargoMotor.setPower(0.5);
        }
        cargoMotor.setPower(0.0);
        release();
    }

    public void resetArm() {
        resetArm(850);
    }
    public void resetArmTeleop() {
        resetArm(0);
    }

    public void resetGrabberAsync() { resetArmAsync(850);}
    public void resetGrabberAsyncTeleop() {
        resetArmAsync(0);
    }

    public void resetArmAsync(int armPosition) {
        if(!resetInProgress) {
            resetInProgress = true;
            grab();

            //cargoMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            cargoMotor.setTargetPosition(armPosition);
            cargoMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            //cargoMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            cargoMotor.setPower(-0.75);
            Log.d("ftc-reset", "exiting resetArm");
        } else {
            Log.d("ftc-reset", "ignoring reset as in progress");
        }
    }
    public void stopIfNotBusy() {
        Log.d("ftc-reset", "curr pos: " +  cargoMotor.getCurrentPosition());
        if ( resetInProgress ) {
            if (cargoMotor.getCurrentPosition() <= 0) {
                Log.d("ftc-reset", "cargoMotor stopping async ");
                cargoMotor.setPower(0.0);
                cargoMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                resetInProgress = false;
            } else {
                Log.d("ftc-reset", "cargoMotor continuing power ");
                //cargoMotor.setPower(-0.75);
            }
        } else {
            cargoMotor.setPower(0);
        }

    }
    public void resetArm(int position){
        grab();

        //cargoMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        cargoMotor.setTargetPosition(position);
        cargoMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
       // cargoMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        cargoMotor.setPower(-0.75);


        while(cargoMotor.isBusy()) {
            Log.d("ftc", "cargoMotor in loop " + cargoMotor.getCurrentPosition());
        }
        Log.d("ftc", "cargoMotor out of loop ");
        cargoMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        cargoMotor.setPower(0.0);

    }

    public void lowerByEncoder(int revolutions){

        cargoMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        //cargoMotor.setTargetPosition(cargoMotor.getCurrentPosition() + revolutions);
        //cargoMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        cargoMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //cargoMotor.setPower(0.5);
        while (cargoMotor.getTargetPosition() > cargoMotor.getCurrentPosition()){
            cargoMotor.setPower(0.5);
            Log.d("FTC-Arm", "c: " +
                    cargoMotor.getCurrentPosition() + " t " + cargoMotor.getTargetPosition());
        }
        //cargoMotor.setPower(0.5);
       /* while(cargoMotor.isBusy()) {

        }*/
        cargoMotor.setPower(0.0);

    }


    public void grabAndLiftByEncoder(int revolutions, LinearOpMode opMode){
        grab();
        opMode.sleep(500);
        cargoMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        cargoMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        cargoMotor.setTargetPosition(cargoMotor.getCurrentPosition() + revolutions);

        while (cargoMotor.getTargetPosition() > cargoMotor.getCurrentPosition()){
            cargoMotor.setPower(0.5);
        }

        cargoMotor.setPower(0.0);

    }
/*
    public void liftByEncoder(int revolutions){

        cargoMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        cargoMotor.setTargetPosition(cargoMotor.getCurrentPosition() + revolutions);
        //cargoMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        cargoMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //cargoMotor.setPower(0.5);
        while (cargoMotor.getTargetPosition() > cargoMotor.getCurrentPosition()){
            cargoMotor.setPower(0.5);
            Log.d("FTC-Arm", "c: " +
                    cargoMotor.getCurrentPosition() + " t " + cargoMotor.getTargetPosition());
        }

        cargoMotor.setPower(0.0);

    }
*/
    public void liftByEncoder(int revolutions){

        cargoMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        cargoMotor.setTargetPosition(cargoMotor.getCurrentPosition() + revolutions);
        cargoMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //cargoMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        cargoMotor.setPower(0.5);


       while(cargoMotor.isBusy()) {

        }
        //cargoMotor.setPower(0.0);

    }

    public void liftInch(LinearOpMode opmode) {
        cargoMotor.setPower(0.5);
        opmode.sleep(325);
        cargoMotor.setPower(0);
    }
    public void dropLift(LinearOpMode opmode) {
        cargoMotor.setPower(-0.5);
        opmode.sleep(2000);
        cargoMotor.setPower(0);
        release();
        cargoMotor.setPower(0.5);
        opmode.sleep(1000);
        cargoMotor.setPower(0);
    }
    public void resetPositionAs0 () {
        cargoMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
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
        cargoGrabber.setPosition(0.50);

    }

    public void open() {
        cargoGrabber.setDirection(Servo.Direction.REVERSE);
        cargoGrabber.setPosition(1);
    }

    public double printServoValue(){
        return cargoGrabber.getPosition();
    }
}
