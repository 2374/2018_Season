package org.usfirst.frc.team2374.robot.subsystems;

import org.usfirst.frc.team2374.robot.RobotMap;
import org.usfirst.frc.team2374.robot.commands.DrivetrainTeleop;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

// TODO: everything related to PID, encoders, sensors, etc. (see 2017 robot drivetrain)
public class Drivetrain extends Subsystem {
	// no use of RobotDrive for now (there was a potential bug involved in
	// casting TalonSRX to SpeedController so I copied the relevant methods
	// if the drivetrain has issues consider going back to using RobotDrive
	
	// keep in mind TalonSRX has capability to limit max amperage (look up
	// CTRE Phoenix documentation)
	private TalonSRX masterLeft, masterRight, fLeft, fRight, bLeft, bRight;
	
	public Drivetrain() {
		// center motors (masters) are mCIMs, front and back are CIMs
		masterLeft = new TalonSRX(RobotMap.TALON_DRIVE_MASTER_LEFT);
		masterRight = new TalonSRX(RobotMap.TALON_DRIVE_MASTER_RIGHT);
		fLeft = new TalonSRX(RobotMap.TALON_DRIVE_FRONT_LEFT);
		fRight = new TalonSRX(RobotMap.TALON_DRIVE_FRONT_RIGHT);
		bLeft = new TalonSRX(RobotMap.TALON_DRIVE_BACK_LEFT);
		bRight = new TalonSRX(RobotMap.TALON_DRIVE_BACK_RIGHT);
		
		// set front and back motors to follow center motors
		fLeft.follow(masterLeft);
		bLeft.follow(masterLeft);
		fRight.follow(masterRight);
		bRight.follow(masterRight);
		
		// you just always need to do this
		masterLeft.setInverted(true);
		masterRight.setInverted(true);
	}

	@Override
	protected void initDefaultCommand() { setDefaultCommand(new DrivetrainTeleop()); }

	/**
	 * 
	 * @param leftValue desired speed for left drive
	 * @param rightValue desired speed for right drive
	 * 
	 * tankDrive sets speed for left and right side of
	 * drivetrain independently (like a tank), squares
	 * inputs (while preserving sign) to improve control
	 * while preserving top speed
	 */
	public void tankDrive(double leftValue, double rightValue) {
		leftValue = limit(leftValue);
		rightValue = limit(rightValue);
		if (leftValue >= 0.0)
	        leftValue = leftValue * leftValue;
	    else
	        leftValue = -(leftValue * leftValue);
	    if (rightValue >= 0.0)
	        rightValue = rightValue * rightValue;
	    else
	        rightValue = -(rightValue * rightValue);
	    masterLeft.set(null, leftValue);
	    masterRight.set(null, rightValue);
	}
	
	 /**
	   * Limit motor values to the -1.0 to +1.0 range.
	   */
	private double limit(double num) {
		if (num > 1.0)
			return 1.0;
	    if (num < -1.0)
	    	return -1.0;
	    return num;
	}

}

