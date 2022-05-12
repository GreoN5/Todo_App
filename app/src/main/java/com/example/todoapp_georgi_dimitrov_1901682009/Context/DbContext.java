package com.example.todoapp_georgi_dimitrov_1901682009.Context;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.todoapp_georgi_dimitrov_1901682009.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class DbContext extends SQLiteOpenHelper {
    private SQLiteDatabase _db;

    public static final String _databaseName = "To-Do database";
    public static final String _tableName = "Tasks";
    public static final String _idColumn = "ID";
    public static final String _nameColumn = "NAME";
    public static final String _isDoneColumn = "DONE";

    public DbContext(@Nullable Context context) {
        super(context, _databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this._db = db;
        this._db.execSQL("CREATE TABLE IF NOT EXISTS " + _tableName + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT , DONE INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this._db.execSQL("DROP TABLE IF EXISTS " + _tableName);
        onCreate(this._db);
    }

    public void insertToDo(ToDoModel todo) {
        this._db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(_nameColumn, todo.getName());
        values.put(_isDoneColumn, 0);

        this._db.insert(_tableName, null, values);
    }

    public void updateToDo(int id , String todoName) {
        this._db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(_nameColumn , todoName);

        this._db.update(_tableName, values, "ID=?", new String[]{String.valueOf(id)});
    }

    public void makeToDoDone(int id) {
        this._db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(_isDoneColumn, 1);

        this._db.update(_tableName, values, "ID=?", new String[]{String.valueOf(id)});
    }

    public void makeToDoNotDone(int id) {
        this._db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(_isDoneColumn, 0);

        this._db.update(_tableName, values, "ID=?", new String[]{String.valueOf(id)});
    }

    public void deleteToDo(int id) {
        this._db = this.getWritableDatabase();
        this._db.delete(_tableName, "ID=?", new String[]{String.valueOf(id)});
    }

    @SuppressLint("Range")
    public List<ToDoModel> getAllTasks() {
        this._db = this.getWritableDatabase();
        Cursor cursor = null;
        List<ToDoModel> modelList = new ArrayList<>();

        this._db.beginTransaction();

        try {
            cursor = this._db.query(_tableName, null, null, null, null, null, "ID");

            if (cursor != null){
                while (cursor.moveToNext()) {
                    ToDoModel todo = new ToDoModel();
                    todo.setId(cursor.getInt(cursor.getColumnIndex(_idColumn)));
                    todo.setName(cursor.getString(cursor.getColumnIndex(_nameColumn)));
                    todo.setIsDone(cursor.getInt(cursor.getColumnIndex(_isDoneColumn)));
                    modelList.add(todo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this._db.endTransaction();
            cursor.close();
        }

        return modelList;
    }
}
