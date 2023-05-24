package com.example.tempfile;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ListActivity extends AppCompatActivity implements ActivityResultListener{
    ArrayList<User> activityList;
    BrandsAdapter mAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        loadData();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        mAdapter = new BrandsAdapter(activityList, this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onResume(){
        super.onResume();
        loadData();
    }

    private void loadData(){
        if(mAdapter == null){
            return;
        }
        SharedPreferences sp = getSharedPreferences("contactDb", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString("users", null);
        Type type = new TypeToken<ArrayList<User>>() {}.getType();
        activityList = gson.fromJson(json, type);

        if (activityList == null){
            activityList = generateUserList(20);
            saveData();
        }
        mAdapter.setUserList(activityList);
    }

    private void saveData(){
        SharedPreferences sp = getSharedPreferences("contactDb", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(activityList);
        editor.putString("users", json);
        editor.apply();

    }

    //Auto generate users
    private ArrayList<User> generateUserList(int count) {
        ArrayList<User> userList = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < count; i++) {
            boolean followed = random.nextBoolean();

            long min1 = 100000000L;  // Minimum 9-digit number
            long max1 = 9999999999L; // Maximum 10-digit number
            long randomNumber1 = min1 + ((long) (random.nextDouble() * (max1 - min1)));
            String name = "User " + randomNumber1;

            long min2 = 100000000L;  // Minimum 9-digit number
            long max2 = 9999999999L; // Maximum 10-digit number
            long randomNumber2 = min2 + ((long) (random.nextDouble() * (max2 - min2)));
            String description = "Description " + randomNumber2;

            User user = new User(name, description, followed);
            userList.add(user);
        }

        return userList;
    }

    @Override
    public void onResult(int resultCode, Intent data) {
        Bundle extras = getIntent().getExtras();
        int index = extras.getInt("position");
        boolean hasFollowed = extras.getBoolean("hasFollowed");
        System.out.println();
    }

    //ViewHolder
    public class BrandViewHolder extends RecyclerView.ViewHolder {
        private ActivityResultLauncher<Intent> activityResultLauncher;
        private ActivityResultListener resultListener;
        TextView nameTextView;
        TextView descriptionTextView;
        ImageView brandImageView;

        ImageView number7Image;

        public BrandViewHolder(View itemView, ActivityResultListener resultListener) {
            super(itemView);
            this.resultListener = resultListener;
//            activityResultLauncher = registerForActivityResult(
//                    new ActivityResultContracts.StartActivityForResult(),
//                    result -> {
//                        this.resultListener.onResult(result.getResultCode(), result.getData());
//                    });

            nameTextView = itemView.findViewById(R.id.nameTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            brandImageView = itemView.findViewById(R.id.imageView2);
            number7Image = itemView.findViewById(R.id.imageView4);
        }
    }

    public class BrandsAdapter extends RecyclerView.Adapter<BrandViewHolder> {
        ArrayList<User> data;
        private ActivityResultListener resultListener;


        public BrandsAdapter(ArrayList<User> input, ActivityResultListener resultListener) {
            data = input;
            this.resultListener = resultListener;
        }

        public void setUserList(ArrayList<User> uList){
            data = uList;
        }

        @Override
        public int getItemViewType(int position) {
            // Return the view type based on your logic
            User user = data.get(position);
            String name = user.getName();
            if(name.charAt(name.length()-1) == '7') {
                return 1;
            }
            return 0;
        }

        @Override //Takes content from xml file to create the UI.
        public BrandViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View item;
            if(viewType == 1){
                item = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item_special, parent, false);
            }
            else {
                item = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
            }
            return new BrandViewHolder(item, resultListener);
        }

        @Override //Android will know which position to use.
        public void onBindViewHolder(BrandViewHolder holder, int position) {
            User user = data.get(position);
            String name = user.getName();
            holder.nameTextView.setText(name);
            holder.descriptionTextView.setText(user.getDescription());


            holder.brandImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Profile");
                    builder.setMessage(user.getDescription());
                    builder.setCancelable(true);
                    builder.setPositiveButton("VIEW", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Bundle extras = new Bundle();
                            User user = data.get(holder.getAdapterPosition());
                            extras.putString("Name", user.name);
                            extras.putString("Description", user.description);
                            extras.putBoolean("Bool", user.followed);
                            extras.putInt("position", holder.getAdapterPosition());

                            Intent message = new Intent(v.getContext(), MainActivity2.class);
                            message.putExtras(extras);
                            v.getContext().startActivity(message);
                        }
                    });
                    builder.setNegativeButton("CLOSE", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

}