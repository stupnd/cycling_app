package com.example.seg2105project.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.seg2105project.DB.UDBHelper;
import com.example.seg2105project.R;

public class CreateClub extends AppCompatActivity {
    VideoView videoView;
    EditText clubName, username, password, repassword;
    Button signup;
    UDBHelper DB;

    /**
     * This function is the onCreate method for the CreateClub activity in an Android app, which sets
     * up the UI, handles user input, and interacts with a database.
     * 
     * @param savedInstanceState This parameter is used to restore the activity's previous state if it
     * was previously destroyed and recreated by the system. It is typically used to restore data or UI
     * state after a configuration change, such as screen rotation.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_create_club);

        videoView = findViewById(R.id.videoView3);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.test);
        videoView.setVideoURI(uri);
        videoView.start();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                mp.setLooping(true);
            }
        });
        clubName = findViewById(R.id.clubName);
        username = findViewById(R.id.newUsername1);
        password = findViewById(R.id.newPassword1);
        repassword = findViewById(R.id.repassword1);
        String type = "C";
        signup = findViewById(R.id.create1);
        DB = new UDBHelper(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String club = clubName.getText().toString();
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                if(user.equals("")||pass.equals("")||repass.equals("")||club.equals(""))
                    Toast.makeText(CreateClub.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    if(pass.equals(repass)){
                        boolean checkuser = DB.validateUsername(user);
                        if(!checkuser){
                            boolean insert = DB.addUser(club, user, pass, type);
                            if(insert){
                                Toast.makeText(CreateClub.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(CreateClub.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(CreateClub.this, "User already exists! please sign in", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(CreateClub.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                    }
                } }
        });
    }
   /**
    * The onPostResume function resumes the video playback and calls the superclass's onPostResume
    * function.
    */
    @Override
    protected void onPostResume(){
        videoView.resume();
        super.onPostResume();
    }
    /**
     * The onRestart() function starts the video playback and calls the superclass's onRestart()
     * method.
     */
    @Override
    protected void onRestart(){
        videoView.start();
        super.onRestart();
    }

    /**
     * The onPause() function suspends the videoView and calls the super.onPause() function.
     */
    @Override
    protected void onPause(){
        videoView.suspend();
        super.onPause();
    }

    /**
     * The function stops video playback and then calls the superclass's onDestroy() method.
     */
    @Override
    protected void onDestroy(){
        videoView.stopPlayback();
        super.onDestroy();
    }
}