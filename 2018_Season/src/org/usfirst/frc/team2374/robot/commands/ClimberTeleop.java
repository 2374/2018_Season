package org.usfirst.frc.team2374.robot.commands;

import org.usfirst.frc.team2374.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ClimberTeleop extends Command {
	
	public ClimberTeleop() { requires(Robot.climb); }

	@Override
	protected void initialize() { }

	@Override
	protected void execute() {
		if (Robot.oi.getButtonBack())
			Robot.climb.fan();
		else
			Robot.climb.fanOff();
		if (Robot.oi.getButtonStart())
			Robot.climb.climbUp();
		else
			Robot.climb.climbStop();
	}

	@Override
	protected boolean isFinished() { return false; }

	@Override
	protected void end() {
		Robot.climb.fanOff();
		Robot.climb.climbStop();
	}

	@Override
	protected void interrupted() { end(); }

}
