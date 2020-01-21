/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auto;

/**
 * Add your docs here.
 */
public class Example {

    private AutoCommands m_autoCommands;

    public Example(AutoCommands mAutoCommands){
        this.m_autoCommands = mAutoCommands;
    }

    public void exampleCommandA(){
        /**
         * Encoder Drive
         * 
         * This autonomus example uses the encoders to drive the robot.
         * The variables that we enter change how the command operates.
         * 
         * The first variable is the setpoint (the distance we would like to acheve)
         * The secound is the speed of the robot from (-1 to 1)
         * The third is the range (+/- the setpoint we want to acheeve)
         * The fourth is the range timeout (the ammount of time we want to be in the range)
         * The fith is the command timout (the max amount of time you want this command to run)
         */
        m_autoCommands.encoderDrive(100, 0.5, 0.5, 250, 10000);
    }

    public void exampleCommandB(){
        /**
         * Gyro Turn
         * 
         * This autonomus example uses the NavX gyro to turn the robot.
         * The variables that we enter change how the command operates.
         * 
         * The first variable is the setpoint (the angle we would like to acheve)
         * The secound is the speed of the robot from (-1 to 1)
         * The third is the range (+/- the setpoint we want to acheeve)
         * The fourth is the range timeout (the ammount of time we want to be in the range in ms)
         * The fith is the command timout (the max amount of time you want this command to run in ms)
         */
        m_autoCommands.gyroTrun(180, 0.5, 2, 125, 2000);
    }

    public void exampleCommandC(){
        /**
         * Straight Drive
         * 
         * This autonomus example uses encoders to drive the robot and 
         *  the NavX gyro to keep the robot straight.
         * The variables that we enter change how the command operates.
         * 
         * Note: The only diffrence from how this command operates compared to
         *  the encoderDrive is that if the robot drives off to the side the
         *  NavX gyro will correct.
         * 
         * The first variable is the setpoint (the distance we would like to acheve)
         * The secound is the encoder speed of the robot from (-1 to 1)
         * The third is the gyro speed of the robot from (-1 to 1)
         * The fourth is the range (+/- the setpoint we want to acheeve)
         * The fith is the range timeout (the ammount of time we want to be in the range)
         * The sixth is the command timout (the max amount of time you want this command to run)
         */
        m_autoCommands.straightDrive(60, 0.5, 0.5, 0.5, 250, 5500);
    }

    public void exampleCommandD(){
        /**
         * Gyro Turn - This command may not 100% work (WIP) Also very complicated
         * 
         * This autonomus example uses encoders to drive forward and the
         * gyro to turn the robot at the same time
         * The variables that we enter change how the command operates.
         * 
         * Note: The thing that will end this loop is the encoders.
         * 
         * The first variable is the encoder setpoint (the distance we would like to acheve)
         * The second is the y-speed of the robot from (-1 to 1)
         * The third is the encoder range (+/- the setpoint we want to acheeve)
         * The fourth variable is the gyro setpoint (the angle we would like to acheve)
         * The fifth is the turn-speed of the robot from (-1 to 1)
         * The sixth is the range timeout (the ammount of time we want to be in the range)
         * The seventh is the command timout (the max amount of time you want this command to run)
         */
        m_autoCommands.turnDrive(100, 0.5, 0.5, 90, 0.25, 250, 9000);
    }
}
