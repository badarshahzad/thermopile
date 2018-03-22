package com.knobtviker.thermopile.presentation.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.collect.ImmutableList;
import com.knobtviker.thermopile.R;
import com.knobtviker.thermopile.data.models.local.Settings;
import com.knobtviker.thermopile.presentation.contracts.SettingsContract;
import com.knobtviker.thermopile.presentation.fragments.implementation.BaseFragment;
import com.knobtviker.thermopile.presentation.presenters.SettingsPresenter;
import com.knobtviker.thermopile.presentation.views.adapters.SettingsPagerAdapter;
import com.knobtviker.thermopile.presentation.views.communicators.MainCommunicator;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by bojan on 15/06/2017.
 */

public class SettingsFragment extends BaseFragment<SettingsContract.Presenter> implements SettingsContract.View {
    public static final String TAG = SettingsFragment.class.getSimpleName();

    private final LocaleFragment regionFragment;
    private final FormatFragment formatFragment;
    private final UnitFragment unitFragment;

    @BindView(R.id.tab_layout)
    public TabLayout tabLayout;

    @BindView(R.id.view_pager)
    public ViewPager viewPager;

    @Nullable
    private MainCommunicator mainCommunicator;

    public static Fragment newInstance() {
        return new SettingsFragment();
    }

    public SettingsFragment() {
        this.regionFragment = LocaleFragment.newInstance();
        this.formatFragment = FormatFragment.newInstance();
        this.unitFragment = UnitFragment.newInstance();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof MainCommunicator) {
            mainCommunicator = (MainCommunicator) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new SettingsPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        bind(this, view);

        setupViewPager();

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        presenter.load(realm);

        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mainCommunicator = null;
    }

    @Override
    public void onLoad(@NonNull Settings settings) {
        regionFragment.onLoad(settings);
        formatFragment.onLoad(settings);
        unitFragment.onLoad(settings);
    }

    @Override
    public void showLoading(boolean isLoading) {
        //TODO: Show loading progress indicator
    }

    @Override
    public void showError(@NonNull Throwable throwable) {
        Log.e(TAG, throwable.getMessage(), throwable);
    }

    @OnClick({R.id.button_back, R.id.button_help, R.id.button_feedback, R.id.button_about})
    public void onClicked(@NonNull final View view) {
        switch (view.getId()) {
            case R.id.button_back:
                if (mainCommunicator != null) {
                    mainCommunicator.back();
                }
                break;
            case R.id.button_help:
                Log.i(TAG, "Show HelpActivity");
                break;
            case R.id.button_feedback:
                Log.i(TAG, "Show FeedbackActivity");
                break;
            case R.id.button_about:
                Log.i(TAG, "Show AboutActivity");
                break;
        }
    }

    private void setupViewPager() {
        viewPager.setAdapter(new SettingsPagerAdapter(
                getChildFragmentManager(),
                ImmutableList.of(getString(R.string.label_locale), getString(R.string.label_format), getString(R.string.label_unit)),
                ImmutableList.of(regionFragment, formatFragment, unitFragment)
            )
        );
        if (viewPager.getAdapter() != null) {
            viewPager.setOffscreenPageLimit(viewPager.getAdapter().getCount());
        }

        tabLayout.setupWithViewPager(viewPager);
    }
}
