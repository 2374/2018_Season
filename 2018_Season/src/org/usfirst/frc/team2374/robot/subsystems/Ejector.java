package org.usfirst.frc.team2374.robot.subsystems;

import org.usfirst.frc.team2374.robot.RobotMap;
import org.usfirst.frc.team2374.robot.commands.EjectorTeleop;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Ejector extends Subsystem {
	private Talon eject1, eject2, kicker1, kicker2, elev1, elev2;
	// there is a very good chance that neither of the limit switches work
	private DigitalInput scaleLimitSwitch, intakeLimitSwitch;
	private double startTime = 0;
	
	// two scale speeds mean one side of the box gets an extra
	// push, causing the box to start rotating
	// these numbers are purely hypothetical, but 1.0 and 0.9-0.95
	// are probably right
	private static final double SCALE_SPEED_1 = 1.0;
	private static final double SCALE_SPEED_2 = 0.95;
	// experimental, idea is to set ejector to an intermediate speed
	// for a fraction of a second before going to full speed, requires
	// testing to determine whether we should use this, what the speed
	// should be, and how long it should ramp up for
	private static final double SCALE_RAMP_SPEED = 0.7;
	private static final double SCALE_RAMP_TIME_S = 0.25;
	private static final double SWITCH_SPEED = 0.25;
	private static final double INTAKE_SPEED = 0.3;
	private static final double KICKER_SPEED = 0.1;
	private static final double KICKER_RAMP_TIME_S = 1;
	private static final double ELEVATION_SPEED = 0.3;

	public static final double ELEVATE_TIMEOUT_S = 5.0;
	
	public Ejector() {
		eject1 = new Talon(RobotMap.TALON_EJECTOR_1);
		eject2 = new Talon(RobotMap.TALON_EJECTOR_2);
		kicker1 = new Talon(RobotMap.TALON_KICKER_1);
		kicker2 = new Talon(RobotMap.TALON_KICKER_2);
		elev1 = new Talon(RobotMap.TALON_ELEVATION_1);
		elev2 = new Talon(RobotMap.TALON_ELEVATION_2);
		scaleLimitSwitch = new DigitalInput(RobotMap.SCALE_LIMIT_SWITCH);
		intakeLimitSwitch = new DigitalInput(RobotMap.INTAKE_LIMIT_SWITCH);
	}

	@Override
	protected void initDefaultCommand() { setDefaultCommand(new EjectorTeleop()); }
	
	/**
	 * Called when delivering to scale, sets the left and right flywheels
	 * to slightly different speeds in order to give the cube some spin
	 * and sets the flywheels to an intermediate speed for a fraction of
	 * a second before moving to full speed
	 */
	// there is a very good chance that all of these are backwards
	public void scaleForward() {
		if (startTime == 0)
			startTime = Timer.getFPGATimestamp();
		if (Timer.getFPGATimestamp() - startTime > SCALE_RAMP_TIME_S)
			setEjectorSpeed(SCALE_SPEED_1, SCALE_SPEED_2);
		else
			setEjectorSpeed(SCALE_RAMP_SPEED, SCALE_RAMP_SPEED);
		if (Timer.getFPGATimestamp() - startTime > KICKER_RAMP_TIME_S)
			setKickerSpeed(SCALE_SPEED_1);
		else
			setKickerSpeed(KICKER_SPEED);
			
	}
	
	/**
	 * Called when delivering to switch
	 */
	public void switchForward() {
		setEjectorSpeed(SWITCH_SPEED, SWITCH_SPEED);
		setKickerSpeed(SWITCH_SPEED);
		startTime = 0;
	}
	
	/**
	 * Called when intaking cubes
	 */
	public void intakeIn() {
		setEjectorSpeed(-INTAKE_SPEED, -INTAKE_SPEED);
		setKickerSpeed(-INTAKE_SPEED);
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
		eject1.setSpeed(speed1);
		eject2.setSpeed(-speed1);
	}
	
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
	}
	
	/**
	 * Called when lowering the ejector for cube intake
	 */
	public void angleDown() {
		if (!atIntakePos()) {
			elev1.setSpeed(-ELEVATION_SPEED);
			elev2.setSpeed(ELEVATION_SPEED);
		}
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
	public boolean atScalePos() { return !scaleLimitSwitch.get(); }
	
	/**
	 * Called when checking ejector angle
	 * @return true if at intake angle, false otherwise
	 */
	public boolean atIntakePos() { return !intakeLimitSwitch.get(); }
	
}
