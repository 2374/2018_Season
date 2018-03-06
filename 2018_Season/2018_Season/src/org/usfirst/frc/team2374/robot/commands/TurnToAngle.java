package org.usfirst.frc.team2374.robot.commands;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 * Called during autonomous, turns the robot to the specified angle.
 * Uses gyro to measure angle and PID algorithm to regulate output.
 * 
 * @author Robotics
 */
public class TurnToAngle extends TimedCommand {
	private double targetAngleDegrees;
	private PIDType PIDType;
	
	public enum PIDType { LONG, SHORT }
	
	public TurnToAngle(double angle, PIDType type, double timeout) {
		super(timeout);
		requires(Robot.drive);
		targetAngleDegrees = angle;
		PIDType = type;
	}
	
	@Override
	protected void initialize() {
		Robot.drive.resetGyro();
		switch (PIDType) {
			case LONG: {
				Robot.drive.setGyroPIDLong();
				break;
			}
			case SHORT: {
				Robot.drive.setGyroPIDShort();
				break;
			}
		}
		Timer.delay(0.1);
		Robot.drive.setGyroPIDSetPoint(targetAngleDegrees);
		Robot.drive.enableGyroPID(true);
	}
	
	@Override
	protected void execute() {
		Robot.drive.arcadeDrive(0, -Robot.drive.getGyroPIDOutput());
	}
	
	@Override
	protected boolean isFinished() {
		return Math.abs(Robot.drive.getGyroPIDError()) <= Drivetrain.GYRO_TOLERANCE_DEG || super.isTimedOut();
	}
	
	@Override
	protected void end() {
		Robot.drive.enableGyroPID(false);
		Robot.drive.arcadeDrive(0, 0);
	}
	
	@Override
	protected void interrupted() { end(); }

}
