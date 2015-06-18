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

import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Class AutoFocusCallback ...
 *
 * @author Administrator
 *         Created on 11-10-22
 */
final class AutoFocusCallback implements Camera.AutoFocusCallback {

    /**
     * Field TAG
     */
    private static final String TAG = AutoFocusCallback.class.getSimpleName();

    /**
     * Field AUTOFOCUS_INTERVAL_MS
     */
    private static final long AUTOFOCUS_INTERVAL_MS = 1500L;

    /**
     * Field autoFocusHandler
     */
    private Handler autoFocusHandler;
    /**
     * Field autoFocusMessage
     */
    private int autoFocusMessage;

    /**
     * Method setHandler ...
     *
     * @param autoFocusHandler of type Handler
     * @param autoFocusMessage of type int
     */
    void setHandler(Handler autoFocusHandler, int autoFocusMessage) {
        this.autoFocusHandler = autoFocusHandler;
        this.autoFocusMessage = autoFocusMessage;
    }

    /**
     * Method onAutoFocus ...
     *
     * @param success of type boolean
     * @param camera  of type Camera
     */
    public void onAutoFocus(boolean success, Camera camera) {
        if (autoFocusHandler != null) {
            Message message = autoFocusHandler.obtainMessage(autoFocusMessage, success);
            // Simulate continuous autofocus by sending a focus request every
            // AUTOFOCUS_INTERVAL_MS milliseconds.
            //Log.d(TAG, "Got auto-focus callback; requesting another");
            autoFocusHandler.sendMessageDelayed(message, AUTOFOCUS_INTERVAL_MS);
            autoFocusHandler = null;
        } else {
            Log.d(TAG, "Got auto-focus callback, but no handler for it");
        }
    }

}
