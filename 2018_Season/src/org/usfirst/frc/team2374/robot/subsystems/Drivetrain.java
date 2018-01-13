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
	// TODO(CR): Capitalization and punctuation
	// no use of RobotDrive for now (there was a potential bug involved in
	// casting TalonSRX to SpeedController so I copied the relevant methods
	// if the drivetrain has issues consider going back to using RobotDrive
	
	// keep in mind TalonSRX has capability to limit max amperage (look up
	// CTRE Phoenix documentation)
	// TODO(CR): Avoid unnecessary abbreviations - front/back isn't much longer and improves
	//           readability. Embrace the Java verbosity.
	private TalonSRX masterLeft, masterRight, fLeft, fRight, bLeft, bRight;
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
		// TODO(CR): front/middle/back or front/center/back makes more sense to me than
		//           front/master/back
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
		
		// TODO(CR): Adhere to a max line length. 80 is common for most languages, but Java is
		//           generally either 100 or 120 characters.
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

	// TODO(CR): Finally some JavaDoc comments! This was copied from 2017, wasn't it? Param/return
	//           value lines generally come after the description.
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
		// make sure input is capped at 1.0
		leftValue = limit(leftValue);
		rightValue = limit(rightValue);
		// TODO(CR): Each set of 4 lines can be simplified to:
		// leftValue = Math.pow(leftValue, 0) * Math.pow(leftValue, 2);
		// square both inputs while preserving sign
		if (leftValue >= 0.0)
	        leftValue = Math.pow(leftValue, 2);
	    else
	        leftValue = -Math.pow(leftValue, 2);
	    if (rightValue >= 0.0)
	        rightValue = Math.pow(rightValue, 2);
	    else
	        rightValue = -Math.pow(rightValue, 2);
	    // set left and right drive
	    masterLeft.set(null, leftValue);
	    masterRight.set(null, rightValue);
	}
	
	// TODO(CR): Nit: Missing param and return in JavaDoc
	/**
	 * Limit motor values to the -1.0 to +1.0 range.
	 */
	private double limit(double num) {
		// TODO(CR): This can be simplified to one line:
		// return Math.max(Math.min(num, 1.0), -1.0);
		if (num > 1.0)
			return 1.0;
	    if (num < -1.0)
	    	return -1.0;
	    return num;
	}

}

