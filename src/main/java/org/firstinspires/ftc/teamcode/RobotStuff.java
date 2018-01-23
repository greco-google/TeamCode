package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;
import static com.qualcomm.robotcore.util.Range.scale;
import static java.lang.Math.PI;

/**
 * Created by zahs on 9/28/2017.
 */

public class RobotStuff {
    private Telemetry telemetry = null;
    public DcMotor BLeft = null;
    public DcMotor FLeft = null;
    public DcMotor BRight = null;
    public DcMotor FRight = null;
    public Servo servoGlyph;
    public Servo servoColor;
    public Servo servoColor2;
    public Servo servoRelic;
    public DcMotor glyphArm;
    public DcMotor relicArmExtends;
    public DcMotor relicArmLift;
    public ColorSensor colorSensor;
    public ColorSensor colorSensor2;
//    public ColorSensor MR;
//     public TouchSensor touchExtendsBack;
//    public TouchSensor touchLiftBottom;
//    public TouchSensor touchLiftTop;
//    public TouchSensor touchLiftHome;
DigitalChannel touchExtendsBack;
    DigitalChannel touchLiftBottom;
    DigitalChannel touchLiftTop;
    DigitalChannel touchLiftHome;

    public float SERVO_LATCH_UP = (float) 1.0;
    public float SERVO_LATCH_DOWN = (float) 0.0;

    private DcMotor.Direction direction = null;
    private final double STOP = 0;
    private final double DEFAULT_FORWARD = 1;
    private final double DEFAULT_REVERSE = -.7;
    static final double ENCODER = 180;
    static final double MOTOR = 30;
    static final double GEAR1 = 20; //gear on wheel
    static final double GEAR2 = 40;
    static final double DIAMETER = 4;
    static final double NEVERREST_40 = 1120;
    double DRIVE_SPEED = 0.3;
    double TURN_SPEED =  0.5;
    static final double PULSE_PER_INCH= (float) (NEVERREST_40 * (GEAR1 / GEAR2) * (DIAMETER * PI));
    static final int PULSE_PER_REVOLUTION_NEVERREST_40 = 1120;

    public RobotStuff() {
    }

