package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;
@Autonomous(name="HitBot: Auto Drive By Gyro", group="test")
//@Disabled
public class GyroDriveTest extends LinearOpMode
{

    /* Declare OpMode members. */
    HitBotHW        robot   = new HitBotHW();   // Use HitBot hardware
    ModernRoboticsI2cGyro   gyro    = null;                    // Additional Gyro device

    static final double     COUNTS_PER_MOTOR_REV    = 1220 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                                                      (WHEEL_DIAMETER_INCHES * 3.1415);

    // These constants define the desired driving/control characteristics
    // The can/should be tweaked to suite the specific robot drive train.
    static final double     DRIVE_SPEED             = 0.7;     // Nominal speed for better accuracy.
    static final double     TURN_SPEED              = 0.5;     // Nominal half speed for better accuracy.

    static final double     HEADING_THRESHOLD       = 1 ;      // As tight as we can make it with an integer gyro
    static final double     P_TURN_COEFF            = 0.1;     // Larger is more responsive, but also less stable
    static final double     P_DRIVE_COEFF           = 0.15;     // Larger is more responsive, but also less stable


    @Override
    public void runOpMode() {

        /*
         * Initialize the standard drive system variables.
         * The init() method of the hardware class does most of the work here
         */
        robot.init(hardwareMap);
        gyro = (ModernRoboticsI2cGyro)hardwareMap.gyroSensor.get("gyro");

        // Ensure the robot it stationary, then reset the encoders and calibrate the gyro.
        robot.leftmotorB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightmotorB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftmotorF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightmotorF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Send telemetry message to alert driver that we are calibrating;
        telemetry.addData(">", "Calibrating Gyro");    //
        telemetry.update();

        gyro.calibrate();

        // make sure the gyro is calibrated before continuing
        while (!isStopRequested() && gyro.isCalibrating())  {
            sleep(50);
            idle();
        }

        telemetry.addData(">", "Robot Ready.");    //
        telemetry.update();

        robot.leftmotorB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightmotorB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.leftmotorF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightmotorF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // Wait for the game to start (Display Gyro value), and reset gyro before we move..
        while (!isStarted()) {
            telemetry.addData(">", "Robot Heading = %d", gyro.getIntegratedZValue());
            telemetry.update();
        }

        gyro.resetZAxisIntegrator();

        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)
        // Put a hold after each turn
        gyroDrive(DRIVE_SPEED, 48.0, 0.0);    // Drive FWD 48 inches
        gyroTurn( TURN_SPEED, -45.0);                // Turn  CCW to -45 Degrees
        gyroHold( TURN_SPEED, -45.0, 0.5);   // Hold -45 Deg heading for a 1/2 second
        gyroDrive(DRIVE_SPEED, 12.0, -45.0);  // Drive FWD 12 inches at 45 degrees
        gyroTurn( TURN_SPEED,  45.0);                // Turn  CW  to  45 Degrees
        gyroHold( TURN_SPEED,  45.0, 0.5);   // Hold  45 Deg heading for a 1/2 second
        gyroTurn( TURN_SPEED,   0.0);                // Turn  CW  to   0 Degrees
        gyroHold( TURN_SPEED,   0.0, 1.0);   // Hold  0 Deg heading for a 1 second
        gyroDrive(DRIVE_SPEED,-48.0, 0.0);    // Drive REV 48 inches

        telemetry.addData("Path", "Complete");
        telemetry.update();
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
