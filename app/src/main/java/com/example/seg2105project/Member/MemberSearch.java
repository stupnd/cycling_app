package com.example.seg2105project.Member;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.seg2105project.ClubOwner.ClubEventDialog;
import com.example.seg2105project.ClubOwner.ClubOwner;
import com.example.seg2105project.DB.EDBHelper;
import com.example.seg2105project.R;
import com.example.seg2105project.Shared.Event;
import java.util.ArrayList;
public class MemberSearch extends AppCompatActivity {
        ListView listEventBtn;
        PopupMenu eventMenu;
        ClubEventDialog clubDialog;
        Button backBtn;
        ImageButton enterBtn;
        EDBHelper Edb;
        EditText searching;
        ArrayList<Event> searchEntries;
        ArrayAdapter<Event> searchAdapter;
        String search;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getSupportActionBar().hide();
            setContentView(R.layout.activity_member_search);

            String user = getIntent().getStringExtra("username");

            Edb = new EDBHelper(this);
            searching = findViewById(R.id.searchText);
            enterBtn = findViewById(R.id.searchEnter);
            enterBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    search = searching.getText().toString();
                    Toast.makeText(getApplicationContext(), "Looking for: " + search, Toast.LENGTH_SHORT).show();
                    searchEntries = Edb.getSearchEvents(search);
                    searchAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, searchEntries);
                    ListView userListView = findViewById(R.id.dbEventList);
                    userListView.setAdapter(searchAdapter);
                }
            });
            listEventBtn = findViewById(R.id.dbEventList);
            listEventBtn.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                    showPopupMenu(arg1, searchEntries.get(pos), Edb, user);
                    return true;
                }
            });

            backBtn = findViewById(R.id.backButton);
            backBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), Member.class);
                    getSupportActionBar().hide();
                    startActivity(intent);
                }
            });
        }

    private void showPopupMenu(View view, Event event, EDBHelper eHelper, String username) {
        clubDialog = new ClubEventDialog();
        eventMenu = new PopupMenu(this, view);
        eventMenu.getMenuInflater().inflate(R.menu.club_event_menu, eventMenu.getMenu());
        eventMenu.show();
        eventMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_item_1) {
                eHelper.addEvent(event.getEventName(), event.getNewName(), event.getAge(), event.getLevel(), event.getPace(), username, event.getParticipants(), event.getDate());
                searchAdapter.notifyDataSetChanged();
                Intent intent = new Intent(getApplicationContext(), Member.class);
                intent.putExtra("username", username);
                startActivity(intent);
                Toast.makeText(this, "Event Added", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                return false;
            }
        });
    }
}