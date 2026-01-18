package frc.robot.constants;

import com.ctre.phoenix6.CANBus;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

public class CanConstants {

    public static CANBus roborioBus = new CANBus("rio");

    //Turret
    public static int ShootingMotor1;
    public static int ShootingMotor2;
    public static int TurningMotor;
    public static int MaxAngle = 360;
    public static int MinAngle = -360;
    public static Translation2d HubPosRed = new Translation2d(11.980,4.070);
    public static Translation2d HubPosBlue = new Translation2d(4.642,4.070);

    //Sucking
    public static int ArmMotor;
    public static int suckingMotor;
    public static int swichdown;
    public static int swichup;

    // Gyro
    public static int Pigeon = 13;
}
