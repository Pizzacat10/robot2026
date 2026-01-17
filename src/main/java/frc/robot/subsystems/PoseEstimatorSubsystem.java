package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.models.Limelight;
import frc.robot.models.Pigeon;
import frc.robot.utils.LimelightHelpers;

public class PoseEstimatorSubsystem extends SubsystemBase {
    private static SwerveSubsystem m_swerve;
    private static Pigeon m_gyro;
    private static boolean first = true;
    public static Limelight limelightFrontDown = new Limelight("limelight-frontdo");
    public static Limelight limelightBackUp = new Limelight("limelight-back");

    public PoseEstimatorSubsystem(SwerveSubsystem swerve) {
        m_swerve = swerve;
        m_gyro = Pigeon.getInstance();
    }

    @Override
    public void periodic() {
        if (!first) {
            updateVisionOdometry();
        } else {
            first = false;
        }
    }

    public static void updateVisionOdometry() {
        if (!first) {
            updateLimelight(limelightBackUp);
            updateLimelight(limelightFrontDown);
        } else {
            first = false;
        }
    }

    private static void updateLimelight(Limelight limelight) {
        limelight.setRobotOrientation(m_swerve.getPose().getRotation().getDegrees());
        LimelightHelpers.PoseEstimate mt2 = limelight.getPoseEstimateBlue();
        if (mt2 != null && Math.abs(m_gyro.getRateStatusSignal().getValueAsDouble()) <= 720 && mt2.tagCount != 0) {
            m_swerve.addVisionMeasurement(mt2.pose, Timer.getFPGATimestamp() -
                    ((limelight.getLatencyPipeline() + limelight.getLatencyCapture()) / 1000.0)
            );
        }
    }

    public static Pose2d getRobotPose() {
        return m_swerve.getPose();
    }

}
