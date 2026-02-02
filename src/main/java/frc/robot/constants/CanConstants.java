package frc.robot.constants;

import com.ctre.phoenix6.CANBus;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

public class CanConstants {

    public static CANBus roborioBus = new CANBus("rio");

    //Turret
    public static int ShootingMotorUp = 17;
    public static int ShootingMotorDown = 18;
    public static int HoodMotor = 21;
    public static int TurretSpin = 22;

    public static double OneTurnToDegrees = 10; // gives you the amount that one turn is equle too calc with 360 / amount of turns

    public static Translation2d HubPosRed = new Translation2d(11.980,4.070);
    public static Translation2d HubPosBlue = new Translation2d(4.642,4.070);

    public static Double MaxTurnAngle = 360.0;

    //Storage
    public static int StorageDown = 16;
    public static int StorageUp = 20;

    //Sucking
    public static int SuckingArmMotor = 14;
    public static int suckingMotor = 15;

    public static double closeSate = -15.528;
    public static double openState = 0.828;

    //Elevator
    public static int ElevatorMotor = 16;


    // Gyro
    public static int Pigeon = 13;
}