    public void init(HardwareMap hwmap, Telemetry telemetryIn, DcMotor.Direction direction) {
        this.telemetry = telemetryIn;
        BLeft = hwmap.dcMotor.get("BLeft");
        BRight = hwmap.dcMotor.get("BRight");
        FLeft = hwmap.dcMotor.get("FLeft");
        FRight = hwmap.dcMotor.get("FRight");
        servoGlyph = hwmap.servo.get("servoGlyph");
        servoColor = hwmap.servo.get("servoColor");
        servoColor2 = hwmap.servo.get("servoColor2");
        servoRelic = hwmap.servo.get("servoRelic");
        glyphArm = hwmap.dcMotor.get("glyphArm");
        relicArmExtends = hwmap.dcMotor.get("relicArmExtends");
        relicArmLift = hwmap.dcMotor.get("relicArmLift");
        colorSensor = hwmap.colorSensor.get("colorSensor");
        colorSensor2 = hwmap.colorSensor.get("colorSensor2");
//        MR = hwmap.colorSensor.get("MR");
//        touchExtendsBack = hwmap.touchSensor.get("touchExtendsBack");
//        touchLiftBottom = hwmap.touchSensor.get("touchLiftBottom");
//        touchLiftTop = hwmap.touchSensor.get("touchLiftTop");
//        touchLiftHome = hwmap.touchSensor.get("touchLiftHome");
        touchExtendsBack = hwmap.get(DigitalChannel.class, "touchExtendsBack");
        touchLiftBottom = hwmap.get(DigitalChannel.class, "touchLiftBottom");
        touchLiftTop = hwmap.get(DigitalChannel.class, "touchLiftTop");
        touchLiftHome = hwmap.get(DigitalChannel.class, "touchLiftHome");

        servoColor.scaleRange(.01, .47);
        servoColor2.scaleRange(.41, .69);
        servoGlyph.scaleRange(.38, .73);
        servoRelic.scaleRange(0, .8);

        BLeft.setPower(STOP);
        FLeft.setPower(STOP);
        BRight.setPower(STOP);
        FRight.setPower(STOP);
        BLeft.setDirection(REVERSE);
        BRight.setDirection(FORWARD);
        FLeft.setDirection(REVERSE);
        FRight.setDirection(FORWARD);

        BLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    public void loop() {}

    public void start() {}

    public void RobotStuff (float speed, float direction, float strafe) {
        float Magnitude = Math.abs(speed) + Math.abs(direction) + Math.abs(strafe);
        if (Magnitude < 1) {
            Magnitude = 1;
        }
        FLeft.setPower(scale(speed + direction + strafe, -Magnitude, Magnitude, -1, 1));
        FRight.setPower(scale(speed - direction - strafe, -Magnitude, Magnitude, -1, 1));
        BLeft.setPower(scale(speed + direction - strafe, -Magnitude, Magnitude, -1, 1));
        BRight.setPower(scale(speed - direction + strafe, -Magnitude, Magnitude, -1, 1));
    }

    public void LeftTurn (int distance) {
        FRight.setDirection(FORWARD);
        BRight.setDirection(FORWARD);
        BLeft.setDirection(REVERSE);
        FLeft.setDirection(REVERSE);
    }

    public void RightTurn (int distance) {
        FRight.setDirection(REVERSE);
        BRight.setDirection(REVERSE);
        BLeft.setDirection(FORWARD);
        FLeft.setDirection(FORWARD);
    }

    public void Forward (int distance) {
        BLeft.setPower(1);
        BRight.setPower(1);
        FLeft.setPower(1);
        FRight.setPower(1);
    }

    public void Backwards (int distance) {
        BLeft.setPower(-.5);
        BRight.setPower(-.5);
        FLeft.setPower(-.5);
        FRight.setPower(-.5);
    }

    public void Lift (int distance) {
        relicArmLift.setPower(-.2);
    }

    public void Out (int distance) {
        relicArmExtends.setPower(.2);
    }
    public void In (int distance) {
        relicArmExtends.setPower(-.2);
    }
    public void Up (int distance) {
        relicArmLift.setPower(-.2);
    }
    public void Down (int distance) {
        relicArmLift.setPower(-2);
    }

    public void Extends (int distance) {
        relicArmExtends.setPower(-.2);
    }

    public void Right (int distance) {
        BLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BLeft.setTargetPosition(BLeft.getCurrentPosition() + (distance * (int) PULSE_PER_INCH));
        FLeft.setTargetPosition(FLeft.getCurrentPosition() + (distance * (int)PULSE_PER_INCH));
        BRight.setTargetPosition(BRight.getCurrentPosition() + (distance * (int)PULSE_PER_INCH));
        FRight.setTargetPosition(FRight.getCurrentPosition() + (distance * (int)PULSE_PER_INCH));
            RobotStuff(0,0,-1);
    }

    public void Left (int distance) {
        BLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BLeft.setTargetPosition(BLeft.getCurrentPosition() + (distance * (int) PULSE_PER_INCH));
        FLeft.setTargetPosition(FLeft.getCurrentPosition() + (distance * (int)PULSE_PER_INCH));
        BRight.setTargetPosition(BRight.getCurrentPosition() + (distance * (int)PULSE_PER_INCH));
        FRight.setTargetPosition(FRight.getCurrentPosition() + (distance * (int)PULSE_PER_INCH));
        RobotStuff(0,0,1);
    }
    public void stop() {
        BLeft.setPower(STOP);
        FLeft.setPower(STOP);
        BRight.setPower(STOP);
        FRight.setPower(STOP);
    }
}
