package com.comp3074.mirpour.jamshaid.studentplanner;

import android.os.Bundle;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_main);
        setupScreen("Dashboard", "Your school day at a glance", "home");
        findViewById(R.id.openSchedule).setOnClickListener(v -> goTo(ClassScheduleActivity.class));
        findViewById(R.id.openHomework).setOnClickListener(v -> goTo(HomeworkActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshDashboard();
    }

    private void refreshDashboard() {
        ArrayList<CourseItem> courses = PlannerRepository.getCourses();
        TextView nextName = findViewById(R.id.nextClassName);
        TextView nextTime = findViewById(R.id.nextClassTime);
        if (courses.isEmpty()) {
            nextName.setText("No classes yet");
            nextTime.setText("Add a class in the Schedule screen.");
        } else {
            CourseItem upcoming = courses.get(0);
            nextName.setText(upcoming.name);
            nextTime.setText(upcoming.day + " • " + FormatUtils.displayTime(upcoming.startTime)
                    + " – " + FormatUtils.displayTime(upcoming.endTime));
        }

        ArrayList<HomeworkItem> pending = new ArrayList<>();
        for (HomeworkItem item : PlannerRepository.getHomework()) {
            if (!item.completed) pending.add(item);
        }
        setHomeworkPreview(R.id.homeworkOneName, R.id.homeworkOneDue, pending, 0);
        setHomeworkPreview(R.id.homeworkTwoName, R.id.homeworkTwoDue, pending, 1);
        int total = PlannerRepository.getHomework().size();
        ((TextView) findViewById(R.id.progressText))
                .setText(PlannerRepository.completedCount() + " of " + total + " homework items completed");
    }

    private void setHomeworkPreview(int titleId, int dueId, ArrayList<HomeworkItem> pending, int index) {
        TextView title = findViewById(titleId);
        TextView due = findViewById(dueId);
        if (index >= pending.size()) {
            title.setText(index == 0 ? "You're all caught up!" : "No other pending homework");
            due.setText(index == 0 ? "Add homework or mark items as complete." : "");
        } else {
            HomeworkItem item = pending.get(index);
            title.setText(item.title);
            due.setText(PlannerRepository.className(item.courseId) + " • Due "
                    + FormatUtils.displayDate(item.dueDate) + " at "
                    + FormatUtils.displayTime(item.dueTime));
        }
    }
}
