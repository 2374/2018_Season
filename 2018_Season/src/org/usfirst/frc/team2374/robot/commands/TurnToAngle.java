package org.usfirst.frc.team2374.robot.commands;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class TurnToAngle extends Command {
	private double targetAngleDegrees;
	
	public TurnToAngle(double angle) {
		requires(Robot.drive);
		targetAngleDegrees = angle;
	}
	
	@Override
	protected void initialize() {
		Robot.drive.resetGyro();
		Robot.drive.setPID();
		Timer.delay(0.1);
		Robot.drive.setGyroPIDSetPoint(targetAngleDegrees);
		Robot.drive.enableGyroPID(true);
	}
	
	@Override
	protected void execute() {
		Robot.drive.arcadeDrive(0, Robot.drive.getGyroPIDOutput());
	}
	
	@Override
	protected boolean isFinished() {
		return Math.abs(Robot.drive.getGyroPIDError()) <= Drivetrain.GYRO_TOLERANCE_DEG;
	}
	
	@Override
	protected void end() {
		// Robot.drive.enableGyroPID(false);
		Robot.drive.arcadeDrive(0, 0);
	}
	
	@Override
	protected void interrupted() { end(); }

}
