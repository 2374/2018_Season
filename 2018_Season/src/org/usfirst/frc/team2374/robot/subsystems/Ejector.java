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
	private DigitalInput scaleLimitSwitch;
	private double startTime = 0;
	
	private static final double SCALE_SPEED_1 = 1.0;
	private static final double SCALE_SPEED_2 = 0.95;
	private static final double SCALE_RAMP_SPEED = 0.5;
	private static final double SCALE_RAMP_TIME_S = 0.25;
	
	private static final double SWITCH_SPEED = 0.75;
	
	private static final double INTAKE_SPEED_FAST = 0.5;
	
	private static final double KICKER_SPEED = 0.5;
	private static final double KICKER_TIME_S = 0.3;
	private static final double SCALE_KICKER_RAMP_TIME_S = 1.5;
	private static final double LOW_SCALE_KICKER_TIME_S = 1;
	
	private static final double ELEVATION_SPEED = 0.3;
	
	public Ejector() {
		eject1 = new Victor(RobotMap.VICTOR_EJECTOR_1);
		eject2 = new Victor(RobotMap.VICTOR_EJECTOR_2);
		kicker1 = new Spark(RobotMap.SPARK_KICKER_1);
		kicker2 = new Spark(RobotMap.SPARK_KICKER_2);
		elev1 = new Spark(RobotMap.SPARK_ELEVATION_1);
		elev2 = new Spark(RobotMap.SPARK_ELEVATION_2);
		scaleLimitSwitch = new DigitalInput(RobotMap.SCALE_LIMIT_SWITCH);
	}

	@Override
	protected void initDefaultCommand() { setDefaultCommand(new EjectorTeleop()); }
	
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
			setEjectorSpeed(SCALE_SPEED_1, SCALE_SPEED_2);
		else
			setEjectorSpeed(SCALE_RAMP_SPEED, SCALE_RAMP_SPEED);
		
		if (Timer.getFPGATimestamp() - startTime > SCALE_KICKER_RAMP_TIME_S) {
			if (Timer.getFPGATimestamp() - startTime < SCALE_KICKER_RAMP_TIME_S + KICKER_TIME_S)
				setKickerSpeed(SCALE_SPEED_1);
			else if (Timer.getFPGATimestamp() - startTime < SCALE_KICKER_RAMP_TIME_S + 2 * KICKER_TIME_S)
				setKickerSpeed(KICKER_SPEED);
			else
				setKickerSpeed(0);
		}
		else
			setKickerSpeed(0);		
	}
	
	/**
	 * Called when delivering to low scale, same as regular but with
	 * lower ramp time and firing speed
	 */
	public void lowScaleForward() {
		if (startTime == 0)
			startTime = Timer.getFPGATimestamp();
		
		setEjectorSpeed(SWITCH_SPEED, SWITCH_SPEED);
		
		if (Timer.getFPGATimestamp() - startTime > LOW_SCALE_KICKER_TIME_S) {
			if (Timer.getFPGATimestamp() - startTime < LOW_SCALE_KICKER_TIME_S + KICKER_TIME_S)
				setKickerSpeed(SCALE_SPEED_1);
			else if (Timer.getFPGATimestamp() - startTime < LOW_SCALE_KICKER_TIME_S + 2 * KICKER_TIME_S)
				setKickerSpeed(KICKER_SPEED);
			else
				setKickerSpeed(0);
		}
		else
			setKickerSpeed(0);		
	}
	
	/**
	 * Called when delivering to switch
	 */
	public void switchForward() {
		setEjectorSpeed(SWITCH_SPEED, SWITCH_SPEED);
		setKickerSpeed(SCALE_SPEED_1);
		startTime = 0;
	}
	
	/**
	 * Called when intaking cubes
	 */
	public void intake() {
		setEjectorSpeed(-INTAKE_SPEED_FAST, -INTAKE_SPEED_FAST);
		setKickerSpeed(-INTAKE_SPEED_FAST);
		startTime = 0;
		
	}
	
	/**
	 * called when stopping flywheels
	 */
	public void flyWheelsStop() {
		setEjectorSpeed(0, 0);
		setKickerSpeed(0);
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
		eject1.setSpeed(-speed1);
		eject2.setSpeed(-speed2);
	}
	
	/**
	 * Set the kicker wheel speed, the left and right kicker
	 * wheels spin in opposite directions
	 * 
	 * @param speed1 left kicker wheel speed
	 * @param speed2 right kicker wheel speed
	 */
	private void setKickerSpeed(double speed) {
		kicker1.setSpeed(speed);
		kicker2.setSpeed(-speed);
	}
	
	/**
	 * Called when raising the ejector for delivery to scale
	 */
	public void angleUp() {
		if (!atScalePos()) {
			elev1.setSpeed(ELEVATION_SPEED);
			elev2.setSpeed(-ELEVATION_SPEED);
		}
		else {
			SmartDashboard.putString("Scale LS Down", "Scale LS Down");
			stopRotation();
		}
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
	
}
