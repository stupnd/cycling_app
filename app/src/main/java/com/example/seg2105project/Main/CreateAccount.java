/**
 * The CreateAccount class is an activity in an Android app that allows users to create a new account
 * by entering a username and password.
 */
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

public class CreateAccount extends AppCompatActivity {
    VideoView videoView;
    EditText username, password, repassword;
    Button signup;
    UDBHelper DB;

    /**
     * This function is the onCreate method for the CreateAccount activity in an Android app, which
     * sets up the layout, initializes variables, and handles the sign up button click event.
     * 
     * @param savedInstanceState The savedInstanceState parameter is a Bundle object that contains the
     * activity's previously saved state. It is used to restore the activity's state when it is
     * recreated, such as when the device is rotated.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_create_account);

        videoView = findViewById(R.id.videoView1);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.test);
        videoView.setVideoURI(uri);
        videoView.start();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                mp.setLooping(true);
            }
        });
        username = findViewById(R.id.newUsername);
        password = findViewById(R.id.newPassword);
        repassword = findViewById(R.id.repassword);
        String tp = "M";
        signup = findViewById(R.id.create);
        DB = new UDBHelper(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();


                if(user.equals("")||pass.equals("")||repass.equals(""))
                    Toast.makeText(CreateAccount.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    if(pass.equals(repass)){
                        boolean checkuser = DB.validateUsername(user);
                        if(!checkuser){
                            boolean insert = DB.addUser(null, user, pass, tp);
                            if(insert){
                                Toast.makeText(CreateAccount.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(CreateAccount.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(CreateAccount.this, "User already exists! please sign in", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(CreateAccount.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
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