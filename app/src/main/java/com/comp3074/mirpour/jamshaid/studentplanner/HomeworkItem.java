package com.comp3074.mirpour.jamshaid.studentplanner;

public class HomeworkItem {
    public int id;
    public String title;
    // Both fields use sortable ISO-style strings, such as "2026-10-12" and "17:00".
    public String dueDate;
    public String dueTime;
    public int courseId;
    public boolean completed;

    public HomeworkItem(int id, String title, String dueDate, String dueTime,
                        int courseId, boolean completed) {
        this.id = id;
        this.title = title;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.courseId = courseId;
        this.completed = completed;
    }
}
