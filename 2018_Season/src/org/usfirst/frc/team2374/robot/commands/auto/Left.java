package org.usfirst.frc.team2374.robot.commands.auto;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.commands.DriveToInch;
import org.usfirst.frc.team2374.robot.commands.TurnToAngle;
import org.usfirst.frc.team2374.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Left extends CommandGroup {
	
	private Drivetrain drivetrain = Robot.drive;
	
	public Left() {
		
		requires(drivetrain);
		
		// LEFT
		
		if (Robot.autoGameData.charAt(0) == 'L') {
			
			addSequential(new DriveToInch(138));
			addSequential(new TurnToAngle(90));
			
			Robot.eject.switchForward();
			
		// NONE	
			
		} else {
			
			addSequential(new DriveToInch(110));
			
		}
		
	}

}
