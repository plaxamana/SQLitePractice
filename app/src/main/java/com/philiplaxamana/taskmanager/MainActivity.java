package com.philiplaxamana.taskmanager;

import android.content.ContentValues;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TaskManager taskManager;
    private EditText txtId, txtTaskName, txtTaskDescription ;
    private Button btnAdd, btnShow, btnEdit, btnDelete;
    private final static String TABLE_NAME = "AndroidTask";

    private static final String tableCreatorString =
            "CREATE TABLE "+ TABLE_NAME + " (taskId integer primary key, taskName text,taskDescription text);";
    //

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        txtId = (EditText) findViewById(R.id.et_taskId);
        txtTaskName = (EditText) findViewById(R.id.et_taskName);
        txtTaskDescription = (EditText) findViewById(R.id.et_taskDesc);
        //
        btnAdd = (Button)findViewById(R.id.btn_addTask);
        btnShow = (Button)findViewById(R.id.btn_showTask);
        btnEdit = (Button)findViewById(R.id.btn_editTask);
        btnDelete = (Button)findViewById(R.id.btn_deleteTask);
        //
        // initialize the tables
        try {
            taskManager = new TaskManager(this);
            //create the table
            taskManager.dbInitialize(TABLE_NAME, tableCreatorString);
        }
        catch(Exception exception)
        {
            Toast.makeText(MainActivity.this,
                    exception.getMessage(), Toast.LENGTH_SHORT).show();
            Log.i("Error: ",exception.getMessage());
        }
    }

    public void showTask(View v)
    {
        try {
            Task task = taskManager.getTaskById(txtId.getText().toString(), "taskId");
            txtTaskName.setText(task.getTaskName());
            txtTaskDescription.setText(task.getTaskDescription());
            Toast.makeText(this, "Showing task #" + task.getTaskId(), Toast.LENGTH_SHORT).show();
//            ArrayList<Task> taskItems = new ArrayList<>();
//
//            TaskItemAdapter foodItemAdapter = new TaskItemAdapter(this, taskItems);
//            ListView listView = findViewById(R.id.myListView);
//            listView.setAdapter(foodItemAdapter);
        }
        catch (Exception exception)
        {
            Toast.makeText(MainActivity.this,
                    exception.getMessage(), Toast.LENGTH_SHORT).show();
            Log.i("Error: ",exception.getMessage());

        }
    }
    //
    public void addTask(View v)
    {
        //read values
        int taskId = Integer.parseInt(txtId.getText().toString());
        String taskName = txtTaskName.getText().toString();
        String taskDescription = txtTaskDescription.getText().toString();
        //initialize ContentValues object with the new task
        ContentValues contentValues = new ContentValues();
        contentValues.put("taskId",taskId);
        contentValues.put("taskName",taskName);
        contentValues.put("taskDescription",taskDescription);
        //
        try {
            taskManager.addRow(contentValues);
            Toast.makeText(this, "Task #" + taskId + " has been added.", Toast.LENGTH_SHORT).show();
            txtId.getText().clear();
            txtTaskName.getText().clear();
            txtTaskDescription.getText().clear();
        }
        catch(Exception exception)
        {
            //
            Toast.makeText(MainActivity.this,
                    exception.getMessage(), Toast.LENGTH_SHORT).show();
            Log.i("Error: ",exception.getMessage());

        }
    }

    public void editTask(View v)
    {
        int taskId = Integer.parseInt(txtId.getText().toString());

        String taskName = txtTaskName.getText().toString();
        String taskDescription = txtTaskDescription.getText().toString();
        try{
            ContentValues contentValues = new ContentValues();
            contentValues.put("taskId",taskId);
            contentValues.put("taskName",taskName);
            contentValues.put("taskDescription",taskDescription);
            //edit the row
            boolean b=taskManager.editRow(taskId, "taskId", contentValues);


        }
        catch(Exception exception)
        {
            Toast.makeText(MainActivity.this,
                    exception.getMessage(), Toast.LENGTH_SHORT).show();
            Log.i("Error: ",exception.getMessage());

        }


    }

    public void deleteTask(View v){
        int taskId = Integer.parseInt(txtId.getText().toString());

        try{
            taskManager.deleteRow(taskId, "taskId");
            Toast.makeText(this, "Task #" + taskId + " has been successfully deleted.", Toast.LENGTH_SHORT).show();
            txtId.getText().clear();
            txtTaskName.getText().clear();
            txtTaskDescription.getText().clear();
        }catch (Exception e){
            Toast.makeText(MainActivity.this,
                    e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.i("Error: ",e.getMessage());
        }

    }


}
