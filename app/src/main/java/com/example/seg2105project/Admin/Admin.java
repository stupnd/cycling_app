package com.example.seg2105project.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.anychart.*;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;

import com.example.seg2105project.Shared.Dialog;
import com.example.seg2105project.DB.EDBHelper;
import com.example.seg2105project.Main.MainActivity;
import com.example.seg2105project.R;
import com.example.seg2105project.DB.UDBHelper;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class Admin extends AppCompatActivity {
    AnyChartView anyChartView;
    Button accountBtn;
    FloatingActionButton fab;
    EDBHelper Edb;
    UDBHelper Udb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(com.example.seg2105project.R.layout.activity_admin);

        BottomNavigationView bNv = findViewById(com.example.seg2105project.R.id.bottomNavigationView);
        bNv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id== com.example.seg2105project.R.id.event){
                    Intent intent = new Intent(getApplicationContext(), EventPage.class);
                    startActivity(intent);
                    return true;
                } else if (id== com.example.seg2105project.R.id.logout){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    return true;
                }

                return false;
            }});
        accountBtn = findViewById(com.example.seg2105project.R.id.accountBtn);
        accountBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminAccounts.class);
                startActivity(intent);
            }
        });

        fab = findViewById(com.example.seg2105project.R.id.searchBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openDialog();
            }
        });

        Edb = new EDBHelper(this);

        Udb = new UDBHelper(this);

        anyChartView = findViewById(R.id.any_chart_view);
        Pie pie = AnyChart.pie();
        List<DataEntry> data = new ArrayList<>();
        if (Udb.getAllMembers() == 0 && Udb.getNumClubs() == 0 && Edb.getNumEvents() == 0){
            data.add(new ValueDataEntry("No Data", 1));
        } else{
            data.add(new ValueDataEntry("Users", Udb.getAllMembers()));
            data.add(new ValueDataEntry("Events", Edb.getNumEvents()));
            data.add(new ValueDataEntry("Clubs", Udb.getNumClubs()));
        }
        pie.data(data);
        pie.background("#F8F4E3");
        anyChartView.setChart(pie);}

    private void openDialog() {
        Dialog dialog = new Dialog();
        dialog.setClubName("admin");
        dialog.show(getSupportFragmentManager(), "Dialog");
    }
}