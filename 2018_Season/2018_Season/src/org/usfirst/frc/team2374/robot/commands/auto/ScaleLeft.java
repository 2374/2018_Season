package org.usfirst.frc.team2374.robot.commands.auto;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.commands.DriveToInch;
import org.usfirst.frc.team2374.robot.commands.EjectorUp;
import org.usfirst.frc.team2374.robot.commands.ScaleDeliveryTimed;
import org.usfirst.frc.team2374.robot.commands.TurnToAngle;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Called during autonomous when robot starts in left position
 * and both allies can deliver to switch, delivers cube to left
 * or right side of scale depending on which side we score on
 * 
 * @author Robotics
 */
public class ScaleLeft extends CommandGroup {
	
	public ScaleLeft() {
		requires(Robot.drive);
		
		if (Robot.autoGameData.charAt(1) == 'L') {
			addSequential(new DriveToInch(141));
			addSequential(new DriveToInch(141));
			addSequential(new TurnToAngle(90, TurnToAngle.PIDType.LONG, 5));
			
			addParallel(new DriveToInch(-20));
			addSequential(new EjectorUp(5));
			Timer.delay(0.2);
			addSequential(new ScaleDeliveryTimed(3));
		} 
		else {
			addSequential(new DriveToInch(102));
			addSequential(new DriveToInch(102));
			addSequential(new TurnToAngle(90, TurnToAngle.PIDType.LONG, 5));
			addSequential(new DriveToInch(87));
			addSequential(new DriveToInch(87));
			addSequential(new TurnToAngle(-90, TurnToAngle.PIDType.LONG, 5));
			addSequential(new DriveToInch(78));
			addSequential(new TurnToAngle(-90, TurnToAngle.PIDType.LONG, 5));
			
			addSequential(new EjectorUp(5));
			//addSequential(new ScaleDeliveryTimed(3));
		}
	}
	
}
