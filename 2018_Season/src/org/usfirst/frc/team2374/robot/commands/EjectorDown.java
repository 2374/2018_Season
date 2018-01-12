package org.usfirst.frc.team2374.robot.commands;

import org.usfirst.frc.team2374.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class EjectorDown extends Command {
	
	public EjectorDown() { requires(Robot.eject); }
	
	@Override
	protected void initialize() { Robot.eject.angleDown(); }
	
	@Override
	protected void execute() { }

	@Override
	protected boolean isFinished() { return Robot.eject.atIntakePos(); }
	
	@Override
	protected void end() { Robot.eject.stopRotation(); }
	
	@Override
	protected void interrupted() { end(); }

}

