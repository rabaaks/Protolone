package frc.robot.subsystems.elevator;

import static frc.robot.subsystems.elevator.ElevatorConstants.*;

import org.littletonrobotics.junction.AutoLogOutput;
import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.units.measure.Time;
import edu.wpi.first.units.measure.Velocity;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;

public class Elevator extends SubsystemBase {

    public Elevator(ElevatorIO io) {
    }

    @Override
    public void periodic() {
    }

    public void setPosition(double position) {
        futureProfileState = profile.calculate(0.02, profileState, new TrapezoidProfile.State(position, 0.0));
        Logger.recordOutput("Elevator/PositionSetpoint", profileState.position);
        Logger.recordOutput("Elevator/VelocitySetpoint", profileState.velocity);
        io.setPosition(position, feedforward.calculateWithVelocities(profileState.velocity, futureProfileState.velocity));
        profileState = futureProfileState;
    }

    @AutoLogOutput(key="Odometry/ElevatorPosition")
    public double getPosition() {
        return inputs.position;
    }

    @AutoLogOutput(key="Odometry/ElevatorVelocity")
    public double getVelocity() {
        return inputs.velocity;
    }

    public Command sysIdRoutine() {
        return Commands.sequence(
            routine.quasistatic(SysIdRoutine.Direction.kForward).until(() -> inputs.position > sysIdMaxPosition),
            routine.quasistatic(SysIdRoutine.Direction.kReverse).until(() -> inputs.position < sysIdMinPosition),
            routine.dynamic(SysIdRoutine.Direction.kForward).until(() -> inputs.position > sysIdMaxPosition),
            routine.dynamic(SysIdRoutine.Direction.kReverse).until(() -> inputs.position < sysIdMinPosition)
        );
    }
}
