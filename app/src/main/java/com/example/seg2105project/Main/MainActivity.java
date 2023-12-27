package com.example.seg2105project.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.seg2105project.Admin.Admin;
import com.example.seg2105project.ClubOwner.ClubOwner;
import com.example.seg2105project.DB.UDBHelper;
import com.example.seg2105project.Member.Member;
import com.example.seg2105project.R;

public class MainActivity extends AppCompatActivity {
    VideoView videoView;
    EditText username, password;
    Button btnlogin;
    Button btnCreate;
    UDBHelper DB;

    /**
     * This function is the onCreate method for the main activity of an Android application, which sets
     * up the UI, handles user login, and starts different activities based on the user's credentials.
     * 
     * @param savedInstanceState The savedInstanceState parameter is a Bundle object that contains the
     * activity's previously saved state. It is used to restore the activity's state when it is
     * recreated, such as when the device is rotated.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature (Window.FEATURE_NO_TITLE);
        this. getWindow() .setFlags (WindowManager. LayoutParams .FLAG_FULLSCREEN, WindowManager. LayoutParams .FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        videoView = findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.test);
        videoView.setVideoURI(uri);
        videoView.start();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                mp.setLooping(true);
            }
        });

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btnlogin = findViewById(R.id.login);

        btnCreate = findViewById(R.id.createaccountscr);

        DB = new UDBHelper(this);
        if (!DB.validateUsername("gccadmin") || !DB.validateUsername("cyclingaddict")) {
            DB.addUser("gccadmin", "gccadmin", "GCCRocks!", "C");
            DB.addUser("cyclingaddict", "cyclingaddict", "cyclingIsLife!", "M");
        }

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(user.equals("")||pass.equals("")) {
                    Toast.makeText(MainActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (user.equals("admin") && pass.equals("admin")) {
                        Toast.makeText(MainActivity.this, "Sign in successfull", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Admin.class);
                        startActivity(intent);
                    }
                    else {
                        boolean checkuserpass = DB.validateUserPassword(user, pass);

                        if (checkuserpass) {
                            if (DB.userType(user, pass).equals("C")) {
                                Toast.makeText(MainActivity.this, "Sign in successfull", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), ClubOwner.class);
                                intent.putExtra("username", user);
                                startActivity(intent);
                            }
                            else if (DB.userType(user, pass).equals("M")) {
                                Toast.makeText(MainActivity.this, "Sign in successfull", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Member.class);
                                intent.putExtra("username", user); // Pass the username as an extra
                                startActivity(intent);
                            }

                        } else {
                            Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChooseAccType.class);
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