package org.firstinspires.ftc.teamcode.discoduckbots.hardware;

import android.util.Log;

import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class DuckDetector {
    DistanceSensor distanceSensor;

    public DuckDetector(DistanceSensor sensor) {
        distanceSensor = sensor;
    }

    public boolean isDuckPresent() {

        double distance = distanceSensor.getDistance(DistanceUnit.CM);
        Log.d("FTC", "Distance : " + distance);
        if ( distance < 5) {
            return true;
        }else {
            return false;
        }
    }
}
