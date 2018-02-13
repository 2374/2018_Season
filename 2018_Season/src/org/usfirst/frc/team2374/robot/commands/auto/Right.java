package org.usfirst.frc.team2374.robot.commands.auto;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.commands.DriveToInch;
import org.usfirst.frc.team2374.robot.commands.DriveToTargetUltrasonic;
import org.usfirst.frc.team2374.robot.commands.EjectorUp;
import org.usfirst.frc.team2374.robot.commands.SwitchDeliveryTimed;
import org.usfirst.frc.team2374.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Right extends CommandGroup{
	
	private Drivetrain drivetrain = Robot.drive;
	
	public Right() {
		requires(drivetrain);
		
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
