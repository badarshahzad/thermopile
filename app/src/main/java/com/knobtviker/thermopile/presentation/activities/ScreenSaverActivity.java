package com.knobtviker.thermopile.presentation.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;

import com.knobtviker.thermopile.presentation.ThermopileApp;
import com.knobtviker.thermopile.presentation.activities.implementation.BaseActivity;
import com.knobtviker.thermopile.presentation.fragments.ScreensaverFragment;

/**
 * Created by bojan on 11/06/2017.
 */

public class ScreenSaverActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showScreensaver();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            ((ThermopileApp)getApplication()).createScreensaver();
            finish();
        }
        return super.dispatchTouchEvent(event);
    }

    private void showScreensaver() {
        Fragment fragment = findFragment(ScreensaverFragment.TAG);
        if (fragment == null) {
            fragment = ScreensaverFragment.newInstance();
        }

        replaceFragment(fragment, ScreensaverFragment.TAG, false);
    }
}
