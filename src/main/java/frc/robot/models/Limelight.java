package frc.robot.models;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.utils.LimelightHelpers;

public class Limelight {
    private final String id;
    private final NetworkTable limelightTable;

    public Limelight(String id) {
        this.id = id;
        this.limelightTable = NetworkTableInstance.getDefault().getTable(id);
    }

    public double getTA() {
        try {
            return limelightTable.getValue("ta").getDouble();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public double getTX() {
        try {
            return limelightTable.getValue("tx").getDouble();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public double getTY() {
        return limelightTable.getValue("ty").getDouble();
    }

    public double getTL() {
        return limelightTable.getValue("tl").getDouble();
    }

    public int getTag() {
        return (int) LimelightHelpers.getFiducialID(id);
    }

    public int getID() {
        return (int) limelightTable.getValue("tid").getDouble();
    }

    public void setRobotOrientation(double rotation) {
        LimelightHelpers.SetRobotOrientation(id, rotation, 0, 0, 0, 0, 0);
    }

    public LimelightHelpers.PoseEstimate getPoseEstimateRed() {
        return LimelightHelpers.getBotPoseEstimate_wpiRed_MegaTag2(id);
    }

    public LimelightHelpers.PoseEstimate getPoseEstimateBlue() {
        return LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2(id);
    }

    public LimelightHelpers.PoseEstimate getPoseEstimateDS() {
        DriverStation.Alliance team = DriverStation.getAlliance().get();
        return team == DriverStation.Alliance.Red ? getPoseEstimateRed() : getPoseEstimateBlue();
    }

    public double getRX() {
        return LimelightHelpers.getTargetPose_CameraSpace(id)[3];
    }

    public double getRY() {
        return LimelightHelpers.getTargetPose_CameraSpace(id)[4];
    }

    public double getLatencyPipeline() {
        return LimelightHelpers.getLatency_Pipeline(id);
    }

    public double getLatencyCapture() {
        return LimelightHelpers.getLatency_Capture(id);
    }

    public String getPipeline() {
        return id;
    }
}
