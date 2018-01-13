package org.usfirst.frc.team2374.robot.subsystems;

import org.usfirst.frc.team2374.robot.RobotMap;
import org.usfirst.frc.team2374.robot.commands.ClimberTeleop;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem {
	private Talon climb1, climb2, fan;
	
	public Climber() {
		climb1 = new Talon(RobotMap.TALON_CLIMBER_1);
		climb2 = new Talon(RobotMap.TALON_CLIMBER_2);
		fan = new Talon(RobotMap.TALON_FAN);
	}
	
	@Override
	protected void initDefaultCommand() { setDefaultCommand(new ClimberTeleop()); }
	
	/**
	 * Called when starting to climb
	 */
	public void climbUp() {
		climb1.setSpeed(1);
		climb2.setSpeed(1);
	}
	
	/**
	 * Called when done climbing
	 */
	public void climbStop() {
		climb1.setSpeed(0);
		climb2.setSpeed(0);
	}
	
	/**
	 * Called when inflating tube
	 */
	public void fan() { fan.setSpeed(1); }
	
	/**
	 * Called when deflating tube
	 */
	public void fanOff() { fan.setSpeed(0); }

}