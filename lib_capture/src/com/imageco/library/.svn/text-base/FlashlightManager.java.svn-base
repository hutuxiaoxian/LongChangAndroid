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

import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This class is used to activate the weak light on some camera phones (not flash)
 * in order to illuminate surfaces for scanning. There is no official way to do this,
 * but, classes which allow access to this function still exist on some devices.
 * This therefore proceeds through a great deal of reflection.
 * <p/>
 * See <a href="http://almondmendoza.com/2009/01/05/changing-the-screen-brightness-programatically/">
 * http://almondmendoza.com/2009/01/05/changing-the-screen-brightness-programatically/</a> and
 * <a href="http://code.google.com/p/droidled/source/browse/trunk/src/com/droidled/demo/DroidLED.java">
 * http://code.google.com/p/droidled/source/browse/trunk/src/com/droidled/demo/DroidLED.java</a>.
 * Thanks to Ryan Alford for pointing out the availability of this class.
 */
final class FlashlightManager {

    /**
     * Field TAG
     */
    private static final String TAG = FlashlightManager.class.getSimpleName();

    /**
     * Field iHardwareService
     */
    private static final Object iHardwareService;
    /**
     * Field setFlashEnabledMethod
     */
    private static final Method setFlashEnabledMethod;

    static {
        iHardwareService = getHardwareService();
        setFlashEnabledMethod = getSetFlashEnabledMethod(iHardwareService);
        if (iHardwareService == null) {
            Log.v(TAG, "This device does supports control of a flashlight");
        } else {
            Log.v(TAG, "This device does not support control of a flashlight");
        }
    }

    /**
     * Constructor FlashlightManager creates a new FlashlightManager instance.
     */
    private FlashlightManager() {
    }

    /**
     * Method getHardwareService returns the hardwareService of this FlashlightManager object.
     *
     * @return the hardwareService (type Object) of this FlashlightManager object.
     */
    private static Object getHardwareService() {
        Class<?> serviceManagerClass = maybeForName("android.os.ServiceManager");
        if (serviceManagerClass == null) {
            return null;
        }

        Method getServiceMethod = maybeGetMethod(serviceManagerClass, "getService", String.class);
        if (getServiceMethod == null) {
            return null;
        }

        Object hardwareService = invoke(getServiceMethod, null, "hardware");
        if (hardwareService == null) {
            return null;
        }

        Class<?> iHardwareServiceStubClass = maybeForName("android.os.IHardwareService$Stub");
        if (iHardwareServiceStubClass == null) {
            return null;
        }

        Method asInterfaceMethod = maybeGetMethod(iHardwareServiceStubClass, "asInterface", IBinder.class);
        if (asInterfaceMethod == null) {
            return null;
        }

        return invoke(asInterfaceMethod, null, hardwareService);
    }

    /**
     * Method getSetFlashEnabledMethod ...
     *
     * @param iHardwareService of type Object
     * @return Method
     */
    private static Method getSetFlashEnabledMethod(Object iHardwareService) {
        if (iHardwareService == null) {
            return null;
        }
        Class<?> proxyClass = iHardwareService.getClass();
        return maybeGetMethod(proxyClass, "setFlashlightEnabled", boolean.class);
    }

    /**
     * Method maybeForName ...
     *
     * @param name of type String
     * @return Class<?>
     */
    private static Class<?> maybeForName(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException cnfe) {
            // OK
            return null;
        } catch (RuntimeException re) {
            Log.w(TAG, "Unexpected error while finding class " + name, re);
            return null;
        }
    }

    /**
     * Method maybeGetMethod ...
     *
     * @param clazz      of type Class<?>
     * @param name       of type String
     * @param argClasses of type Class<?>...
     * @return Method
     */
    private static Method maybeGetMethod(Class<?> clazz, String name, Class<?>... argClasses) {
        try {
            return clazz.getMethod(name, argClasses);
        } catch (NoSuchMethodException nsme) {
            // OK
            return null;
        } catch (RuntimeException re) {
            Log.w(TAG, "Unexpected error while finding method " + name, re);
            return null;
        }
    }

    /**
     * Method invoke ...
     *
     * @param method   of type Method
     * @param instance of type Object
     * @param args     of type Object...
     * @return Object
     */
    private static Object invoke(Method method, Object instance, Object... args) {
        try {
            return method.invoke(instance, args);
        } catch (IllegalAccessException e) {
            Log.w(TAG, "Unexpected error while invoking " + method, e);
            return null;
        } catch (InvocationTargetException e) {
            Log.w(TAG, "Unexpected error while invoking " + method, e.getCause());
            return null;
        } catch (RuntimeException re) {
            Log.w(TAG, "Unexpected error while invoking " + method, re);
            return null;
        }
    }

    /**
     * Method enableFlashlight ...
     */
    static void enableFlashlight() {
        setFlashlight(true);
    }

    /**
     * Method disableFlashlight ...
     */
    static void disableFlashlight() {
        setFlashlight(false);
    }

    /**
     * Method setFlashlight sets the flashlight of this FlashlightManager object.
     *
     * @param active the flashlight of this FlashlightManager object.
     */
    private static void setFlashlight(boolean active) {
        if (iHardwareService != null) {
            invoke(setFlashEnabledMethod, iHardwareService, active);
        }
    }

}
