package org.maxim.example.implementations;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.maxim.core.helper.TimeHelper;

public class DefaultTimeHelper implements TimeHelper {

    public static OffsetDateTime parse(String utcIso) {
        return OffsetDateTime.parse(utcIso);
    }

    public Duration timeDiffNow(String utcIso) {
        return timeDiff(timeCurr().toString(), utcIso);
    }

    public Duration timeDiff(String utcIsoA, String utcIsoB) {
        OffsetDateTime a = OffsetDateTime.parse(utcIsoA);
        OffsetDateTime b = OffsetDateTime.parse(utcIsoB);
        return Duration.between(b, a);
    }

    public boolean timeInPast(String utcIso) {
        OffsetDateTime t = OffsetDateTime.parse(utcIso);
        return timeCurr().isAfter(t);
    }

    public OffsetDateTime timeCurr() {
        return OffsetDateTime.now(ZoneOffset.UTC);
    }
}
