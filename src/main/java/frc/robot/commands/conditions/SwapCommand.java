package frc.robot.commands.conditions;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import java.util.function.BooleanSupplier;

public class SwapCommand extends Command {
    private final BooleanSupplier supplier;
    private final Command defaultCommand;
    private final Command onTrueCommand;

    public SwapCommand(BooleanSupplier supplier, Command defaultCommand, Command onTrueCommand) {
        this.supplier = supplier;
        this.defaultCommand = defaultCommand;
        this.onTrueCommand = onTrueCommand;
    }

    @Override
    public void initialize() {
        if (supplier.getAsBoolean()) CommandScheduler.getInstance().schedule(onTrueCommand);
        else CommandScheduler.getInstance().schedule(defaultCommand);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
