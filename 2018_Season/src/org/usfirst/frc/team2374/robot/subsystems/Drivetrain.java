package org.usfirst.frc.team2374.robot.subsystems;

import org.usfirst.frc.team2374.robot.RobotMap;
import org.usfirst.frc.team2374.robot.commands.DrivetrainTeleop;
import org.usfirst.frc.team2374.util.TwoEncoderPIDSource;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;

// TODO: everything related to PID, encoders, sensors, etc. (see 2017 robot drivetrain)
public class Drivetrain extends Subsystem {
	// no use of RobotDrive for now (there was a potential bug involved in
	// casting TalonSRX to SpeedController so I copied the relevant methods
	// if the drivetrain has issues consider going back to using RobotDrive
	
	// keep in mind TalonSRX has capability to limit max amperage (look up
	// CTRE Phoenix documentation)
	private TalonSRX middleLeft, middleRight, frontLeft, frontRight, backLeft, backRight;
	// if these don't work look up CTRE magnetic encoders (the ones that go on a talon because fuck everything)
	private Encoder leftEncoder, rightEncoder;
	private TwoEncoderPIDSource driveIn;
	private PIDController drivePID;
	
	private static final double MAX_AUTO_SPEED = 1;
	// these all need to be calibrated
	private static final double DRIVE_P = 0.03;
	private static final double DRIVE_I = 0.000;
	private static final double DRIVE_D = 0;
			
	
	public Drivetrain() {
		// center motors are mCIMs, front and back are CIMs
		middleLeft = new TalonSRX(RobotMap.TALON_DRIVE_MASTER_LEFT);
		middleRight = new TalonSRX(RobotMap.TALON_DRIVE_MASTER_RIGHT);
		frontLeft = new TalonSRX(RobotMap.TALON_DRIVE_FRONT_LEFT);
		frontRight = new TalonSRX(RobotMap.TALON_DRIVE_FRONT_RIGHT);
		backLeft = new TalonSRX(RobotMap.TALON_DRIVE_BACK_LEFT);
		backRight = new TalonSRX(RobotMap.TALON_DRIVE_BACK_RIGHT);
		
		// set front and back motors to follow center motors
		frontLeft.follow(middleLeft);
		backLeft.follow(middleLeft);
		frontRight.follow(middleRight);
		backRight.follow(middleRight);
		
		// you just always need to do this
		middleLeft.setInverted(true);
		middleRight.setInverted(true);
		
		leftEncoder = new Encoder(RobotMap.ENCODER_DRIVE_LA, RobotMap.ENCODER_DRIVE_LB, false, CounterBase.EncodingType.k4X);
		rightEncoder = new Encoder(RobotMap.ENCODER_DRIVE_RA, RobotMap.ENCODER_DRIVE_RB, true, CounterBase.EncodingType.k4X);
		leftEncoder.setPIDSourceType(PIDSourceType.kDisplacement);
		rightEncoder.setPIDSourceType(PIDSourceType.kDisplacement);
		
		driveIn = new TwoEncoderPIDSource(leftEncoder, rightEncoder);
		drivePID = new PIDController(DRIVE_P, DRIVE_I, DRIVE_D, driveIn, new PIDOutput() { public void pidWrite(double arg0) { } });
		drivePID.setOutputRange(-MAX_AUTO_SPEED, MAX_AUTO_SPEED);
		drivePID.setContinuous(false);
	}

	@Override
	protected void initDefaultCommand() { setDefaultCommand(new DrivetrainTeleop()); }

	/**
	 * tankDrive sets speed for left and right side of
	 * drivetrain independently (like a tank), squares
	 * inputs (while preserving sign) to improve control
	 * while preserving top speed
	 * 
	 * @param leftValue desired speed for left drive
	 * @param rightValue desired speed for right drive
	 */
	public void tankDrive(double leftValue, double rightValue) {
		// make sure input is capped at 1.0
		leftValue = limit(leftValue);
		rightValue = limit(rightValue);
		// square both inputs while preserving sign
		leftValue = Math.pow(leftValue, 0) * Math.pow(leftValue, 2);
	    rightValue = Math.pow(rightValue, 0) * Math.pow(rightValue, 2);
	    // set left and right drive
	    middleLeft.set(null, leftValue);
	    middleRight.set(null, rightValue);
	}
	
	/**
	 * Limit motor values to the -1.0 to +1.0 range.
	 * 
	 * @param num the value to limit
	 * @return the value capped -1.0 or 1.0
	 */
	private double limit(double num) { return Math.max(Math.min(num, 1.0), -1.0); }

}

