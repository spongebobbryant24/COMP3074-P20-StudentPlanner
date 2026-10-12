package com.comp3074.mirpour.jamshaid.studentplanner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * UI PROTOTYPE ONLY: Items are held in memory and reset when Android kills the app process.
 * Replace this class with real storage in the later Functional Prototype milestone.
 */
public final class PlannerRepository {
    private static final ArrayList<CourseItem> courses = new ArrayList<>();
    private static final ArrayList<HomeworkItem> homework = new ArrayList<>();
    private static int nextCourseId = 1;
    private static int nextHomeworkId = 1;

    static {
        CourseItem mobile = saveCourse(new CourseItem(0, "COMP3074", "Monday", "10:00", "11:30"));
        CourseItem math = saveCourse(new CourseItem(0, "Math", "Tuesday", "13:00", "14:30"));
        CourseItem english = saveCourse(new CourseItem(0, "English", "Wednesday", "09:00", "10:00"));
        saveHomework(new HomeworkItem(0, "Math Worksheet", dateAfterDays(1), "17:00", math.id, false));
        saveHomework(new HomeworkItem(0, "Essay Draft", dateAfterDays(2), "23:59", english.id, false));
        saveHomework(new HomeworkItem(0, "COMP3074 Lab", dateAfterDays(4), "23:59", mobile.id, false));
    }

    private PlannerRepository() { }

    private static String dateAfterDays(int days) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, days);
        return new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(c.getTime());
    }

    public static ArrayList<CourseItem> getCourses() {
        return new ArrayList<>(courses);
    }

    public static ArrayList<HomeworkItem> getHomework() {
        ArrayList<HomeworkItem> result = new ArrayList<>(homework);
        Collections.sort(result, new Comparator<HomeworkItem>() {
            @Override public int compare(HomeworkItem a, HomeworkItem b) {
                int byDate = a.dueDate.compareTo(b.dueDate);
                return byDate != 0 ? byDate : a.dueTime.compareTo(b.dueTime);
            }
        });
        return result;
    }

    public static CourseItem findCourse(int id) {
        for (CourseItem item : courses) if (item.id == id) return item;
        return null;
    }

    public static HomeworkItem findHomework(int id) {
        for (HomeworkItem item : homework) if (item.id == id) return item;
        return null;
    }

    public static CourseItem saveCourse(CourseItem data) {
        if (data.id == 0) {
            data.id = nextCourseId++;
            courses.add(data);
            return data;
        }
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).id == data.id) {
                courses.set(i, data);
                return data;
            }
        }
        throw new IllegalArgumentException("Class does not exist");
    }

    public static HomeworkItem saveHomework(HomeworkItem data) {
        if (findCourse(data.courseId) == null) {
            throw new IllegalArgumentException("Please choose an existing class");
        }
        if (data.id == 0) {
            data.id = nextHomeworkId++;
            homework.add(data);
            return data;
        }
        for (int i = 0; i < homework.size(); i++) {
            if (homework.get(i).id == data.id) {
                homework.set(i, data);
                return data;
            }
        }
        throw new IllegalArgumentException("Homework item does not exist");
    }

    // Deleting a class also deletes homework linked to it to avoid orphaned assignments.
    public static void deleteCourse(int id) {
        for (int i = homework.size() - 1; i >= 0; i--) {
            if (homework.get(i).courseId == id) homework.remove(i);
        }
        for (int i = courses.size() - 1; i >= 0; i--) {
            if (courses.get(i).id == id) courses.remove(i);
        }
    }

    public static void deleteHomework(int id) {
        for (int i = homework.size() - 1; i >= 0; i--) {
            if (homework.get(i).id == id) homework.remove(i);
        }
    }

    public static void markCompleted(int id, boolean completed) {
        HomeworkItem item = findHomework(id);
        if (item != null) item.completed = completed;
    }

    public static String className(int courseId) {
        CourseItem course = findCourse(courseId);
        return course == null ? "Unknown class" : course.name;
    }

    public static int completedCount() {
        int count = 0;
        for (HomeworkItem h : homework) if (h.completed) count++;
        return count;
    }
}
