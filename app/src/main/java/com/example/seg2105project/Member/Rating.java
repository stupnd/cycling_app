// Feedback.java

package com.example.seg2105project.Member;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import com.example.seg2105project.Admin.Admin;
import com.example.seg2105project.ClubOwner.ClubEventDialog;
import com.example.seg2105project.DB.UDBHelper;
import com.example.seg2105project.Main.MainActivity;
import com.example.seg2105project.R;
import com.example.seg2105project.Shared.Dialog;
import com.example.seg2105project.Shared.Event;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;


public class Rating extends AppCompatActivity implements Dialog.DialogListener{

    ImageButton backBtn;
    FloatingActionButton btnSearch;
    ArrayList<String> clubEntries;
    ArrayAdapter<String> clubAdapter;
    ListView clubListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_rating);

        UDBHelper udb = new UDBHelper(this);

        clubEntries = udb.getClubList();
        clubAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, clubEntries);
        clubListView = findViewById(R.id.clubDBView);
        clubListView.setAdapter(clubAdapter);
        clubListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                showPopupMenu(arg1, clubEntries.get(pos));
                return true;
            }
        });

        String user = getIntent().getStringExtra("username");

        btnSearch = findViewById(R.id.searchBtn);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MemberSearch.class);
                intent.putExtra("username", user);
                startActivity(intent);
            }
        });

        BottomNavigationView bNv = findViewById(R.id.bottomNavigationView);
        bNv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.event) {
                    Intent intent = new Intent(getApplicationContext(), Feedback.class);
                    intent.putExtra("username", user);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.logout) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
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

    private void showPopupMenu(View view, String clubName) {
        PopupMenu deleteMenu = new PopupMenu(this, view);
        MenuInflater inflater = deleteMenu.getMenuInflater();
        inflater.inflate(R.menu.rating_longpress_menu, deleteMenu.getMenu());
        deleteMenu.show();

        deleteMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_item_1) {
                openDialog(clubName);
                return true;
            } else {
                return false;
            }
        });
    }

    private void openDialog(String clubName) {
        RatingDialog dialog = new RatingDialog(clubName);
        dialog.setListener(this);
        dialog.show(getSupportFragmentManager(), "Dialog");
    }

    @Override
    public void onEventAdded() {
    }
}
