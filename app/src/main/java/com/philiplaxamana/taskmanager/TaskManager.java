package com.philiplaxamana.taskmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskManager extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "TaskDB";
    private final static int DATABASE_VERSION = 1;
    private static String tableName;
    private static String tableCreatorString;

    public TaskManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tableCreatorString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(db);
    }

    public void dbInitialize(String tableName, String tableCreatorString){
        this.tableName = tableName;
        this.tableCreatorString = tableCreatorString;
    }

    public boolean addRow(ContentValues values) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();

        long nr = db.insert(tableName, null, values);
        db.close();
        return nr > -1;
    }

    public Task getTaskById(Object id, String fieldName) throws Exception{
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + tableName + " WHERE " + fieldName + " ='" + String.valueOf(id)+"' ",null );
        Task task = new Task();
        if(c.moveToNext()){
            c.moveToFirst();
            task.setTaskId(c.getInt(0));
            task.setTaskName(c.getString(1));
            task.setTaskDescription(c.getString(2));
            c.close();
        }
        return task;
    }

    public boolean editRow(Object id, String fieldname, ContentValues values ){
        SQLiteDatabase db = this.getWritableDatabase();
        int nr = db.update(tableName, values, fieldname + " = ?", new String[] {String.valueOf(id)});
        return nr > -1 ;
    }

    // delete a row
    public void deleteRow(Object id, String fieldName) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, fieldName + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

}
