package frc.robot.subsystems.shooter;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
  private final ShooterIO io;
  private final ShooterIOInputsAutoLogged inputs = new ShooterIOInputsAutoLogged();

  public Shooter(ShooterIO io) {
    this.io = io;
  }

  public void shoot(double velocityRadPerSec) {
    io.setShootVelocity(velocityRadPerSec);
  }

  public void feed() {
    io.setFeedOpenLoop(12.0);
  }

  public void intake() {
    io.setShootOpenLoop(-3.0);
    io.setFeedOpenLoop(-3.0);
  }

  public void stop() {
    io.setShootOpenLoop(0.0);
    io.setFeedOpenLoop(0.0);
  }
}
