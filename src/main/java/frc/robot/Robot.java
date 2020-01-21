/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.auto.AutoCommands;
import frc.robot.auto.Example;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Grabber;
import frc.robot.subsystems.Lift;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kExampleAutoA = "Example Auto A";
  private static final String kExampleAutoB = "Example Auto B";
  private static final String kExampleAutoC = "Example Auto C";
  private static final String kExampleAutoD = "Example Auto D";
  private String mautoSelected;
  private final SendableChooser<String> mchooser = new SendableChooser<>();

  private AHRS ahrs = new AHRS(Port.kUSB);

  private OI mOi = new OI();

  //Subsystems
  private Drivetrain mDrivetrain = new Drivetrain();
  private Climber mClimber = new Climber();
  private Grabber mGrabber = new Grabber();
  private Lift mLift = new Lift();

  //Teleop
  private Teleop mTeleop = new Teleop(mDrivetrain, mClimber, mGrabber, mLift, mOi);

  // Auto
  private AutoCommands mAutoCommands = new AutoCommands(mDrivetrain, mClimber, mGrabber, mLift, ahrs);
  private Example mExample = new Example(mAutoCommands);

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    mchooser.setDefaultOption("Default Auto", kDefaultAuto);
    mchooser.addOption("My Auto", kExampleAutoA);
    mchooser.addOption("My Auto", kExampleAutoB);
    mchooser.addOption("My Auto", kExampleAutoC);
    mchooser.addOption("My Auto", kExampleAutoD);
    SmartDashboard.putData("Auto choices", mchooser);
    
    SmartDashboard.putNumber("encoderLift", -1);

    mDrivetrain.resetEncoders();
    mLift.resetEncoder();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("encoderLift", mLift.getEncoder());
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    mautoSelected = mchooser.getSelected();
    // mautoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + mautoSelected);

    mDrivetrain.resetEncoders();
    mLift.resetEncoder();
  }

  @Override
  public void disabledInit() {
    mDrivetrain.resetEncoders();
    mLift.resetEncoder();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (mautoSelected) {
      case kExampleAutoA:
          mExample.exampleCommandA();
        break;
      case kExampleAutoB:
        mExample.exampleCommandB();
      break;
      case kExampleAutoC:
          mExample.exampleCommandC();
        break;
      case kExampleAutoD:
        mExample.exampleCommandD();
      break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    mTeleop.driveStick();
    mTeleop.funStick();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
  
}
