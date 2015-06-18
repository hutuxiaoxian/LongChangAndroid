/*
 * Copyright (C) 2010 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.imageco.library;

import android.app.Activity;

import java.util.concurrent.*;

/**
 * Finishes an activityImpl after a period of inactivity.
 */
public final class InactivityTimer {

    /**
     * Field INACTIVITY_DELAY_SECONDS
     */
    private static final int INACTIVITY_DELAY_SECONDS = 5 * 60;

    /**
     * Field inactivityTimer
     */
    private final ScheduledExecutorService inactivityTimer =
            Executors.newSingleThreadScheduledExecutor(new DaemonThreadFactory());
    /**
     * Field activityImpl
     */
    private final Activity activity;
    /**
     * Field inactivityFuture
     */
    private ScheduledFuture<?> inactivityFuture = null;

    /**
     * Constructor InactivityTimer creates a new InactivityTimer instance.
     *
     * @param activity of type Activity
     */
    public InactivityTimer(Activity activity) {
        this.activity = activity;
        onActivity();
    }

    /**
     * Method onActivity ...
     */
    public void onActivity() {
        cancel();
        inactivityFuture = inactivityTimer.schedule(new FinishListener(activity),
                INACTIVITY_DELAY_SECONDS,
                TimeUnit.SECONDS);
    }

    /**
     * Method cancel ...
     */
    private void cancel() {
        if (inactivityFuture != null) {
            inactivityFuture.cancel(true);
            inactivityFuture = null;
        }
    }

    /**
     * Method shutdown ...
     */
    public void shutdown() {
        cancel();
        inactivityTimer.shutdown();
    }

    /**
     * Class DaemonThreadFactory ...
     *
     * @author Administrator
     *         Created on 11-10-22
     */
    private static final class DaemonThreadFactory implements ThreadFactory {
        /**
         * Method newThread ...
         *
         * @param runnable of type Runnable
         * @return Thread
         */
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setDaemon(true);
            return thread;
        }
    }

}
