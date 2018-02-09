package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by luckm on 1/20/2018.
 */
@TeleOp (name = "The Hit Not 1.0", group = "test")
public class TheHitNot1 extends LinearOpMode
{
    HitBotHW robot   = new HitBotHW();
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException
    {
        robot.init(hardwareMap);

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


        }
    }
}
