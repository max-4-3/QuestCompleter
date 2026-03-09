package org.maxim.example.implementations;

import org.maxim.core.helper.SleepHelper;
import java.util.concurrent.TimeUnit;

public class DefaultSleepHelper implements SleepHelper {

    @Override
    public void sleep(long durationSecs) {
        try {
            TimeUnit.SECONDS.sleep(durationSecs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
