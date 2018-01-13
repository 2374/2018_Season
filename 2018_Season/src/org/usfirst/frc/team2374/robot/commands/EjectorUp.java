package org.usfirst.frc.team2374.robot.commands;

import org.usfirst.frc.team2374.robot.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;

// TODO(CR): JavaPaper
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
