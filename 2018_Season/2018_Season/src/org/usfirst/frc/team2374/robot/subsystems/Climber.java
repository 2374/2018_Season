package org.usfirst.frc.team2374.robot.subsystems;

import org.usfirst.frc.team2374.robot.RobotMap;
import org.usfirst.frc.team2374.robot.commands.ClimberTeleop;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem {
	private Talon climb1, climb2;
	private Victor arm;
	
	private static final double CLIMB_SPEED = 1;
	private static final double ARM_SPEED = 0.25;
	
	public Climber() {
		climb1 = new Talon(RobotMap.TALON_CLIMBER_1);
		climb2 = new Talon(RobotMap.TALON_CLIMBER_2);
		arm = new Victor(RobotMap.VICTOR_ARM);
	}
	
	@Override
	protected void initDefaultCommand() { setDefaultCommand(new ClimberTeleop()); }
	
	/**
	 * Called when starting to climb
	 */
	public void climbUp() {
		climb1.setSpeed(CLIMB_SPEED);
		climb2.setSpeed(CLIMB_SPEED);
	}
	
	/**
	 * Called when done climbing
	 */
	public void climbStop() {
		climb1.setSpeed(0);
		climb2.setSpeed(0);
	}
	
	/**
	 * Called when raising climber
	 */
	public void raiseClimber() { arm.setSpeed(-ARM_SPEED); }
	
	/**
	 * Called when lowering climber
	 */
	public void lowerClimber() { arm.setSpeed(ARM_SPEED); }
	
	/**
	 * Called when climber is in position
	 */
	public void armOff() { arm.setSpeed(0); }

}