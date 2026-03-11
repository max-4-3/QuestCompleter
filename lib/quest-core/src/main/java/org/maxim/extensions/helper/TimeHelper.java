package org.maxim.extensions.helper;

import java.time.Duration;
import java.time.OffsetDateTime;

public interface TimeHelper {
    /* Everything is in UTC */

    Duration timeDiffNow(String utcIso);
    // return timeDiff(timeCurr().isoformat(), utcIso)

    Duration timeDiff(String utcIsoA, String utcIsoB);
    // return datetime.fromisoformat(utcIsoA) - datetime.fromisoformat(utcIsoB)

    boolean timeInPast(String utcIso);
    // return timeCurr() > datetime.fromisoformat(utcIso)

    OffsetDateTime timeCurr();
    // return datetime.now(timezone.utc)

}