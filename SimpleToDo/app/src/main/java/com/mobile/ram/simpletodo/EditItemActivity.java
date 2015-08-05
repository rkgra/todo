package com.mobile.ram.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Ram on 7/13/15.
 * This class to edit selected Item
 */


public class EditItemActivity extends AppCompatActivity {

    private EditText etEditItem;
    private int itemPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        // Get Selected item from TDOList
        String itemSelected= getIntent().getStringExtra("item");
        itemPosition=getIntent().getIntExtra("itemPosition",0);
        // Show Selected item in text box
        etEditItem= (EditText) findViewById(R.id.etEditItem);
        etEditItem.setText(itemSelected);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
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
        To handle Cancel button on click event
      */
    public void onCancelItem(View view) {
        // closes the activity and returns to first screen
        this.finish();

    }
    /*
       To handle Save button on click event
     */
    public void onSaveItem(@SuppressWarnings("UnusedParameters") View view) {
        etEditItem= (EditText) findViewById(R.id.etEditItem);
           Intent data = new Intent();
            data.putExtra("item", etEditItem.getText().toString());
            data.putExtra("itemPosition", itemPosition);
            data.putExtra("code", 200);
            setResult(RESULT_OK, data);
            this.finish();

    }
}
