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
import android.graphics.Point;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Class PreviewCallback ...
 *
 * @author Administrator
 *         Created on 11-10-22
 */
final class PreviewCallback implements Camera.PreviewCallback {

    /**
     * Field TAG
     */
    private static final String TAG = PreviewCallback.class.getSimpleName();

    /**
     * Field configManager
     */
    private final CameraConfigurationManager configManager;
    /**
     * Field useOneShotPreviewCallback
     */
    private final boolean useOneShotPreviewCallback;
    /**
     * Field previewHandler
     */
    private Handler previewHandler;
    /**
     * Field previewMessage
     */
    private int previewMessage;

    /**
     * Constructor PreviewCallback creates a new PreviewCallback instance.
     *
     * @param configManager             of type CameraConfigurationManager
     * @param useOneShotPreviewCallback of type boolean
     */
    PreviewCallback(CameraConfigurationManager configManager, boolean useOneShotPreviewCallback) {
        this.configManager = configManager;
        this.useOneShotPreviewCallback = useOneShotPreviewCallback;
    }

    /**
     * Method setHandler ...
     *
     * @param previewHandler of type Handler
     * @param previewMessage of type int
     */
    void setHandler(Handler previewHandler, int previewMessage) {
        this.previewHandler = previewHandler;
        this.previewMessage = previewMessage;
    }

    /**
     * Method onPreviewFrame ...
     *
     * @param data   of type byte[]
     * @param camera of type Camera
     */
    public void onPreviewFrame(byte[] data, Camera camera) {
        Point cameraResolution = configManager.getCameraResolution();
        if (!useOneShotPreviewCallback) {
            camera.setPreviewCallback(null);
        }
        if (previewHandler != null) {
            Message message = previewHandler.obtainMessage(previewMessage, cameraResolution.x,
                    cameraResolution.y, data);
            message.sendToTarget();
            previewHandler = null;
        } else {
            Log.d(TAG, "Got preview callback, but no handler for it");
        }
    }

}
