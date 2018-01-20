package org.usfirst.frc.team2374.robot.subsystems;

import org.usfirst.frc.team2374.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Ultrasonic extends Subsystem{
	private AnalogInput ultrasonicSensor;
	
	public static final double TOLERANCE_IN = 0.0;
	public static final double MIN_DISTANCE_IN = 6.0;
	
	public Ultrasonic() { ultrasonicSensor = new AnalogInput(RobotMap.ULTRASONIC); }

	@Override
	protected void initDefaultCommand() { }
	
	public double getVoltage() { return ultrasonicSensor.getAverageVoltage(); }
	
	public double getDistanceInches() { return convertVoltageToDistance(getVoltage()); }
	
	private double convertVoltageToDistance(double d)  { return d * (512/5); }

}
