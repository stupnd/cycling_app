/**
 * The ChooseAccType class is an activity in an Android app that allows the user to choose between
 * creating a club or a member account, and includes a video background.
 */
package com.example.seg2105project.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.VideoView;
import android.view.View;

import com.example.seg2105project.R;


public class ChooseAccType extends AppCompatActivity {
    VideoView videoView;
    Button btnCreateClub, btnCreateMember;

    /**
     * This function sets up the layout and functionality for an activity that allows the user to
     * choose between creating a club or creating a member account.
     * 
     * @param savedInstanceState The savedInstanceState parameter is a Bundle object that contains the
     * activity's previously saved state. It is used to restore the activity's state when it is
     * recreated, such as when the device is rotated or when the activity is destroyed and recreated
     * due to a configuration change.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_choose_acc_type);

        videoView = findViewById(R.id.videoView2);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.test);
        videoView.setVideoURI(uri);
        videoView.start();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                mp.setLooping(true);
            }
        });

        btnCreateClub = findViewById(R.id.createClubBtn);
        btnCreateMember = findViewById(R.id.createMmbrBtn);
        btnCreateClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateClub.class);
                startActivity(intent);
            }
        });

        btnCreateMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateAccount.class);
                startActivity(intent);
            }
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