/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Grabber;
import frc.robot.subsystems.Lift;
import frc.robot.OI;

public class Teleop {

    private Drivetrain mDrivetrain;
    private Climber mClimber;
    private Grabber mGrabber;
    private Lift mLift;
    private OI mOi;

    public Teleop(Drivetrain mDrivetrain , Climber mClimber, Grabber mGrabber, Lift mLift, OI mOi){
        this.mDrivetrain = mDrivetrain;
        this.mClimber = mClimber;
        this.mGrabber = mGrabber;
        this.mLift = mLift;
        this.mOi = mOi;
    }

    public double y = 0;
    public double turn = 0;
    public double speed = 0;
    public static double deadzone = 0.07;

    public void driveStick(){

        speed = 0.7;

        //if(mOi.buttonSnail.get()) speed = 0.4;
        //if(mOi.buttonTurbo.get()) speed = 1;

        y = deadzone(mOi.driveStick.getRawAxis(RobotMap.OI_DRIVESTICK_MOVEY), 0.07, 0.07);
        turn = deadzone(mOi.driveStick.getRawAxis(RobotMap.OI_DRIVESTICK_TURN), 0.07, 0.07);

        mDrivetrain.arcade(y * speed, turn * speed * 0.5);

        if(mOi.buttonSnail.get()){ mGrabber.grabberOpen(); }
        else if(mOi.buttonTurbo.get()) mGrabber.grabberClose();

    }

    public void funStick(){

        //intakeSpeed = -INTAKE_OUT + INTAKE_IN
        double intakeSpeed = -mOi.funStick.getRawAxis(RobotMap.OI_FUNSTICK_INTAKE_OUT) + mOi.funStick.getRawAxis(RobotMap.OI_FUNSTICK_INTAKE_IN);
        mGrabber.intake(intakeSpeed);

        if(mOi.buttonGrabberOpen.get()){ mGrabber.grabberOpen(); }
        else if(mOi.buttonGrabberClose.get()) mGrabber.grabberClose();


        double ladderSpeed = deadzone(mOi.funStick.getRawAxis(RobotMap.OI_FUNSTICK_LIFT), 0.07, 0.07);
        mLift.moveLift(ladderSpeed);

        mClimber.armsMove(0);

    }

    public void proStick(){

    }

    public void testStick(){

    }
    
    private double deadzone(double input, double deadzonePos, double deadzoneNeg){
    
        if(input < deadzonePos && input > -deadzoneNeg) input = 0;
        return input;
    }
}
