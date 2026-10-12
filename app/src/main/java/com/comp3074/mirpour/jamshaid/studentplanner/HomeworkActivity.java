package com.comp3074.mirpour.jamshaid.studentplanner;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeworkActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_homework);
        setupScreen("Homework", "Your assignments and due dates", "homework");
        findViewById(R.id.addHomeworkButton).setOnClickListener(v -> openEditor(0));
    }

    @Override
    protected void onResume() {
        super.onResume();
        showHomework();
    }

    private void openEditor(int id) {
        Intent intent = new Intent(this, EditHomeworkActivity.class);
        intent.putExtra("homeworkId", id);
        startActivity(intent);
    }

    private void showHomework() {
        LinearLayout container = findViewById(R.id.homeworkListContainer);
        container.removeAllViews();
        ArrayList<HomeworkItem> items = PlannerRepository.getHomework();
        ((TextView) findViewById(R.id.homeworkSummary)).setText(
                PlannerRepository.completedCount() + " of " + items.size() + " completed • Tap an item to edit");
        if (items.isEmpty()) {
            TextView message = new TextView(this);
            message.setText("No homework yet. Add a class first, then add an assignment.");
            message.setTextColor(getColor(R.color.muted));
            message.setTextSize(15);
            message.setPadding(dp(12), dp(12), dp(12), dp(12));
            container.addView(message);
        }
        for (HomeworkItem item : items) {
            View row = LayoutInflater.from(this).inflate(R.layout.item_homework, container, false);
            TextView title = row.findViewById(R.id.homeworkRowTitle);
            TextView meta = row.findViewById(R.id.homeworkRowMeta);
            CheckBox check = row.findViewById(R.id.homeworkRowCheck);
            title.setText(item.title);
            meta.setText(PlannerRepository.className(item.courseId) + " • Due "
                    + FormatUtils.displayDate(item.dueDate) + ", " + FormatUtils.displayTime(item.dueTime));
            check.setChecked(item.completed);
            setStrikeThrough(title, item.completed);
            check.setOnCheckedChangeListener((button, completed) -> {
                PlannerRepository.markCompleted(item.id, completed);
                setStrikeThrough(title, completed);
                ((TextView) findViewById(R.id.homeworkSummary)).setText(
                        PlannerRepository.completedCount() + " of " + PlannerRepository.getHomework().size()
                                + " completed • Tap an item to edit");
            });
            row.findViewById(R.id.homeworkRowClickArea).setOnClickListener(v -> openEditor(item.id));
            row.findViewById(R.id.homeworkRowEdit).setOnClickListener(v -> openEditor(item.id));
            container.addView(row);
        }
    }

    private void setStrikeThrough(TextView text, boolean done) {
        int flags = text.getPaintFlags();
        text.setPaintFlags(done ? flags | Paint.STRIKE_THRU_TEXT_FLAG : flags & ~Paint.STRIKE_THRU_TEXT_FLAG);
        text.setTextColor(getColor(done ? R.color.muted : R.color.ink));
    }
}
