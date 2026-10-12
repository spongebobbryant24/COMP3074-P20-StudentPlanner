# Student Planner — COMP3074 UI Prototype 1

**Project:** Student Planner  
**Course:** COMP3074 — Mobile Application Development I  
**Team:** P20  
**Prototype scope:** All five screens + navigation + basic interactions

## What is included

| Screen | Android Activity | Demonstration actions |
|---|---|---|
| Dashboard | `MainActivity` | See example next-class/homework cards, navigate to lists |
| Class Schedule | `ClassScheduleActivity` | View class list, choose a class to edit, open Add Class |
| Homework | `HomeworkActivity` | View assignments, mark complete, choose an item to edit |
| Add / Edit Class | `EditClassActivity` | Enter name/day/start/end time, Save, Cancel, Edit, Delete |
| Add / Edit Homework | `EditHomeworkActivity` | Enter title, select class, due date/time, completion checkbox, Save/Edit/Delete |

Screens and fields follow the COMP3074 **Student Planner** design proposal. The UI is implemented in Java and XML layouts rather than Jetpack Compose.

**Important limitation:** This is a UI prototype. Classes and homework are stored **in memory only** and initialized with sample data (`COMP3074`, `Math`, `English`). Items you create and edit will be lost when Android restarts the app process. There is **no database, login, cloud sync, or reminder notification** in this milestone. Real persistence belongs to the Functional Prototype stage.

## Getting it running in Android Studio (Windows)

1. Extract the entire ZIP. Keep the `StudentPlanner` folder intact.
2. Run **`SETUP_GRADLE_WRAPPER.bat`** in the extracted `StudentPlanner` folder **once**, while connected to the internet. This downloads the standard Gradle 8.9 wrapper JAR from the official Gradle project's GitHub repository. The script checks that a valid JAR was received.
3. Open Android Studio. Choose **File > Open**, browse to the extracted **`StudentPlanner`** folder (the folder with `settings.gradle`), then select it.
4. Wait for Gradle sync. If Android Studio offers to install Android SDK Platform 35 or missing components, accept.
5. Open **Tools > Device Manager** and select an emulator, then click **Run** (green triangle).
6. After it starts, use the bottom Home / Schedule / Homework tabs.

**Required software:** Current Android Studio, Android SDK 35, Java 17-compatible Gradle JDK (Android Studio's bundled JDK usually works), and an Android emulator or device. `minSdk` is 24 (Android 7.0).

### If Gradle sync fails

- Verify you opened the **project folder**, not the ZIP or `app` subfolder.
- Check whether `gradle/wrapper/gradle-wrapper.jar` exists. If it doesn't, run `SETUP_GRADLE_WRAPPER.bat` with an internet connection.
- Check **Settings > Build, Execution, Deployment > Build Tools > Gradle** and choose the bundled JDK (usually JDK 17 or later).
- If SDK 35 is missing, install it through **Tools > SDK Manager**.
- Wait for initial Gradle/plugin dependencies to download; a first sync can take several minutes.
- If it still fails, copy the exact Gradle error to ChatGPT to troubleshoot. **The complete Android APK has not been built or run inside this delivery environment.**

## Test checklist

- [ ] Dashboard loads and navigates to Class Schedule and Homework.
- [ ] Bottom Home / Schedule / Homework buttons work on all screens.
- [ ] Class Schedule shows the three example classes.
- [ ] Add a class with name, day, start and end time. Verify it appears in Class Schedule.
- [ ] Edit that class and verify your change is shown.
- [ ] Add homework using the class selector, due date, and due time. Verify it appears in Homework.
- [ ] Tick the Completed checkbox in Homework. Verify the progress total changes on the Dashboard.
- [ ] Edit an assignment and verify its new title/due date.
- [ ] Delete a test homework item. Try deleting a test class and verify its linked assignments are removed.
- [ ] Back/Cancel does not save unsaved changes.

## Recording Prototype 1 demonstration

The project specification asks for a **short video explaining progress** and a **Git repository link**, along with UI for **all screens** and logic for **at least two screens**. A short demonstration can show:

1. Dashboard and three navigation tabs.
2. Class Schedule, its Add / Edit Class form, and a saved change.
3. Homework, its Add / Edit Homework form, and a saved change / completion checkbox.
4. Explain that permanent storage and notifications are **not yet implemented** in Prototype 1.
5. Briefly show the Android Studio project structure and working emulator.

Commit this Android Studio project to GitHub and submit the repository link and video as required. The grading instructions also expect contributions from all team members over the project; keep the contribution record accurate.

## Project files

- `app/src/main/java/com/comp3074/mirpour/jamshaid/studentplanner/` — activities, model classes, and temporary repository.
- `app/src/main/res/layout/` — five screen layouts and reusable UI layouts.
- `app/src/main/res/values/` — theme colors, string resources, and text styles.
- `app/src/main/AndroidManifest.xml` — activity declarations.

## Academic integrity

This Android Studio prototype's source code and layouts were generated with ChatGPT assistance. If submitting this work, review, understand, test and adapt it to match your own design, and **accurately disclose AI assistance according to your course's AI Usage Declaration instructions**. Do not claim group contributions that did not happen.
