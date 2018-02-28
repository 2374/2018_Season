package org.usfirst.frc.team2374.robot.subsystems;

import org.usfirst.frc.team2374.robot.RobotMap;
import org.usfirst.frc.team2374.robot.commands.DrivetrainTeleop;
import org.usfirst.frc.team2374.util.TwoEncoderPIDSource;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
 
 
public class Drivetrain extends Subsystem {
	// keep in mind TalonSRX has capability to limit max amperage (look up
	// CTRE Phoenix documentation)
	private TalonSRX middleRight, middleLeft, frontRight, frontLeft, backRight, backLeft;
	private AHRS navX;
	private TwoEncoderPIDSource driveIn;
	private PIDController drivePID;
	private PIDController gyroPID;

	private static final double MAX_AUTO_SPEED_DRIVE = 0.75;
	private static final double MAX_AUTO_SPEED_GYRO = 1;
	// calibrated for 5-12 ft, can drive further but it swerves a bit
	private static final double DRIVE_P = 0.015;
	private static final double DRIVE_I = 0.00;
	private static final double DRIVE_D = 0;
	
	private static final double GYRO_P_DRIVE = 0.04;
	private static final double GYRO_I_DRIVE = 0.000;
	private static final double GYRO_D_DRIVE = 0.0000;
	// calibrated for 70-90 degrees
	private static final double GYRO_P_LONG = 0.0055;
	private static final double GYRO_I_LONG = 0.00003;
	private static final double GYRO_D_LONG = 0.00;
	// calibrated for 30-45 degrees
	private static final double GYRO_P_SHORT = 0.005;
	private static final double GYRO_I_SHORT = 0.00015;
	private static final double GYRO_D_SHORT = 0.000;
	
	public static final double GYRO_TOLERANCE_DEG = 1.0;
	public static final double DRIVE_TOLERANCE_IN = 1.0;
	  
	public Drivetrain() {
		// center motors are mCIMs, front and back are CIMs
		middleRight = new TalonSRX(RobotMap.TALON_DRIVE_MIDDLE_RIGHT);
		middleLeft = new TalonSRX(RobotMap.TALON_DRIVE_MIDDLE_LEFT);
		frontRight = new TalonSRX(RobotMap.TALON_DRIVE_FRONT_RIGHT);
		frontLeft = new TalonSRX(RobotMap.TALON_DRIVE_FRONT_LEFT);
		backRight = new TalonSRX(RobotMap.TALON_DRIVE_BACK_RIGHT);
		backLeft = new TalonSRX(RobotMap.TALON_DRIVE_BACK_LEFT);
		
		// set front and back motors to follow center motors
		frontRight.follow(middleRight);
		backRight.follow(middleRight);
		frontLeft.follow(middleLeft);
		backLeft.follow(middleLeft);

		middleLeft.setInverted(true);
		frontLeft.setInverted(true);
		backLeft.setInverted(true);
		
		// the magnetic encoders we have are plugged directly into the talons, the method below
		// sets the middle motors on each side to receive feedback from magnetic encoders, the 0
		// is there for some random setting (just go with it) and the 10 sets it to update every
		// 10 ms
		middleRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
		middleLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
		
		navX = new AHRS(SPI.Port.kMXP);
		navX.setPIDSourceType(PIDSourceType.kDisplacement);
		
		driveIn = new TwoEncoderPIDSource(middleLeft, middleRight);
		drivePID = new PIDController(DRIVE_P, DRIVE_I, DRIVE_D, driveIn, new PIDOutput() {
			@Override
			public void pidWrite(double arg0) {
			}
		});
		drivePID.setOutputRange(-MAX_AUTO_SPEED_DRIVE, MAX_AUTO_SPEED_DRIVE);
		
		gyroPID = new PIDController(GYRO_P_LONG, GYRO_I_LONG, GYRO_D_LONG, navX, new PIDOutput() {
			@Override
			public void pidWrite(double arg0) {
			}
		});
		gyroPID.setInputRange(-180.0, 180.0);
		gyroPID.setOutputRange(-MAX_AUTO_SPEED_GYRO, MAX_AUTO_SPEED_GYRO);
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
		leftValue = Math.abs(leftValue) * leftValue;
		rightValue = Math.abs(rightValue) * rightValue;
	    // set left and right drive
	    middleRight.set(ControlMode.PercentOutput, rightValue);
	    middleLeft.set(ControlMode.PercentOutput, leftValue);
	}
	
	/**
	   * Arcade drive implements single stick driving. This function lets you directly provide
	   * joystick values from any source.
	   *
	   * @param moveValue The value to use for forwards/backwards
	   * @param rotateValue The value to use for the rotate right/left
	   */
	public void arcadeDrive(double moveValue, double rotateValue) {
	    double leftMotorSpeed;
	    double rightMotorSpeed;
	    // cap values at -1.0 to 1.0
	    moveValue = limit(moveValue);
	    rotateValue = limit(rotateValue);
	    if (moveValue > 0.0) {
	    	if (rotateValue > 0.0) {
	    		leftMotorSpeed = moveValue - rotateValue;
	    		rightMotorSpeed = Math.max(moveValue, rotateValue);
	    	} 
	    	else {
	    		leftMotorSpeed = Math.max(moveValue, -rotateValue);
	    		rightMotorSpeed = moveValue + rotateValue;
	    	}
	    }
	    else {
	    	if (rotateValue > 0.0) {
	    		leftMotorSpeed = -Math.max(-moveValue, rotateValue);
	    		rightMotorSpeed = moveValue + rotateValue;
	    	} 
	    	else {
	    		leftMotorSpeed = moveValue - rotateValue;
	    		rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
	    	}
	    }
	    middleRight.set(ControlMode.PercentOutput, leftMotorSpeed);
	    middleLeft.set(ControlMode.PercentOutput, rightMotorSpeed);
	}
	/**
	 * Set PID constants for driving forward in straight line
	 */
	public void setDrivePID() { 
		drivePID.setPID(DRIVE_P, DRIVE_I, DRIVE_D);
		gyroPID.setPID(GYRO_P_DRIVE, GYRO_I_DRIVE, GYRO_D_DRIVE);
	}
	
