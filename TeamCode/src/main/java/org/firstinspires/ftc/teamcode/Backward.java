package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by luckm on 1/24/2018.
 */
@Autonomous (name = "Backward", group = "z")
public class Backward extends LinearOpMode
{
    HitBotHW robot   = new HitBotHW();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        waitForStart();
        robot.leftmotorF.setPower(1);
        robot.leftmotorB.setPower(1);
        robot.rightmotorF.setPower(1);
        robot.rightmotorB.setPower(1);
    }
}
