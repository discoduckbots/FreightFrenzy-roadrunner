package org.firstinspires.ftc.teamcode.discoduckbots.hardware;

import com.qualcomm.robotcore.hardware.Servo;

public class RingBlocker {


    private Servo ringBlocker;

    public RingBlocker(Servo ringBlocker) {
        this.ringBlocker = ringBlocker;

    }

    public void up() {
        ringBlocker.setPosition(0);
    }

    public void down() {
        ringBlocker.setPosition(0.58);
    }
}
