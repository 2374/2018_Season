package org.usfirst.frc.team2374.robot.commands.auto;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.commands.DriveToInch;
import org.usfirst.frc.team2374.robot.commands.EjectorUp;
import org.usfirst.frc.team2374.robot.commands.SwitchDeliveryTimed;
import org.usfirst.frc.team2374.robot.commands.TurnToAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Called during autonomous when robot starts in center position,
 * delivers cube to either right or left side of switch depending
 * on which side we score on 
 * 
 * @author Robotics
 */
public class Center extends CommandGroup {
	
	public Center() {
		requires(Robot.drive);
		// RIGHT SIDE
		if (Robot.autoGameData != null && Robot.autoGameData.charAt(0) == 'R') {
			addSequential(new TurnToAngle(35, TurnToAngle.PIDType.SHORT, 5));
			addSequential(new DriveToInch(85));
			addSequential(new TurnToAngle(-35, TurnToAngle.PIDType.SHORT, 5));
			// these numbers (as well as switch delivery in general) need testing
			addSequential(new EjectorUp(5));
			addSequential(new DriveToInch(25));
			addSequential(new SwitchDeliveryTimed(2));
		// LEFT SIDE
		} 
		else if (Robot.autoGameData != null && Robot.autoGameData.charAt(0) == 'L') {
			addSequential(new TurnToAngle(-35, TurnToAngle.PIDType.SHORT, 5));
			addSequential(new DriveToInch(70));
			addSequential(new TurnToAngle(35, TurnToAngle.PIDType.SHORT, 5));
			// these numbers (as well as switch delivery in general) need testing
			addSequential(new EjectorUp(5));
			addSequential(new DriveToInch(25));
			addSequential(new SwitchDeliveryTimed(2));
		}
		else
			addSequential(new DriveToInch(110));
	}
}
