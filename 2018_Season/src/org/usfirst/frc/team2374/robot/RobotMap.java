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
	public static int rsButtonBack = 7;
	public static int rsButtonStart = 8;
	// CAN
	public static final int TALON_DRIVE_FRONT_LEFT = 0;
	public static final int TALON_DRIVE_FRONT_RIGHT = 1;
	public static final int TALON_DRIVE_MASTER_LEFT = 2;
	public static final int TALON_DRIVE_MASTER_RIGHT = 3;
	public static final int TALON_DRIVE_BACK_LEFT = 4;
	public static final int TALON_DRIVE_BACK_RIGHT = 5;
	// PWM
	public static final int TALON_EJECTOR_1 = 0;
	public static final int TALON_EJECTOR_2 = 1;
	public static final int TALON_EJECTOR_3 = 2;
	public static final int TALON_EJECTOR_4 = 3;
	public static final int TALON_KICKER = 4;
	public static final int TALON_ELEVATION_1 = 5;
	public static final int TALON_ELEVATION_2 = 6;
	public static final int TALON_CLIMBER_1 = 7;
	public static final int TALON_CLIMBER_2 = 8;
	public static final int TALON_FAN = 9;
	// DIO
	public static final int SCALE_LIMIT_SWITCH = 0;
	public static final int INTAKE_LIMIT_SWITCH = 1;
	public static final int ENCODER_DRIVE_LA = 2;
	public static final int ENCODER_DRIVE_LB = 3;
	public static final int ENCODER_DRIVE_RA = 4;
	public static final int ENCODER_DRIVE_RB = 5;
}
