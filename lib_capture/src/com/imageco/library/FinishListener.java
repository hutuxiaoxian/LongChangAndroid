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
import android.content.DialogInterface;

/**
 * Simple listener used to exit the app in a few cases.
 *
 * @author Sean Owen
 */
public final class FinishListener
        implements DialogInterface.OnClickListener, DialogInterface.OnCancelListener, Runnable {

    /**
     * Field activityToFinish
     */
    private final Activity activityToFinish;

    /**
     * Constructor FinishListener creates a new FinishListener instance.
     *
     * @param activityToFinish of type Activity
     */
    public FinishListener(Activity activityToFinish) {
        this.activityToFinish = activityToFinish;
    }

    /**
     * Method onCancel ...
     *
     * @param dialogInterface of type DialogInterface
     */
    public void onCancel(DialogInterface dialogInterface) {
        run();
    }

    /**
     * Method onClick ...
     *
     * @param dialogInterface of type DialogInterface
     * @param i               of type int
     */
    public void onClick(DialogInterface dialogInterface, int i) {
        run();
    }

    /**
     * Method run ...
     */
    public void run() {
        activityToFinish.finish();
    }

}
