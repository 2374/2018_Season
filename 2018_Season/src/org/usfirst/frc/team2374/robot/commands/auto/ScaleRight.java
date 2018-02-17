package org.usfirst.frc.team2374.robot.commands.auto;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.commands.DriveToInch;
import org.usfirst.frc.team2374.robot.commands.EjectorUp;
import org.usfirst.frc.team2374.robot.commands.ScaleDeliveryTimed;
import org.usfirst.frc.team2374.robot.commands.TurnToAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Called during autonomous when robot starts in left position
 * and both allies can deliver to switch, delivers cube to left
 * or right side of scale depending on which side we score on
 * 
 * @author Robotics
 */
public class ScaleRight extends CommandGroup {
	
	public ScaleRight() {
		requires(Robot.drive);
		
		if (Robot.autoGameData.charAt(1) == 'R') {
			addSequential(new DriveToInch(252));
			addSequential(new TurnToAngle(-90, TurnToAngle.LONG));
			
			addSequential(new EjectorUp(5));
			addSequential(new ScaleDeliveryTimed(3));
		} 
		else {
			addSequential(new DriveToInch(210));
			addSequential(new TurnToAngle(-90, TurnToAngle.LONG));
			addSequential(new DriveToInch(180));
			addSequential(new TurnToAngle(90, TurnToAngle.LONG));
			addSequential(new DriveToInch(78));
			addSequential(new TurnToAngle(-90, TurnToAngle.LONG));
			
			addSequential(new EjectorUp(5));
			addSequential(new ScaleDeliveryTimed(3));
		}
	}
	
}
