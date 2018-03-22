package org.usfirst.frc.team2374.robot.commands;

import org.usfirst.frc.team2374.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Default ejector command, controls flywheels for delivery to
 * scale and switch as well as for cube intake. Control of ejector
 * elevation and the kicker motor is implemented elsewhere.
 * 
 * @author robotics
 */
public class EjectorTeleop extends Command {
	private boolean toggle;

	public EjectorTeleop() { requires(Robot.eject); }
	
	@Override
	protected void initialize() { }
	
	@Override
	protected void execute() {
		Robot.eject.displayScaleSpeed();
		
		if (Robot.oi.getButtonM1Pressed())
			Robot.eject.scaleSpeedLowDown();
		else if (Robot.oi.getButtonM2Pressed())
			Robot.eject.scaleSpeedLowUp();
		
		if (Robot.oi.getButtonBackPressed())
			Robot.eject.scaleSpeedDown();
		else if (Robot.oi.getButtonStartPressed())
			Robot.eject.scaleSpeedUp();
		
		if (Robot.oi.getPOV() == 180) {
			Robot.eject.scaleForward();
			toggle = false;
		}
		else if (Robot.oi.getPOV() == 90) {
			Robot.eject.switchForward();
			toggle = false;
		}
		else if (Robot.oi.getPOV() == 0 || toggle) {
			Robot.eject.intake();
			if (!toggle)
				toggle = true;
		}
		else if (Robot.oi.getPOV() == 270) {
			Robot.eject.lowScaleForward();
			toggle = false;
		}
		else {
			Robot.eject.flyWheelsStop();
			toggle = false;
		}
		
		if (Robot.oi.getLeftBumper())
			Robot.eject.angleDown();
		else if (Robot.oi.getRightBumper())
			Robot.eject.angleUp(false);
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
