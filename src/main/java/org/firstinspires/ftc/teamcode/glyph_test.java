package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import static com.qualcomm.robotcore.hardware.Servo.Direction.FORWARD;
import static com.qualcomm.robotcore.hardware.Servo.Direction.REVERSE;

/**
 * Created by redsp on 1/18/2018.
 */
@TeleOp(name = "glyphtest", group = "test")
public class glyph_test extends OpMode {

    //Servos
    private Servo servoGlyphTL;
    private Servo servoGlyphTR;
    private Servo servoGlyphBL;
    private Servo servoGlyphBR;

    private final float SERVO_START = (float) 1;
    private final float SERVO_OPEN = (float) .3;
    private final float SERVO_CLOSED = (float) 0;

    @Override
    public void init() {
        servoGlyphTL = hardwareMap.servo.get("servoGlyphTL");
        servoGlyphTR = hardwareMap.servo.get("servoGlyphTR");
        servoGlyphBL = hardwareMap.servo.get("servoGlyphBL");
        servoGlyphBR = hardwareMap.servo.get("servoGlyphBR");

//Use Interface Module to find scale
        servoGlyphTL.scaleRange(0.0, 1.0);      //155    #2
        servoGlyphTL.setPosition(SERVO_START);
        servoGlyphTL.setDirection(FORWARD);

        servoGlyphTR.scaleRange(0.0, 1.0);      //80    #3
        servoGlyphTR.setPosition(SERVO_START);
        servoGlyphTR.setDirection(REVERSE);

        servoGlyphBL.scaleRange(0.0, 1.0);      //175  #1
        servoGlyphBL.setPosition(SERVO_START);
        servoGlyphBL.setDirection(FORWARD);

        servoGlyphBR.scaleRange(0.0, 1.0);      //80    #4
        servoGlyphBR.setPosition(SERVO_START);
        servoGlyphBR.setDirection(REVERSE);

        servoGlyphTL.setPosition(SERVO_START);
        servoGlyphTR.setPosition(SERVO_START);
        servoGlyphBL.setPosition(SERVO_START);
        servoGlyphBR.setPosition(SERVO_START);

    }

    @Override
    public void loop() {

        if (gamepad1.left_bumper) {
            servoGlyphTL.setPosition(SERVO_OPEN);
            servoGlyphTR.setPosition(SERVO_OPEN);
            servoGlyphBL.setPosition(SERVO_OPEN);
            servoGlyphBR.setPosition(SERVO_OPEN);
        } else if (gamepad1.right_bumper) {
            servoGlyphTL.setPosition(SERVO_CLOSED);
            servoGlyphTR.setPosition(SERVO_CLOSED);
            servoGlyphBL.setPosition(SERVO_CLOSED);
            servoGlyphBR.setPosition(SERVO_CLOSED);
        }

        if ((gamepad1.left_trigger > 0.5)) {
            servoGlyphBL.setPosition(SERVO_OPEN);
            servoGlyphBR.setPosition(SERVO_OPEN);
        } else if (gamepad1.right_trigger > 0.5) {
            servoGlyphBL.setPosition(SERVO_CLOSED);
            servoGlyphBR.setPosition(SERVO_CLOSED);
        }

    }
}
