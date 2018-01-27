package org.usfirst.frc.team2374.robot.commands;

import org.usfirst.frc.team2374.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Default ejector command, controls flywheels for delivery to
 * scale and switch as well as for cube intake. Control of ejector
 * elevation and the kicker motor is implemented elsewhere.
 * @author robotics
 *
 */
public class EjectorTeleop extends Command {

	public EjectorTeleop() { requires(Robot.eject); }
	
	@Override
	protected void initialize() { }
	
	@Override
	protected void execute() {
		// currently assumes that ejector is either at intake position
		// or scale position (in other words no specific switch
		// position and no variable position)
		
		// currently right and left bumper cause shooter to raise
		// or lower
		// these functionalities are currently in the OI and need to
		// be tested, possible fixes include creating command instances
		// in this command instead of OI, recreating their functionalities 
		// in here, or creating separate subsystems for rotation and for kicker
		
		// you'll almost definitely have to change the buttons later
		if (Robot.oi.getPOV() == 180)
			Robot.eject.scaleForward();
		else if (Robot.oi.getPOV() == 90)
			Robot.eject.switchForward();
		else if (Robot.oi.getPOV() == 270)
			Robot.eject.intakeIn();
		else
			Robot.eject.flyWheelsStop();
		
		if (Robot.oi.getLeftBumper())
			Robot.eject.angleDown();
		else if (Robot.oi.getRightBumper())
			Robot.eject.angleUp();
		else
			Robot.eject.stopRotation();
	}

	@Override
	protected boolean isFinished() { return false; }
	
	@Override
	protected void end() { 
		Robot.eject.flyWheelsStop();
		Robot.eject.stopRotation();
	}
	
	@Override
	protected void interrupted() { end(); }

}
