package frc.robot.constants;

import com.ctre.phoenix6.CANBus;

public class CanConstants {

    public static CANBus roborioBus = new CANBus("rio");

    //Turret
    public static int ShootingMotor1;
    public static int ShootingMotor2;
    public static int TurningMotor;
    public static int TurningCancoder;

    //Sucking

    // Gyro
    public static int Pigeon = 13;
}
