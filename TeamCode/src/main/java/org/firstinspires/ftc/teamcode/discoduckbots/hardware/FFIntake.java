package org.firstinspires.ftc.teamcode.discoduckbots.hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class FFIntake {

    private  DcMotor intakeMotor;
    private  LinearOpMode opMode;

    private static final double POWER = .6;



    public FFIntake(DcMotor intakeMotor, LinearOpMode opMode) {
        this.intakeMotor = intakeMotor;
        this.opMode = opMode;

    }


    public void intake() {
        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeMotor.setPower(POWER);
    }

    public void outtake(){
        intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeMotor.setPower(POWER);
    }

    public void stop(){
        intakeMotor.setPower(0);
    }

    }

