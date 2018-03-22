package org.usfirst.frc.team2374.robot.subsystems;

import org.usfirst.frc.team2374.robot.RobotMap;
import org.usfirst.frc.team2374.robot.commands.EjectorTeleop;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Ejector extends Subsystem {
	private Victor eject1, eject2;
	private Spark kicker1, kicker2, elev1, elev2;
	private DigitalInput scaleLimitSwitch, intakeLimitSwitch;
	private double startTime = 0;
	
	private static double scaleSpeed = 0.975;
	private static double scaleSpeedLow = 0.85;
	
	private static final double SCALE_RAMP_SPEED = 0.5;
	private static final double KICKER_SPEED = 1;
	
	private static final double SCALE_RAMP_TIME_S = 0.25;
	
	private static final double SWITCH_SPEED = 0.65;
	
	private static final double INTAKE_SPEED = 0.5;
	 
	private static final double SCALE_KICKER_RAMP_TIME_S = 1.5;
	private static final double LOW_SCALE_KICKER_TIME_S = 1;
	
	private static final double ELEVATION_SPEED = 0.425;
	
	public Ejector() {
		eject1 = new Victor(RobotMap.VICTOR_EJECTOR_1);
		eject2 = new Victor(RobotMap.VICTOR_EJECTOR_2);
		kicker1 = new Spark(RobotMap.SPARK_KICKER_1);
		kicker2 = new Spark(RobotMap.SPARK_KICKER_2);
		elev1 = new Spark(RobotMap.SPARK_ELEVATION_1);
		elev2 = new Spark(RobotMap.SPARK_ELEVATION_2);
		scaleLimitSwitch = new DigitalInput(RobotMap.SCALE_LIMIT_SWITCH);
		intakeLimitSwitch = new DigitalInput(RobotMap.INTAKE_LIMIT_SWITCH);
	}

	@Override
	protected void initDefaultCommand() { setDefaultCommand(new EjectorTeleop()); }
	
	/**
	 * Called during the game when raising speed for mid and high scale (max speed is 1)
	 */
	public void scaleSpeedUp() { 
		scaleSpeed += 0.25;
		if (scaleSpeed > 1)
			scaleSpeed = 1;
	}
	
	/**
	 * Called during the game when lowering speed for mid and high scale (min speed is 0.85)
	 */
	public void scaleSpeedDown() {
		scaleSpeed -= 0.25;
		if (scaleSpeed < 0.85)
			scaleSpeed = 0.85;
	}
	
	/**
	 * Called during the game when raising speed for low scale (max speed is 0.9)
	 */
	public void scaleSpeedLowUp() { 
		scaleSpeedLow += 0.25;
		if (scaleSpeedLow > 0.9)
			scaleSpeedLow = 0.9;
	}
	
	/**
	 * Called during the game when lowering speed for low scale (min speed is 0.75)
	 */
	public void scaleSpeedLowDown() {
		scaleSpeedLow -= 0.25;
		if (scaleSpeedLow < 0.75)
			scaleSpeedLow = 0.75;
	}
	
	/**
	 * Displays scaleSpeed and scaleSpeedLow on SmartDashboard
	 */
	public void displayScaleSpeed() {
		SmartDashboard.putNumber("scale speed", scaleSpeed);
		SmartDashboard.putNumber("scale speed low", scaleSpeedLow);
	}
	
	/**
	 * Called when delivering to scale, sets the left and right flywheels
	 * to slightly different speeds in order to give the cube some spin
	 * and sets the flywheels to an intermediate speed for a fraction of
	 * a second before moving to full speed
	 */
	public void scaleForward() {
		if (startTime == 0)
			startTime = Timer.getFPGATimestamp();
		
		if (Timer.getFPGATimestamp() - startTime > SCALE_RAMP_TIME_S)
			setEjectorSpeed(scaleSpeed, scaleSpeed);
		else
			setEjectorSpeed(SCALE_RAMP_SPEED, SCALE_RAMP_SPEED);
		
		if (Timer.getFPGATimestamp() - startTime > SCALE_KICKER_RAMP_TIME_S)
			setKickerSpeed(KICKER_SPEED, KICKER_SPEED);
		else
			setKickerSpeed(0, 0);		
	}
	
	/**
	 * Called when delivering to low scale, same as regular but with
	 * lower ramp time and firing speed
	 */
	public void lowScaleForward() {
		if (startTime == 0)
			startTime = Timer.getFPGATimestamp();
		
		setEjectorSpeed(scaleSpeedLow, scaleSpeedLow);
		
		if (Timer.getFPGATimestamp() - startTime > LOW_SCALE_KICKER_TIME_S)
			setKickerSpeed(KICKER_SPEED, KICKER_SPEED);
		else
			setKickerSpeed(0, 0);		
	}
	
	/**
	 * Called when delivering to switch
	 */
	public void switchForward() {
		setEjectorSpeed(SWITCH_SPEED, SWITCH_SPEED);
		setKickerSpeed(scaleSpeed, scaleSpeed);
		startTime = 0;
	}
	
	/**
	 * Called when intaking cubes
	 */
	public void intake() {
		setEjectorSpeed(-INTAKE_SPEED, -INTAKE_SPEED);
		setKickerSpeed(-INTAKE_SPEED, -INTAKE_SPEED);
		startTime = 0;
		
	}
	
	/**
	 * called when stopping flywheels
	 */
	public void flyWheelsStop() {
		setEjectorSpeed(0, 0);
		setKickerSpeed(0, 0);
		startTime = 0;
	}
	
	/**
	 * Set the flywheel speed, allows the left and right flywheels
	 * to be set to different values, regardless of speed the left 
	 * and right flywheels spin in opposite directions
	 * 
	 * @param speed1 left flywheel speed
	 * @param speed2 right flywheel speed
	 */
	private void setEjectorSpeed(double speed1, double speed2) {
		eject1.setSpeed(speed1);
		eject2.setSpeed(speed2);
	}
	
	/**
	 * Set the kicker wheel speed, the left and right kicker
	 * wheels spin in opposite directions
	 * 
	 * @param speed1 left kicker wheel speed
	 * @param speed2 right kicker wheel speed
	 */
	private void setKickerSpeed(double speed1, double speed2) {
		kicker1.setSpeed(speed1);
		kicker2.setSpeed(-speed2);
	}
	
	/**
	 * Called when raising the ejector for delivery to scale
	 */
	public void angleUp(boolean useLimitSwitch) {
		if (!atScalePos() || !useLimitSwitch) {
			elev1.setSpeed(ELEVATION_SPEED);
			elev2.setSpeed(-ELEVATION_SPEED);
		}
		else
			stopRotation();
	}
	
	/**
	 * Called when lowering the ejector for cube intake
	 */
	public void angleDown() {
		elev1.setSpeed(-ELEVATION_SPEED);
		elev2.setSpeed(ELEVATION_SPEED);	
	}
	
	/**
	 * Called when ejector is in appropriate position
	 */
	public void stopRotation() {
		elev1.setSpeed(0);
		elev2.setSpeed(0);
	}
	
	/**
	 * Called when checking ejector angle
	 * @return true if at scale angle, false otherwise
	 */
	public boolean atScalePos() { return scaleLimitSwitch.get(); }
	
	/**
	 * Called when checking ejector angle
	 * @return true if at scale angle, false otherwise
	 */
	public boolean atIntakePos() { return intakeLimitSwitch.get(); }
	
}
