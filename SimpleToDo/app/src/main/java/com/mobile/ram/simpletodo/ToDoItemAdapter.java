package com.mobile.ram.simpletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mobile.ram.simpletodo.model.TodoItem;

import java.util.List;

/**
 * Created by rgauta01 on 8/25/15.
 */
public class ToDoItemAdapter extends ArrayAdapter <TodoItem> {

    public ToDoItemAdapter(Context context, List<TodoItem> todoItems) {
        super(context, 0,todoItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        TodoItem todoItem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todoitem, parent, false);
        }
        // Lookup view for data population
        TextView tvId = (TextView) convertView.findViewById(R.id.tvId);
        TextView tvItem = (TextView) convertView.findViewById(R.id.tvItem);


        // Populate the data into the template view using the data object

        tvId.setText(Integer.toString(todoItem.id));
        tvItem.setText(todoItem.item);

        // Return the completed view to render on screen
        return convertView;
    }

}
