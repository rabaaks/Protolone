package frc.robot.subsystems.shooter;

import static frc.robot.subsystems.shooter.ShooterConstants.*;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.shooter.ShooterIO.ShooterIOInputs;

public class Shooter extends SubsystemBase {
    private final ShooterIO io;
    private final ShooterIOInputs inputs = new ShooterIOInputs();

    private final SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(s, v, a);

    public Shooter(ShooterIO io) {
        this.io = io;
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        // Logger.processInputs("Shooter", inputs);
    }

    public void setVelocity(double velocity) {
        Logger.recordOutput("Shooter/VelocitySetpoint", velocity);
        io.setVoltage(feedforward.calculate(velocity));
    }
}
