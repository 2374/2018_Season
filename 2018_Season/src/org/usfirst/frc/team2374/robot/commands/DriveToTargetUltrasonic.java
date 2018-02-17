package org.usfirst.frc.team2374.robot.commands;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.Ultrasonic;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Called during autonomous, drives in a straight line until it reaches a wall.
 * Uses ultrasonic sensor to measure distance and gyro to measure angle. Uses
 * PID algorithm to stay in a straight line but not for distance. Robot drives
 * at fixed speed and then skids to a stop right in front of wall.
 * 
 * @author Robotics
 */
public class DriveToTargetUltrasonic extends Command {
	
	public DriveToTargetUltrasonic() {
		requires(Robot.drive);
		requires(Robot.ultra);
	}
	
	// this works without PID (we want speed over accuracy for this) but use
	// commented code if PID is required
	@Override
	protected void initialize() {
		Robot.drive.resetGyro();
		Robot.drive.setGyroPIDLong();
		Timer.delay(0.1);
		Robot.drive.setGyroPIDSetPoint(0);
		Robot.drive.enableGyroPID(true);
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
		Robot.drive.arcadeDrive(-0.75, -Robot.drive.getGyroPIDOutput());
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
		Robot.drive.enableGyroPID(false);
	}
	
	@Override
	protected void interrupted() { end(); }

}
