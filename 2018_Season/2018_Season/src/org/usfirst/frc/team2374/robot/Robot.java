
package org.usfirst.frc.team2374.robot;

import org.usfirst.frc.team2374.robot.commands.DriveToInch;
import org.usfirst.frc.team2374.robot.commands.DriveToTargetUltrasonic;
import org.usfirst.frc.team2374.robot.commands.EjectorUp;
import org.usfirst.frc.team2374.robot.commands.ScaleDeliveryTimed;
import org.usfirst.frc.team2374.robot.commands.SwitchDeliveryTimed;
import org.usfirst.frc.team2374.robot.commands.auto.RunCenter;
import org.usfirst.frc.team2374.robot.commands.auto.RunLeft;
import org.usfirst.frc.team2374.robot.commands.auto.RunRight;
import org.usfirst.frc.team2374.robot.commands.auto.RunScaleLeft;
import org.usfirst.frc.team2374.robot.commands.auto.RunScaleRight;
import org.usfirst.frc.team2374.robot.subsystems.Climber;
import org.usfirst.frc.team2374.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2374.robot.subsystems.Ejector;
import org.usfirst.frc.team2374.robot.subsystems.Ultrasonic;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static Climber climb;
	public static Drivetrain drive;
	public static Ejector eject;
	public static OI oi;
	public static Ultrasonic ultra;
	
	public static String autoGameData;

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		climb = new Climber();
		drive = new Drivetrain();
		eject = new Ejector();
		oi = new OI();
		ultra = new Ultrasonic();
		
		/*chooser.addDefault("Turn to 90", new TurnToAngle(90, TurnToAngle.PIDType.LONG));
		chooser.addObject("Turn to -90", new TurnToAngle(-90, TurnToAngle.PIDType.LONG));
		chooser.addObject("Turn to 70", new TurnToAngle(70, TurnToAngle.PIDType.LONG));
		chooser.addObject("Turn to -70", new TurnToAngle(-70, TurnToAngle.PIDType.LONG));
		chooser.addObject("Turn to 45", new TurnToAngle(45, TurnToAngle.PIDType.SHORT));
		chooser.addObject("Turn to -45", new TurnToAngle(-45, TurnToAngle.PIDType.SHORT));
		chooser.addObject("Turn to 35", new TurnToAngle(35, TurnToAngle.PIDType.SHORT));
		chooser.addObject("Turn to -35", new TurnToAngle(-35, TurnToAngle.PIDType.SHORT));*/
		
		//chooser.addObject("Ejector up", new EjectorUp(5));
		//chooser.addObject("Switch timed", new SwitchDeliveryTimed(3));
		//chooser.addObject("Scale timed", new ScaleDeliveryTimed(3));
		
		//chooser.addObject("Drive 12 ft", new DriveToInch(144));
		chooser.addObject("Drive 10 ft", new DriveToInch(120));
		//chooser.addObject("Drive 5 ft", new DriveToInch(60));
		
		//chooser.addObject("Drive to target with ultrasonic", new DriveToTargetUltrasonic());
		
		chooser.addObject("Right", new RunRight());
		chooser.addObject("Center", new RunCenter());
		chooser.addObject("Left", new RunLeft());
		
		chooser.addObject("Scale Left", new RunScaleLeft());
		chooser.addDefault("Scale Right", new RunScaleRight());
		
		SmartDashboard.putData("Auto mode", chooser);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() { }

	@Override
	public void disabledPeriodic() { Scheduler.getInstance().run(); }

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		autoGameData = DriverStation.getInstance().getGameSpecificMessage();
		autonomousCommand = chooser.getSelected();
		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() { Scheduler.getInstance().run(); }

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		autoGameData = null;
		Robot.drive.resetAllSenors();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("ultra inches", Robot.ultra.getDistanceInches());
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}