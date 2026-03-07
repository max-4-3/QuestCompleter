package org.maxim.core.helper;

import java.time.Duration;
import java.time.OffsetDateTime;

public interface TimeHelper {
    /* Everything is in UTC */

    public Duration timeDiffNow(String utcIso);
    // return timeDiff(timeCurr().isoformat(), utcIso)

    public Duration timeDiff(String utcIsoA, String utcIsoB);
    // return datetime.fromisoformat(utcIsoA) - datetime.fromisoformat(utcIsoB)

    public boolean timeInPast(String utcIso);
    // return timeCurr() > datetime.fromisoformat(utcIso)

    public OffsetDateTime timeCurr();
    // return datetime.now(timezone.utc)

}
