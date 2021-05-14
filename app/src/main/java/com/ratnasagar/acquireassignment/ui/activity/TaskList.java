package com.ratnasagar.acquireassignment.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ratnasagar.acquireassignment.R;

public class TaskList extends AppCompatActivity {

    private Button btnTask1,btnTask2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        btnTask1 = (Button) findViewById(R.id.btnTask1);
        btnTask2 = (Button) findViewById(R.id.btnTask2);

        btnTask1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskList.this,MainActivity.class);
                startActivity(intent);
            }
        });
        btnTask2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskList.this,UserActivity.class);
                startActivity(intent);
            }
        });
    }
}