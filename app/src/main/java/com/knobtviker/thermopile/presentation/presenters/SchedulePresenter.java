package com.knobtviker.thermopile.presentation.presenters;

import android.support.annotation.NonNull;

import com.knobtviker.thermopile.data.models.local.Settings;
import com.knobtviker.thermopile.data.models.local.Threshold;
import com.knobtviker.thermopile.domain.repositories.SettingsRepository;
import com.knobtviker.thermopile.domain.repositories.ThresholdRepository;
import com.knobtviker.thermopile.presentation.contracts.ScheduleContract;

import io.reactivex.disposables.CompositeDisposable;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by bojan on 15/07/2017.
 */

public class SchedulePresenter implements ScheduleContract.Presenter {

    private final ScheduleContract.View view;

    private SettingsRepository settingsRepository;
    private ThresholdRepository thresholdRepository;
    private CompositeDisposable compositeDisposable;

    private RealmResults<Settings> resultsSettings;
    private RealmChangeListener<RealmResults<Settings>> changeListenerSettings;

    private RealmResults<Threshold> resultsThresholds;
    private RealmChangeListener<RealmResults<Threshold>> changeListenerThresholds;

    public SchedulePresenter(@NonNull final ScheduleContract.View view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        settingsRepository = SettingsRepository.getInstance();
        thresholdRepository = ThresholdRepository.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void unsubscribe() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable = null;
        }
        if (resultsSettings != null && resultsSettings.isValid()) {
            resultsSettings.removeChangeListener(changeListenerSettings);
            resultsSettings = null;
            changeListenerSettings = null;
        }
        if (resultsThresholds != null && resultsThresholds.isValid()) {
            resultsThresholds.removeChangeListener(changeListenerThresholds);
            resultsThresholds = null;
            changeListenerThresholds = null;
        }
        SettingsRepository.destroyInstance();
        ThresholdRepository.destroyInstance();
    }

    @Override
    public void error(@NonNull Throwable throwable) {
        completed();

        view.showError(throwable);
    }

    @Override
    public void started() {
        view.showLoading(true);
    }

    @Override
    public void completed() {
        view.showLoading(false);
    }

    @Override
    public void settings() {
        resultsSettings = settingsRepository.load();

        if (resultsSettings != null && resultsSettings.isValid()) {
            changeListenerSettings = settingsRealmResults -> {
                if (!settingsRealmResults.isEmpty()) {
                    view.onSettingsChanged(settingsRealmResults.first());
                }
            };
            resultsSettings.addChangeListener(changeListenerSettings);
        }
    }

    @Override
    public void thresholds() {
        started();

        resultsThresholds = thresholdRepository.load();

        if (resultsThresholds != null && resultsThresholds.isValid()) {
            changeListenerThresholds = thresholdsRealmResults -> {
                if (!thresholdsRealmResults.isEmpty()) {
                    view.onThresholds(thresholdsRealmResults);
                }
            };
            resultsThresholds.addChangeListener(changeListenerThresholds);
        }

        completed();
    }
}
