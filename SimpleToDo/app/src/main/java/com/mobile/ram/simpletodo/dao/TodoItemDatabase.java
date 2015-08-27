package com.mobile.ram.simpletodo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mobile.ram.simpletodo.model.TodoItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ram on 8/24/15.
 * This class is managing database operations
 */
public class TodoItemDatabase extends SQLiteOpenHelper {
    // All Static variables
// Database Version


    private static final int DATABASE_VERSION = 2;
    // Database Name
    private static final String DATABASE_NAME = "todoListDatabase";
    // Todo table name
    private static final String TABLE_TODO = "todo_items";
    // Todo Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_ITEM = "item";


    public TodoItemDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    /*
     Creating our initial tables
     These is where we need to write create table statements.
      This is called when database is created.
     */

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Construct a table for todo items
        String CREATE_TODO_TABLE = "CREATE TABLE " + TABLE_TODO + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_ITEM + " TEXT"
                + ")";
        db.execSQL(CREATE_TODO_TABLE);
    }
    /*
       Upgrading the database between versions
     */

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Wipe older tables if existed
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
            // Create tables again
            onCreate(db);
        }
    }

    /**
     * @param TodoItem item- item to add in database
     * @return none
     * @desc Insert record into the database
     */
    public void addTodoItem(TodoItem item) {

        // Open database connection
        SQLiteDatabase db = this.getWritableDatabase();

        try {


            // Define values for each field
            ContentValues values = new ContentValues();
            values.put(KEY_ITEM, item.getItem());

            // Insert Row and get generated id
            long rowId = db.insertOrThrow(TABLE_TODO, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            // Closing database connection

            db.close();
        }


    }
    /**
     * @desc To get all todo items stored in database
     * @param None
     * @return   TodoItem List
     */

    public List<TodoItem> getAllTodoItems() {

        List<TodoItem> todoItems = new ArrayList<TodoItem>();
        SQLiteDatabase db = this.getReadableDatabase();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_TODO;
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {




            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    TodoItem item = new TodoItem(cursor.getInt(0), cursor.getString(1));
                    // item.setId(cursor.getInt(0));
                    // Adding todo item to list
                    todoItems.add(item);
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            // Close the cursor
            if (cursor != null)
                cursor.close();

        }

        // return todo list
        return todoItems;
    }

    /**
     * @desc To update Tod Item in databse
     * @param todo item
     * @return   updated row ID
     */
    public int updateTodoItem(TodoItem item) {
        // Open database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        int result=0;
        try {


            // Setup fields to update
            ContentValues values = new ContentValues();
            values.put(KEY_ITEM, item.getItem());

            // Updating row
             result = db.update(TABLE_TODO, values, KEY_ID + " = ?",
                    new String[]{String.valueOf(item.getId())});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            // Close the database
            db.close();
        }


        return result;
    }

    /**
     * @desc delete record from the database
     * @param TodoItem item- item to delete from the database
     * @return void
     *
     */
    public void deleteTodoItem(TodoItem item) {

        // Open database for writing
        SQLiteDatabase db = this.getWritableDatabase();

        try {


            // Delete the record with the specified id
            db.delete(TABLE_TODO, KEY_ID + " = ?",
                    new String[]{String.valueOf(item.getId())});
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {

            db.close();
        }


    }
}


