package com.example.seg2105project.ClubOwner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.seg2105project.Member.Member;
import com.example.seg2105project.R;

public class ClubOwnerMembers extends AppCompatActivity {

    ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_owner_members);

        backBtn = findViewById(R.id.backButton);
        backBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ClubOwner.class);
                getSupportActionBar().hide();
                startActivity(intent);
            }
        });
    }

    // this event will enable the back
    // function to the button on press

}