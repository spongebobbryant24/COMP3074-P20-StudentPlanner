package com.comp3074.mirpour.jamshaid.studentplanner;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class EditHomeworkActivity extends BaseActivity {
    private EditText titleInput;
    private Spinner courseSpinner;
    private Button dateButton;
    private Button timeButton;
    private Button deleteButton;
    private CheckBox completed;
    private ArrayList<CourseItem> availableCourses;
    private String dueDate;
    private String dueTime = "17:00";
    private int editingId;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_edit_homework);
        editingId = getIntent().getIntExtra("homeworkId", 0);
        setupScreen(editingId == 0 ? "Add Homework" : "Edit Homework",
                "Choose the class, due date, and status", "homework");

        titleInput = findViewById(R.id.homeworkTitleInput);
        courseSpinner = findViewById(R.id.homeworkCourseSpinner);
        dateButton = findViewById(R.id.dueDateButton);
        timeButton = findViewById(R.id.dueTimeButton);
        deleteButton = findViewById(R.id.deleteHomeworkButton);
        completed = findViewById(R.id.completedCheckbox);
        availableCourses = PlannerRepository.getCourses();
        ArrayList<String> names = new ArrayList<>();
        for (CourseItem course : availableCourses) names.add(course.name);
        if (names.isEmpty()) names.add("No classes available — add a class first");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(adapter);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        dueDate = FormatUtils.paddedDate(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        HomeworkItem item = editingId == 0 ? null : PlannerRepository.findHomework(editingId);
        if (editingId != 0 && item == null) {
            Toast.makeText(this, "Homework no longer exists", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        if (item != null) {
            titleInput.setText(item.title);
            dueDate = item.dueDate;
            dueTime = item.dueTime;
            completed.setChecked(item.completed);
            for (int i = 0; i < availableCourses.size(); i++) {
                if (availableCourses.get(i).id == item.courseId) courseSpinner.setSelection(i);
            }
        }
        updateDueButtons();
        dateButton.setOnClickListener(v -> chooseDate());
        timeButton.setOnClickListener(v -> chooseTime());
        deleteButton.setVisibility(editingId == 0 ? View.GONE : View.VISIBLE);
        findViewById(R.id.saveHomeworkButton).setOnClickListener(v -> saveHomework());
        findViewById(R.id.cancelHomeworkButton).setOnClickListener(v -> finish());
        deleteButton.setOnClickListener(v -> new AlertDialog.Builder(this)
                .setTitle("Delete this homework?")
                .setMessage("This assignment will be removed from the list.")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Delete", (dialog, which) -> {
                    PlannerRepository.deleteHomework(editingId);
                    Toast.makeText(this, "Homework deleted", Toast.LENGTH_SHORT).show();
                    finish();
                }).show());
    }

    private void updateDueButtons() {
        dateButton.setText(FormatUtils.displayDate(dueDate));
        timeButton.setText(FormatUtils.displayTime(dueTime));
    }

    private void chooseDate() {
        String[] parts = dueDate.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]) - 1;
        int day = Integer.parseInt(parts[2]);
        new DatePickerDialog(this, (picker, y, m, d) -> {
            dueDate = FormatUtils.paddedDate(y, m + 1, d);
            updateDueButtons();
        }, year, month, day).show();
    }

    private void chooseTime() {
        String[] parts = dueTime.split(":");
        new TimePickerDialog(this, (picker, hour, minute) -> {
            dueTime = FormatUtils.paddedTime(hour, minute);
            updateDueButtons();
        }, Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), false).show();
    }

    private void saveHomework() {
        String title = titleInput.getText().toString().trim();
        if (title.isEmpty()) {
            titleInput.setError("Please enter a homework title");
            titleInput.requestFocus();
            return;
        }
        if (availableCourses.isEmpty()) {
            Toast.makeText(this, "Add a class in Schedule before saving homework", Toast.LENGTH_LONG).show();
            return;
        }
        int selectedIndex = courseSpinner.getSelectedItemPosition();
        int courseId = availableCourses.get(selectedIndex).id;
        PlannerRepository.saveHomework(new HomeworkItem(editingId, title,
                dueDate, dueTime, courseId, completed.isChecked()));
        Toast.makeText(this, editingId == 0 ? "Homework added" : "Homework updated", Toast.LENGTH_SHORT).show();
        finish();
    }
}
