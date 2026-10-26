package com.garvitsharma.expensetracker;

import android.app.Application;

import com.google.android.material.color.DynamicColors;
import com.google.android.material.color.DynamicColorsOptions;
import com.google.android.material.color.HarmonizedColors;
import com.google.android.material.color.HarmonizedColorsOptions;

/**
 * Turns on Material You dynamic colour (Android 12+) for every screen and shifts the
 * income green towards the user's wallpaper palette so it still looks native.
 */
public class ExpenseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        final HarmonizedColorsOptions harmonized = new HarmonizedColorsOptions.Builder()
                .setColorResourceIds(new int[]{
                        R.color.income, R.color.income_container, R.color.on_income_container})
                .build();
        DynamicColors.applyToActivitiesIfAvailable(this,
                new DynamicColorsOptions.Builder()
                        .setOnAppliedCallback(activity ->
                                HarmonizedColors.applyToContextIfAvailable(activity, harmonized))
                        .build());
    }
}
