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
//        boolean[] variable = {false};
//        User user = new User("Hello World!", "Lorem ipsum dolor sit amet, consecttur adipiscing elit, sed do eiusmod tempor incidiunt ut labore et dolore magna aliqua\"", false);
//        EditText name = findViewById(R.id.editTextText);
//        TextView description = findViewById(R.id.descriptionTextView);
//        name.setText(user.getName());
//        description.setText(user.getDescription());

        Bundle extras = getIntent().getExtras();
        String name = extras.getString("Name");
        String description = extras.getString("Description");
        boolean bool = extras.getBoolean("Bool", false);
        position = extras.getInt("position");



        user = new User(name, description, bool);
        if (bool == true){
            btn.setText("Follow");
        }
        else if (bool == false){
            btn.setText("Unfollow");
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user.followed == true) {
                    user.followed = false;
                    btn.setText("Unfollow");
                    Toast.makeText(getApplicationContext(), "Unfollowed", Toast.LENGTH_SHORT).show();

                } else {
                    user.followed = true;
                    btn.setText("Follow");
                    Toast.makeText(getApplicationContext(), "Followed", Toast.LENGTH_SHORT).show();
                }

                if(activityList == null){
                    loadData();
                }

                activityList.get(position).followed = user.followed;
                saveData();
//                Bundle extras = new Bundle();
//                extras.putBoolean("hasFollowed", user.followed);
//                extras.putInt("position", position);
//                Intent returnIntent = new Intent();
//                returnIntent.putExtras(extras);
//                setResult(Activity.RESULT_OK, returnIntent);
//                finish();
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
        SharedPreferences sp = getSharedPreferences("contactDb", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString("users", null);
        Type type = new TypeToken<ArrayList<User>>() {}.getType();
        activityList = gson.fromJson(json, type);
    }

    private void saveData(){
        SharedPreferences sp = getSharedPreferences("contactDb", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(activityList);
        editor.putString("users", json);
        editor.apply();

    }
}