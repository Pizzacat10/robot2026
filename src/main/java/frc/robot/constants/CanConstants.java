package frc.robot.constants;

import com.ctre.phoenix6.CANBus;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

public class CanConstants {

    public static CANBus roborioBus = new CANBus("rio");

    //Turret
    public static int ShootingMotorUp = 15;
    public static int ShootingMotorDown = 14;
    public static int HoodMotor = 16;
    public static int TurretSpin = 13;

    public static double MaxPoseOfHood = 0.75;
    public static double OneTurnToDegrees = 0.01336; // gives you the amount that one turn is equle too calc with 360 / amount of turns
    public static double TurretOffset = -0.073;

    public static Translation2d HubPosRed = new Translation2d(11.980,4.070);
    public static Translation2d HubPosBlue = new Translation2d(4.642,4.070);

    public static Double MaxTurnAngle = 360.0;

    //Storage
    public static int StorageDown = 12;
    public static int StorageUp = 11;

    //Sucking
    public static int SuckingArmMotor = 10;
    public static int suckingMotor = 9;

    public static double closeSate = -15.528;
    public static double openState = 0.828;

    //Elevator
    public static int ElevatorMotor = 17;

    public static double HangArmOpen = 0;
    public static double HangArmClsoe = -179;
    public static double HangArmHang = -110;


    // Gyro
    public static int Pigeon = 22;
}
