package org.usfirst.frc.team2374.robot.commands.auto;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.commands.DriveToInch;
import org.usfirst.frc.team2374.robot.commands.EjectorUp;
import org.usfirst.frc.team2374.robot.commands.ScaleDeliveryTimed;
import org.usfirst.frc.team2374.robot.commands.TurnToAngle;
import org.usfirst.frc.team2374.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScaleRight extends CommandGroup {

	private Drivetrain drivetrain = Robot.drive;
	
	public ScaleRight() {
		
		requires(drivetrain);
		
		if (Robot.autoGameData.charAt(1) == 'R') {
			
			addSequential(new DriveToInch(252));
			addSequential(new TurnToAngle(-90, TurnToAngle.LONG));
			
			addSequential(new EjectorUp(5));
			addSequential(new ScaleDeliveryTimed(3));
			
		} else {
			
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
