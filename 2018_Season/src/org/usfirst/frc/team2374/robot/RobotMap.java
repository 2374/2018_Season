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
	public static int rsButtonA = 1;
	public static int rsButtonB = 2;
	public static int rsButtonX = 3;
	public static int rsButtonY = 4;
	public static int rsLeftBumper = 5;
	public static int rsRightBumper = 6;
	public static int rsButtonBack = 7;
	public static int rsButtonStart = 8;
	// CAN
	public static final int TALON_DRIVE_FRONT_RIGHT = 5;
	public static final int TALON_DRIVE_MIDDLE_RIGHT = 2;
	public static final int TALON_DRIVE_BACK_RIGHT = 1;
	public static final int TALON_DRIVE_FRONT_LEFT = 6;
	public static final int TALON_DRIVE_MIDDLE_LEFT = 3;
	public static final int TALON_DRIVE_BACK_LEFT = 4;
	// PWM
	public static final int VICTOR_EJECTOR_1 = 0;
	public static final int VICTOR_EJECTOR_2 = 1;
	public static final int SPARK_KICKER_1 = 2;
	public static final int SPARK_KICKER_2 = 3;
	public static final int SPARK_ELEVATION_1 = 4;
	public static final int SPARK_ELEVATION_2 = 5;
	public static final int TALON_CLIMBER_1 = 6;
	public static final int TALON_CLIMBER_2 = 7;
	public static final int TALON_ARM = 8;
	// DIO
	public static final int SCALE_LIMIT_SWITCH = 0;
	// Analog
	public static final int ULTRASONIC = 0;
}
