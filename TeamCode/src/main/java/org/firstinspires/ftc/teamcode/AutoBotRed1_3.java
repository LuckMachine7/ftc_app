package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Locale;

/**
 * Created by luckm on 1/9/2018.
 */
@Autonomous (name = "AutoBot Red Side 1.2", group = "red")
//@Disabled
public class AutoBotRed1_3 extends LinearOpMode
{
    HitBotHW robot   = new HitBotHW();
    private ElapsedTime runtime = new ElapsedTime();

    ModernRoboticsI2cGyro   gyro    = null;                    // Additional Gyro device

    public ColorSensor sensorColor;
    public DistanceSensor sensorDistance;


    static final double     COUNTS_PER_MOTOR_REV    = 1220 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                                                      (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 1.0;
    static final double     CONTROL_SPEED           = 0.75;
    static final double     TURN_SPEED              = 0.5;
    static final double     SLOW_SPEED              = 0.325;
    static final double     STOP_SPEED              = 0.25;

    static final double     HEADING_THRESHOLD       = 1 ;      // As tight as we can make it with an integer gyro
    static final double     P_TURN_COEFF            = 0.1;     // Larger is more responsive, but also less stable
    static final double     P_DRIVE_COEFF           = 0.15;    // Larger is more responsive, but also less stable

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        gyro = (ModernRoboticsI2cGyro)hardwareMap.gyroSensor.get("gyro");

        sensorColor = hardwareMap.get(ColorSensor.class, "sensor_color_distance");
        sensorDistance = hardwareMap.get(DistanceSensor.class, "sensor_color_distance");

        float hsvValues[] = {0F, 0F, 0F};

        final float values[] = hsvValues;

        final double SCALE_FACTOR = 255;

        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);


        telemetry.addData("Status", "Resetting Encoders");
        telemetry.update();

        robot.leftmotorF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightmotorF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftmotorB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightmotorB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        telemetry.addData(">", "Calibrating Gyro");    //Calibrating Gyro
        telemetry.update();

        gyro.calibrate();

        // make sure the gyro is calibrated before continuing
        while (!isStopRequested() && gyro.isCalibrating())  {
            sleep(50);
            idle();}

        robot.leftmotorF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightmotorF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.leftmotorB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightmotorB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Path0",  "Starting at %7d :%7d",
                robot.leftmotorB.getCurrentPosition(),
                robot.leftmotorF.getCurrentPosition(),
                robot.rightmotorB.getCurrentPosition(),
                robot.rightmotorF.getCurrentPosition());
        telemetry.update();

        while (!isStarted()) {
            telemetry.addData(">", "Robot Heading = %d", gyro.getIntegratedZValue());
            telemetry.update();
        }

        gyro.resetZAxisIntegrator();

