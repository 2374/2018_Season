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
public class EjectorUp extends TimedCommand {
	
	public EjectorUp(double timeout) { 
		super(timeout);
		requires(Robot.eject);
	}
	
	@Override
	protected void initialize() { Robot.eject.angleUp(); }
	
	@Override
	protected void execute() { }

	@Override
	protected boolean isFinished() { return Robot.eject.atScalePos() || super.isTimedOut(); }
	
	@Override
	protected void end() { Robot.eject.stopRotation(); }
	
	@Override
	protected void interrupted() { end(); }

}
