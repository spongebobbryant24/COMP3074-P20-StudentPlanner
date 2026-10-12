package com.comp3074.mirpour.jamshaid.studentplanner;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EditClassActivity extends BaseActivity {
    private static final String[] DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday",
            "Friday", "Saturday", "Sunday"};
    private EditText nameInput;
    private Spinner daySpinner;
    private Button startButton;
    private Button endButton;
    private Button deleteButton;
    private String startTime = "10:00";
    private String endTime = "11:00";
    private int editingId;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_edit_class);
        editingId = getIntent().getIntExtra("courseId", 0);
        setupScreen(editingId == 0 ? "Add Class" : "Edit Class",
                "Enter the class name, day, and times", "schedule");

        nameInput = findViewById(R.id.classNameInput);
        daySpinner = findViewById(R.id.classDaySpinner);
        startButton = findViewById(R.id.startTimeButton);
        endButton = findViewById(R.id.endTimeButton);
        deleteButton = findViewById(R.id.deleteClassButton);
        ArrayAdapter<String> days = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, DAYS);
        days.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(days);

        CourseItem course = editingId == 0 ? null : PlannerRepository.findCourse(editingId);
        if (editingId != 0 && course == null) {
            Toast.makeText(this, "Class no longer exists", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        if (course != null) {
            nameInput.setText(course.name);
            startTime = course.startTime;
            endTime = course.endTime;
            for (int i = 0; i < DAYS.length; i++) {
                if (DAYS[i].equals(course.day)) daySpinner.setSelection(i);
            }
        }
        updateTimeButtons();
        startButton.setOnClickListener(v -> chooseTime(true));
        endButton.setOnClickListener(v -> chooseTime(false));
        deleteButton.setVisibility(editingId == 0 ? View.GONE : View.VISIBLE);
        findViewById(R.id.saveClassButton).setOnClickListener(v -> saveClass());
        findViewById(R.id.cancelClassButton).setOnClickListener(v -> finish());
        deleteButton.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Delete this class?")
                    .setMessage("Homework linked to this class will also be deleted.")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Delete", (dialog, which) -> {
                        PlannerRepository.deleteCourse(editingId);
                        Toast.makeText(this, "Class deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .show();
        });
    }

    private void updateTimeButtons() {
        startButton.setText(FormatUtils.displayTime(startTime));
        endButton.setText(FormatUtils.displayTime(endTime));
    }

    private void chooseTime(boolean start) {
        String current = start ? startTime : endTime;
        String[] parts = current.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        new TimePickerDialog(this, (picker, hour, minute) -> {
            if (start) startTime = FormatUtils.paddedTime(hour, minute);
            else endTime = FormatUtils.paddedTime(hour, minute);
            updateTimeButtons();
        }, hours, minutes, false).show();
    }

    private void saveClass() {
        String name = nameInput.getText().toString().trim();
        if (name.isEmpty()) {
            nameInput.setError("Please enter a class name");
            nameInput.requestFocus();
            return;
        }
        if (startTime.compareTo(endTime) >= 0) {
            Toast.makeText(this, "End time must be after start time", Toast.LENGTH_LONG).show();
            return;
        }
        PlannerRepository.saveCourse(new CourseItem(editingId, name,
                (String) daySpinner.getSelectedItem(), startTime, endTime));
        Toast.makeText(this, editingId == 0 ? "Class added" : "Class updated", Toast.LENGTH_SHORT).show();
        finish();
    }
}
