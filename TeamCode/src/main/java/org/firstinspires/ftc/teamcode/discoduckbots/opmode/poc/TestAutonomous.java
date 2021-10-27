package org.firstinspires.ftc.teamcode.discoduckbots.opmode.poc;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.discoduckbots.hardware.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode.discoduckbots.hardware.WobbleMover;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="TestAutonomous", group="Linear Opmode")
@Disabled
public class TestAutonomous extends LinearOpMode {


    private ElapsedTime runtime = new ElapsedTime();
    private MecanumDrivetrain mMecanumDrivetrain = null;
    private WobbleMover wobbleMover = null;
    @Override
    public void runOpMode() throws InterruptedException {
        // initialize hardware
        DcMotorEx frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        DcMotorEx frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        DcMotorEx backRight = hardwareMap.get(DcMotorEx.class, "backRight");
        DcMotorEx backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        mMecanumDrivetrain = new MecanumDrivetrain(telemetry, this, frontLeft, frontRight, backLeft, backRight);
        DcMotor wobbleMoverMotor = hardwareMap.get(DcMotor.class, "wobbleMover");
        Servo wobbleGrabber = hardwareMap.get(Servo.class, "wobbleGrabber");
        wobbleMover = new WobbleMover(wobbleMoverMotor, wobbleGrabber);
        // wait for start
        waitForStart();
        runtime.reset();

        autonomousByEncoder();
    }


    private void autonomousByEncoder() {
        double autonomousSpeed = 0.5;

//        mMecanumDrivetrain.forwardByTime(this, .1, 5);
        mMecanumDrivetrain.strafeLeftByTime(this, .1, 5);


    }
}
