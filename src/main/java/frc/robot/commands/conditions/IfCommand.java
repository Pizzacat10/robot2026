package frc.robot.commands.conditions;

import edu.wpi.first.wpilibj2.command.Command;

import java.util.function.Supplier;

public class IfCommand extends Command {

    private final Supplier<Boolean> ifStatement;

    public IfCommand(Supplier<Boolean> ifStatement) {
        this.ifStatement = ifStatement;
    }

    @Override
    public boolean isFinished() {
        return ifStatement.get();
    }
}
