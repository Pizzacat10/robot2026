package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import frc.robot.subsystems.LimeLightFollowingSubsystems;

public class LimeLightFollowingCommand extends Command {

    private double tx;
    private CommandPS5Controller joystick; // when true give the player to control the turret
    private LimeLightFollowingSubsystems limeLightFollowingSubsystems;
    private boolean auto;

    public LimeLightFollowingCommand(double tx, CommandPS5Controller autoOff ,LimeLightFollowingSubsystems limeLightFollowingSubsystems) {
        this.tx = tx;
        this.limeLightFollowingSubsystems = limeLightFollowingSubsystems;
        this.joystick = autoOff;

        auto = true;
    }

    @Override
    public void initialize() {
        limeLightFollowingSubsystems.reset();
    }


    @Override
    public void execute() {
        if (joystick.circle().getAsBoolean()) auto = !auto;

        if (auto) limeLightFollowingSubsystems.Turn(limeLightFollowingSubsystems.InRangeOfTag(tx));
        else limeLightFollowingSubsystems.Turn(joystick.getLeftX());
    }


    @Override
    public void end(boolean interrupted) {
        limeLightFollowingSubsystems.reset();
    }

}
