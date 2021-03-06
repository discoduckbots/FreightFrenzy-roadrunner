/* Copyright (c) 2017 FIRST. All rights reserved.
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

package org.firstinspires.ftc.teamcode.discoduckbots.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.discoduckbots.hardware.HardwareStore;
import org.firstinspires.ftc.teamcode.discoduckbots.hardware.Intake;
import org.firstinspires.ftc.teamcode.discoduckbots.hardware.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode.discoduckbots.hardware.Shooter;
import org.firstinspires.ftc.teamcode.discoduckbots.hardware.WobbleMover;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Mecanum OpMode - Kathir", group="Linear Opmode")
public class MecanumDrivetrainTeleOpKathirControls extends LinearOpMode {

    private static double THROTTLE = 0.55;
    private static double intakeSpeed = .81;
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private MecanumDrivetrain mecanumDrivetrain = null;
    private Intake intake = null;
    private Shooter shooter = null;
    private WobbleMover wobbleMover = null;

    @Override
    public void runOpMode() {
        HardwareStore hardwareStore = new HardwareStore(hardwareMap, telemetry, this);
        mecanumDrivetrain = hardwareStore.getMecanumDrivetrain();
        intake = hardwareStore.getIntake();
        shooter = hardwareStore.getShooter();
        wobbleMover = hardwareStore.getWobbleMover();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Pusher Servo Position: ", shooter.getPusherServo().getPosition());
            telemetry.update();

            /* Gamepad 1 */
            mecanumDrivetrain.drive(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x, THROTTLE);

            if (gamepad1.a) {
                intake.intake(intakeSpeed);
            } else if (gamepad1.b) {
                intake.outtake();
            } else if (gamepad1.right_bumper) {
                intake.stop();
            }

            if (gamepad1.y) {
                intake.pushRing();
            } else if (gamepad1.x) {
                intake.resetPusher();
            }

            /* Gamepad 2 */
            if (gamepad2.right_trigger > 0) {
                shooter.setPowerForHighGoal();
            } else if (gamepad2.left_trigger > 0) {
                shooter.setPowerForPowerShot();
            }

            if (gamepad2.y) {
                shooter.pushRing();
            } else if (gamepad2.x) {
                shooter.resetPusher();
            }

            if (gamepad2.dpad_down) {
                wobbleMover.lower(1);
            } else if (gamepad2.dpad_up) {
                wobbleMover.lift(1);
            } else {
                wobbleMover.stop();
            }

            if (gamepad2.left_bumper) {

                wobbleMover.grab();
            }
            if (gamepad2.right_bumper) {
                wobbleMover.release();
            }
            if (gamepad1.left_trigger > 0) {
                THROTTLE = .6;
            }
            if (gamepad1.right_trigger > 0) {
                THROTTLE = .5;
            }
        }

        telemetry.addData("MecanumDrivetrainTeleOp", "Stopping");

        shutDown();
    }

    private void shutDown(){
        mecanumDrivetrain.stop();
        intake.stop();
        shooter.stop();
    }
}