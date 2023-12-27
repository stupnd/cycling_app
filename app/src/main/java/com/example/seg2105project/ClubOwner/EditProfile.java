package com.example.seg2105project.ClubOwner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seg2105project.DB.CDBHelper;
import com.example.seg2105project.Main.MainActivity;
import com.example.seg2105project.Member.Member;
import com.example.seg2105project.R;

public class EditProfile extends AppCompatActivity {
    CDBHelper Cdb;
    TextView instagram, contact, phoneNumber;
    Button submit;
    ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_edit_profile);

        Cdb = new CDBHelper(getApplicationContext());
        String clubName = getIntent().getStringExtra("clubname");

        submit = findViewById(R.id.accountBtn3);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String instagramStr = instagram.getText().toString();
                String contactStr = contact.getText().toString();
                String phoneNumberStr = phoneNumber.getText().toString();

                if (instagramStr.isEmpty() || contactStr.isEmpty() || phoneNumberStr.isEmpty()) {
                    Toast.makeText(EditProfile.this, "Please enter all fields!", Toast.LENGTH_SHORT).show();
                } else {
                    if (Cdb.addClubInfo(clubName, instagramStr, contactStr, phoneNumberStr, 0, null)) {
                        Toast.makeText(EditProfile.this, "Changes Saved!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        instagram = findViewById(R.id.instaLink);
        contact = findViewById(R.id.contactTextfield);
        phoneNumber = findViewById(R.id.phoneNumTextfield);

        backBtn = findViewById(R.id.backButton);
        backBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ClubOwner.class);
                getSupportActionBar().hide();
                startActivity(intent);
            }
        });
    }
}