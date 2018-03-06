package org.usfirst.frc.team2374.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * Runs ScaleLeft CommandGroup during autonomous, this command exists
 * because there was an issue where putting the CommandGroup directly
 * in the SendableChooser prevented it from using GameData
 * 
 * @author Robotics
 */
public class RunScaleLeft extends Command {

    public RunScaleLeft() { }

    // Called just before this Command runs the first time
    @Override
	protected void initialize() { Scheduler.getInstance().add(new ScaleLeft()); }

    // Called repeatedly when this Command is scheduled to run
    @Override
	protected void execute() { }

    // Make this return true when this Command no longer needs to run execute()
    @Override
	protected boolean isFinished() { return false; }

    // Called once after isFinished returns true
    @Override
	protected void end() { }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
	protected void interrupted() { }
}
