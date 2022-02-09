package org.firstinspires.ftc.teamcode.discoduckbots.hardware;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

public class HardwareStore {
    private MecanumDrivetrain mecanumDrivetrain;
    private Intake intake;
    private FFIntake ffIntake;
    private CarouselSpinner carouselSpinner;
    private CargoGrabber cargoGrabber;
    private Shooter shooter;
    private WobbleMover wobbleMover;
    private IMU imu;
    private ColorSensor colorSensor = null;
    private TouchSensor touchSensor = null;
    private DistanceSensor distanceSensor = null;
    private DistanceSensor distanceSensor2 = null;
    private DistanceSensor blockSensor = null;
    private TouchSensor armStoppingSensor = null;
    private WebcamName webcamName = null;

    public DcMotorEx frontLeft ;
    public DcMotorEx frontRight ;
    public DcMotorEx backRight ;
    public DcMotorEx backLeft ;
    public DcMotor intakeMotor;
    public DcMotorEx carouselMotor;
    public DcMotor cargoMotor;
    public Servo cargoServo;
    public Servo intakePusher;
    public RevBlinkinLedDriver ledDriver;

    public HardwareStore(HardwareMap hardwareMap, Telemetry telemetry, LinearOpMode opMode) {
         frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
         frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
         backRight = hardwareMap.get(DcMotorEx.class, "backRight");
         backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
         intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
         carouselMotor = hardwareMap.get(DcMotorEx.class, "carouselMotor");
         cargoMotor = hardwareMap.get(DcMotor.class, "cargoMotor");
         cargoServo = hardwareMap.get(Servo.class, "cargoServo");
         distanceSensor = hardwareMap.get(DistanceSensor.class, "distanceSensor");
        distanceSensor2 = hardwareMap.get(DistanceSensor.class, "distanceSensor2");
        blockSensor = hardwareMap.get(DistanceSensor.class, "blockSensor");
        armStoppingSensor = hardwareMap.get(TouchSensor.class, "armSensor");
        webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");
       ledDriver = hardwareMap.get(RevBlinkinLedDriver.class, "led");
        ledDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.YELLOW);

        //colorSensor = hardwareMap.get(ColorSensor.class, "sensor_color");
        //touchSensor = hardwareMap.get(TouchSensor.class, "sensor_touch");
        
        carouselSpinner = new CarouselSpinner(carouselMotor, opMode);
        cargoGrabber = new CargoGrabber(cargoMotor, cargoServo);
        ffIntake = new FFIntake(intakeMotor, opMode);
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

    public TouchSensor getArmStoppingSensor() { return armStoppingSensor; }
    public DistanceSensor getDistanceSensor() { return distanceSensor; }
    public DistanceSensor getDistanceSensor2() { return distanceSensor2; }
    public DistanceSensor getBlockSensor() { return blockSensor; }

    public CarouselSpinner getCarouselSpinner() {
        return carouselSpinner;
    }

    public FFIntake getFFIntake() { return ffIntake; }

    public CargoGrabber getCargoGrabber() { return cargoGrabber; }

    public WebcamName getWebcamName() {return webcamName;}

    public RevBlinkinLedDriver getLedDriver() { return ledDriver;}

}
