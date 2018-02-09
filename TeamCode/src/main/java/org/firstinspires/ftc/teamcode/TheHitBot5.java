package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by luckm on 1/5/2018.
 */
@TeleOp(name = "TheHitBot 2.4", group = "Run")
//@Disabled
public class TheHitBot5 extends LinearOpMode
{
    HitBotHW robot   = new HitBotHW();
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException
    {
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

        telemetry.addData("Status", "Finding da wae");
        telemetry.update();

        waitForStart();

        runtime.reset();

        while (opModeIsActive())
        {
            telemetry.addData("Status", "He does not know da wae. Spit on him brothers!");  //Don't worry about it
            telemetry.update();

            //Gamepad1

            if (gamepad1.left_bumper)
                robot.leftmotorB.setPower   (gamepad1.left_stick_y * 0.5 + gamepad1.left_stick_x * 0.5);
            else
                robot.leftmotorB.setPower   (gamepad1.left_stick_y + gamepad1.left_stick_x);

            if (gamepad1.left_bumper)
                robot.leftmotorF.setPower   (gamepad1.left_stick_y * 0.5 - gamepad1.left_stick_x * 0.5);
            else
                robot.leftmotorF.setPower   (gamepad1.left_stick_y - gamepad1.left_stick_x);

            if (gamepad1.left_bumper)
                robot.rightmotorB.setPower  (gamepad1.right_stick_y * 0.5 - gamepad1.right_stick_x * 0.5);
            else
                robot.rightmotorB.setPower  (gamepad1.right_stick_y - gamepad1.right_stick_x);

            if (gamepad1.left_bumper)
                robot.rightmotorF.setPower  (gamepad1.right_stick_y * 0.5 + gamepad1.right_stick_x * 0.5);
            else
                robot.rightmotorF.setPower  (gamepad1.right_stick_y + gamepad1.right_stick_x);

            //Gamepad 2

            robot.leftSW.setPower           (gamepad2.right_stick_y + gamepad2.right_stick_x);
            robot.rightSW.setPower          (gamepad2.right_stick_y - gamepad2.right_stick_x);

            if (gamepad2.right_bumper)
                robot.leftMW.setPower       (0.25 * gamepad2.right_stick_y + 0.125 * gamepad2.right_stick_x);
            else
                robot.leftMW.setPower       (gamepad2.right_stick_y + gamepad2.right_stick_x);

            if (gamepad2.right_bumper)
                robot.rightMW.setPower      (0.25 * gamepad2.right_stick_y - 0.125 * gamepad2.right_stick_x);
            else
                robot.rightMW.setPower      (gamepad2.right_stick_y - gamepad2.right_stick_x);

            robot.liftmotor.setPower        (0.5 * gamepad2.left_stick_y - 0.125);

            if (gamepad2.a)
                robot.servoarm.setPosition(0.7);
            else
                robot.servoarm.setPosition(0.3);

            if (gamepad2.y)
                robot.servohand.setPosition(0.45);

            if (gamepad2.b)
                robot.servohand.setPosition(0.9);

            if (gamepad2.x)
                robot.servohand.setPosition(0.1);
        }
    }
}
