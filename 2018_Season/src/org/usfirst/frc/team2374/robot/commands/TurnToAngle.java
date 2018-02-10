package org.usfirst.frc.team2374.robot.commands;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnToAngle extends Command {
	private double targetAngleDegrees;
	private boolean PIDVersion;
	
	public static final boolean LONG = true;
	public static final boolean SHORT = false;
	
	public TurnToAngle(double angle, boolean b) {
		requires(Robot.drive);
		targetAngleDegrees = angle;
		if (b)
			PIDVersion = LONG;
		else
			PIDVersion = SHORT;
	}
	
	@Override
	protected void initialize() {
		Robot.drive.resetGyro();
		if (LONG)
			Robot.drive.setGyroPIDLong();
		else if (SHORT)
			Robot.drive.setGyroPIDShort();
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
		return Math.abs(Robot.drive.getGyroPIDError()) <= Drivetrain.GYRO_TOLERANCE_DEG;
	}
	
	@Override
	protected void end() {
		Robot.drive.enableGyroPID(false);
		Robot.drive.arcadeDrive(0, 0);
	}
	
	@Override
	protected void interrupted() { end(); }

}
