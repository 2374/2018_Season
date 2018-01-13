package org.usfirst.frc.team2374.robot.commands;

import org.usfirst.frc.team2374.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.TimedCommand;

// TODO(CR): CoffeePaper
public class EjectorDown extends TimedCommand {
	
	public EjectorDown(double timeout) { 
		super(timeout);
		requires(Robot.eject); 
	}
	
	@Override
	protected void initialize() { Robot.eject.angleDown(); }
	
	@Override
	protected void execute() { }

	@Override
	protected boolean isFinished() { return Robot.eject.atIntakePos() || super.isTimedOut(); }
	
	@Override
	protected void end() { Robot.eject.stopRotation(); }
	
	@Override
	protected void interrupted() { end(); }

}

