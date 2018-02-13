package org.usfirst.frc.team2374.robot.commands;

import org.usfirst.frc.team2374.robot.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 * Raises ejector to scale position, the command stops
 * after a certain amount of time if the limit switch
 * hasn't been tripped yet
 * 
 * @author robotics
 */
public class SwitchDeliveryTimed extends TimedCommand {
	
	public SwitchDeliveryTimed(double timeout) { 
		super(timeout);
		requires(Robot.eject);
	}
	
	@Override
	protected void initialize() { Robot.eject.switchForward(); }
	
	@Override
	protected void execute() { }

	@Override
	protected boolean isFinished() { return super.isTimedOut(); }
	
	@Override
	protected void end() { Robot.eject.flyWheelsStop(); }
	
	@Override
	protected void interrupted() { end(); }

}
