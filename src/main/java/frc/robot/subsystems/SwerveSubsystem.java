// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.CanConstants;
import frc.robot.constants.SwerveConstants;
import frc.robot.models.*;
import frc.robot.models.interpolating.InterpolatingDouble;
import frc.robot.models.interpolating.InterpolatingTreeMap;

import static edu.wpi.first.units.Units.Volts;

public class SwerveSubsystem extends SubsystemBase {
    private static SwerveSubsystem swerve = null;

    private SwerveModule m_frontLeft;
    private SwerveModule m_frontRight;
    private SwerveModule m_backLeft;
    private SwerveModule m_backRight;
    private SwerveModule[] m_swerveModules = new SwerveModule[4];

    private SwerveDriveKinematics m_kinematics;
    private SwerveDrivePoseEstimator m_odometry;
    private InterpolatingTreeMap<InterpolatingDouble, Pose2d> m_pastPoses;

    private Pigeon m_gyro;
    private Field2d field;

    public boolean activateLeft = true;
    public boolean activateRight = false;

    public Field2d getField() {
        return field;
    }

    public SwerveSubsystem() {
        Pigeon.CreateInstance(CanConstants.Pigeon, CanConstants.roborioBus);
        m_gyro = Pigeon.getInstance();
        if (DriverStation.getAlliance().get() == DriverStation.Alliance.Red)
            m_gyro.setYaw(180);
        field = new Field2d();
        m_frontLeft = new SwerveModule(SwerveConstants.FL);
        m_frontRight = new SwerveModule(SwerveConstants.FR);
        m_backLeft = new SwerveModule(SwerveConstants.BL);
        m_backRight = new SwerveModule(SwerveConstants.BR);
        m_swerveModules[0] = m_frontLeft;
        m_swerveModules[1] = m_frontRight;
        m_swerveModules[2] = m_backLeft;
        m_swerveModules[3] = m_backRight;

        m_kinematics = new SwerveDriveKinematics(SwerveConstants.frontLeftPos, SwerveConstants.frontRightPos, SwerveConstants.backLeftPos, SwerveConstants.backRightPos);
        m_odometry = new SwerveDrivePoseEstimator(m_kinematics, Rotation2d.fromDegrees(0), getModulesPositions(), new Pose2d(0, 0, Rotation2d.fromDegrees(DriverStation.getAlliance().get() == DriverStation.Alliance.Red ? 180 : 0)));

        int k_maxPoseHistorySize = 51;
        m_pastPoses = new InterpolatingTreeMap<>(k_maxPoseHistorySize);
    }

    public void drive(ChassisSpeeds chassisSpeeds, boolean openLoop, boolean fieldRelative, Translation2d centerOfRtation) {
        Rotation2d heading = (DriverStation.getAlliance().isPresent() && (DriverStation.getAlliance().get() == DriverStation.Alliance.Red)) ? getHeading().plus(Rotation2d.fromDegrees(180)) : getHeading();
        chassisSpeeds = ChassisSpeeds.discretize(chassisSpeeds, 0.02);
        SwerveModuleState[] states = fieldRelative ? m_kinematics.toSwerveModuleStates(ChassisSpeeds.fromFieldRelativeSpeeds(chassisSpeeds, heading), centerOfRtation) : m_kinematics.toSwerveModuleStates(chassisSpeeds, centerOfRtation);
        SwerveDriveKinematics.desaturateWheelSpeeds(states, SwerveConstants.maxSpeed);
        setModulesStates(states, openLoop, true);
    }

    public void setModulesStates(SwerveModuleState[] states, boolean isOpenLoop, boolean avoidJittering) {
        for (int i = 0; i < states.length; i++) {
            m_swerveModules[i].setState(states[i], isOpenLoop, avoidJittering);
        }
    }

    public void setModulesNetrualMode(NeutralModeValue neutralMode) {
        for (int i = 0; i < m_swerveModules.length; i++) {
            m_swerveModules[i].setSteeringNeturalMode(neutralMode);
        }
    }

    public Rotation2d getHeading() {
        return getPose().getRotation();
    }

    public Pose2d getPose() {
        return m_odometry.getEstimatedPosition();
    }

