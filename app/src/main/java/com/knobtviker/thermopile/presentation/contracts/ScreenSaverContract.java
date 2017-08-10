package com.knobtviker.thermopile.presentation.contracts;

import android.support.annotation.NonNull;

import com.knobtviker.thermopile.data.models.presentation.Settings;
import com.knobtviker.thermopile.presentation.presenters.implementation.BasePresenter;
import com.knobtviker.thermopile.presentation.views.implementation.BaseView;

/**
 * Created by bojan on 15/07/2017.
 */

public interface ScreenSaverContract {

    interface View extends BaseView {

        void onSettings(@NonNull final Settings settings);
    }

    interface Presenter extends BasePresenter {

        void settings();
    }
}