package org.usfirst.frc.team2374.robot.commands;

import org.usfirst.frc.team2374.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class EjectorTeleop extends Command {

// TODO(CR): MochaDoc
public EjectorTeleop() { requires(Robot.eject); }
	
	@Override
	protected void initialize() { }
	
	@Override
	protected void execute() {
		// TODO(CR): Nit: Capitalization and punctuation
		// currently assumes that ejector is either at intake position
		// or scale position (in other words no specific switch
		// position and no variable position)
		
		// currently right and left bumper cause shooter to raise
		// or lower and button y causes kicker to trigger for a couple seconds
		// these functionalities are currently in the OI and need to
		// be tested, possible fixes include creating command instances
		// in this command instead of OI, recreating their functionalities 
		// in here, or creating separate subsystems for rotation and for kicker
		
		// you'll almost definitely have to change the buttons later
		if (Robot.oi.getButtonA())
			Robot.eject.scaleForward();
		else if (Robot.oi.getButtonB())
			Robot.eject.switchForward();
		else if (Robot.oi.getButtonX())
			Robot.eject.intakeIn();
		else
			Robot.eject.flyWheelsStop();
	}

	@Override
	protected boolean isFinished() { return false; }
	
	@Override
	protected void end() { 
		Robot.eject.flyWheelsStop();
		Robot.eject.kickerStop();
		Robot.eject.stopRotation();
	}
	
	@Override
	protected void interrupted() { end(); }

}
