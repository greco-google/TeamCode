package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;

/**
 * Created by zahs on 9/28/2017.
 */
@Autonomous (name = "B1" , group = "test")
public class B1 extends OpMode{
    //public static final String TAG = "Vuforia VuMark Sample"; int cameraMonitorViewId = hardwareMap.appContext.getResources().g

        //OpenGLMatrix lastLocation = null;

        //VuforiaLocalizer vuforia;

        ImportantStuff robot = new ImportantStuff();
        private enum State
        {
            STATE_CLOSE,
            STATE_UP,
            STATE_OUT,
            STATE_OUT2,
            STATE_DOWN,
            STATE_RELIC,
            STATE_IN,
            STATE_DRIVE2,
            STATE_UP2,
            STATE_IN2,
            STATE_DOWN2,
            STATE_SPIN,
            STATE_SERVO,
            STATE_INITIAL,
            STATE_SERVO2,
            STATE_COLOR,
            STATE_DRIVE,
            STATE_GLYPH,
            STATE_PARK,
            STATE_STOP
        }
        private State CurrentState;
        ElapsedTime Runtime = new ElapsedTime();


    public B1(){}

        @Override
        public void init() {
            robot.init(hardwareMap, telemetry);
            robot.robotStuff.servoRelic.scaleRange(0, .8);
            robot.robotStuff.servoColor.setPosition(robot.robotStuff.SERVO_LATCH_UP);
            robot.robotStuff.servoColor2.setPosition(robot.robotStuff.SERVO_LATCH_UP);
            robot.robotStuff.servoGlyph.setPosition(robot.robotStuff.SERVO_LATCH_DOWN);
//       etIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
//        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
//
//        parameters.vuforiaLicenseKey = "AVvRb+j/////AAAAGabuHrQd6kQ7l5YaZFAEzC6AmLVcjBBw1TKdOQhxTBsNH/lNWBs70Q3M5qMoXQyOrF1IPTU6moBI3dDs+e93BWS4bQ2/LdMj10COpd8u9BAXwXgjAuXa3gkmQ3FvTtz78/1ynZ25zMe0po8fOMttCGhcL/PU1eaDmvI2Cdr41Lj3LJc2ASoLw8wWbJ2I1kTHCDRw/63m6zQklqWPCTHfLMOmdGRE1lgP/ukUtmbvWXnUCGPxI1YiKkwIzPk7CyNeUnRSXiXmPHp3Gp5kNj+9YmjheT5pJe9QID2AR+a8fJQZIYcLkODLGj83xcwBJPXupOWGb/R9dRevEx4CJWa8SNuGrcqxGPDeVP4CfEpTf0B1";
//
//        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
//        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
    }

    @Override
    public void start() {
        super.start();

        Runtime.reset();
        newState(State. STATE_OUT);
    }

