package org.firstinspires.ftc.teamcode.discoduckbots.hardware;

import android.util.Log;

import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.logging.Level;

public class DuckDetector {
    DistanceSensor distanceSensor1;
    DistanceSensor distanceSensor2;
    int SENSOR1 = 1;
    int SENSOR2 = 2;
    static public int LEVEL1 = 1757;
    static public int LEVEL2 = 2704;
    static public int LEVEL3 = 3773;

    double SENSOR1_MAX = 15;
    double SENSOR2_MAX = 5;
    public DuckDetector(DistanceSensor sensor1, DistanceSensor sensor2) {

        distanceSensor1 = sensor1;
        distanceSensor2 = sensor2;
    }
    /*
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
    } */
    public int getLevelRed() {
        double distance1 = distanceSensor1.getDistance(DistanceUnit.CM);
        double distance2 = distanceSensor2.getDistance(DistanceUnit.CM);
        Log.d("FTC-Duck4" , "d1: " + distance1 + " d2: " + distance2);
        if(sensor1Detected(distance1) == true && sensor2Detected(distance2)== false){
            return LEVEL1;
        } else if (sensor1Detected(distance1) ==false && sensor2Detected(distance2)==true) {
            return LEVEL2;
        } else return LEVEL3;

    }
    public int getLevel() {
        double distance1 = distanceSensor1.getDistance(DistanceUnit.CM);
        double distance2 = distanceSensor2.getDistance(DistanceUnit.CM);
        Log.d("FTC-Duck3" , "d1: " + distance1 + " d2: " + distance2);
        if(sensor1Detected(distance1) == false && sensor2Detected(distance2)== false){
            return LEVEL1;
        } else if (sensor1Detected(distance1) ==true && sensor2Detected(distance2)==false) {
            return LEVEL2;
        } else return LEVEL3;
    }

    public void print() {
        Log.d("FTC-Duck3", "distance1 " + distanceSensor1.getDistance(DistanceUnit.CM) +
                " distance2 " + distanceSensor2.getDistance(DistanceUnit.CM));
    }
    private boolean sensor1Detected(double distance) {
        return distance < SENSOR1_MAX;
    }
    private boolean sensor2Detected(double distance) {
        return distance < SENSOR2_MAX;
    }
}
