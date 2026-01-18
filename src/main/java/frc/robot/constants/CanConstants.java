package frc.robot.constants;

import com.ctre.phoenix6.CANBus;

public class CanConstants {

    public static CANBus roborioBus = new CANBus("rio");

    //Turret
    public static int ShootingMotor1;
    public static int ShootingMotor2;
    public static int TurningMotor;
    public static int TurningCancoder;
    public static int MaxAngle = 360;
    public static int MinAngle = -360;

    //Sucking

    // Gyro
    public static int Pigeon = 13;
}
