package frc.robot.models;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import frc.robot.constants.SwerveConstants;
import frc.robot.utils.swerve.SwerveModuleConstants;

public class SwerveModule {
    private TalonFX m_driveMotor;
    private TalonFX m_steeringMotor;
    private CANcoder m_absoluteEncoder;

    private final StatusSignal<Angle> m_driveMotorPositionSignal;
    private final StatusSignal<AngularVelocity> m_driveMotorVelocitySignal;
    private final StatusSignal<Current> m_driveMotorSupplyCurrentSignal;
    private final StatusSignal<Double> m_driveMotorClosedLoopErrorSignal;

    private final StatusSignal<Angle> m_steerMotorPositionSignal;
    private final StatusSignal<AngularVelocity> m_steerMotorVelocitySignal;
    private final StatusSignal<Current> m_steerMotorSupplyCurrentSignal;
    private final StatusSignal<Double> m_steerMotorClosedLoopErrorSignal;

    private final StatusSignal<Angle> m_CANCoderAbsolutePositionSignal;

    private final BaseStatusSignal[] odometrySignals;

    @SuppressWarnings("unused")
    private SwerveModuleConstants m_moduleConstants;

    private final DutyCycleOut driveDutyCycle = new DutyCycleOut(0);
    private final VelocityVoltage driveVelocity = new VelocityVoltage(0).withSlot(0);

    private final PositionVoltage anglePosition = new PositionVoltage(0).withSlot(0);

    private final VoltageOut m_sysidControl = new VoltageOut(0);

    private Rotation2d m_angleOffset = new Rotation2d();
    private Rotation2d m_lastAngle = new Rotation2d();

    public SwerveModule(SwerveModuleConstants swerveModuleConstants) {
        m_moduleConstants = swerveModuleConstants;

        m_absoluteEncoder = new CANcoder(swerveModuleConstants.absoluteEncoderID, SwerveConstants.canBus);
        m_absoluteEncoder.getConfigurator().apply(SwerveConstants.canCoderConfigs());

        m_steeringMotor = new TalonFX(swerveModuleConstants.steeringMotorID, SwerveConstants.canBus);
        TalonFXConfiguration steerConfiguration = SwerveConstants.steerTalonFXConfigs();
        if (m_steeringMotor.getIsProLicensed().getValue() && SwerveConstants.feedbackSensorSource == FeedbackSensorSourceValue.FusedCANcoder) {
            steerConfiguration.Feedback.FeedbackSensorSource = FeedbackSensorSourceValue.RotorSensor;
            steerConfiguration.Feedback.SensorToMechanismRatio = SwerveConstants.angleGearRatio;
            steerConfiguration.Feedback.RotorToSensorRatio = 1.0;
        } else {
            steerConfiguration.Feedback.FeedbackSensorSource = FeedbackSensorSourceValue.RotorSensor;
            steerConfiguration.Feedback.SensorToMechanismRatio = SwerveConstants.angleGearRatio;
            steerConfiguration.Feedback.RotorToSensorRatio = 1.0;
        }
        m_steeringMotor.getConfigurator().apply(steerConfiguration);

        m_driveMotor = new TalonFX(swerveModuleConstants.driveMotorID, SwerveConstants.canBus);
        m_driveMotor.getConfigurator().apply(SwerveConstants.driveTalonFXConfigs());
        m_driveMotor.getConfigurator().apply(swerveModuleConstants.slot0Configs);
        m_driveMotor.getConfigurator().setPosition(0.0);

        m_driveMotorPositionSignal = m_driveMotor.getPosition();
        m_driveMotorVelocitySignal = m_driveMotor.getVelocity();
        m_driveMotorSupplyCurrentSignal = m_driveMotor.getSupplyCurrent();
        m_driveMotorClosedLoopErrorSignal = m_driveMotor.getClosedLoopError();

        m_steerMotorPositionSignal = m_steeringMotor.getPosition();
        m_steerMotorVelocitySignal = m_steeringMotor.getVelocity();
        m_steerMotorSupplyCurrentSignal = m_steeringMotor.getSupplyCurrent();
        m_steerMotorClosedLoopErrorSignal = m_steeringMotor.getClosedLoopError();

        m_CANCoderAbsolutePositionSignal = m_absoluteEncoder.getAbsolutePosition();

        odometrySignals = new BaseStatusSignal[4];
        odometrySignals[0] = m_driveMotorVelocitySignal;
        odometrySignals[1] = m_driveMotorPositionSignal;
        odometrySignals[2] = m_steerMotorVelocitySignal;
        odometrySignals[3] = m_steerMotorPositionSignal;

        CanBusProperties(250);

        m_angleOffset = swerveModuleConstants.angleOffset;
        setAngleOffset(swerveModuleConstants.angleOffset);
        resetToAbsolute();
        resetToAbsolute();
    }