	/**
	 * Set PID constants for turning to large angle (70-90 deg)
	 */
	public void setGyroPIDLong() { gyroPID.setPID(GYRO_P_LONG, GYRO_I_LONG, GYRO_D_LONG); }
	
	/**
	 * Set PID constants for turning to small angle (30-50 deg)
	 */
	public void setGyroPIDShort() { gyroPID.setPID(GYRO_P_SHORT, GYRO_I_SHORT, GYRO_D_SHORT); }
	
	/**
	 * Setpoint for drivetrain PID
	 * 
	 * @param inches target distance in inches
	 */
	public void setDrivePIDSetPoint(double inches) { drivePID.setSetpoint(inches); }
	
	/**
	 * Setpoint for gyro PID
	 * 
	 * @param degrees target angle in deg
	 */
	public void setGyroPIDSetPoint(double degrees) { gyroPID.setSetpoint(degrees); }
	
	/**
	 * Output for drivetrain based on drive PID
	 * 
	 * @return the number (-1.0 to 1.0) the drivetrain should be set to
	 */
	public double getDrivePIDOutput() { return drivePID.get(); }
	
	/**
	 * Output for drivetrain based on gyro PID
	 * 
	 * @return the number (-1.0 to 1.0) the drivetrain should be set to
	 */
	public double getGyroPIDOutput() { return gyroPID.get(); }
	
	/**
	 * Difference between target distance and actual distance
	 * 
	 * @return difference between target and actual distance in inches
	 */
	public double getDrivePIDError() { return drivePID.getError(); }
	
	/**
	 * Difference between target angle and actual angle
	 * 
	 * @return difference between target and actual angle in inches
	 */
	public double getGyroPIDError() { return gyroPID.getError(); }
	
	/**
	 * The robot's angle based on navX internal gyro
	 * 
	 * @return the robot's angle in deg
	 */
	public double getAngle() { return navX.getYaw(); }
	
	/**
	 * Called when enabling drive PID
	 * 
	 * @param enable enables drive PID if true, resets drive PID if false
	 */
	public void enableDrivePID(boolean enable) {
		if (enable)
			drivePID.enable();
		else
			drivePID.reset();
	}
	
	/**
	 * Called when enabling gyro PID
	 * 
	 * @param enable enables gyro PID if true, resets gyro PID if false
	 */
	public void enableGyroPID(boolean enable) {
		if (enable)
			gyroPID.enable();
		else
			gyroPID.reset();
	}
	
	/**
	 * Called when reseting encoders (for example before executing DriveToInch())
	 */
	public void resetEncoders() {
		middleLeft.setSelectedSensorPosition(0, 0, 10);
		middleRight.setSelectedSensorPosition(0, 0, 10);
	}
	
	/**
	 * Called when reseting gyro (for example before executing TurnToAngle())
	 */
	public void resetGyro() { navX.reset(); }
	
	/**
	 * Called when reseting encoders and gyro (for example before executing DriveToInch())
	 */
	public void resetAllSenors() {
		resetEncoders();
		resetGyro();
	}
	
	/**
	 * Returns raw encoder counts from left encoder, used when testing left encoder
	 * 
	 * @return left encoder counts
	 */
	public double encoderLeftRaw() { return middleLeft.getSelectedSensorPosition(0); }
	
	/**
	 * Returns raw encoder counts from right encoder, used when testing right encoder
	 * 
	 * @return right encoder counts
	 */
	public double encoderRightRaw() { return middleRight.getSelectedSensorPosition(0); }
	
	/**
	 * Convert left encoder counts to inches
	 * 
	 * @return distance in inches measured by left encoder
	 */
	public double getLeftDistanceInches() {
		return encoderCntsToInches(middleLeft.getSelectedSensorPosition(0), TwoEncoderPIDSource.EC_PER_REV_LEFT);
	}
	
	/**
	 * Convert right encoder counts to inches
	 * 
	 * @return distance in inches measured by right encoder
	 */
	public double getRightDistanceInches() {
		return encoderCntsToInches(middleRight.getSelectedSensorPosition(0), TwoEncoderPIDSource.EC_PER_REV_RIGHT);
	}
	
	/**
	 * Convert encoder counts to inches
	 * 
	 * @param counts encoder counts
	 * @param countsPerRev number of encoder counts per wheel revolution
	 * @return distance in inches measured by encoders
	 */
	public static double encoderCntsToInches(double counts, double countsPerRev) {
		return (counts / countsPerRev) * (TwoEncoderPIDSource.WHEEL_DIAMETER_INCHES * Math.PI);
	}
	
	/**
	 * Convert inches to encoder counts
	 * 
	 * @param counts inches
	 * @param countsPerRev number of encoder counts per wheel revolution
	 * @return encoder counts based on inches travelled
	 */
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

