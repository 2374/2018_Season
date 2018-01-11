package org.usfirst.frc.team2374.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// joystick ports
	public static int driverJoy = 0;
	// joystick buttons
	public static int rsLeftAxisY = 1;
	public static int rsRightAxisY = 5;
	public static int rsLeftTrigger = 2;
	public static int rsRightTrigger = 3;
	public static int rsLeftBumper = 5;
	public static int rsRightBumper = 6;
	public static int rsButtonA = 1;
	public static int rsButtonB = 2;
	public static int rsButtonX = 3;
	public static int rsButtonY = 4;
	// CAN
	public static final int TALON_DRIVE_MASTER_LEFT = 1;
	public static final int TALON_DRIVE_MASTER_RIGHT = 2;
	public static final int TALON_DRIVE_FRONT_LEFT = 3;
	public static final int TALON_DRIVE_FRONT_RIGHT = 4;
	public static final int TALON_DRIVE_BACK_LEFT = 5;
	public static final int TALON_DRIVE_BACK_RIGHT = 6;
}