    public double getVelocity() {
        return m_driveMotor.getVelocity().getValueAsDouble();
    }

    public Rotation2d getAngle() {
        return Rotation2d.fromRotations(m_steeringMotor.getPosition().getValueAsDouble());
    }

    public Rotation2d getAbsAngleWithOffset() {
        return Rotation2d.fromRotations(m_absoluteEncoder.getAbsolutePosition().getValueAsDouble()).minus(m_angleOffset);
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(getVelocity(), getAngle());
    }

    public SwerveModulePosition getModulePosition() {
        return new SwerveModulePosition(
                Conversions.rotationsToMeters(m_driveMotor.getPosition().getValueAsDouble(), SwerveConstants.wheelCircumference),
                getAngle()
        );
    }

    public void setState(SwerveModuleState desiredState, boolean openLoop, boolean avoidJittering) {
        desiredState.optimize(getState().angle);
        if (avoidJittering && Math.abs(desiredState.speedMetersPerSecond) <= (SwerveConstants.maxSpeed * 0.03)) {
            desiredState.angle = m_lastAngle;
        }
        setAngle(desiredState.angle);
        m_lastAngle = desiredState.angle;
        setSpeed(desiredState, openLoop);
    }

    public void setSteeringNeturalMode(NeutralModeValue neutralMode) {
        m_steeringMotor.setNeutralMode(neutralMode);
    }

    public void setAngleOffset(Rotation2d angleOffset) {
        m_angleOffset = angleOffset;
        resetToAbsolute();
    }

    public void resetToAbsolute() {
        m_steeringMotor.setPosition(getAbsAngleWithOffset().getRotations());
    }

    public void runCharacterization(double volts) {
        setAngle(Rotation2d.fromRotations(0));
        m_driveMotor.setControl(m_sysidControl.withOutput(volts));
    }

    public void DisableMotors() {
        m_driveMotor.disable();
        m_steeringMotor.disable();
    }

    public void refreshAllSignals() {
        BaseStatusSignal.refreshAll(
                m_driveMotorPositionSignal,
                m_driveMotorVelocitySignal,
                m_driveMotorSupplyCurrentSignal,
                m_driveMotorClosedLoopErrorSignal,
                m_steerMotorPositionSignal,
                m_steerMotorVelocitySignal,
                m_steerMotorSupplyCurrentSignal,
                m_steerMotorClosedLoopErrorSignal,
                m_CANCoderAbsolutePositionSignal);
    }

    private void setAngle(Rotation2d angle) {
        m_steeringMotor.setControl(anglePosition.withPosition(angle.getRotations()));
    }

    private void setSpeed(SwerveModuleState desiredState, boolean isOpenLoop) {
        if (isOpenLoop) {
            driveDutyCycle.Output = desiredState.speedMetersPerSecond / SwerveConstants.maxSpeed;
            m_driveMotor.setControl(driveDutyCycle);
        } else {
            driveVelocity.Velocity = Conversions.MPSToRPS(desiredState.speedMetersPerSecond, SwerveConstants.wheelCircumference);
            m_driveMotor.setControl(driveVelocity);
        }
    }

    private void CanBusProperties(int CanBusFrequency) {
        BaseStatusSignal.setUpdateFrequencyForAll(50,
                m_driveMotorPositionSignal,
                m_driveMotorVelocitySignal,
                m_driveMotorSupplyCurrentSignal,
                m_driveMotorClosedLoopErrorSignal,
                m_steerMotorPositionSignal,
                m_steerMotorVelocitySignal,
                m_steerMotorSupplyCurrentSignal,
                m_steerMotorClosedLoopErrorSignal,
                m_CANCoderAbsolutePositionSignal);
        m_driveMotor.optimizeBusUtilization();
        m_steeringMotor.optimizeBusUtilization();
        m_absoluteEncoder.optimizeBusUtilization();
    }
}