//start game

        waitForStart();

        telemetry.addData("Status", "Finding Da Wae");
        telemetry.update();

        encoderDrive(SLOW_SPEED,2.65,2.65,2.65,2.65,0.25); //backwards

        telemetry.addData("Status", "Relic");
        telemetry.update();

        robot.servohand.setPosition(0.45);

        sleep(125);

        robot.servoarm.setPosition(0.8);

        sleep(750);

        if (sensorColor.red() > 30)
            telemetry.addData("Color", "Red");
        else
            telemetry.addData("color", "Not Red");

        if (sensorColor.red() > 30)
            robot.servohand.setPosition(0);
        else
            robot.servohand.setPosition(1);

        telemetry.update();


        sleep(1000);

        robot.servoarm.setPosition(0);

        telemetry.addData("Status", "Leaving Balancing stone");
        telemetry.update();

        encoderDrive(DRIVE_SPEED,-2,-2,-2,-2,1.75); //forward

        robot.servohand.setPosition(0.5);

        sleep(500);

        encoderDrive(DRIVE_SPEED,-40,40,40,-40,2.0); //left

        telemetry.addData("Status", "Capturing second glyph!        Suck ON");
        telemetry.update();

        robot.leftMW.setPower(1);
        robot.rightMW.setPower(1);
        robot.leftSW.setPower(1);
        robot.rightSW.setPower(1);

        gyroDrive(DRIVE_SPEED,-35,0.0);   //forward
        gyroDrive(DRIVE_SPEED,8,0.0);     //Backward
        encoderDrive(DRIVE_SPEED,10,-10,-10,10,0.75); //right
        gyroDrive(DRIVE_SPEED,-10,0.0);   //forward
        gyroDrive(DRIVE_SPEED,10,0.0);    //backwards

        telemetry.addData("Status", "Suck OFF");
        telemetry.update();

        robot.leftMW.setPower(0);
        robot.rightMW.setPower(0);

        sleep(250);

        telemetry.addData("Status", "Turning 180.0*");
        telemetry.update();

        encoderDrive(TURN_SPEED,-36,-36,36,36,2.0); //180* turn
        gyroHold(STOP_SPEED,180.0,1.75);    //Hold 180 turn

        telemetry.addData("Status", "Returning glyph 1");
        telemetry.update();

        gyroDrive(DRIVE_SPEED,-40,180.0);    //forward

        robot.leftSW.setPower(0);
        robot.rightSW.setPower(0);
        robot.leftMW.setPower(-.5);
        robot.rightMW.setPower(-.5);
        sleep(500);

        encoderDrive(DRIVE_SPEED,2,-2,-2,2,0.5);   //left
        encoderDrive(CONTROL_SPEED,7,7,7,7,0.5);   //backward

        gyroHold(STOP_SPEED,180,1.0); //Hold and set

        encoderDrive(DRIVE_SPEED,-6,-6,-6,-6,0.75); //forward
        encoderDrive(CONTROL_SPEED,8,8,8,8,0.5);   //backward

        gyroHold(STOP_SPEED,180,1.0); //Hold and set

        encoderDrive(TURN_SPEED,14,-14,-14,14,0.5);//left

        telemetry.addData("Status", "Returning glyph 2");
        telemetry.update();

        robot.rightSW.setPower(-1);
        robot.leftSW.setPower(-1);

        encoderDrive(DRIVE_SPEED,-8,-8,-8,-8,0.5); //forward
        gyroHold(STOP_SPEED,180,1.0); //Hold and set
        encoderDrive(CONTROL_SPEED,7,7,7,7,0.5); //backward

        robot.leftMW.setPower(0);
        robot.rightMW.setPower(0);

        telemetry.addData("Status", "Parking");
        telemetry.update();

        encoderDrive(DRIVE_SPEED,-7,-7,-7,-7,0.5); //forward
        encoderDrive(CONTROL_SPEED,7,7,7,7,0.5); //backward
        encoderDrive(DRIVE_SPEED,-3,3,3,-3,0.5); // right
        gyroHold(STOP_SPEED,180,1.0); //Hold and set



