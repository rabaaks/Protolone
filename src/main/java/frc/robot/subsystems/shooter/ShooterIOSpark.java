package frc.robot.subsystems.shooter;

import static frc.robot.subsystems.shooter.ShooterConstants.*;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkClosedLoopController.ArbFFUnits;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

public class ShooterIOSpark implements ShooterIO {
  private final SparkBase feedSpark;
  private final SparkBase shootSpark;

  private final RelativeEncoder shootEncoder;

  private final SparkClosedLoopController shootController;

  public ShooterIOSpark() {
    feedSpark = new SparkMax(feedId, MotorType.kBrushless);
    shootSpark = new SparkMax(shootId, MotorType.kBrushless);

    shootEncoder = shootSpark.getEncoder();
    shootController = shootSpark.getClosedLoopController();

    var feedConfig = new SparkMaxConfig();
    feedConfig
        .inverted(wheelInverted)
        .idleMode(IdleMode.kCoast)
        .smartCurrentLimit(80)
        .voltageCompensation(12.0);

    var shootConfig = new SparkMaxConfig();
    shootConfig
        .inverted(wheelInverted)
        .idleMode(IdleMode.kCoast)
        .smartCurrentLimit(40)
        .voltageCompensation(12.0);
    shootConfig
        .encoder
        .positionConversionFactor(wheelEncoderPositionFactor)
        .velocityConversionFactor(wheelEncoderVelocityFactor);

    feedSpark.configure(feedConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    shootSpark.configure(
        shootConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  @Override
  public void updateInputs(ShooterIOInputs inputs) {
    inputs.feedConnected = true;
    inputs.shootConnected = true;

    inputs.shootVelocityRadPerSec = shootEncoder.getVelocity();

    inputs.feedAppliedVolts = feedSpark.getAppliedOutput() * feedSpark.getBusVoltage();
    inputs.shootAppliedVolts = shootSpark.getAppliedOutput() * shootSpark.getBusVoltage();

    inputs.feedCurrentAmps = feedSpark.getOutputCurrent();
    inputs.shootCurrentAmps = shootSpark.getOutputCurrent();
  }

  public void setFeedOpenLoop(double output) {
    feedSpark.setVoltage(output);
  }

  public void setShootOpenLoop(double output) {
    shootSpark.setVoltage(output);
  }

  public void setShootVelocity(double velocityRadPerSec) {
    shootController.setReference(
        velocityRadPerSec,
        ControlType.kVelocity,
        ClosedLoopSlot.kSlot0,
        velocityRadPerSec * shootKv,
        ArbFFUnits.kVoltage);
  }
}
