package com.example.seg2105project.Member;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.seg2105project.DB.EDBHelper;
import com.example.seg2105project.Main.MainActivity;
import com.example.seg2105project.R;
import com.example.seg2105project.Shared.Event;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class Member extends AppCompatActivity {
    /**
     * This function sets up the Member activity by hiding the action bar, setting the layout,
     * retrieving the username from the previous activity, and displaying a welcome message with the
     * username.
     * 
     * @param savedInstanceState The savedInstanceState parameter is a Bundle object that contains the
     * activity's previously saved state. It is used to restore the activity's state when it is
     * recreated, such as when the device is rotated.
     */
    FloatingActionButton btnSearch;
    EDBHelper Edb;
    ArrayList<Event> eventDisplay;
    ArrayAdapter<Event> eventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_member);

        String user = getIntent().getStringExtra("username");

        TextView memberMsg = findViewById(R.id.memberMsg);
        memberMsg.setText("Welcome " + user + "! You are logged in as a Member.");
        btnSearch = findViewById(R.id.searchBtn);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), MemberSearch.class);
                intent.putExtra("username", user);
                startActivity(intent);
            }
        });

        BottomNavigationView bNv = findViewById(com.example.seg2105project.R.id.bottomNavigationView);
        bNv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id== com.example.seg2105project.R.id.event){
                    Intent intent = new Intent(Member.this, Rating.class);
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

        Edb = new EDBHelper(this);
        eventDisplay = Edb.getSearchEvents(user);
        eventAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, eventDisplay);
        ListView userListView = findViewById(R.id.eventList);
        userListView.setAdapter(eventAdapter);




    }
}
