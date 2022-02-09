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

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.discoduckbots.hardware.CargoGrabber;
import org.firstinspires.ftc.teamcode.discoduckbots.hardware.HardwareStore;
import org.firstinspires.ftc.teamcode.discoduckbots.hardware.MecanumDrivetrain;


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

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TestLed", group="Linear Opmode")
public class TestLed extends LinearOpMode {

  //  private static final double THROTTLE = 0.45;

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private MecanumDrivetrain mecanumDrivetrain = null;
    /* private Intake intake = null;
    private Shooter shooter = null;
    private WobbleMover wobbleMover = null;
    private TouchSensor touchSensor = null;
    private ColorSensor colorSensor = null; */
    private static final double AUTONOMOUS_SPEED = 0.6;
   private static final int LEVEL_1 = 3176;
   private static final int LEVEL_2 = 2111;
   private static final int LEVEL_3 = 1406;
    @Override
    public void runOpMode() {

      /*   intake = hardwareStore.getIntake();
        shooter = hardwareStore.getShooter();
        wobbleMover = hardwareStore.getWobbleMover();
        touchSensor = hardwareStore.getTouchSensor();
        colorSensor = hardwareStore.getColorSensor(); */

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        //DuckDetector duckDetector = new DuckDetector(hardwareStore.getDistanceSensor2());
        RevBlinkinLedDriver ledDriver = hardwareMap.get(RevBlinkinLedDriver.class, "led");
        ledDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.YELLOW);
        while (opModeIsActive()) {
            if (gamepad1.a) ledDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
            if (gamepad1.b) ledDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
            if (gamepad1.x) ledDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.CP1_HEARTBEAT_FAST);
            if (gamepad1.y) ledDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.BEATS_PER_MINUTE_PARTY_PALETTE);

        }
    }

    private void shutDown(){
        mecanumDrivetrain.stop();
        //intake.stop();
        //shooter.stop();
    }
}
