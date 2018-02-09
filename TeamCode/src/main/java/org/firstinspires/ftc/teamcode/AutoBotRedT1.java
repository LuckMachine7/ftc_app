package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by luckm on 1/9/2018.
 */
@Autonomous (name = "AutoBot Red Top 1.0", group = "red")
public class AutoBotRedT1 extends LinearOpMode
{
    HitBotHW robot   = new HitBotHW();
    private ElapsedTime runtime = new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV    = 1220 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.8;
    static final double     MID_SPEED              = 0.5;
    static final double     SLOW_SPEED              = 0.2;

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        telemetry.addData("Status", "Resetting Encoders");
        telemetry.update();

        robot.leftmotorF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightmotorF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftmotorB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightmotorB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

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

        waitForStart();

        telemetry.addData("Status", "Finding Da Wae");
        telemetry.update();

        encoderDrive(DRIVE_SPEED,-22,-22,-22,-22,2.0); //forward
        encoderDrive(DRIVE_SPEED,18,-18,-18,18 ,2.0); //left
        encoderDrive(DRIVE_SPEED,-15,-15,-15,-15,2.0); //forward


        robot.leftMW.setPower(-.75);
        robot.rightMW.setPower(-.75);

        encoderDrive(DRIVE_SPEED,5,5,5,5,2.5); //backward

        sleep(500);

        robot.leftMW.setPower(0);
        robot.rightMW.setPower(0);

        encoderDrive(MID_SPEED,  -4,-4,-4,-4,0.75); //forward
        encoderDrive(DRIVE_SPEED,3,3,3,3,1.0); //backward

      //References
        //encoderDrive(DRIVE_SPEED,  ,  ,  ,  , ); //backwards
        //encoderDrive(DRIVE_SPEED,- ,- ,- ,- , ); //forward
        //encoderDrive(DRIVE_SPEED,- ,  ,  ,- , ); //left
        //encoderDrive(DRIVE_SPEED,  ,- ,- ,  , ); //right

        telemetry.addData("Path", "You don't know da wae");
        telemetry.update();
    }
//Encoded Driving x4Wheels
    public void encoderDrive(double speed,
                             double leftFtInches, double leftBkInches, double rightFtInches, double rightBkInches, //motor locations
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
}
