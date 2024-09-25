package indubitables.config.util.action;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;

import java.util.Timer;
import java.util.TimerTask;

public class SleepAction implements Action {
    private final double dt;
    private boolean isFinished = false;

    public SleepAction(double dt) {this.dt = dt;
    }

    @Override
    public boolean run(TelemetryPacket p) {
        if (!isFinished) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    isFinished = true;
                }
            }, (long) (dt * 1000));
        } else {
            isFinished = false; // Reset the flag after sleep is complete
            return false; // Indicate that the action is finished
        }
        return true; // Indicate that the action is still running
    }
}