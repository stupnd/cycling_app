package com.example.seg2105project.Member;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.seg2105project.ClubOwner.ClubInfo;
import com.example.seg2105project.ClubOwner.ClubOwnerMembers;
import com.example.seg2105project.DB.CDBHelper;
import com.example.seg2105project.Main.MainActivity;
import com.example.seg2105project.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class Feedback extends AppCompatActivity {
    ListView feedbackListView;
    CDBHelper cdb;
    ArrayList<ClubInfo> feedbackDisplay;
    ArrayAdapter<ClubInfo> feedbackAdapter;
    FloatingActionButton btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_feedback);

        String user = getIntent().getStringExtra("username");

        cdb = new CDBHelper(this);
        feedbackDisplay = cdb.getAllFeedback();
        feedbackAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, feedbackDisplay);
        ListView feedbackListView = findViewById(R.id.feedbackList);
        feedbackListView.setAdapter(feedbackAdapter);

        BottomNavigationView bNv = findViewById(com.example.seg2105project.R.id.bottomNavigationView);
        bNv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id== com.example.seg2105project.R.id.event){
                    Intent intent = new Intent(getApplicationContext(), Rating.class);
                    intent.putExtra("username", user);
                    startActivity(intent);
                    return true;
                } else if (id== com.example.seg2105project.R.id.logout){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }});

        btnSearch = findViewById(R.id.searchBtn);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), MemberSearch.class);
                intent.putExtra("username", user);
                startActivity(intent);
            }
        });
    }
}
