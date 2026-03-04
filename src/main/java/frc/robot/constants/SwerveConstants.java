package frc.robot.constants;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.utils.swerve.COTSTalonFXSwerveConstants;
import frc.robot.utils.swerve.SwerveModuleConstants;

public class SwerveConstants {
    public static COTSTalonFXSwerveConstants chosenModule =  //TODO: This must be tuned to specific robot
            COTSTalonFXSwerveConstants.SDS.MK5n.KrakenX60(COTSTalonFXSwerveConstants.SDS.MK5n.driveRatios.R1);

    /*can bus */
    public static CANBus canBus = new CANBus("rio");

    /* Drivetrain Constants */
    public static double wheelCircumference = chosenModule.wheelCircumference;

    /*swerve module position*/
    public static Translation2d frontLeftPos = new Translation2d(0.32, 0.34);
    public static Translation2d frontRightPos = new Translation2d(0.32,-0.34);
    public static Translation2d backLeftPos = new Translation2d(-0.32, 0.34);
    public static Translation2d backRightPos = new Translation2d(-0.32, -0.34);
    public static Translation2d[] modulesPositions = new Translation2d[4];

    /* Module Gear Ratios */
    public static double driveGearRatio = chosenModule.driveGearRatio;
    public static double angleGearRatio = chosenModule.angleGearRatio;

    /* Motor Inverts */
    public static InvertedValue angleMotorInvert = chosenModule.angleMotorInvert;
    public static InvertedValue driveMotorInvert = chosenModule.driveMotorInvert;

    /* Angle Encoder Invert */
    public static SensorDirectionValue canCoderInvert = chosenModule.cancoderInvert;

    /*Feedback Sensor Azimuth */
    public static FeedbackSensorSourceValue feedbackSensorSource = FeedbackSensorSourceValue.RotorSensor;

    /* Swerve Current Limiting */
    public static int angleContinuousCurrentLimit = 25;
    public static int anglePeakCurrentLimit = 40;
    public static double anglePeakCurrentDuration = 0.1;
    public static boolean angleEnableCurrentLimit = true;

    public static int driveStatorCurrentLimit = 60; //TODO: cheak when training
    public static boolean driveEnableStatorCurrentLimit = true;
    public static int driveContinuousCurrentLimit = 40;
    public static int drivePeakCurrentLimit = 60;
    public static double drivePeakCurrentDuration = 0.1;
    public static boolean driveEnableCurrentLimit = true;

    /* These values are used by the drive falcon to ramp in open loop and closed loop driving.
     * We found a small open loop ramp (0.25) helps with tread wear, tipping, etc */
    public static double openLoopRamp = 0.2;
    public static double closedLoopRamp = 0.2;

    /* Angle Motor PID Values */
    public static double angleKP = chosenModule.angleKP;
    public static double angleKI = chosenModule.angleKI;
    public static double angleKD = chosenModule.angleKD;

    /* Drive Motor PID Values */
    public static double driveKP = 3.0; //TODO: This must be tuned to specific robot
    public static double driveKI = 0.0;
    public static double driveKD = 0.0;
    public static double driveKF = 0.0;

    /* Heading PID Values */
    public static double HeadingKP = 4;
    public static double HeadingKI = 0.0;
    public static double HeadingKD = 0;
    public static double HeadingTolerence = 0;


    /* Drive Motor Characterization Values
     * Divide SYSID values by 12 to convert from volts to percent output for CTRE */
    public static double driveKS = (0.0); //TODO: This must be tuned to specific robot
    public static double driveKV = (0.0);
    public static double driveKA = (0.0);

    /*wheel parameters */
    public static double WheelRadius = 0.0508;
    public static double WheelCircumference = WheelRadius * 2 * Math.PI;

    /* Swerve Profiling Values */
    /**
     * Meters per Second
     */
    public static double maxSpeed = 5.2; //TODO: This must be tuned to specific robot
    /**
     * Radians per Second
     */
    public static double maxAngularVelocity = 10.0; //TODO: This must be tuned to specific robot

    /* Neutral Modes */
    public static NeutralMode angleNeutralMode = NeutralMode.Brake;
    public static NeutralMode driveNeutralMode = NeutralMode.Brake;

