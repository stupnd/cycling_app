package com.example.seg2105project.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.seg2105project.R;
import com.example.seg2105project.DB.UDBHelper;

import java.util.ArrayList;

public class AdminAccounts extends AppCompatActivity {
    ImageButton backBtn;
    ListView listMemberBtn;
    ListView listOwnerBtn;
    ArrayList<String> memberEntries;  // Declare as a class-level field
    ArrayList<String> clubEntries;    // Declare as a class-level field
    ArrayAdapter<String> usernameAdapter;
    ArrayAdapter<String> clubAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_admin_accounts);

        UDBHelper UDBHelper = new UDBHelper(this);
        memberEntries = UDBHelper.getMemberList();
        clubEntries = UDBHelper.getClubList();

        usernameAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, memberEntries);
        ListView userListView = findViewById(R.id.dbMemberListView);
        userListView.setAdapter(usernameAdapter);

        clubAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, clubEntries);
        ListView clubListView = findViewById(R.id.dbOwnerListView);
        clubListView.setAdapter(clubAdapter);

        backBtn = findViewById(R.id.backButton);
        backBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Admin.class);
                getSupportActionBar().hide();
                startActivity(intent);
            }
        });

        listMemberBtn = findViewById(R.id.dbMemberListView);
        listMemberBtn.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                showPopupMenu(arg1, memberEntries.get(pos));
                return true;
            }
        });

        listOwnerBtn = findViewById(R.id.dbOwnerListView);
        listOwnerBtn.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                showPopupMenu(arg1, clubEntries.get(pos));
                return true;
            }
        });
    }

    private void showPopupMenu(View view, String username) {
        UDBHelper udbHelper = new UDBHelper(this);
        PopupMenu deleteMenu = new PopupMenu(this, view);
        MenuInflater inflater = deleteMenu.getMenuInflater();
        inflater.inflate(R.menu.account_manage_menu, deleteMenu.getMenu());
        deleteMenu.show();

        deleteMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_item_1) {
                Toast.makeText(getApplicationContext(), "Deleted Account: " + username, Toast.LENGTH_SHORT).show();
                udbHelper.delete(username);

                // Remove the deleted item from the list
                memberEntries.remove(username);
                clubEntries.remove(username);

                // Notify the adapters about the dataset changes
                usernameAdapter.notifyDataSetChanged();
                clubAdapter.notifyDataSetChanged();

                return true;
            } else {
                return false;
            }
        });
    }
}