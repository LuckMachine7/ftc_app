package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by luckm on 12/29/2017.
 */

public class HitBotHW
{
    public DcMotor leftmotorB = null;
    public DcMotor leftmotorF = null;
    public DcMotor rightmotorB = null;
    public DcMotor rightmotorF = null;

    public DcMotor liftmotor = null;

    public CRServo leftSW = null;
    public CRServo rightSW = null;

    public DcMotor leftMW = null;
    public DcMotor rightMW = null;


    public Servo servoarm = null;
    public Servo servohand = null;

    //public ColorSensor sensorColor;
    //public DistanceSensor sensorDistance;

    public static double RED  = 30;
    public static double BLUE = 15;

    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    public HitBotHW() {

    }

    public void init(HardwareMap ahwMap)
    {
        hwMap = ahwMap;

        leftmotorF = hwMap.get(DcMotor.class,  "leftmotor2");
        leftmotorB = hwMap.get(DcMotor.class,  "leftmotor");
        rightmotorF = hwMap.get(DcMotor.class, "rightmotor2");
        rightmotorB = hwMap.get(DcMotor.class, "rightmotor");
        leftmotorF.setDirection(DcMotor.Direction.REVERSE);
        rightmotorF.setDirection(DcMotor.Direction.REVERSE);
        leftmotorB.setDirection(DcMotor.Direction.REVERSE);
        rightmotorB.setDirection(DcMotor.Direction.FORWARD);

        leftmotorF.setPower(0);
        rightmotorF.setPower(0);
        leftmotorB.setPower(0);
        rightmotorB.setPower(0);

        liftmotor = hwMap.get(DcMotor.class, "liftmotor");
        liftmotor.setDirection(DcMotor.Direction.REVERSE);
        liftmotor.setPower(0);

        leftSW = hwMap.get(CRServo.class, "LSW");
        rightSW = hwMap.get(CRServo.class, "RSW");
        leftSW.setDirection(CRServo.Direction.REVERSE);
        rightSW.setDirection(CRServo.Direction.FORWARD);
        leftSW.setPower(0);
        rightSW.setPower(0);

        leftMW = hwMap.get(DcMotor.class,  "LMW");
        rightMW = hwMap.get(DcMotor.class, "RMW");
        leftMW.setDirection(CRServo.Direction.REVERSE);
        rightMW.setDirection(CRServo.Direction.REVERSE);
        leftMW.setPower(0);
        rightMW.setPower(0);

        servoarm = hwMap.get(Servo.class, "servoarm");
        servohand = hwMap.get(Servo.class, "servohand");
        servoarm.setPosition(0.1);
        servohand.setPosition(0.8);

        //sensorColor = hwMap.get(ColorSensor.class, "sensor_color_distance");
        //sensorDistance = hwMap.get(DistanceSensor.class, "sensor_color_distance");
    }
}
