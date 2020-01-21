/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auto;

import com.kauailabs.navx.frc.AHRS;

import frc.robot.RobotMap;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Grabber;
import frc.robot.subsystems.Lift;

public class AutoCommands {

    private double kP, kI, kD;
    private double error, previous_error, derivative, intergral, rcw;
    private double endTime, inRange, inRangeSet;

    private double kP2, kI2, kD2;
    private double error2, previous_error2, derivative2, intergral2, rcw2;

    private Drivetrain mDrivetrain;
    //private Climber mClimber;
    //private Grabber mGrabber;
    //private Lift mLift;
    private AHRS ahrs;

    public AutoCommands(Drivetrain mDrivetrain , Climber mClimber, Grabber mGrabber, Lift mLift, AHRS ahrs){
        this.mDrivetrain = mDrivetrain;
        //this.mClimber = mClimber;
        //this.mGrabber = mGrabber;
        //this.mLift = mLift;
        this.ahrs = ahrs;
    }

    /**
	 * Drive Robot using only the encoders
	 *
	 * @param setpoint The distance you would like to move (inches)
	 * @param speed Speed of the robot (-1 to 1)
     * @param range The range of error you would like to be within (inches)
     * @param rangeTimeMs The amount of time you would like to be within the range (ms)
     * @param timeoutMs The max amount of time you want this command to run (ms)
	 */
    public void encoderDrive(double setpoint, double speed, double range, double rangeTimeMs, double timeoutMs){

        this.kP = RobotMap.P_DRIVETRAIN;
        this.kI = RobotMap.I_DRIVETRAIN;
        this.kD = RobotMap.D_DRIVETRAIN;

        this.endTime = System.currentTimeMillis() + timeoutMs;
        this.inRange = 0;

        while(System.currentTimeMillis() < this.endTime || this.inRangeSet < this.inRange){
            
            /*      If the encoder is within the range then set the inRange to currentTime when 
             *      this happens then the inRange incresses until it is equal to the inRangeSet
             *      when that happens the loop ends. When you are not in the range the 
             *      inRangeSet catches up to the currentTime
             */
            if((mDrivetrain.getEncoderBL() < (setpoint + range)) && (mDrivetrain.getEncoderBL() > (setpoint - range))){
                this.inRange = System.currentTimeMillis();
            } else {
                this.inRangeSet = System.currentTimeMillis() + rangeTimeMs;
            }

            //PID code starts here
            //this.previous_error = this.error;

            this.error = setpoint - mDrivetrain.getEncoderBL();
            this.intergral = this.intergral + (this.error * 0.02);
            this.derivative = (this.error - this.previous_error) / 0.02;
            this.rcw = this.kP * this.error + this.kI * this.intergral + this.kD * this.derivative;

            mDrivetrain.arcade(rcw * speed, 0);
        }
    }

    /**
	 * Turn the Robot using only the navX gyro
	 *
	 * @param setpoint The distance you would like to move (degrees)
	 * @param speed Speed of the robot (-1 to 1)
     * @param range The range of error you would like to be within (degrees)
     * @param rangeTimeMs The amount of time you would like to be within the range (ms)
     * @param timeoutMs The max amount of time you want this command to run (ms)
	 */
    public void gyroTrun(double setpoint, double speed, double range, double rangeTimeMs, double timeoutMs){

        this.kP = RobotMap.P_NAVX;
        this.kI = RobotMap.I_NAVX;
        this.kD = RobotMap.D_NAVX;

        this.endTime = System.currentTimeMillis() + timeoutMs;
        this.inRange = 0;

        while(System.currentTimeMillis() < this.endTime || this.inRangeSet < this.inRange){
            
            /*      If the encoder is within the range then set the inRange to currentTime when 
             *      this happens then the inRange incresses until it is equal to the inRangeSet
             *      when that happens the loop ends. When you are not in the range the 
             *      inRangeSet catches up to the currentTime
             */
            if((ahrs.getAngle() < (setpoint + range)) && (ahrs.getAngle() > (setpoint - range))){
                this.inRange = System.currentTimeMillis();
            } else {
                this.inRangeSet = System.currentTimeMillis() + rangeTimeMs;
            }

            //PID code starts here
            //this.previous_error = this.error;

            this.error = setpoint - ahrs.getAngle();
            this.intergral = this.intergral + (this.error * 0.02);
            this.derivative = (this.error - this.previous_error) / 0.02;
            this.rcw = this.kP * this.error + this.kI * this.intergral + this.kD * this.derivative;

            mDrivetrain.arcade(0, rcw * speed);
        }
    }