    @Override
    public void loop() {
        robot.robotStuff.colorSensor2.red();
        robot.robotStuff.colorSensor2.green();
        robot.robotStuff.colorSensor2.blue();
        robot.robotStuff.colorSensor2.alpha();
        robot.robotStuff.colorSensor2.argb();
        switch (CurrentState) {
            case STATE_OUT:
                if (Runtime.milliseconds() > 500) {
                    robot.robotStuff.relicArmExtends.setPower(1);
                    //robot.robotStuff.Out(1/10);
                    newState(State.STATE_DOWN);
                }
                break;
            case STATE_DOWN:
                if (Runtime.milliseconds() > 2280) {
                    robot.robotStuff.relicArmExtends.setPower(0);
                    robot.robotStuff.relicArmLift.setPower(.1);
                    //robot.robotStuff.Down(1/1000);
                    newState(State.STATE_RELIC);
                }
                break;
//            case STATE_OUT2:
//                if (Runtime.milliseconds() > 5) {
//                    robot.robotStuff.relicArmLift.setPower(0);
//                    robot.robotStuff.relicArmExtends.setPower(1);
//                    //robot.robotStuff.Out(1/10000000);
//                    newState(State.STATE_STOP);
//                }
//                break;
            case STATE_RELIC:
                if (Runtime.milliseconds() > 500) {
                    robot.robotStuff.relicArmLift.setPower(0);
                    robot.robotStuff.servoRelic.setPosition(robot.robotStuff.SERVO_LATCH_DOWN);
                    newState(State.STATE_UP);
                }
                break;
            case STATE_UP:
                if (Runtime.milliseconds() > 1000) {
                    robot.robotStuff.relicArmLift.setPower(1);
                    newState(State.STATE_STOP);
                }
                break;
//            case STATE_IN:
//                if (Runtime.milliseconds() > 1000) {
//                    robot.robotStuff.relicArmLift.setPower(0);
//                    robot.robotStuff.relicArmExtends.setPower(-.5);
//                    //robot.robotStuff.In(1/1000);
//                    newState(State.STATE_UP2);
//                }
//                break;
//            case STATE_UP2:
//                if (Runtime.milliseconds() > 250) {
//                    robot.robotStuff.relicArmLift.setPower(1);
//                    //robot.robotStuff.Up(1/100);
//                    newState(State.STATE_IN2);
//                }
//                break;
//            case STATE_IN2:
//                if (Runtime.milliseconds() > 500) {
//                    robot.robotStuff.In(1/10000);
//                    newState(State.STATE_SERVO);
//                }
//                break;
            case STATE_SERVO:
                if (Runtime.milliseconds() > 2000) {
                    robot.robotStuff.servoColor2.setPosition(robot.robotStuff.SERVO_LATCH_DOWN);
                    newState(State.STATE_INITIAL);
                }
                break;
            case STATE_INITIAL:
                if (Runtime.milliseconds() > 2000) {
                    while (20 < robot.robotStuff.colorSensor2.red()) {
                       robot.robotStuff.Backwards(1/1000);
                    }
                    while (20 < robot.robotStuff.colorSensor2.blue()) {
                        robot.robotStuff.Forward(1/1000);
                    }
                    newState(State.STATE_SERVO2);
                }
                break;
            case STATE_SERVO2:
                if (Runtime.milliseconds() > 50) {
                    robot.robotStuff.servoColor2.setPosition(robot.robotStuff.SERVO_LATCH_UP);
                    newState(State.STATE_COLOR);
                }
                break;
            case STATE_COLOR:
                if (Runtime.milliseconds() > 1000) {
                    //robot.robotStuff.Forward((int) .000000000000001);
                    robot.robotStuff.FLeft.setPower(1);
                    robot.robotStuff.FRight.setPower(1);
                    robot.robotStuff.BLeft.setPower(1);
                    robot.robotStuff.BRight.setPower(1);
                    newState(State.STATE_DRIVE);
                }
                break;
            case STATE_DRIVE:
                if (Runtime.milliseconds() > 500) {
                    robot.robotStuff.FLeft.setPower(-1);
                    robot.robotStuff.BLeft.setPower(-1);
                    robot.robotStuff.FRight.setPower(1);
                    robot.robotStuff.BRight.setPower(1);
                    newState(State.STATE_GLYPH);
                }
                break;
//            case STATE_DRIVE2:
//                if (Runtime.milliseconds() > 2000) {
//                    robot.robotStuff.Forward((int) .001);
//                    newState(State.STATE_GLYPH);
//                }
//                break;
            case STATE_GLYPH:
                if (Runtime.milliseconds() > 750) {
                    robot.robotStuff.servoGlyph.setPosition(robot.robotStuff.SERVO_LATCH_UP);
                    newState(State.STATE_STOP);
                }
                break;
//            case STATE_PARK:
//                robot.stop();
//                newState(State.STATE_STOP);
//                break;
            case STATE_STOP:
                if (Runtime.milliseconds() > 1000) {
                robot.stop(); }
                break;
        }  //
        telemetry.addData("state", CurrentState);
        telemetry.addData("FLeft", robot.robotStuff.FLeft.getCurrentPosition());
        telemetry.addData("FRight", robot.robotStuff.FRight.getCurrentPosition());


//        telemetry.addData("extends", robot.robotStuff.relicArmExtends.getCurrentPosition());
//        telemetry.addData("lift", robot.robotStuff.relicArmLift.getCurrentPosition());
    }
        String format(OpenGLMatrix transformationMatrix) {
            return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";

    }

    private void newState(State newState)
    {
        Runtime.reset();
        CurrentState = newState;
    }

    @Override
    public void stop() {
        super.stop();
        robot.stop();
    }
}
