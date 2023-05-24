package com.example.tempfile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    ArrayList<User> activityList;
    User user;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.button);

        Bundle extras = getIntent().getExtras();
        int id = extras.getInt("Id");
        String name = extras.getString("Name");
        String description = extras.getString("Description");
        boolean bool = extras.getBoolean("Bool", false);
        position = extras.getInt("position");



        user = new User(id, name, description, bool);
        if (bool == true){
            btn.setText("Unfollow");
        }
        else if (bool == false){
            btn.setText("Follow");
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user.followed == true) {
                    user.followed = false;
                    btn.setText("Follow");
                    Toast.makeText(getApplicationContext(), "Unfollowed", Toast.LENGTH_SHORT).show();

                } else {
                    user.followed = true;
                    btn.setText("Unfollow");
                    Toast.makeText(getApplicationContext(), "Followed", Toast.LENGTH_SHORT).show();
                }

                if(activityList == null){
                    loadData();
                }

                activityList.get(position).followed = user.followed;
                saveData(user);
            }
        });

        //To edit the textView to a new message which was created in ListActivity
        Intent receivingEnd = getIntent();
        String message = receivingEnd.getStringExtra("New_Text");

        TextView textView = findViewById(R.id.editTextText);
        textView.setText(message);

        //event listener for message button
        Button myButton = findViewById(R.id.button2);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, MessageGroup.class);
                startActivity(intent);
            }
        });

    }

    private void loadData(){
//        SharedPreferences sp = getSharedPreferences("contactDb", MODE_PRIVATE);
//        Gson gson = new Gson();
//        String json = sp.getString("users", null);
//        Type type = new TypeToken<ArrayList<User>>() {}.getType();
//        activityList = gson.fromJson(json, type);
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        activityList = dbHandler.getUsers();
    }

    private void saveData(){
        SharedPreferences sp = getSharedPreferences("contactDb", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(activityList);
        editor.putString("users", json);
        editor.apply();

    }

    public void saveData(User user){
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        dbHandler.updateUser(user);

    }
}