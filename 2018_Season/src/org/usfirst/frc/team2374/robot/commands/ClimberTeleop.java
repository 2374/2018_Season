package org.usfirst.frc.team2374.robot.commands;

import org.usfirst.frc.team2374.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Default climber command, controls winch and fan motors in 
 * order to attack the hook to the rung and lift the robot up
 *
 * @author robotics
 */
public class ClimberTeleop extends Command {
	
	public ClimberTeleop() { requires(Robot.climb); }

	@Override
	protected void initialize() { }

	@Override
	protected void execute() {
		if (Robot.oi.getButtonA())
			Robot.climb.raiseClimber();
		else if (Robot.oi.getButtonB())
			Robot.climb.lowerClimber();
		else
			Robot.climb.armOff();
		
		if (Robot.oi.getButtonX()) {
			Robot.climb.climbUp();
			Robot.climb.armOff();
		}
		else
			Robot.climb.climbStop();
	}

	@Override
	protected boolean isFinished() { return false; }

	@Override
	protected void end() {
		Robot.climb.armOff();
		Robot.climb.climbStop();
	}

	@Override
	protected void interrupted() { end(); }

}
