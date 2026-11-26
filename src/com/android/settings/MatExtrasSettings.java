/*
 * Copyright (C) 2026 The Android Open Source Project
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

package com.android.settings;

import android.app.settings.SettingsEnums;
import android.os.Bundle;

import androidx.preference.ListPreference;
import androidx.preference.Preference;

import com.android.settings.dashboard.DashboardFragment;

import lineageos.providers.LineageSettings;

public class MatExtrasSettings extends DashboardFragment {

    private static final String TAG = "MatExtrasSettings";
    private static final String KEY_SPECTRUM_WIDTH = "spectrum_width";
    private static final String KEY_SPECTRUM_LOCKSCREEN_HEIGHT = "spectrum_lockscreen_height";

    @Override
    public int getMetricsCategory() {
        return SettingsEnums.SETTINGS_SYSTEM_CATEGORY;
    }

    @Override
    protected String getLogTag() {
        return TAG;
    }

    @Override
    protected int getPreferenceScreenResId() {
        return R.xml.mat_extras_settings;
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        updateSpectrumWidthSummary();
        updateSpectrumLockscreenHeightSummary();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateSpectrumWidthSummary();
        updateSpectrumLockscreenHeightSummary();
        bindListSummary(KEY_SPECTRUM_WIDTH, R.string.mat_extras_spectrum_width_summary, 15);
        bindListSummary(KEY_SPECTRUM_LOCKSCREEN_HEIGHT,
                R.string.mat_extras_spectrum_lockscreen_height_summary, 96);
    }

    private void bindListSummary(String key, int summaryRes, int defaultValue) {
        Preference pref = findPreference(key);
        if (pref != null) {
            pref.setOnPreferenceChangeListener((p, newValue) -> {
                p.setSummary(getString(summaryRes, newValue));
                return true;
            });
        }
    }

    private void updateSpectrumWidthSummary() {
        updateListSummary(KEY_SPECTRUM_WIDTH, R.string.mat_extras_spectrum_width_summary, 15);
    }

    private void updateSpectrumLockscreenHeightSummary() {
        updateListSummary(KEY_SPECTRUM_LOCKSCREEN_HEIGHT,
                R.string.mat_extras_spectrum_lockscreen_height_summary, 96);
    }

    private void updateListSummary(String key, int summaryRes, int defaultValue) {
        ListPreference pref = findPreference(key);
        if (pref == null) {
            return;
        }
        String value = String.valueOf(LineageSettings.System.getInt(
                getContext().getContentResolver(), key, defaultValue));
        pref.setSummary(getString(summaryRes, value));
    }
}