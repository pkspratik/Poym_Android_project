package com.example.kanthi.projectmonitoring.Distance_cam;

import android.content.Context;
import android.hardware.SensorManager;
import android.view.OrientationEventListener;

/**
 * Created by Nishant on 10/31/2017.
 */

class CameraOrientationListener extends OrientationEventListener {
    private int mCurrentNormalizedOrientation;
    private int mRememberedNormalOrientation;

    public CameraOrientationListener(Context context) {
        super(context, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onOrientationChanged(int orientation) {
        if (orientation != ORIENTATION_UNKNOWN) {
            mCurrentNormalizedOrientation = normalize(orientation);
        }
    }

    /**
     * @param degrees Amount of clockwise rotation from the device's natural position
     * @return Normalized degrees to just 0, 90, 180, 270
     */
    private int normalize(int degrees) {
        if (degrees > 315 || degrees <= 45) {
            return 0;
        }

        if (degrees > 45 && degrees <= 135) {
            return 90;
        }

        if (degrees > 135 && degrees <= 225) {
            return 180;
        }

        if (degrees > 225 && degrees <= 315) {
            return 270;
        }

        throw new RuntimeException("The physics as we know them are no more. Watch out for anomalies.");
    }

    public void rememberOrientation() {
        mRememberedNormalOrientation = mCurrentNormalizedOrientation;
    }

    public int getRememberedNormalOrientation() {
        rememberOrientation();
        return mRememberedNormalOrientation;
    }
}
