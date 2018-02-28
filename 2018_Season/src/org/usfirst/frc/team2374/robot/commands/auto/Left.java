package org.usfirst.frc.team2374.robot.commands.auto;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.commands.DriveToInch;
import org.usfirst.frc.team2374.robot.commands.EjectorUp;
import org.usfirst.frc.team2374.robot.commands.ScaleDeliveryTimed;
import org.usfirst.frc.team2374.robot.commands.SwitchDeliveryTimed;
import org.usfirst.frc.team2374.robot.commands.TurnToAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Called during autonomous when robot starts in left position,
 * either delivers cube to switch or goes for scale depending on
 * what side we score on
 * 
 * @author Robotics
 */
public class Left extends CommandGroup {
	
	public Left() {
		requires(Robot.drive);
		// LEFT
		if (Robot.autoGameData != null && Robot.autoGameData.charAt(0) == 'L') {
			addSequential(new DriveToInch(132));
			addSequential(new TurnToAngle(90, TurnToAngle.PIDType.LONG));
			addSequential(new EjectorUp(5));
			addSequential(new SwitchDeliveryTimed(2));
		} 
		// Go for scale
		else if (Robot.autoGameData.charAt(1) == 'L') {
			addSequential(new DriveToInch(141));
			addSequential(new DriveToInch(141));
			addSequential(new TurnToAngle(90, TurnToAngle.PIDType.LONG));
			
			addSequential(new EjectorUp(5));
			addSequential(new ScaleDeliveryTimed(3));
		} 
		else {
			addSequential(new DriveToInch(102));
			addSequential(new DriveToInch(102));
			addSequential(new TurnToAngle(90, TurnToAngle.PIDType.LONG));
			addSequential(new DriveToInch(87));
			addSequential(new DriveToInch(87));
			addSequential(new TurnToAngle(-90, TurnToAngle.PIDType.LONG));
			addSequential(new DriveToInch(78));
			addSequential(new TurnToAngle(90, TurnToAngle.PIDType.LONG));
			
			addSequential(new EjectorUp(5));
			addSequential(new ScaleDeliveryTimed(3));
		}
	}
}
