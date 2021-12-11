package org.firstinspires.ftc.teamcode.discoduckbots.hardware;

import android.util.Log;

import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class DuckDetector {
    DistanceSensor distanceSensor;

    public DuckDetector(DistanceSensor sensor) {
        distanceSensor = sensor;
    }

    public boolean isDuckPresent(int distanceSensorNum) {

        double distance = distanceSensor.getDistance(DistanceUnit.CM);
        Log.d("FTC", "Distance : " + distance);
        int maxValue = 5;
        if (distanceSensorNum == 2){
            maxValue = 8;
        }
        Log.d("FTC", "max value : " + maxValue + " distanceNum " + distanceSensorNum);
        if ( distance < maxValue) {
            Log.d("FTC", "found duck");
            return true;
        }else {
            return false;
        }
    }
}
