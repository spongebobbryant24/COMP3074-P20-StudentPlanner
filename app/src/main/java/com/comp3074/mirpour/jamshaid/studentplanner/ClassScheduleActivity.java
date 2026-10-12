package com.comp3074.mirpour.jamshaid.studentplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ClassScheduleActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_schedule);
        setupScreen("Class Schedule", "View and manage your classes", "schedule");
        findViewById(R.id.addClassButton).setOnClickListener(v -> openEditor(0));
    }

    @Override
    protected void onResume() {
        super.onResume();
        showCourses();
    }

    private void openEditor(int id) {
        Intent i = new Intent(this, EditClassActivity.class);
        i.putExtra("courseId", id);
        startActivity(i);
    }

    private void showCourses() {
        LinearLayout container = findViewById(R.id.classListContainer);
        container.removeAllViews();
        if (PlannerRepository.getCourses().isEmpty()) {
            TextView message = new TextView(this);
            message.setText("No classes saved yet. Tap Add Class to get started.");
            message.setTextColor(getColor(R.color.muted));
            message.setTextSize(15);
            message.setPadding(dp(12), dp(12), dp(12), dp(12));
            container.addView(message);
        }
        for (CourseItem course : PlannerRepository.getCourses()) {
            View row = LayoutInflater.from(this).inflate(R.layout.item_course, container, false);
            ((TextView) row.findViewById(R.id.courseRowTitle)).setText(course.name);
            String when = course.day + " • " + FormatUtils.displayTime(course.startTime)
                    + " – " + FormatUtils.displayTime(course.endTime);
            ((TextView) row.findViewById(R.id.courseRowMeta)).setText(when);
            row.findViewById(R.id.courseRowEdit).setOnClickListener(v -> openEditor(course.id));
            row.setOnClickListener(v -> openEditor(course.id));
            container.addView(row);
        }
    }
}
