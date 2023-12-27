package com.example.seg2105project.ClubOwner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import com.example.seg2105project.Admin.Admin;
import com.example.seg2105project.DB.EDBHelper;
import com.example.seg2105project.DB.UDBHelper;
import com.example.seg2105project.R;
import com.example.seg2105project.Shared.Dialog;
import com.example.seg2105project.Shared.Event;

import java.util.ArrayList;

public class ClubEvent extends AppCompatActivity {
    ListView listEventBtn;
    ArrayAdapter<Event> usernameAdapter;
    ArrayList<Event> eventDisplay;
    EDBHelper Edb;
    PopupMenu eventMenu;
    ClubEventDialog clubDialog;
    ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_event_page);
        String clubName = getIntent().getStringExtra("clubname");

        Edb = new EDBHelper(this);

        eventDisplay = Edb.getAllEvents();
        usernameAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, eventDisplay);
        ListView userListView = findViewById(R.id.dbEventList);
        userListView.setAdapter(usernameAdapter);

        listEventBtn = findViewById(R.id.dbEventList);
        listEventBtn.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                showPopupMenu(arg1, eventDisplay.get(pos), Edb, clubName);
                return true;
            }
        });

        backBtn = findViewById(R.id.backButton);
        backBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ClubOwner.class);
                getSupportActionBar().hide();
                startActivity(intent);
            }
        });
    }

    private void showPopupMenu(View view, Event event, EDBHelper eHelper, String clubName) {
        clubDialog = new ClubEventDialog();
        eventMenu = new PopupMenu(this, view);
        eventMenu.getMenuInflater().inflate(R.menu.club_event_menu, eventMenu.getMenu());
        eventMenu.show();
        eventMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_item_1) {
                eHelper.addEvent(event.getEventName(), "My New Event", event.getAge(), event.getLevel(), event.getPace(), clubName, 0, null);
                usernameAdapter.notifyDataSetChanged();
                Intent intent = new Intent(getApplicationContext(), ClubOwner.class);
                intent.putExtra("username", clubName);
                startActivity(intent);
                Toast.makeText(this, "Event Added", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                return false;
            }
        });
    }
}
