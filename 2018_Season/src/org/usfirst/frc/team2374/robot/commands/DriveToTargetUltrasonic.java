package org.usfirst.frc.team2374.robot.commands;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.Ultrasonic;

import edu.wpi.first.wpilibj.command.Command;

public class DriveToTargetUltrasonic extends Command {
	
	public DriveToTargetUltrasonic() {
		requires(Robot.drive);
		requires(Robot.ultra);
	}
	
	// ideally this will work without PID (we want speed over accuracy for this) but if it doesn't then
	// commented code is if PID is required
	@Override
	protected void initialize() {
		Robot.drive.arcadeDrive(1, 0);
		/* Robot.drive.resetAllSenors();
		Robot.drive.setPID();
		// I don't like doing this but the sensors need a bit of time to reset
		Timer.delay(0.1);
		Robot.drive.setDrivePIDSetPoint(Robot.ultra.getDistanceInches());
		Robot.drive.setGyroPIDSetPoint(0);
		Robot.drive.enableDrivePID(true);
		Robot.drive.enableGyroPID(true); */
	}
	
	@Override
	protected void execute() {
		// Robot.drive.arcadeDrive(Robot.drive.getDrivePIDOutput(), Robot.drive.getGyroPIDOutput());
	}
	
	@Override
	protected boolean isFinished() {
		// if this doesn't work we can use a method based on encoders where we record an initial distance
		// and then compare that distance plus Ultrasonic.TOLERANCE_IN to the average of the right and
		// left distance traveled measured by encoders
		return Robot.ultra.getDistanceInches() < (Ultrasonic.MIN_DISTANCE_IN + Ultrasonic.TOLERANCE_IN);
	}
	
	@Override
	protected void end() {
		Robot.drive.arcadeDrive(0, 0);
		// Robot.drive.enableDrivePID(false);
		// Robot.drive.enableGyroPID(false);
	}
	
	@Override
	protected void interrupted() { end(); }

}
