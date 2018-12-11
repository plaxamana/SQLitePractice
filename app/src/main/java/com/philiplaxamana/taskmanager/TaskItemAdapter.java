package com.philiplaxamana.taskmanager;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class TaskItemAdapter extends ArrayAdapter<Task> {

    private static final String LOG_TAG = TaskItemAdapter.class.getSimpleName();


    public TaskItemAdapter(FragmentActivity context, ArrayList<Task> taskItems) {
        super(context, 0, taskItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.custom_list_view, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        Task currentTaskItem = getItem(position);

        // Find the TextView in the list_food_item_item.xml layout with the ID version_name
        TextView taskIDTextView = (TextView) listItemView.findViewById(R.id.taskID);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        taskIDTextView.setText(currentTaskItem.getTaskId());

        TextView taskNameTextView = (TextView) listItemView.findViewById(R.id.taskName);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        taskNameTextView.setText(currentTaskItem.getTaskName());

        TextView taskDescTextView = (TextView) listItemView.findViewById(R.id.taskDesc);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        taskDescTextView.setText(currentTaskItem.getTaskDescription());


        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }
}
