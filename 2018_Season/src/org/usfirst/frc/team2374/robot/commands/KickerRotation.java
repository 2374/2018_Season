package org.usfirst.frc.team2374.robot.commands;

import org.usfirst.frc.team2374.robot.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 * Causes kicker to rotate for a couple seconds, pushing the
 * cube into the flywheels
 * 
 * @author robotics
 */
public class KickerRotation extends TimedCommand {
	
	public KickerRotation(double timeout) {
		super(timeout);
		requires(Robot.eject);
	}
	
	@Override
	protected void initialize() { Robot.eject.kick(); }
	
	@Override
	protected void execute() { }

	@Override
	protected boolean isFinished() { return super.isTimedOut(); }
	
	@Override
	protected void end() { Robot.eject.kickerStop(); }
	
	@Override
	protected void interrupted() { end(); }

}
