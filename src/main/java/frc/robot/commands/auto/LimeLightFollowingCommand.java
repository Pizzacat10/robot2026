package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import frc.robot.subsystems.LimeLightFollowingSubsystems;

public class LimeLightFollowingCommand extends Command {

    private double tx;
    private CommandPS5Controller joystick; // when true give the player to control the turret
    private LimeLightFollowingSubsystems limeLightFollowingSubsystems;

    public LimeLightFollowingCommand(double tx, CommandPS5Controller autoOff ,LimeLightFollowingSubsystems limeLightFollowingSubsystems) {
        this.tx = tx;
        this.limeLightFollowingSubsystems = limeLightFollowingSubsystems;
        this.joystick = autoOff;
    }

    @Override
    public void initialize() {
        limeLightFollowingSubsystems.Turn(0);
    }


    @Override
    public void execute() {
        if (!joystick.circle().getAsBoolean()) limeLightFollowingSubsystems.Turn(limeLightFollowingSubsystems.InRangeOfTag(tx));
        else limeLightFollowingSubsystems.Turn(joystick.getLeftX());
    }


    @Override
    public void end(boolean interrupted) {
        limeLightFollowingSubsystems.Turn(0);
    }

}
