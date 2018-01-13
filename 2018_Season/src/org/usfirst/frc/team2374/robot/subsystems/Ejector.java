package org.usfirst.frc.team2374.robot.subsystems;

import org.usfirst.frc.team2374.robot.RobotMap;
import org.usfirst.frc.team2374.robot.commands.EjectorTeleop;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Ejector extends Subsystem {
	private Talon eject1, eject2, eject3, eject4, kicker, elev1, elev2;
	// there is a very good chance that neither of the limit switches work
	private DigitalInput scaleLimitSwitch, intakeLimitSwitch;
	
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
	private static final double SCALE_RAMP_TIME = 0.25;
	private static final double SWITCH_SPEED = 0.25;
	private static final double INTAKE_SPEED = 0.1;
	private static final double KICKER_SPEED = 0.5;
	private static final double ELEVATION_SPEED = 0.3;

	public static final double ELEVATE_TIMEOUT = 5.0;
	public static final double KICKER_TIMEOUT = 2.5;
	
	public Ejector() {
		eject1 = new Talon(RobotMap.TALON_EJECTOR_1);
		eject2 = new Talon(RobotMap.TALON_EJECTOR_2);
		eject3 = new Talon(RobotMap.TALON_EJECTOR_3);
		eject4 = new Talon(RobotMap.TALON_EJECTOR_4);
		kicker = new Talon(RobotMap.TALON_KICKER);
		elev1 = new Talon(RobotMap.TALON_ELEVATION_1);
		elev2 = new Talon(RobotMap.TALON_ELEVATION_2);
		scaleLimitSwitch = new DigitalInput(RobotMap.SCALE_LIMIT_SWITCH);
		intakeLimitSwitch = new DigitalInput(RobotMap.INTAKE_LIMIT_SWITCH);
	}

	@Override
	protected void initDefaultCommand() { setDefaultCommand(new EjectorTeleop()); }
	
	// there is a very good chance that all of these are backwards
	public void scaleForward() {
		double startTime = Timer.getFPGATimestamp();
		while (Timer.getFPGATimestamp() - startTime > SCALE_RAMP_TIME) {
			eject1.setSpeed(-SCALE_RAMP_SPEED);
			eject2.setSpeed(-SCALE_RAMP_SPEED);
			eject3.setSpeed(SCALE_RAMP_SPEED);
			eject4.setSpeed(SCALE_RAMP_SPEED);
			// this shouldn't be necessary but I really don't want an infinite loop
			if (Timer.getFPGATimestamp() - startTime > SCALE_RAMP_TIME)
				break;
		}
		eject1.setSpeed(-SCALE_SPEED_1);
		eject2.setSpeed(-SCALE_SPEED_1);
		eject3.setSpeed(SCALE_SPEED_2);
		eject4.setSpeed(SCALE_SPEED_2);
	}
	
	public void switchForward() {
		eject1.setSpeed(-SWITCH_SPEED);
		eject2.setSpeed(-SWITCH_SPEED);	
		eject3.setSpeed(SWITCH_SPEED);
		eject4.setSpeed(SWITCH_SPEED);
	}
	
	public void intakeIn() {
		eject1.setSpeed(INTAKE_SPEED);
		eject2.setSpeed(INTAKE_SPEED);
		eject3.setSpeed(-INTAKE_SPEED);
		eject4.setSpeed(-INTAKE_SPEED);
	}
	
	public void flyWheelsStop() {
		eject1.setSpeed(0);
		eject2.setSpeed(0);
		eject3.setSpeed(0);
		eject4.setSpeed(0);
	}
	// push the cube into the fly wheels
	public void kick() { kicker.setSpeed(KICKER_SPEED); }
	
	public void kickerStop() { kicker.setSpeed(0); }

	public void angleUp() {
		if (!atIntakePos()) {
			elev1.setSpeed(ELEVATION_SPEED);
			elev2.setSpeed(-ELEVATION_SPEED);
		}
	}
	
	public void angleDown() {
		if (!atScalePos()) {
			elev1.setSpeed(-ELEVATION_SPEED);
			elev2.setSpeed(ELEVATION_SPEED);
		}
	}
	
	public void stopRotation() {
		elev1.setSpeed(0);
		elev2.setSpeed(0);
	}
	
	public boolean atScalePos() { return !scaleLimitSwitch.get(); }
	
	public boolean atIntakePos() { return !intakeLimitSwitch.get(); }
	
}
