package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import com.qualcomm.robotcore.hardware.ColorSensor;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.SwitchableLight;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Jessica on 10/3/2017.
 */

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "colorSensor" , group = "TOComp")

public class colorSensor extends OpMode{

        private DcMotor FRight;
        private DcMotor BRight;
        private DcMotor BLeft;
        private DcMotor FLeft;
        private DcMotor RaiseArmThing;

        private Servo servoGlyph;
        private Servo servoColor;

        ColorSensor color_sensor;

        private float SERVO_LATCH_UP = (float) 1.0;
        private float SERVO_LATCH_DOWN = (float) 0.0;


    @Override
    public void init() {

        FLeft = hardwareMap.dcMotor.get("frontLeft");
        FRight = hardwareMap.dcMotor.get("frontRight");
        BLeft = hardwareMap.dcMotor.get("backLeft");
        BRight = hardwareMap.dcMotor.get("backRight");
        RaiseArmThing = hardwareMap.dcMotor.get("Arm");

        servoGlyph = hardwareMap.servo.get("servoGlyph");
        servoColor = hardwareMap.servo.get("servoColor");

        color_sensor = hardwareMap.colorSensor.get("color");

        servoColor.scaleRange(.54, 1);


    }

    @Override
    public void loop() {

        color_sensor.red();   // Red channel value
        color_sensor.green(); // Green channel value
        color_sensor.blue();  // Blue channel value

        color_sensor.alpha(); // Total luminosity
        color_sensor.argb();  // Combined color value

        //while ((80 < color_sensor.red()) && (color_sensor.red() < 98)) { // number less than && number greater than
        while (20 < color_sensor.red()){
            servoColor.setPosition(SERVO_LATCH_DOWN);
        }

        //while ((64 < color_sensor.blue()) && (color_sensor.blue() < 79)) {
        while (20 < color_sensor.blue()){
            servoColor.setPosition(SERVO_LATCH_UP);
        }

        telemetry.addData("colorSensorAlpha", color_sensor.alpha());
        telemetry.addData("colorSensorRed", color_sensor.red());
        telemetry.addData("colorSensorBlue", color_sensor.blue());

    }
}
