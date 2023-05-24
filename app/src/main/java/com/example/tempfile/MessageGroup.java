package com.example.tempfile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MessageGroup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_group);

        Button myButton = findViewById(R.id.button3);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Group1 myFragment = new Group1();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, myFragment)
                        .commit();
            }
        });

        Button myButton2 = findViewById(R.id.button4);

        myButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Group2 myFragment = new Group2();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, myFragment)
                        .commit();
            }
        });
    }
}