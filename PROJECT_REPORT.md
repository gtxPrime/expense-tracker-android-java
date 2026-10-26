# Project Report - Expense Tracker (Major Project)

Student: Garvit Sharma | Roll Number: AD-JV-A2/JY-11199 | Program: Android App Development + Java Training Program

## Project Overview
An Android app to record income and expenses, view totals and keep data on the phone.

## Objective
Practise lists, forms, local storage, validation and app structure in Java.

## Features
Add income/expense with amount, description and category (Food, Travel, Shopping, Bills, Other); transaction list in a RecyclerView; Total Income, Total Expenses, Remaining Balance; delete with confirmation; SQLite storage; validation of empty, zero, negative, too many decimals and too large amounts; Indian number format; Material 3 with Material You colours, light and dark.

## Technologies Used
Java, Android Studio, XML, RecyclerView, SQLite, Material Components, Gradle, JUnit 4.

## Android Components Used
Activity (2), Intent, Application class, RecyclerView + Adapter, SQLiteOpenHelper, TextInputLayout/EditText, ChipGroup, MaterialButtonToggleGroup, FloatingActionButton, AlertDialog, Toast.

## Setup Instructions
Open the folder in Android Studio, sync Gradle, run on an emulator or phone. Or `./gradlew assembleDebug` and install `app/build/outputs/apk/debug/app-debug.apk`.

## Screenshots
Real screenshots from an emulator/phone: **pending, to be added by the student after running the app.**
Layout previews (rendered on a computer from the real XML with sample data, not captured from a running app) are in `docs/layout-previews/`.

## Challenges Faced
- Amounts stored as paise (`long`) to avoid floating point errors.
- Layout previews showed the type toggle missing, and long text and very large amounts squeezing the list rows; these were fixed in the XML.

## Key Learning Outcomes
SQLite with SQLiteOpenHelper, RecyclerView adapters, input validation, Material 3 theming.

## Project Structure
See README.md.

## Testing
12 unit tests pass (parsing/validation, totals, currency format). Layout previews inspected in light and dark mode and at 1.5x font. Not yet tested on an emulator or phone: saving, deleting, persistence after restart, keyboard behaviour.

## Development note
This code was written and built in one session on 26 Oct 2026 with AI assistance and committed afterwards as a single honest snapshot; the commit history does not show gradual development.
