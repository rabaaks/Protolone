package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Carriage extends SubsystemBase{

    SparkMax motorOne;
    SparkMax motorTwo;

    SparkMaxConfig motorConfig = new SparkMaxConfig();
    public static SparkClosedLoopController motorOnePID;
    public static SparkClosedLoopController motorTwoPID;
            
            
    void Carrige(){
            
        motorOne = new SparkMax(1, MotorType.kBrushless);
        motorTwo = new SparkMax(2, MotorType.kBrushless);
            
        motorOnePID = motorOne.getClosedLoopController();
        motorTwoPID = motorTwo.getClosedLoopController();
            
        motorConfig
            .inverted(true)
            .idleMode(IdleMode.kCoast);
            
         motorConfig.closedLoop
            .pid(0, 0, 0);
        motorTwo.configure(motorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
            
        motorConfig
            .inverted(false)
            .idleMode(IdleMode.kCoast);
        motorConfig.closedLoop
            .pid(0, 0, 0);
        motorOne.configure(motorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
                }
            
    public static void setspeed(double reference){
        
    motorOnePID.setReference(reference, ControlType.kVelocity);
    motorTwoPID.setReference(reference, ControlType.kVelocity);

    }

}
