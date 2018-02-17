package org.usfirst.frc.team2374.robot.commands.auto;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.commands.DriveToInch;
import org.usfirst.frc.team2374.robot.commands.EjectorUp;
import org.usfirst.frc.team2374.robot.commands.SwitchDeliveryTimed;
import org.usfirst.frc.team2374.robot.commands.TurnToAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Called during autonomous when robot starts in left position,
 * either delivers cube to switch or crosses line depending on
 * which side we score on
 * 
 * @author Robotics
 */
public class Left extends CommandGroup {
	
	public Left() {
		requires(Robot.drive);
		// LEFT
		if (Robot.autoGameData != null && Robot.autoGameData.charAt(0) == 'L') {
			addSequential(new DriveToInch(138));
			addSequential(new TurnToAngle(90, TurnToAngle.LONG));
			// these numbers (as well as switch delivery in general) need testing
			addSequential(new EjectorUp(5));
			addSequential(new SwitchDeliveryTimed(2));
		} 
		// NONE
		else
			addSequential(new DriveToInch(110));
	}
}
