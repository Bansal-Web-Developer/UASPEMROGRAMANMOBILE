package com.example.uas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseClass extends SQLiteOpenHelper {

    Context context;
    private static final String DatabaseName = "MyNotes";
    private static final int DatabaseVersion = 2;

    private static final String TableName = "mynotes";
    private static final String ColumnId = "id";
    private static final String ColumnDate = "date";
    private static final String ColumnTitle = "title";
    private static final String ColumnDescription = "description";


    public DatabaseClass(@Nullable Context context) {
        super(context, DatabaseName, null, DatabaseVersion);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query= "CREATE TABLE " + TableName +
                " (" + ColumnId + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ColumnDate + " TEXT, " +
                ColumnTitle + " TEXT, " +
                ColumnDescription + " TEXT);";

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TableName);
        onCreate(db);
    }

    void addNotes(String date, String title, String description)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(ColumnDate, date);
        cv.put(ColumnTitle, title);
        cv.put(ColumnDescription, description);

        long resultValue = db.insert(TableName,null, cv);

        if (resultValue == -1)
        {
            Toast.makeText(context, "Catatan Gagal Ditambahkan", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Catatan Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData()
    {
        String query = "SELECT * FROM " + TableName + " ORDER BY " + ColumnDate + " DESC, " + ColumnId + " DESC";
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = null;
        if (database != null)
        {
            cursor = database.rawQuery(query , null);
        }
        return cursor;
    }

    void deleteAllNotes(){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "DELETE FROM " + TableName;
        database.execSQL(query);
    }

    void updateNotes(String date, String title,String description,String id)
    {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(ColumnDate, date);
        cv.put(ColumnTitle, title);
        cv.put(ColumnDescription, description);

        long result = database.update(TableName, cv,"id=?", new String[]{id});
        if (result == -1)
        {
            Toast.makeText(context, "Gagal", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Berhasil", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteSingleItem(String id)
    {
        SQLiteDatabase database = this.getWritableDatabase();

        long result = database.delete(TableName,"id=?",new String[]{id});
        if (result == -1)
        {
            Toast.makeText(context, "Catatan Tidak Terhapus", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Catatan Berhasil Dihapus", Toast.LENGTH_SHORT).show();
        }
    }
}
