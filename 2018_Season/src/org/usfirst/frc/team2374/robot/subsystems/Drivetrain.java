package org.usfirst.frc.team2374.robot.subsystems;

import org.usfirst.frc.team2374.robot.RobotMap;
import org.usfirst.frc.team2374.robot.commands.DrivetrainTeleop;
import org.usfirst.frc.team2374.util.TwoEncoderPIDSource;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.hal.HAL;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tInstances;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tResourceType;

// TODO: everything related to PID, encoders, sensors, etc. (see 2017 robot drivetrain)
public class Drivetrain extends Subsystem {
	// no use of RobotDrive for now (there was a potential bug involved in
	// casting TalonSRX to SpeedController so I copied the relevant methods
	// if the drivetrain has issues consider going back to using RobotDrive
	
	// keep in mind TalonSRX has capability to limit max amperage (look up
	// CTRE Phoenix documentation)
	private TalonSRX middleLeft, middleRight, frontLeft, frontRight, backLeft, backRight;
	private AHRS navX;
	// if these don't work look up CTRE magnetic encoders (the ones that go on a talon because fuck everything)
	private Encoder leftEncoder, rightEncoder;
	private TwoEncoderPIDSource driveIn;
	private PIDController drivePID;
	private PIDController gyroPID;
	 
	private static final double MAX_AUTO_SPEED = 1;
	// these all need to be calibrated
	private static final double DRIVE_P = 0.03;
	private static final double DRIVE_I = 0.000;
	private static final double DRIVE_D = 0;
	private static final double GYRO_P = 0.008;
	private static final double GYRO_I = 0.00045;
	private static final double GYRO_D = 0;
	
	public static final double GYRO_TOLERANCE_DEG = 0;
	public static final double DRIVE_TOLERANCE_IN = 0;
	
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
		
		navX = new AHRS(SPI.Port.kMXP);
		navX.setPIDSourceType(PIDSourceType.kDisplacement);
		
		driveIn = new TwoEncoderPIDSource(leftEncoder, rightEncoder);
		drivePID = new PIDController(DRIVE_P, DRIVE_I, DRIVE_D, driveIn, new PIDOutput() {
			@Override
			public void pidWrite(double arg0) {
			}
		});
		drivePID.setOutputRange(-MAX_AUTO_SPEED, MAX_AUTO_SPEED);
		drivePID.setContinuous(false);
		
		gyroPID = new PIDController(GYRO_P, GYRO_I, GYRO_D, navX, new PIDOutput() {
			@Override
			public void pidWrite(double arg0) {
			}
		});
		gyroPID.setInputRange(-180.0, 180.0);
		gyroPID.setOutputRange(-MAX_AUTO_SPEED, MAX_AUTO_SPEED);
		gyroPID.setContinuous(true);
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
	   * Arcade drive implements single stick driving. This function lets you directly provide
	   * joystick values from any source.
	   *
	   * @param moveValue The value to use for forwards/backwards
	   * @param rotateValue The value to use for the rotate right/left
	   * @param squaredInputs If set, decreases the sensitivity at low speeds
	   */
	public void arcadeDrive(double moveValue, double rotateValue) {
	    double leftMotorSpeed;
	    double rightMotorSpeed;
	    // cap values at -1.0 to 1.0
	    moveValue = limit(moveValue);
	    rotateValue = limit(rotateValue);
	    // square inputs
		moveValue = Math.pow(moveValue, 0) * Math.pow(moveValue, 2);
	    rotateValue = Math.pow(rotateValue, 0) * Math.pow(rotateValue, 2);
	    if (moveValue > 0.0)
	    	if (rotateValue > 0.0) {
	    		leftMotorSpeed = moveValue - rotateValue;
	    		rightMotorSpeed = Math.max(moveValue, rotateValue);
	    	} 
	    	else {
	    		leftMotorSpeed = Math.max(moveValue, -rotateValue);
	    		rightMotorSpeed = moveValue + rotateValue;
	    	}
	    else
	    	if (rotateValue > 0.0) {
	    		leftMotorSpeed = -Math.max(-moveValue, rotateValue);
	    		rightMotorSpeed = moveValue + rotateValue;
	    	} 
	    	else {
	    		leftMotorSpeed = moveValue - rotateValue;
	    		rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
	    	}
	    middleLeft.set(null, leftMotorSpeed);
	    middleRight.set(null, rightMotorSpeed);
	}
	
	// this isn't too useful now but it will be relevant if we need
	// multiple PID constants for different distances
	public void setPID() {
		drivePID.setPID(DRIVE_P, DRIVE_I, DRIVE_D);
		gyroPID.setPID(GYRO_P, GYRO_I, GYRO_D);
	}
	
	public void setDrivePIDSetPoint(double inches) { drivePID.setSetpoint(inches); }
	
	public void setGyroPIDSetPoint(double degrees) { gyroPID.setSetpoint(degrees); }
	
	public double getDrivePIDOutput() { return drivePID.get(); }
	
	public double getGyroPIDOutput() { return gyroPID.get(); }
	
	public double getDrivePIDError() { return drivePID.getError(); }
	
	public double getGyroPIDError() { return gyroPID.getError(); }
	
	public double getAngle() { return navX.getYaw(); }
	
	public void enableDrivePID(boolean enable) {
		if (enable)
			drivePID.enable();
		else
			drivePID.reset();
	}

	public void enableGyroPID(boolean enable) {
		if (enable)
			gyroPID.enable();
		else
			gyroPID.reset();
	}

	public void resetEncoders() {
		leftEncoder.reset();
		rightEncoder.reset();
	}

	public void resetGyro() { navX.reset(); }

	public void resetAllSenors() {
		resetEncoders();
		resetGyro();
	}
	
	public double getLeftDistanceInches() {
		return encoderCntsToInches(leftEncoder.getDistance(), TwoEncoderPIDSource.EC_PER_REV_LEFT);
	}

	public double getRightDistanceInches() {
		return encoderCntsToInches(rightEncoder.getDistance(), TwoEncoderPIDSource.EC_PER_REV_RIGHT);
	}

	public static double encoderCntsToInches(double counts, double countsPerRev) {
		return (counts / countsPerRev) * (TwoEncoderPIDSource.WHEEL_DIAMETER_INCHES * Math.PI);
	}

	public static double inchesToEncoderCnts(double inches, double countsPerRev) {
		return inches * countsPerRev / (TwoEncoderPIDSource.WHEEL_DIAMETER_INCHES * Math.PI);
	}
	
	/**
	 * Limit motor values to the -1.0 to +1.0 range.
	 * 
	 * @param num the value to limit
	 * @return the value capped -1.0 or 1.0
	 */
	private double limit(double num) { return Math.max(Math.min(num, 1.0), -1.0); }

}

