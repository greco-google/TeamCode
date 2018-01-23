package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_TO_POSITION;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_USING_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_WITHOUT_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.STOP_AND_RESET_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;
import static com.qualcomm.robotcore.util.Range.scale;

/**
 * Created by Joceline on 4/13/2017.
 */

@TeleOp(name = "Lily" , group = "TOComp")
public class Lily extends OpMode {

    private final int PULSE_PER_REVOLUTION_NEVEREST60 = 3360; //Put into shared file
    private final int PULSE_PER_REVOLUTION_NEVEREST40 = 1120;
    private final int PULSE_PER_REVOLUTION_NEVEREST20 = 560;

//Motors
    private DcMotor FRight;
    private DcMotor BRight;
    private DcMotor BLeft;
    private DcMotor FLeft;
    private DcMotor glyphArm;
    private DcMotor relicArmExtends;
    private DcMotor relicArmLift;

//Servos
    private Servo servoGlyph;
    private Servo servoColor;
    private Servo servoColor2;
    private Servo servoRelic;

//Sensors
    DigitalChannel digitalTouchTest;

    DigitalChannel touchExtendsBack;
    DigitalChannel touchLiftBottom;
    DigitalChannel touchLiftTop;
    DigitalChannel touchLiftHome;


//Servos Open Close/Up Down
    private float SERVO_LATCH_UP = (float) 1.0;
    private float SERVO_LATCH_DOWN = (float) 0.0;

//GlyphArm Up Down
    private double MAX_SPEED_UP = 0.5;
    private double MAX_SPEED_DOWN = -0.5;
//RelicArmOut,In,Up,Down

//Speeds
    private float relicSpeed = (float) 0.5;
    private final double MAX_SPEED = 0.5;

    @Override
    public void init() {
//Motors
        FLeft = hardwareMap.dcMotor.get("FLeft");
        FRight = hardwareMap.dcMotor.get("FRight");
        BLeft = hardwareMap.dcMotor.get("BLeft");
        BRight = hardwareMap.dcMotor.get("BRight");
        glyphArm = hardwareMap.dcMotor.get("glyphArm");
        relicArmLift = hardwareMap.dcMotor.get("relicArmLift");
        relicArmExtends = hardwareMap.dcMotor.get("relicArmExtends");

//Servos
        servoGlyph = hardwareMap.servo.get("servoGlyph");
        servoColor = hardwareMap.servo.get("servoColor");
        servoColor2 = hardwareMap.servo.get("servoColor2");
        servoRelic = hardwareMap.servo.get("servoRelic");

//Sensors
        digitalTouchTest = hardwareMap.get(DigitalChannel.class, "digitalTouchTest");

        touchExtendsBack = hardwareMap.get(DigitalChannel.class, "touchExtendsBack");
        touchLiftBottom = hardwareMap.get(DigitalChannel.class, "touchLiftBottom");
        touchLiftTop = hardwareMap.get(DigitalChannel.class, "touchLiftTop");
        touchLiftHome = hardwareMap.get(DigitalChannel.class, "touchLiftHome");

//Use Interface Module to find scale
        servoGlyph.scaleRange(.28, .62);
        servoGlyph.setPosition(SERVO_LATCH_UP);
//
        servoColor.scaleRange(.01, .47);
        servoColor.setPosition(SERVO_LATCH_UP);

        servoColor2.scaleRange(.41, .7);
        servoColor2.setPosition(SERVO_LATCH_UP);

        servoRelic.scaleRange(0, .74);
        servoRelic.setPosition(SERVO_LATCH_UP);

//Wheels
        FLeft.setMode(RUN_WITHOUT_ENCODER);
        FRight.setMode(RUN_WITHOUT_ENCODER);
        BLeft.setMode(RUN_WITHOUT_ENCODER);
        BRight.setMode(RUN_WITHOUT_ENCODER);

//Arms
        glyphArm.setMode(RUN_WITHOUT_ENCODER);
        //Makes relic manual while able to use encoders
        //relicArmExtends.setMode(STOP_AND_RESET_ENCODER);
        relicArmExtends.setMode(RUN_WITHOUT_ENCODER);
        //relicArmLift.setMode(STOP_AND_RESET_ENCODER);
        //****relicArmLift.setMode(RUN_WITHOUT_ENCODER);

//Wheel Directions
        BLeft.setDirection(REVERSE);
        FLeft.setDirection(REVERSE);
        FRight.setDirection(FORWARD);
        BRight.setDirection(FORWARD);

//Arm Directions
        glyphArm.setDirection(FORWARD);
        relicArmLift.setDirection(REVERSE);
        relicArmExtends.setDirection(REVERSE);
    }

    @Override
    public void init_loop() {

        super.init_loop();
        telemetry.addData("relicArmLiftBusy", relicArmLift.isBusy());
        telemetry.addData("relicArmExtendsBusy", relicArmExtends.isBusy());
        telemetry.addData("relicArmLiftPosition", relicArmLift.getCurrentPosition());
        telemetry.addData("relicArmExtendsPosition", relicArmExtends.getCurrentPosition());
        telemetry.addData("relicArmLiftTarget", relicArmLift.getTargetPosition());
        telemetry.addData("relicArmExtendsTarget", relicArmExtends.getTargetPosition());

    }

