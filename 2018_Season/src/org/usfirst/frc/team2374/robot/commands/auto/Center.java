package org.usfirst.frc.team2374.robot.commands.auto;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.commands.DriveToInch;
import org.usfirst.frc.team2374.robot.commands.EjectorUp;
import org.usfirst.frc.team2374.robot.commands.SwitchDeliveryTimed;
import org.usfirst.frc.team2374.robot.commands.TurnToAngle;
import org.usfirst.frc.team2374.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Center extends CommandGroup {

	private Drivetrain drivetrain = Robot.drive;
	
	public Center() {
		requires(drivetrain);
		
		// RIGHT SIDE
		if (Robot.autoGameData != null && Robot.autoGameData.charAt(0) == 'R') {
			addSequential(new TurnToAngle(35, TurnToAngle.SHORT));
			addSequential(new DriveToInch(134));
			addSequential(new TurnToAngle(-35, TurnToAngle.SHORT));
			// these numbers (as well as switch delivery in general) need testing
			addSequential(new EjectorUp(5));
			addSequential(new SwitchDeliveryTimed(2));
		// LEFT SIDE
		} 
		else if (Robot.autoGameData != null && Robot.autoGameData.charAt(0) == 'L') {
			addSequential(new TurnToAngle(-35, TurnToAngle.SHORT));
			addSequential(new DriveToInch(134));
			addSequential(new TurnToAngle(35, TurnToAngle.SHORT));
			// these numbers (as well as switch delivery in general) need testing
			addSequential(new EjectorUp(5));
			addSequential(new SwitchDeliveryTimed(2));
		}
		else
			addSequential(new DriveToInch(110));
	}
}