    public void addVisionMeasurement(Pose2d visionPose, double timestamp) {
        m_odometry.setVisionMeasurementStdDevs(VecBuilder.fill(.7, .7, 9999999));
        m_odometry.addVisionMeasurement(visionPose, timestamp);
    }

    public SwerveDriveKinematics getKinematics() {
        return m_kinematics;
    }

    public Pose2d getInterpolatedPose(double latencySeconds) {
        double timestamp = Timer.getFPGATimestamp() - latencySeconds;
        return m_pastPoses.getInterpolated(new InterpolatingDouble(timestamp));
    }

    public SwerveModulePosition[] getModulesPositions() {
        SwerveModulePosition[] positions = new SwerveModulePosition[4];
        for (int i = 0; i < positions.length; i++) {
            positions[i] = m_swerveModules[i].getModulePosition();
        }
        return positions;
    }

    public SwerveModuleState[] getStates() {
        SwerveModuleState[] states = new SwerveModuleState[4];
        for (int i = 0; i < states.length; i++) {
            states[i] = m_swerveModules[i].getState();
        }
        return states;
    }

    public SwerveModule[] getSwerveModules() {
        return m_swerveModules;
    }

    public ChassisSpeeds getRobotRelativeVelocity() {
        return m_kinematics.toChassisSpeeds(getStates());
    }

    @Override
    public void periodic() {
        for (SwerveModule m : m_swerveModules) {
            m.refreshAllSignals();
        }

        m_gyro.getYawStatusSignal().refresh();
        Pose2d currentPose = m_odometry.update(m_gyro.getYaw(), getModulesPositions());
        m_pastPoses.put(new InterpolatingDouble(Timer.getFPGATimestamp()), currentPose);

        field.setRobotPose(currentPose);
        SmartDashboard.putNumber("FL", getSwerveModules()[0].getAbsAngleWithOffset().getDegrees());
        SmartDashboard.putNumber("FR", getSwerveModules()[1].getAbsAngleWithOffset().getDegrees());
        SmartDashboard.putNumber("BL", getSwerveModules()[2].getAbsAngleWithOffset().getDegrees());
        SmartDashboard.putNumber("BR", getSwerveModules()[3].getAbsAngleWithOffset().getDegrees());
        SmartDashboard.putNumber("RobotHeading", getHeading().getDegrees());
        SmartDashboard.putData("field", field);
        SmartDashboard.putString("robot_coords", String.format("(%f, %f, %f)", currentPose.getX(), currentPose.getY(), currentPose.getRotation().getDegrees()));
    }

    public void zeroHeading() {
        Rotation2d heading = (DriverStation.getAlliance().isPresent() && (DriverStation.getAlliance().get() == DriverStation.Alliance.Red)) ? Rotation2d.fromDegrees(180) : Rotation2d.fromDegrees(0);
        m_odometry.resetPosition(m_gyro.getYaw(), getModulesPositions(), new Pose2d(getPose().getTranslation(), heading));
        m_gyro.setYaw(heading.getDegrees());
    }

    public void resetToAbsolute() {
        for (int i = 0; i < m_swerveModules.length; i++) {
            m_swerveModules[i].resetToAbsolute();
        }
    }

    public void resetOdometry(Pose2d pose) {
        m_odometry.resetPosition(m_gyro.getYaw(), getModulesPositions(), pose);
    }

    public void resetOdometry() {
        m_odometry.resetPosition(m_gyro.getYaw().plus(Rotation2d.fromDegrees(180)), getModulesPositions(), Pose2d.kZero);
    }

    public void disableModules() {
        for (int i = 0; i < m_swerveModules.length; i++) {
            m_swerveModules[i].DisableMotors();
        }
    }

    public void runSwerveCharacterization(Voltage volts) {
        for (int i = 0; i < 4; i++) {
            m_swerveModules[i].runCharacterization(volts.in(Volts));
        }
    }


    public static SwerveSubsystem createInstance() {
        if (swerve == null) {
            swerve = new SwerveSubsystem();
        }
        return swerve;
    }

    public static SwerveSubsystem getInstance() {
        if (swerve != null) {
            return swerve;
        }
        return null;
    }

    public Pigeon getGyro() {
        return m_gyro;
    }
}
