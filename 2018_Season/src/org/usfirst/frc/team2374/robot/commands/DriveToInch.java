package org.usfirst.frc.team2374.robot.commands;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class DriveToInch extends Command {

	protected double wantedDistanceInches;
	
	public DriveToInch(double targetDistance) {
		this.wantedDistanceInches = targetDistance;
	}
	
	@Override
	protected void initialize() {
		Robot.drive.resetAllSenors();
		Robot.drive.setPID();
		Timer.delay(0.1);
		Robot.drive.setDrivePIDSetPoint(wantedDistanceInches);
		Robot.drive.setGyroPIDSetPoint(0);
		Robot.drive.enableDrivePID(true);
		Robot.drive.enableGyroPID(true);
	}
	
	@Override
	protected void execute() {
		Robot.drive.arcadeDrive(Robot.drive.getDrivePIDOutput(), Robot.drive.getGyroPIDOutput());	
	}
	
	@Override
	protected boolean isFinished() {
		return Math.abs(Robot.drive.getDrivePIDError()) <= Drivetrain.DRIVE_TOLERANCE_IN;
	}
	
	@Override
	protected void end() {
		Robot.drive.enableDrivePID(false);
		Robot.drive.enableGyroPID(false);
		Robot.drive.arcadeDrive(0, 0);
	}
	
	@Override
	protected void interrupted() {
		end();
	}
}