    /**
	 * Drive the robot straight using the navX to keep straight
	 *
	 * @param setpoint The distance you would like to move (inches)
	 * @param speedENCO Encoder speed of the robot (-1 to 1)
     * @param speedGYRO Gyro speed of the robot (-1 to 1)
     * @param range The range of error you would like to be within (inches)
     * @param rangeTimeMs The amount of time you would like to be within the range (ms)
     * @param timeoutMs The max amount of time you want this command to run (ms)
	 */
    public void straightDrive(double setpoint, double speedENCO, double speedGYRO, double range, double rangeTimeMs, double timeoutMs){

        this.kP = RobotMap.P_DRIVETRAIN;
        this.kI = RobotMap.I_DRIVETRAIN;
        this.kD = RobotMap.D_DRIVETRAIN;

        this.kP2 = RobotMap.P_NAVX;
        this.kI2 = RobotMap.I_NAVX;
        this.kD2 = RobotMap.D_NAVX;

        this.endTime = System.currentTimeMillis() + timeoutMs;
        this.inRange = 0;

        while(System.currentTimeMillis() < this.endTime || this.inRangeSet < this.inRange){
            
            /*      If the encoder is within the range then set the inRange to currentTime when 
             *      this happens then the inRange incresses until it is equal to the inRangeSet
             *      when that happens the loop ends. When you are not in the range the 
             *      inRangeSet catches up to the currentTime
             */
            if((mDrivetrain.getEncoderBL() < (setpoint + range)) && (mDrivetrain.getEncoderBL() > (setpoint - range))){
                this.inRange = System.currentTimeMillis();
            } else {
                this.inRangeSet = System.currentTimeMillis() + rangeTimeMs;
            }

            //PID code starts here
            //this.previous_error = this.error;

            this.error = setpoint - ahrs.getAngle();
            this.intergral = this.intergral + (this.error * 0.02);
            this.derivative = (this.error - this.previous_error) / 0.02;
            this.rcw = this.kP * this.error + this.kI * this.intergral + this.kD * this.derivative;

            //this.previous_error2 = this.error2;

            this.error2 = 0 - ahrs.getAngle();
            this.intergral2 = this.intergral2 + (this.error2 * 0.02);
            this.derivative2 = (this.error2 - this.previous_error2) / 0.02;
            this.rcw2 = this.kP2 * this.error2 + this.kI2 * this.intergral2 + this.kD2 * this.derivative2;

            mDrivetrain.arcade(rcw * speedENCO, rcw2 * speedGYRO);
        }
    }

    /**
	 * Drive the robot and turn to specifc angle
	 *
	 * @param setpointENCO The distance you would like to move (inches)
	 * @param speedENCO Speed of the robot in the y-axis (-1 to 1)
     * @param rangeENCO The range of error you would like to be within (inches)
     * @param setpointGYRO The angle you would like to move (degrees)
	 * @param speedGYRO Speed of the robot for turning (-1 to 1)
     * @param rangeTimeMs The amount of time you would like to be within the range (ms)
     * @param timeoutMs The max amount of time you want this command to run (ms)
	 */
    public void turnDrive(double setpointENCO, double speedENCO, double rangeENCO, double setpointGYRO, double speedGYRO, double rangeTimeMs, double timeoutMs){

        this.kP = RobotMap.P_DRIVETRAIN;
        this.kI = RobotMap.I_DRIVETRAIN;
        this.kD = RobotMap.D_DRIVETRAIN;

        this.kP2 = RobotMap.P_NAVX;
        this.kI2 = RobotMap.I_NAVX;
        this.kD2 = RobotMap.D_NAVX;

        this.endTime = System.currentTimeMillis() + timeoutMs;
        this.inRange = 0;

        while(System.currentTimeMillis() < this.endTime || this.inRangeSet < this.inRange){
            
            /*      If the encoder is within the range then set the inRange to currentTime when 
             *      this happens then the inRange incresses until it is equal to the inRangeSet
             *      when that happens the loop ends. When you are not in the range the 
             *      inRangeSet catches up to the currentTime
             */
            if((mDrivetrain.getEncoderBL() < (setpointENCO + rangeENCO)) && (mDrivetrain.getEncoderBL() > (setpointENCO - rangeENCO))){
                this.inRange = System.currentTimeMillis();
            } else {
                this.inRangeSet = System.currentTimeMillis() + rangeTimeMs;
            }

            //PID code starts here
            //this.previous_error = this.error;

            this.error = setpointENCO - mDrivetrain.getEncoderBL();
            this.intergral = this.intergral + (this.error * 0.02);
            this.derivative = (this.error - this.previous_error) / 0.02;
            this.rcw = this.kP * this.error + this.kI * this.intergral + this.kD * this.derivative;

            //this.previous_error2 = this.error2;

            this.error2 = setpointGYRO - ahrs.getAngle();
            this.intergral2 = this.intergral2 + (this.error2 * 0.02);
            this.derivative2 = (this.error2 - this.previous_error2) / 0.02;
            this.rcw2 = this.kP2 * this.error2 + this.kI2 * this.intergral2 + this.kD2 * this.derivative2;

            mDrivetrain.arcade(rcw * speedENCO, rcw2 * speedGYRO);
        }
    }


}