    @Override
    public void start(){

        telemetry.addData("relicArmLiftBusy", relicArmLift.isBusy());
        telemetry.addData("relicArmExtendsBusy", relicArmExtends.isBusy());
        telemetry.addData("relicArmLiftPosition", relicArmLift.getCurrentPosition());
        telemetry.addData("relicArmExtendsPosition", relicArmExtends.getCurrentPosition());
        telemetry.addData("relicArmLiftTarget", relicArmLift.getTargetPosition());
        telemetry.addData("relicArmExtendsTarget", relicArmExtends.getTargetPosition());

    }


    @Override
    public void loop() {

//Drive program start
        float speed = -gamepad1.right_stick_y;
        float direction = gamepad1.right_stick_x;
        float strafe = gamepad1.left_stick_x;

        float Magnitude = Math.abs(speed) + Math.abs(direction) + Math.abs(strafe);
        if (Magnitude < 1) {
            Magnitude = 1;
        }
        FLeft.setPower(scale(speed + direction - strafe, -Magnitude, Magnitude, -1, 1));
        FRight.setPower(scale(speed - direction + strafe, -Magnitude, Magnitude, -1, 1));
        BLeft.setPower(scale(speed + direction + strafe, -Magnitude, Magnitude, -1, 1));
        BRight.setPower(scale(speed - direction - strafe, -Magnitude, Magnitude, -1, 1));
//Drive program end

        //Gamepad button names
        boolean Gp1RBumper = gamepad1.right_bumper;
        boolean Gp1LBumper = gamepad1.left_bumper;

//Glyph
        if (gamepad2.left_bumper) {
            servoGlyph.setPosition(SERVO_LATCH_DOWN);
        } else if (gamepad2.right_bumper) {
            servoGlyph.setPosition(SERVO_LATCH_UP);
        }

//Raising Glyphs
        if (gamepad2.y) {
            glyphArm.setPower(MAX_SPEED_DOWN); //Max Speed up and down
        } else if (gamepad2.a) {
            glyphArm.setPower(MAX_SPEED_UP);
        } else {
            glyphArm.setPower(0);
        }

//Relic Servo
        if (Gp1LBumper) {
            servoRelic.setPosition(SERVO_LATCH_DOWN);
        } else if (Gp1RBumper) {
            servoRelic.setPosition(SERVO_LATCH_UP);
        }

        //if ( relicArmExtends.getCurrentPosition() < 6716) {

//Relic Arm Extends
            if (gamepad2.dpad_left){
                relicArmExtends.setPower(-1);
            }
            else {
                relicArmExtends.setPower(0);
            }

            if (touchExtendsBack.getState() == false){
                relicArmExtends.setPower(0);
            }
            else if (gamepad2.dpad_right){
                relicArmExtends.setPower(relicSpeed);
            }
            else {
                relicArmExtends.setPower(0);
            }

//Relic Arm Lift Down
            if (gamepad2.dpad_down) {
                relicArmLift.setPower(-relicSpeed);
            }
            else {
                relicArmLift.setPower(0);
            }

//            if (touchLiftBottom.getState() == false){
//                relicArmLift.setPower(0);
//            }
//            else if (gamepad2.dpad_up){
//                relicArmLift.setPower(7);
//            }
//            else {
//                relicArmLift.setPower(0);
//            }

//Relic Arm Lift Up
            if (gamepad2.dpad_up){
             relicArmLift.setPower(7);
            }
            else {
                relicArmLift.setPower(0);
            }

//            if (touchLiftTop.getState() == false){
//                relicArmLift.setPower(0);
//            }
//            else if (gamepad2.dpad_down){
//                relicArmLift.setPower(-relicSpeed);
//            }
//            else {
//                relicArmLift.setPower(0);
//            }

//Relic Arm Lift Home
            if (gamepad1.a == true){
                relicArmLift.setPower(relicSpeed);
            }
            else if (touchLiftHome.getState() == false){
                relicArmLift.setPower(0);
            }
            else {
                relicArmLift.setPower(0);
            }

            if (gamepad1.y){
                relicArmLift.setPower(-relicSpeed);
            }
            else if (touchLiftHome.getState() == false){
                relicArmLift.setPower(0);
            }
            else {
                relicArmLift.setPower(0);
            }
    //relic ends

//        telemetry.addData("relicExtendsEncoder = ", relicArmExtends.getCurrentPosition());
//        telemetry.addData("relicLiftEncoder = ", relicArmLift.getCurrentPosition());
        telemetry.addData("speed = ", gamepad1.right_stick_y);
        telemetry.addData("direction = ", gamepad1.right_stick_x);
        telemetry.addData("frontLeft = ", FLeft.getPower());
        telemetry.addData("frontRight = ", FRight.getPower());
        telemetry.addData("backLeft = ", BLeft.getPower());
        telemetry.addData("backRight = ", BRight.getPower());

        }
    }
//}
