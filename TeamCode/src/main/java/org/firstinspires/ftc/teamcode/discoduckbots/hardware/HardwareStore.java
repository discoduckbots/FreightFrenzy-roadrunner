package org.firstinspires.ftc.teamcode.discoduckbots.hardware;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class HardwareStore {
    private MecanumDrivetrain mecanumDrivetrain;
    private Intake intake;
    private Shooter shooter;
    private WobbleMover wobbleMover;
    private IMU imu;
    private ColorSensor colorSensor = null;
    private TouchSensor touchSensor = null;

    public DcMotorEx frontLeft ;
    public DcMotorEx frontRight ;
    public DcMotorEx backRight ;
    public DcMotorEx backLeft ;
    public DcMotor carouselSpinner ;
    public Servo intakePusher;

    public HardwareStore(HardwareMap hardwareMap, Telemetry telemetry, LinearOpMode opMode) {
         frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
         frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
         backRight = hardwareMap.get(DcMotorEx.class, "backRight");
         backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        // carouselSpinner = hardwareMap.get(DcMotor.class, "carouselMotor");
        //colorSensor = hardwareMap.get(ColorSensor.class, "sensor_color");
        //touchSensor = hardwareMap.get(TouchSensor.class, "sensor_touch");
        


       // DcMotorEx intakeMotor = hardwareMap.get(DcMotorEx.class, "intake");
        //intakePusher = hardwareMap.get(Servo.class, "intakePusher");
       /* intake = new Intake(intakeMotor, intakePusher);

        DcMotorEx shooterMotor = hardwareMap.get(DcMotorEx.class, "shooter");
        Servo pusherServo = hardwareMap.get(Servo.class, "pusher");
        shooter = new Shooter(shooterMotor, pusherServo);

        DcMotor wobbleMoverMotor = hardwareMap.get(DcMotor.class, "wobbleMover");
        Servo wobbleGrabber = hardwareMap.get(Servo.class, "wobbleGrabber");
        wobbleMover = new WobbleMover(wobbleMoverMotor, wobbleGrabber);

*/

        BNO055IMU gyro = hardwareMap.get(BNO055IMU.class, "imu");
        imu = new IMU(gyro);
        imu.initialize();

        mecanumDrivetrain = createDrivetrain(telemetry, opMode, imu, colorSensor, frontLeft, frontRight, backLeft, backRight);
    }

    protected MecanumDrivetrain createDrivetrain(Telemetry telemetry,
                                 LinearOpMode opMode,
                                 IMU imu,
                                 ColorSensor colorSensor,
                                                 DcMotorEx frontLeft,
                                                 DcMotorEx frontRight,
                                                 DcMotorEx backLeft,
                                                 DcMotorEx backRight){
        return new MecanumDrivetrain(telemetry, opMode, imu, colorSensor, frontLeft, frontRight, backLeft, backRight);
    }

    public MecanumDrivetrain getMecanumDrivetrain() {
        return mecanumDrivetrain;
    }

    public Intake getIntake() {
        return intake;
    }

    public Shooter getShooter() {
        return shooter;
    }

    public WobbleMover getWobbleMover() {
        return wobbleMover;
    }

    public IMU getImu(){
        return imu;
    }

    public ColorSensor getColorSensor(){
        return colorSensor;
    }

    public TouchSensor getTouchSensor() { return touchSensor; }

    public DcMotor getCarouselMotor() {
        return carouselSpinner;
    }
}
