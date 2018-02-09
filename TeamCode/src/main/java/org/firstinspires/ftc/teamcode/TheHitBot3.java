package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by luckm on 1/5/2018.
 */
@TeleOp(name = "TheHitBot 2.2", group = "Run")
@Disabled
public class TheHitBot3 extends LinearOpMode
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

        waitForStart();

        runtime.reset();

        while (opModeIsActive())
        {
            telemetry.addData("Status", "Do you know the way??");  //Don't worry about it
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

            robot.leftMW.setPower           (0.5 * gamepad2.right_stick_y + 0.25 * gamepad2.right_stick_x);
            robot.rightMW.setPower          (0.5 * gamepad2.right_stick_y - 0.25 * gamepad2.right_stick_x);

            robot.liftmotor.setPower   (0.5 * gamepad2.left_stick_y);
        }
    }
}