//end of Autonomous

        //Strafe & drive reference

         //encoderDrive(DRIVE_SPEED,  ,  ,  ,  , ); // backwards
         //encoderDrive(DRIVE_SPEED,- ,- ,- ,- , ); // forward
         //encoderDrive(DRIVE_SPEED,- ,  ,  ,- , ); // left
         //encoderDrive(DRIVE_SPEED,  ,- ,- ,  , ); // right

         //gyroDrive(DRIVE_SPEED,,);                // Drive Forward
         //gyroTurn( TURN_SPEED,-45.0);             // Turn  CCW to -45 Degrees
         //gyroHold( TURN_SPEED,-45.0, 0.5);        // Hold -45 Deg

        telemetry.addData("Path", "You don't know da wae. You need to have ebola to know da wae");
        telemetry.update();

        while (opModeIsActive()) {
            Color.RGBToHSV((int) (sensorColor.red() * SCALE_FACTOR),
                    (int) (sensorColor.green() * SCALE_FACTOR),
                    (int) (sensorColor.blue() * SCALE_FACTOR),
                    hsvValues);

            telemetry.addData("Distance (cm)",
                    String.format(Locale.US, "%.02f", sensorDistance.getDistance(DistanceUnit.CM)));
            telemetry.addData("Alpha", sensorColor.alpha());
            telemetry.addData("Red  ", sensorColor.red());
            telemetry.addData("Green", sensorColor.green());
            telemetry.addData("Blue ", sensorColor.blue());
            telemetry.addData("Hue", hsvValues[0]);

            relativeLayout.post(new Runnable() {
                public void run() {
                    relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
                }
            });

            telemetry.update();
        }
        relativeLayout.post(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.WHITE);
            }
        });
    }
    //Encoded Driving x4Wheels
    public void encoderDrive(double speed,
                             double leftFtInches, double leftBkInches, double rightFtInches, double rightBkInches,
                             double timeoutS) {
        int newBLeftTarget;
        int newBRightTarget;
        int newFLeftTarget;
        int newFRightTarget;

        if (opModeIsActive()) {

            newBLeftTarget = robot.leftmotorB.getCurrentPosition() + (int)(leftBkInches * COUNTS_PER_INCH);
            newBRightTarget = robot.rightmotorB.getCurrentPosition() + (int)(rightBkInches * COUNTS_PER_INCH);
            newFLeftTarget = robot.leftmotorF.getCurrentPosition() + (int)(leftFtInches * COUNTS_PER_INCH);
            newFRightTarget = robot.rightmotorF.getCurrentPosition() + (int)(rightFtInches * COUNTS_PER_INCH);
            robot.leftmotorB.setTargetPosition(newBLeftTarget);
            robot.rightmotorB.setTargetPosition(newBRightTarget);
            robot.leftmotorF.setTargetPosition(newFLeftTarget);
            robot.rightmotorF.setTargetPosition(newFRightTarget);

            robot.leftmotorB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightmotorB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.leftmotorF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightmotorF.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            runtime.reset();
            robot.leftmotorB.setPower(Math.abs(speed));
            robot.rightmotorB.setPower(Math.abs(speed));
            robot.leftmotorF.setPower(Math.abs(speed));
            robot.rightmotorF.setPower(Math.abs(speed));

            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.leftmotorB.isBusy() && robot.rightmotorB.isBusy() && robot.leftmotorF.isBusy() && robot.rightmotorF.isBusy())) {

                telemetry.addData("Path1",  "Running to %7d :%7d",newBLeftTarget, newFLeftTarget,  newBRightTarget, newFRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.leftmotorB.getCurrentPosition(),
                        robot.leftmotorF.getCurrentPosition(),
                        robot.rightmotorB.getCurrentPosition(),
                        robot.rightmotorF.getCurrentPosition());
                telemetry.update();
            }
            robot.leftmotorB.setPower(0);
            robot.rightmotorB.setPower(0);
            robot.leftmotorF.setPower(0);
            robot.rightmotorF.setPower(0);

            robot.leftmotorB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.rightmotorB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.rightmotorF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.leftmotorF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
    public void gyroDrive ( double speed,
                            double distance,
                            double angle) {

        int     newBkLeftTarget;
        int     newBkRightTarget;
        int     newFtLeftTarget;
        int     newFtRightTarget;

        int     moveCounts;
        double  max;
        double  error;
        double  steer;
        double  leftSpeed;
        double  rightSpeed;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            moveCounts = (int)(distance * COUNTS_PER_INCH);
            newBkLeftTarget = robot.leftmotorB.getCurrentPosition() + moveCounts;
            newBkRightTarget = robot.rightmotorB.getCurrentPosition() + moveCounts;
            newFtLeftTarget = robot.leftmotorF.getCurrentPosition() + moveCounts;
            newFtRightTarget = robot.rightmotorF.getCurrentPosition() + moveCounts;

            // Set Target and Turn On RUN_TO_POSITION
            robot.leftmotorB.setTargetPosition(newBkLeftTarget);
            robot.rightmotorB.setTargetPosition(newBkRightTarget);
            robot.leftmotorF.setTargetPosition(newFtLeftTarget);
            robot.rightmotorF.setTargetPosition(newFtRightTarget);

            robot.leftmotorB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightmotorB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.leftmotorF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightmotorF.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // start motion.
            speed = Range.clip(Math.abs(speed), 0.0, 1.0);
            robot.leftmotorB.setPower(speed);
            robot.rightmotorB.setPower(speed);
            robot.leftmotorF.setPower(speed);
            robot.rightmotorF.setPower(speed);

            // keep looping while we are still active, and BOTH motors are running.
            while (opModeIsActive() &&
                    (robot.leftmotorB.isBusy() && robot.rightmotorB.isBusy() && robot.leftmotorF.isBusy() && robot.rightmotorF.isBusy())) {

                // adjust relative speed based on heading error.
                error = getError(angle);
                steer = getSteer(error, P_DRIVE_COEFF);

                // if driving in reverse, the motor correction also needs to be reversed
                if (distance < 0)
                    steer *= -1.0;

                leftSpeed = speed - steer;
                rightSpeed = speed + steer;

                // Normalize speeds if either one exceeds +/- 1.0;
                max = Math.max(Math.abs(leftSpeed), Math.abs(rightSpeed));
                if (max > 1.0)
                {
                    leftSpeed /= max;
                    rightSpeed /= max;
                }

                robot.leftmotorB.setPower(leftSpeed);
                robot.rightmotorB.setPower(rightSpeed);
                robot.leftmotorF.setPower(leftSpeed);
                robot.rightmotorF.setPower(rightSpeed);

                // Display drive status for the driver.
                telemetry.addData("Err/St",  "%5.1f/%5.1f",  error, steer);
                telemetry.addData("Target",  "%7d:%7d",      newBkLeftTarget,  newBkRightTarget, newFtLeftTarget,  newFtRightTarget);
                telemetry.addData("Actual",  "%7d:%7d",      robot.leftmotorB.getCurrentPosition(),
                        robot.rightmotorB.getCurrentPosition(),
                        robot.leftmotorF.getCurrentPosition(),
                        robot.rightmotorF.getCurrentPosition());
                telemetry.addData("Speed",   "%5.2f:%5.2f",  leftSpeed, rightSpeed);
                telemetry.update();
            }

            // Stop all motion;
            robot.leftmotorB.setPower(0);
            robot.rightmotorB.setPower(0);
            robot.leftmotorF.setPower(0);
            robot.rightmotorF.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.leftmotorB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.leftmotorB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.rightmotorF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.rightmotorF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
    public void gyroTurn (  double speed, double angle) {

        // keep looping while we are still active, and not on heading.
        while (opModeIsActive() && !onHeading(speed, angle, P_TURN_COEFF)) {
            // Update telemetry & Allow time for other processes to run.
            telemetry.update();
        }
    }
    public void gyroHold( double speed, double angle, double holdTime) {

        ElapsedTime holdTimer = new ElapsedTime();

        // keep looping while we have time remaining.
        holdTimer.reset();
        while (opModeIsActive() && (holdTimer.time() < holdTime)) {
            // Update telemetry & Allow time for other processes to run.
            onHeading(speed, angle, P_TURN_COEFF);
            telemetry.update();
        }

        // Stop all motion;
        robot.leftmotorB.setPower(0);
        robot.rightmotorB.setPower(0);
        robot.leftmotorF.setPower(0);
        robot.rightmotorF.setPower(0);
    }
    boolean onHeading(double speed, double angle, double PCoeff) {
        double   error ;
        double   steer ;
        boolean  onTarget = false ;
        double leftSpeed;
        double rightSpeed;

        // determine turn power based on +/- error
        error = getError(angle);

        if (Math.abs(error) <= HEADING_THRESHOLD) {
            steer = 0.0;
            leftSpeed  = 0.0;
            rightSpeed = 0.0;
            onTarget = true;
        }
        else {
            steer = getSteer(error, PCoeff);
            rightSpeed  = speed * steer;
            leftSpeed   = -rightSpeed;
        }

        // Send desired speeds to motors.
        robot.leftmotorB.setPower(leftSpeed);
        robot.rightmotorB.setPower(rightSpeed);
        robot.leftmotorF.setPower(leftSpeed);
        robot.rightmotorF.setPower(rightSpeed);

        // Display it for the driver.
        telemetry.addData("Target", "%5.2f", angle);
        telemetry.addData("Err/St", "%5.2f/%5.2f", error, steer);
        telemetry.addData("Speed.", "%5.2f:%5.2f", leftSpeed, rightSpeed);

        return onTarget;
    }
    public double getError(double targetAngle) {

        double robotError;

        // calculate error in -179 to +180 range  (
        robotError = targetAngle - gyro.getIntegratedZValue();
        while (robotError > 180)  robotError -= 360;
        while (robotError <= -180) robotError += 360;
        return robotError;
    }
    public double getSteer(double error, double PCoeff) {
        return Range.clip(error * PCoeff, -1, 1);
    }

}
