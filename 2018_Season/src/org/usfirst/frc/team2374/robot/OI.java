package org.usfirst.frc.team2374.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	Joystick driver;

	public OI() {
		driver = new Joystick(RobotMap.driverJoy);
	}

	public double getDriverLeftY() { return driver.getRawAxis(RobotMap.rsLeftAxisY); }

	public double getDriverRightY() { return driver.getRawAxis(RobotMap.rsRightAxisY); }

	public double getLeftTrigger() { return driver.getRawAxis(RobotMap.rsLeftTrigger); }

	public double getRightTrigger() { return driver.getRawAxis(RobotMap.rsRightTrigger); }

	public boolean getButtonA() { return driver.getRawButton(RobotMap.rsButtonA); }
	
	public boolean getButtonB() { return driver.getRawButton(RobotMap.rsButtonB); }

	public boolean getButtonX() { return driver.getRawButton(RobotMap.rsButtonX); }

	public boolean getButtonY() { return driver.getRawButton(RobotMap.rsButtonY); }

	public boolean getLeftBumper() { return driver.getRawButton(RobotMap.rsLeftBumper); }

	public boolean getRightBumper() { return driver.getRawButton(RobotMap.rsRightBumper); }

}
