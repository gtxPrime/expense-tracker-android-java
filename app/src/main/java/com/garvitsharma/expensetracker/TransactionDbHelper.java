package com.garvitsharma.expensetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/** SQLite storage so transactions survive closing and reopening the app. */
public class TransactionDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "expenses.db";
    private static final int DB_VERSION = 1;

    private static final String TABLE = "transactions";
    private static final String COL_ID = "_id";
    private static final String COL_TYPE = "type";
    private static final String COL_AMOUNT = "amount_paise";
    private static final String COL_DESC = "description";
    private static final String COL_CATEGORY = "category";
    private static final String COL_TIME = "created_at";

    public TransactionDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TYPE + " TEXT NOT NULL, "
                + COL_AMOUNT + " INTEGER NOT NULL, "
                + COL_DESC + " TEXT NOT NULL, "
                + COL_CATEGORY + " TEXT NOT NULL, "
                + COL_TIME + " INTEGER NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    /** Returns the new row id, or -1 if the insert failed. */
    public long insert(TransactionType type, long amountPaise, String description,
                       String category) {
        ContentValues values = new ContentValues();
        values.put(COL_TYPE, type.name());
        values.put(COL_AMOUNT, amountPaise);
        values.put(COL_DESC, description);
        values.put(COL_CATEGORY, category);
        values.put(COL_TIME, System.currentTimeMillis());
        try {
            return getWritableDatabase().insertOrThrow(TABLE, null, values);
        } catch (SQLiteException e) {
            return -1;
        }
    }

    public boolean delete(long id) {
        try {
            return getWritableDatabase().delete(TABLE, COL_ID + " = ?",
                    new String[]{String.valueOf(id)}) > 0;
        } catch (SQLiteException e) {
            return false;
        }
    }

    /** All transactions, newest first. Throws SQLiteException if the database fails. */
    public List<Transaction> getAll() {
        List<Transaction> result = new ArrayList<>();
        try (Cursor c = getReadableDatabase().query(TABLE, null, null, null, null, null,
                COL_TIME + " DESC, " + COL_ID + " DESC")) {
            while (c.moveToNext()) {
                result.add(new Transaction(
                        c.getLong(c.getColumnIndexOrThrow(COL_ID)),
                        TransactionType.fromString(
                                c.getString(c.getColumnIndexOrThrow(COL_TYPE))),
                        c.getLong(c.getColumnIndexOrThrow(COL_AMOUNT)),
                        c.getString(c.getColumnIndexOrThrow(COL_DESC)),
                        c.getString(c.getColumnIndexOrThrow(COL_CATEGORY)),
                        c.getLong(c.getColumnIndexOrThrow(COL_TIME))));
            }
        }
        return result;
    }
}
