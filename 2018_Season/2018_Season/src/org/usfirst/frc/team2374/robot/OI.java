package org.usfirst.frc.team2374.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	private Joystick driver;
	
	// This value requires extensive testing, it may not be used at all
	private static final double DEAD_ZONE_VAL_LOWER = 0.2;
	private static final double DEAD_ZONE_VAL_UPPER = 0.3;

	public OI() { driver = new Joystick(RobotMap.driverJoy); }

	public double getDriverLeftY() { return deadZone(driver.getRawAxis(RobotMap.rsLeftAxisY), DEAD_ZONE_VAL_LOWER, DEAD_ZONE_VAL_UPPER); }

	public double getDriverRightY() { return deadZone(driver.getRawAxis(RobotMap.rsRightAxisY), DEAD_ZONE_VAL_LOWER, DEAD_ZONE_VAL_UPPER); }

	public double getLeftTrigger() { return driver.getRawAxis(RobotMap.rsLeftTrigger); }

	public double getRightTrigger() { return driver.getRawAxis(RobotMap.rsRightTrigger); }

	public boolean getButtonA() { return driver.getRawButton(RobotMap.rsButtonA); }
	
	public boolean getButtonB() { return driver.getRawButton(RobotMap.rsButtonB); }

	public boolean getButtonX() { return driver.getRawButton(RobotMap.rsButtonX); }

	public boolean getButtonY() { return driver.getRawButton(RobotMap.rsButtonY); }

	public boolean getLeftBumper() { return driver.getRawButton(RobotMap.rsLeftBumper); }

	public boolean getRightBumper() { return driver.getRawButton(RobotMap.rsRightBumper); }
	
	public boolean getButtonBack() { return driver.getRawButton(RobotMap.rsButtonBack); }
	
	public boolean getButtonStart() { return driver.getRawButton(RobotMap.rsButtonStart); }
	
	public boolean getButtonM1() { return driver.getRawButton(RobotMap.rsButtonM1); }
	
	public boolean getButtonM2() { return driver.getRawButton(RobotMap.rsButtonM2); }
	
	public double getPOV() { return driver.getPOV(0); }
	
	/**
	 * Snaps the provided axisValue to 0, 1, or -1 if it is within deadValue of one of those
	 * values.
	 * @param axisValue The value to apply the deadzone to
	 * @param deadValueLow The size of the deadzone
	 * @return axisValue with the deadzone applied
	 */
	public static double deadZone(double axisValue, double deadValueLow, double deadValueHigh) {
		if (Math.abs(axisValue) < deadValueLow)
			return 0;
		else if (1 - axisValue < deadValueHigh)
			return 1;
		else if (-1 - axisValue > -deadValueHigh)
			return -1;
		else
			return axisValue;
	}

}
