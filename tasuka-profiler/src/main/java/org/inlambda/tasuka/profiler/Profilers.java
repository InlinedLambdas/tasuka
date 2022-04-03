package org.inlambda.tasuka.profiler;

import org.jetbrains.annotations.ApiStatus;

/**
 * Utility methods to create profilers, also used to get the default profiler.
 */
@ApiStatus.AvailableSince("0.1.0")
public class Profilers {
    private static Profiler defaultProfiler;

    public static void setDefaultProfiler(Profiler defaultProfiler) {
        Profilers.defaultProfiler = defaultProfiler;
    }

    public static Profiler getDefaultProfiler() {
        return defaultProfiler;
    }
}
