package com.example.tempfile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);



        Button btn = findViewById(R.id.login_btn);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText username = findViewById(R.id.username_login);
                EditText password = findViewById(R.id.password_login);
                // Write a message to the database
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                //DatabaseReference dbRef = database.getReference("Users/"+username.toString());
                DatabaseReference dbRef = database.getReference("Users").child(username.getText().toString());
                Log.d("Database Reference", dbRef.toString());

                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Handle the retrieved data here
                        // You can access the data using dataSnapshot.getValue() and iterate over the children if needed
                        Log.d("Log", "Login 1");
                        Login_User value = dataSnapshot.getValue(Login_User.class);
                        Log.d("Log", "Login 2");
                        try {
                            if (value.password.equals(password.getText().toString())) {
                                Log.d("Log", "Login Sucess");
                                Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Log.d("Log", "Login Fail");
                                Toast.makeText(getApplicationContext(), "Login Fail", Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch(Exception e){
                            Toast.makeText(getApplicationContext(), "Invalid Login User", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle any errors that occur
                        Log.d("Log", "Login Fail");
                        Toast.makeText(getApplicationContext(), "Invalid Login", Toast.LENGTH_SHORT).show();
                    }
                });


//                dbRef.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        // This method is called once with the initial value and again
//                        // whenever data at this location is updated.
//                        Login_User value = dataSnapshot.getValue(Login_User.class);
//                        if(value.password.equals(password)){
//                            Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
//                        }
//                        else{
//                            Toast.makeText(getApplicationContext(), "Login Fail", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        //System.out.println("The read failed: " + databaseError.getCode());
//                        Toast.makeText(getApplicationContext(), "Login Fail", Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
        });


    }
}