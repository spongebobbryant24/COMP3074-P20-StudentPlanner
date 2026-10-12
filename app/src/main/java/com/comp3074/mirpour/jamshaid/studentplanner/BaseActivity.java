package com.comp3074.mirpour.jamshaid.studentplanner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public abstract class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(getColor(R.color.page_bg));
        getWindow().setNavigationBarColor(getColor(R.color.paper));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    protected void setupScreen(String title, String subtitle, String selectedNav) {
        ((TextView) findViewById(R.id.headerPageTitle)).setText(title);
        ((TextView) findViewById(R.id.headerSubtitle)).setText(subtitle);

        Button home = findViewById(R.id.navHome);
        Button schedule = findViewById(R.id.navSchedule);
        Button homework = findViewById(R.id.navHomework);
        styleNav(home, "home".equals(selectedNav));
        styleNav(schedule, "schedule".equals(selectedNav));
        styleNav(homework, "homework".equals(selectedNav));

        home.setOnClickListener(v -> goTo(MainActivity.class));
        schedule.setOnClickListener(v -> goTo(ClassScheduleActivity.class));
        homework.setOnClickListener(v -> goTo(HomeworkActivity.class));
    }

    private void styleNav(Button nav, boolean active) {
        if (active) {
            nav.setBackgroundResource(R.drawable.soft_bg);
            nav.setTextColor(getColor(R.color.blue_dark));
        } else {
            nav.setBackgroundColor(Color.TRANSPARENT);
            nav.setTextColor(getColor(R.color.muted));
        }
    }

    protected void goTo(Class<? extends Activity> screen) {
        if (getClass() == screen) return;
        Intent intent = new Intent(this, screen);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        // Finishing forms prevents piling duplicate screens onto the back stack.
        if (this instanceof EditClassActivity || this instanceof EditHomeworkActivity) finish();
    }

    protected int dp(float value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }
}
