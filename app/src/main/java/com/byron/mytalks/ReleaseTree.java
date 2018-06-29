package com.byron.mytalks;

import org.jetbrains.annotations.NotNull;

import timber.log.Timber;

public class ReleaseTree extends Timber.DebugTree {

    @Override
    protected void log(int priority, String tag, @NotNull String message, Throwable t) {
        super.log(priority, tag, message, t);
    }
}
