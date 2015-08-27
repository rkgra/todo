package com.mobile.ram.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.mobile.ram.simpletodo.dao.TodoItemDatabase;
import com.mobile.ram.simpletodo.model.TodoItem;

import java.util.List;

/**
 * Created by Ram on 7/13/15.
 * This class to View, add and Delete TODOItems
 */

public class MainActivity extends AppCompatActivity {


    private final int REQUEST_CODE = 20;

    public ToDoItemAdapter toDoItemAdapter;
    public ListView lvItems;
    private List<TodoItem> items;
    private TodoItemDatabase db;
    private EditText etNewItem;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get List view context
        lvItems = (ListView) findViewById(R.id.lvItems);
        etNewItem = (EditText) findViewById(R.id.etNewItem);
        // Create our sqlite database object
        db = new TodoItemDatabase(this);
        // get data from database
        items = db.getAllTodoItems();
        // Print out properties
//        for (TodoItem ti : items) {
//            String log = "Id: " + ti.getId() + " , Item: " + ti.getItem();
//            // Writing Todo Items to log
//            Log.d("Name: ", log);
//        }

        toDoItemAdapter = new ToDoItemAdapter(this, items);

        // Attach the adapter to a ListView

        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(toDoItemAdapter);

        // Register Listener to handle ListView long click event
        setupListViewOnLongClickListener();
        // Register Listener to handle ListView  click event

        setupListViewOnClickListener();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
        To Add new Item entered in Text box
     */
    public void onAddItems(View view) {

        String itemText = etNewItem.getText().toString();



        if (isValidItem(itemText)) {
            // Trim text to avoid space in ToDOlist

            TodoItem newTodoItem = new TodoItem(itemText.trim());

            items.add(newTodoItem);
            toDoItemAdapter.notifyDataSetChanged();
            
            Log.d("***onAddItems added**", newTodoItem.getItem());

            db.addTodoItem(newTodoItem);


            etNewItem.setText("");

        }


    }
    /*
       To check if string is not null or empty
       @ input - entered text
       @ output - boolean

    */

    private boolean isValidItem(String itemText) {

        boolean isValid = false;

        if (itemText != null && !itemText.equals("") && itemText.length() > 0) {
            isValid = true;
        }
        return isValid;
    }

    /*
       To Register Listener to handle ListView long click event
       and remove selected item from TODOList
     */
    private void setupListViewOnLongClickListener() {

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {

                Log.d("setupLis pos--", String.valueOf(pos));



                TodoItem deleteItem = items.get(pos);
                items.remove(deleteItem);

                db.deleteTodoItem(deleteItem);


                toDoItemAdapter.notifyDataSetChanged();


                return true;
            }
        });

    }
   /*
       To Register Listener to handle ListView  click event
       and store selected item in intent and pass to edit screen
     */

    private void setupListViewOnClickListener() {

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {


                TodoItem selectedTodoItem = toDoItemAdapter.getItem(pos);

                Intent iEditItemActivity = new Intent(MainActivity.this, EditItemActivity.class);


                iEditItemActivity.putExtra("selectedTodoItem", selectedTodoItem);
                iEditItemActivity.putExtra("itemPosition", pos);

                startActivityForResult(iEditItemActivity, REQUEST_CODE);


            }
        });

    }

    /*
       This method will call after user edit item in edit screen.
       Update Edited item in Array list and write in TODOList
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (RESULT_OK == resultCode && requestCode == REQUEST_CODE) {


            TodoItem updatedTodoItem = (TodoItem) data.getSerializableExtra("selectedTodoItem");


            int itemPosition = data.getExtras().getInt("itemPosition", 0);

            items.set(itemPosition, updatedTodoItem);


            toDoItemAdapter.notifyDataSetChanged();

            db.updateTodoItem(updatedTodoItem);


        }

    }

}



