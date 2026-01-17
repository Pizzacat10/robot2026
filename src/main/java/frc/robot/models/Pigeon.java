package frc.robot.models;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.MountPoseConfigs;
import com.ctre.phoenix6.configs.Pigeon2Configuration;
import com.ctre.phoenix6.hardware.Pigeon2;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;

public class Pigeon {

    private static Pigeon mInstance;

    public static void CreateInstance(int ID, CANBus bus) {
        if (mInstance == null) {
            mInstance = new Pigeon(ID, bus);
        }
    }

    public static Pigeon getInstance() {
        return mInstance;
    }

    private final Pigeon2 mGyro;

    private boolean inverted = false;
    private Rotation2d yawAdjustmentAngle = Rotation2d.fromDegrees(0);

    private Pigeon(int port, CANBus bus) {
        mGyro = new Pigeon2(port, bus);
        BaseStatusSignal.setUpdateFrequencyForAll(50, mGyro.getRoll(), mGyro.getPitch(), mGyro.getYaw());
        mGyro.reset();
        mGyro.getConfigurator().apply(new Pigeon2Configuration().withMountPose(new MountPoseConfigs().withMountPosePitch(0.3566450774669647).withMountPoseRoll(-1.2803443670272827).withMountPoseYaw(-90.36228942871094)));
        mGyro.optimizeBusUtilization();
    }

    public Rotation2d getYaw() {
        Rotation2d angle = getUnadjustedYaw().rotateBy(yawAdjustmentAngle.unaryMinus());
        if (inverted) {
            return angle.unaryMinus();
        }
        return angle;
    }

    public void setYaw(double angleDeg) {
        yawAdjustmentAngle = Rotation2d.fromDegrees(getYawStatusSignal().getValueAsDouble())
                .rotateBy(Rotation2d.fromDegrees(angleDeg).unaryMinus());
    }

    public Rotation2d getUnadjustedYaw() {
        return Rotation2d.fromDegrees(
                BaseStatusSignal.getLatencyCompensatedValueAsDouble(getYawStatusSignal(), getRateStatusSignal()));
    }

    public Rotation2d getUnadjustedPitch() {
        return Rotation2d.fromDegrees(mGyro.getPitch().getValueAsDouble());
    }

    public StatusSignal<Angle> getYawStatusSignal() {
        return mGyro.getYaw();
    }

    public StatusSignal<AngularVelocity> getRateStatusSignal() {
        return mGyro.getAngularVelocityZDevice();
    }

}