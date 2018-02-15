package org.usfirst.frc.team2374.robot.commands;

import org.usfirst.frc.team2374.robot.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;

public class ScaleDeliveryTimed extends TimedCommand {
	
	public ScaleDeliveryTimed(double timeout) { 
		super(timeout);
		requires(Robot.eject);
	}
	
	@Override
	protected void initialize() { Robot.eject.scaleForward(); }
	
	@Override
	protected void execute() { }

	@Override
	protected boolean isFinished() { return super.isTimedOut(); }
	
	@Override
	protected void end() { Robot.eject.flyWheelsStop(); }
	
	@Override
	protected void interrupted() { end(); }

}
