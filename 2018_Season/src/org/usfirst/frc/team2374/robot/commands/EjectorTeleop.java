package org.usfirst.frc.team2374.robot.commands;

import org.usfirst.frc.team2374.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class EjectorTeleop extends Command {

public EjectorTeleop() { requires(Robot.eject); }
	private boolean toggle = false;
	
	@Override
	protected void initialize() { }
	
	@Override
	protected void execute() {
		// currently assumes that ejector is either at intake position
		// or scale position (in other words no specific switch
		// position and no variable position)
		
		// currently right and left bumper cause shooter to raise
		// or lower
		// this functionality is currently in the OI and needs to
		// be tested, possible fixes include creating command instances
		// in this command instead of OI or recreating their functionalities 
		// in here
		
		// you'll almost definitely have to change the buttons later
		if (Robot.oi.getButtonA())
			Robot.eject.scaleForward();
		else if (Robot.oi.getButtonB())
			Robot.eject.switchForward();
		else if (Robot.oi.getButtonX())
			Robot.eject.intakeIn();
		else
			Robot.eject.flyWheelsStop();
		// test the toggle
		if (Robot.oi.getButtonY() && !toggle) {
			toggle = true;
			Robot.eject.kick();
		}
		else if (Robot.oi.getButtonY() && toggle) {
			toggle = false;
			Robot.eject.kickerStop();
		}
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
