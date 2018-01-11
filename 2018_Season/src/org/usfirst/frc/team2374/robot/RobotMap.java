package org.usfirst.frc.team2374.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// CAN
	public static final int TALON_DRIVE_MASTER_LEFT = 1;
	public static final int TALON_DRIVE_MASTER_RIGHT = 2;
	public static final int TALON_DRIVE_FRONT_LEFT = 4;
	public static final int TALON_DRIVE_FRONT_RIGHT = 3;
	public static final int TALON_DRIVE_BACK_LEFT = 6;
	public static final int TALON_DRIVE_BACK_RIGHT = 5;
}
