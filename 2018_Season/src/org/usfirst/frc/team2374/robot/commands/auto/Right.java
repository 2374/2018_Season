package org.usfirst.frc.team2374.robot.commands.auto;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.commands.DriveToInch;
import org.usfirst.frc.team2374.robot.commands.DriveToTargetUltrasonic;
import org.usfirst.frc.team2374.robot.commands.EjectorUp;
import org.usfirst.frc.team2374.robot.commands.SwitchDeliveryTimed;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Called during autonomous when robot starts in right position,
 * either delivers cube to switch or crosses line depending on
 * which side we score on
 * 
 * @author Robotics
 */
public class Right extends CommandGroup{
	
	public Right() {
		requires(Robot.drive);
		// RIGHT
		if (Robot.autoGameData != null && Robot.autoGameData.charAt(0) == 'R'){
			addSequential(new DriveToTargetUltrasonic());
			// these numbers (as well as switch delivery in general) need testing
			addSequential(new EjectorUp(5));
			addSequential(new SwitchDeliveryTimed(2));
		} 
		// NONE
		else	
			addSequential(new DriveToInch(110));
	}
}
