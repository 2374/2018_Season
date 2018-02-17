package org.usfirst.frc.team2374.robot.subsystems;

import org.usfirst.frc.team2374.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Ultrasonic extends Subsystem{
	private AnalogInput ultrasonicSensor;
	
	public static final double TOLERANCE_IN = 10;
	public static final double MIN_DISTANCE_IN = 6.0;
	
	public Ultrasonic() { ultrasonicSensor = new AnalogInput(RobotMap.ULTRASONIC); }

	@Override
	protected void initDefaultCommand() { }
	
	/**
	 * The average voltage of the ultrasonic sensor
	 * 
	 * @return average voltage
	 */
	public double getVoltage() { return ultrasonicSensor.getAverageVoltage(); }
	
	/**
	 * The distance in inches measured by ultrasonic sensor
	 * 
	 * @return distance in inches
	 */
	public double getDistanceInches() { return convertVoltageToDistance(getVoltage()); }
	
	/**
	 * Converts voltage to inches
	 * 
	 * @param volts the voltage to convert
	 * @return inches
	 */
	private double convertVoltageToDistance(double volts)  { return volts * (512/5); }

}
