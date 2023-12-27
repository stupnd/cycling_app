/**
 * The ClubOwner class is an activity in an Android app that implements the EventManagement interface
 * and displays a welcome message for a club owner user.
 */
package com.example.seg2105project.ClubOwner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.seg2105project.DB.EDBHelper;
import com.example.seg2105project.DB.UDBHelper;
import com.example.seg2105project.Main.MainActivity;
import com.example.seg2105project.R;
import com.example.seg2105project.Shared.Dialog;
import com.example.seg2105project.Shared.Event;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class ClubOwner extends AppCompatActivity implements Dialog.DialogListener{
    ListView listOwnerBtn;
    FloatingActionButton fab;
    ImageButton settingsbtn;
    ArrayList<Event> eventDisplay;
    EDBHelper Edb;
    ArrayAdapter<Event> eventAdapter;
    PopupMenu eventMenu;
    String user;

    /**
     * This function sets up the Club Owner activity and displays a welcome message with the username.
     *
     * @param savedInstanceState The savedInstanceState parameter is a Bundle object that contains the
     * activity's previously saved state. It is used to restore the activity's state when it is
     * recreated, such as when the device is rotated.
     */
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_club_owner);

        user = getIntent().getStringExtra("username");

        UDBHelper uHelper = new UDBHelper(this);
        user = uHelper.getClubName(user);
        String finalUser = user;

        BottomNavigationView bNv = findViewById(com.example.seg2105project.R.id.bottomNavigationView);
        bNv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id== com.example.seg2105project.R.id.members){
                    Intent intent = new Intent(getApplicationContext(), ClubOwnerMembers.class);
                    startActivity(intent);
                    return true;
                } else if (id== com.example.seg2105project.R.id.logout){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    return true;
                }

                return false;
            }});

        settingsbtn = findViewById(R.id.settingsbtn);
        settingsbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditProfile.class);
                getSupportActionBar().hide();
                intent.putExtra("clubname", finalUser);
                startActivity(intent);
            }
        });


        fab = findViewById(com.example.seg2105project.R.id.searchBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ClubEvent.class);
                intent.putExtra("clubname", finalUser);
                startActivity(intent);
            }
        });
        Edb = new EDBHelper(this);
        // Username List here is temporary until we have an event list
        // This code is setting up a ListView to display a list of events.
        eventDisplay = Edb.getSearchEvents(finalUser);
        eventAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, eventDisplay);
        ListView userListView = findViewById(R.id.eventDBView);
        userListView.setAdapter(eventAdapter);

        listOwnerBtn = findViewById(R.id.eventDBView);
        listOwnerBtn.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                showPopupMenu(arg1, eventDisplay.get(pos), Edb, finalUser);
                return true;
            }
        });
    }
    private void showPopupMenu(View view, Event event, EDBHelper eHelper, String clubName) {
        eventMenu = new PopupMenu(this, view);
        eventMenu.getMenuInflater().inflate(R.menu.club_main_menu, eventMenu.getMenu());
        eventMenu.show();
        eventMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_item_1) {
                    eHelper.deleteClubEvent(event.getEventName(), event.getAge(), event.getPace(), event.getLevel(), clubName);
                    eventDisplay.remove(event);
                    eventAdapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "Event Deleted", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (item.getItemId() == R.id.menu_item_2){
                    Toast.makeText(getApplicationContext(), "Club Name: " + clubName, Toast.LENGTH_SHORT).show();
                    openDialog(event, clubName);
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    private void openDialog(Event event, String clubName) {
        ClubEventDialog dialog = new ClubEventDialog();
        dialog.setClubName(clubName);
        dialog.setOldEvent(event);
        dialog.setListener(this); // Set the listener
        dialog.show(getSupportFragmentManager(), "Dialog");
    }

    @Override
    public void onEventAdded() {
        eventDisplay.clear();
        eventDisplay.addAll(Edb.getSearchEvents(user));
        eventAdapter.notifyDataSetChanged();
    }
}