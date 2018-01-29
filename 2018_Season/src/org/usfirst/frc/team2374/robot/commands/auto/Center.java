package org.usfirst.frc.team2374.robot.commands.auto;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.commands.DriveToInch;
import org.usfirst.frc.team2374.robot.commands.TurnToAngle;
import org.usfirst.frc.team2374.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Center extends CommandGroup {

	private Drivetrain drivetrain = Robot.drive;
	
	public Center() {
		
		requires(drivetrain);
		
		// RIGHT SIDE
		
		if (Robot.autoGameData.charAt(0) == 'R') {
			
			addSequential(new TurnToAngle(35));
			addSequential(new DriveToInch(134));
			addSequential(new TurnToAngle(-35));
			
			Robot.eject.switchForward();
			
		// LEFT SIDE	
			
		} else {
			
			addSequential(new TurnToAngle(-35));
			addSequential(new DriveToInch(134));
			addSequential(new TurnToAngle(35));
			
			Robot.eject.switchForward();
			
		}
		
	}
	
}
