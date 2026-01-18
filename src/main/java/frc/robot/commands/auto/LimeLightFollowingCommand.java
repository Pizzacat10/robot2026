package frc.robot.commands.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import frc.robot.subsystems.LimeLightFollowingSubsystems;

public class LimeLightFollowingCommand extends Command {

    private CommandPS5Controller joystick; // when true give the player to control the turret
    private LimeLightFollowingSubsystems limeLightFollowingSubsystems;
    private Pose2d robotPos;
    private boolean auto;

    public LimeLightFollowingCommand(Pose2d robotPos , CommandPS5Controller joystick ,LimeLightFollowingSubsystems limeLightFollowingSubsystems) {
        this.limeLightFollowingSubsystems = limeLightFollowingSubsystems;
        this.joystick = joystick;
        this.robotPos = robotPos;

        auto = false;
    }

    @Override
    public void initialize() {
        limeLightFollowingSubsystems.Turn(
                limeLightFollowingSubsystems.getSpeed(
                        limeLightFollowingSubsystems.getAngle(),0,5));
    }


    @Override
    public void execute() {
        limeLightFollowingSubsystems.overLimet(
                limeLightFollowingSubsystems.getSpeed(
                        limeLightFollowingSubsystems.getAngle(),0,5));

        if (auto) limeLightFollowingSubsystems.Turn(
                limeLightFollowingSubsystems.getSpeed(limeLightFollowingSubsystems.getAngle(), limeLightFollowingSubsystems.calcTargetAngle(robotPos), 5 )
        );
        else limeLightFollowingSubsystems.Turn(joystick.povLeft().getAsBoolean() ? 0.5 : joystick.povRight().getAsBoolean() ? -0.5 : 0);

    }


    @Override
    public void end(boolean interrupted) {
        limeLightFollowingSubsystems.Turn(
                limeLightFollowingSubsystems.getSpeed(
                        limeLightFollowingSubsystems.getAngle(),0,5));
    }

}
