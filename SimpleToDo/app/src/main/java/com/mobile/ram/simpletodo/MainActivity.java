package com.mobile.ram.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Ram on 7/13/15.
 * This class to View, add and Delete TODOItems
 */

public class MainActivity extends AppCompatActivity {


    private final int REQUEST_CODE = 20;
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdaptor;
    private ListView lvItems;
    private EditText etNewItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get List view context
        lvItems = (ListView) findViewById(R.id.lvItems);
        // create empty Array List
        items = new ArrayList<>();
        // Get New it, test box context
        etNewItem = (EditText) findViewById(R.id.etNewItem);
        // Read stored ToDoitems from txt file
        readItems();

        // Create Todolist view adapter
        itemsAdaptor = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);

        // lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(itemsAdaptor);

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
            // Trip text to avoid space in ToDOlist
            itemsAdaptor.add(itemText.trim());
            etNewItem.setText("");
            writeItems();
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

                items.remove(pos);
                itemsAdaptor.notifyDataSetChanged();
                writeItems();

                return true;
            }
        });

    }
   /*
       To Register Listener to handle ListView  click event
       and store selected item in intent and pass to edit screen
     */

    private void setupListViewOnClickListener() {
        System.out.println("***In setupListViewOnClickListener*** ");
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {


                String itemSelected = itemsAdaptor.getItem(pos);
                System.out.println("View**itemSelected:" + itemSelected);

                Intent iEditItemActivity = new Intent(MainActivity.this, EditItemActivity.class);
                iEditItemActivity.putExtra("itemPosition", pos);
                iEditItemActivity.putExtra("item", itemSelected);

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

            String updatedItem = data.getExtras().getString("item");
            int itemPosition = data.getExtras().getInt("itemPosition", 0);

            items.set(itemPosition, updatedItem);

            itemsAdaptor.notifyDataSetChanged();

            writeItems();


        }

    }

    /*
     To read all TODOItems from todoitem file
     */
    private void readItems() {

        File filesDir = getFilesDir();

        File todoFile = new File(filesDir, "todo.txt");

        try {

            items = new ArrayList<String>(FileUtils.readLines(todoFile));

        } catch (IOException e) {

            items = new ArrayList<>();
            e.printStackTrace();

        }

    }

    /*
    To write new TODOItem into todoItem file
    */
    private void writeItems() {

        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);

        } catch (Exception e) {
            e.printStackTrace();

        }

    }


}
