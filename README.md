# Expense Tracker

Major Project - Android App Development + Java Training Program (InternsElite)

**Student:** Garvit Sharma
**Roll Number:** AD-JV-A2/JY-11199 (matched by name in the official InternsElite roll list)

An Android app to record income and expenses, see totals at a glance and keep the data on the phone. Written in Java with a Material 3 (Material You) interface.

## Features

- Add a transaction: Income or Expense, amount, description
- Expense categories: Food, Travel, Shopping, Bills, Other
- List of all transactions (newest first) in a `RecyclerView`
- Total Income, Total Expenses and Remaining Balance, updated on every change
- Delete a transaction (with a confirmation dialog)
- Data is saved in a local SQLite database and is still there after restarting the app
- Validation: empty amount, zero or negative amount, more than 2 decimals, too large an amount, empty description
- Amounts are stored as whole paise (`long`), so totals have no floating point errors
- Indian number grouping (for example Rs 12,34,567.00)
- Material You dynamic colours on Android 12+, light and dark theme

## Tech

Java 8 source level, Android Studio, XML, RecyclerView, SQLite (`SQLiteOpenHelper`), Material Components 1.11, JUnit 4.
minSdk 24, targetSdk 34.

## Project structure

| File | Purpose |
|------|---------|
| `MainActivity.java` | Home screen: totals, list, delete, opens the add screen |
| `AddTransactionActivity.java` | Form with validation, started with an `Intent` |
| `TransactionAdapter.java` | RecyclerView adapter for the rows |
| `TransactionDbHelper.java` | SQLite create, insert, delete and read |
| `Transaction.java`, `TransactionType.java` | Model classes |
| `Summary.java` | Calculates income, expenses and balance |
| `AmountParser.java` | Validates the amount text and converts to paise |
| `CurrencyUtils.java` | Formats paise as rupees |
| `ExpenseApp.java` | Application class that turns on dynamic colour |

## Run it

1. Open this folder in Android Studio (Hedgehog or newer, JDK 17) and let Gradle sync.
2. Press Run on an emulator or phone.
3. Command line: `./gradlew testDebugUnitTest assembleDebug` - APK at `app/build/outputs/apk/debug/app-debug.apk`.

## Testing done

- 12 unit tests: amount parsing and validation, totals, balance, currency format.
- Layouts rendered in light and dark mode on a Pixel 5 frame (see `docs/`).
- Not yet recorded: a run on an emulator or phone (saving, deleting, restart persistence). See the report.