    private static Slot0Configs slot0Configs = new Slot0Configs().withKS(driveKS).withKV(driveKV).withKA(driveKA).withKP(driveKP).withKD(driveKD);
    //Rotation2d.fromRotations(0.14501953125 + (1.0/4.0)
    //Rotation2d.fromRotations(0.479736328125)
    //Rotation2d.fromRotations(-0.38134765625 - (3.0/4.0))
    //Rotation2d.fromRotations(-0.230224609375 - 0.5)
    public static SwerveModuleConstants FR = new SwerveModuleConstants(5, 6, 20, Rotation2d.fromDegrees(-39.375).rotateBy(Rotation2d.k180deg), slot0Configs, frontLeftPos);
    public static SwerveModuleConstants FL = new SwerveModuleConstants(7, 8, 21, Rotation2d.fromDegrees(-99.229), slot0Configs, frontRightPos);
    public static SwerveModuleConstants BR = new SwerveModuleConstants(3, 4, 18, Rotation2d.fromDegrees(-33.135).rotateBy(Rotation2d.k180deg), slot0Configs, backLeftPos);
    public static SwerveModuleConstants BL = new SwerveModuleConstants(1, 2, 19, Rotation2d.fromDegrees(-47.9), slot0Configs, backRightPos);

//    public String filepath = "/home/lvuser/natinst/ModuleOffsets.csv";

    public static TalonFXConfiguration driveTalonFXConfigs() {
        TalonFXConfiguration configs = new TalonFXConfiguration();
        /** Swerve Drive Motor Configuration */
        /* Motor Inverts and Neutral Mode */
        configs.MotorOutput.Inverted = chosenModule.driveMotorInvert;
        configs.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        /* Gear Ratio Config */
        configs.Feedback.SensorToMechanismRatio = chosenModule.driveGearRatio;

        /* Current Limiting */
        configs.CurrentLimits.StatorCurrentLimit = driveStatorCurrentLimit;
        configs.CurrentLimits.SupplyCurrentLimitEnable = driveEnableCurrentLimit;
        configs.CurrentLimits.SupplyCurrentLimit = driveContinuousCurrentLimit;
        configs.CurrentLimits.SupplyCurrentLowerLimit = drivePeakCurrentLimit;
        configs.CurrentLimits.SupplyCurrentLowerTime = drivePeakCurrentDuration;

        /* PID Config */
        configs.Slot0.kP = driveKP;
        configs.Slot0.kI = driveKI;
        configs.Slot0.kD = driveKD;

        /* Open and Closed Loop Ramping */
        configs.OpenLoopRamps.DutyCycleOpenLoopRampPeriod = openLoopRamp;
        configs.OpenLoopRamps.VoltageOpenLoopRampPeriod = openLoopRamp;

        configs.ClosedLoopRamps.DutyCycleClosedLoopRampPeriod = closedLoopRamp;
        configs.ClosedLoopRamps.VoltageClosedLoopRampPeriod = closedLoopRamp;
        return configs;
    }

    public static TalonFXConfiguration steerTalonFXConfigs() {
        TalonFXConfiguration configs = new TalonFXConfiguration();
        /** Swerve Angle Motor Configurations */
        /* Motor Inverts and Neutral Mode */
        configs.MotorOutput.Inverted = chosenModule.angleMotorInvert;
        configs.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        /* Gear Ratio and Wrapping Config */
        configs.Feedback.SensorToMechanismRatio = chosenModule.angleGearRatio;
        configs.Feedback.FeedbackSensorSource = feedbackSensorSource;
        configs.ClosedLoopGeneral.ContinuousWrap = true;

        /* Current Limiting */
        configs.CurrentLimits.SupplyCurrentLimitEnable = angleEnableCurrentLimit;
        configs.CurrentLimits.SupplyCurrentLimit = angleContinuousCurrentLimit;
        configs.CurrentLimits.SupplyCurrentLimit = anglePeakCurrentLimit;
        configs.CurrentLimits.SupplyCurrentLowerTime = anglePeakCurrentDuration;

        /* PID Config */
        configs.Slot0.kP = chosenModule.angleKP;
        configs.Slot0.kI = chosenModule.angleKI;
        configs.Slot0.kD = chosenModule.angleKD;
        return configs;
    }

    public static CANcoderConfiguration canCoderConfigs() {
        CANcoderConfiguration configs = new CANcoderConfiguration();
        /** Swerve CANCoder Configuration */
        configs.MagnetSensor.SensorDirection = chosenModule.cancoderInvert;
        return configs;
    }
}
