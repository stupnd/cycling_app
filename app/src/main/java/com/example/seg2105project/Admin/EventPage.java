/**
 * The `EventPage` class is an activity in an Android app that displays a list of events, allows the
 * user to manage events through a popup menu, and provides functionality to add new events through a
 * dialog.
 */
package com.example.seg2105project.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import com.example.seg2105project.DB.EDBHelper;
import com.example.seg2105project.Shared.Dialog;
import com.example.seg2105project.Shared.Event;
import com.example.seg2105project.R;

import java.util.ArrayList;

public class EventPage extends AppCompatActivity implements Dialog.DialogListener {
    // These lines of code are declaring and initializing variables used in the `EventPage` class:
    ImageButton backBtn;
    ListView listEventBtn;
    ArrayAdapter<Event> usernameAdapter;
    ArrayList<Event> eventDisplay;
    EDBHelper Edb;
    PopupMenu eventMenu;
    Boolean isEditing = false;
    Boolean deleted = false;

    /**
     * This function sets up the layout and functionality for an event page, including hiding the
     * action bar, setting up a back button, retrieving events from a database, and displaying them in
     * a ListView.
     * 
     * @param savedInstanceState The savedInstanceState parameter is a Bundle object that contains the
     * activity's previously saved state. It is used to restore the activity's state when it is
     * recreated, such as after a configuration change (e.g., screen rotation) or when the activity is
     * destroyed and recreated due to memory constraints.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_event_page);

        backBtn = findViewById(R.id.backButton);
        backBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Admin.class);
                startActivity(intent);
            }
        });

        Edb = new EDBHelper(this);
        // Username List here is temporary until we have an event list

        // This code is setting up a ListView to display a list of events.
        eventDisplay = Edb.getAllEvents();
        usernameAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, eventDisplay);
        ListView userListView = findViewById(R.id.dbEventList);
        userListView.setAdapter(usernameAdapter);

        listEventBtn = findViewById(R.id.dbEventList);
        listEventBtn.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                showPopupMenu(arg1, eventDisplay.get(pos));
                return true;
            }
        });
    }

    /**
     * The function `showPopupMenu` creates and displays a popup menu for managing events, with options
     * to open a dialog or delete an event.
     * 
     * @param view The view parameter is the view on which the popup menu will be anchored. It is
     * usually the view that triggers the popup menu when clicked or long-pressed.
     * @param event The "event" parameter is an object of the Event class. It contains information
     * about a specific event, such as its name, age, pace, and level.
     */
    private void showPopupMenu(View view, Event event) {
        eventMenu = new PopupMenu(this, view);
        eventMenu.getMenuInflater().inflate(R.menu.event_manage_menu, eventMenu.getMenu());
        eventMenu.show();
        eventMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_item_1) {
                openDialog(event, true);
                if (deleted) {
                    eventDisplay.remove(event);
                    usernameAdapter.notifyDataSetChanged();
                }
                return true;
            } else if (item.getItemId() == R.id.menu_item_2) {
                if (Edb.deleteEvent(event.getEventName(), event.getAge(), event.getPace(), event.getLevel())) {
                    Toast.makeText(getApplicationContext(), "Event Deleted", Toast.LENGTH_SHORT).show();
                }
                eventDisplay.remove(event);
                usernameAdapter.notifyDataSetChanged();
                return true;
            } else {
                return false;
            }
        });
    }
    /**
     * The function opens a dialog and sets the old event and listener for the dialog.
     * 
     * @param oldevent The "oldevent" parameter is an instance of the Event class. It is used to pass
     * the old event object to the dialog so that it can be accessed and used within the dialog.
     */
    private void openDialog(Event oldevent, Boolean isEditing) {
        Dialog dialog = new Dialog();
        dialog.setClubName("admin");
        dialog.setEditStatus(isEditing);
        dialog.setOldEvent(oldevent);
        dialog.setListener(this); // Set the listener
        dialog.show(getSupportFragmentManager(), "Dialog");

        this.deleted = dialog.deleted;
    }

    // Implement the onEventAdded method to update the event list
    /**
     * The function updates the event list and notifies the adapter when a new event is added.
     */
    @Override
    public void onEventAdded() {
        // Update the event list and notify the adapter
        eventDisplay.clear();
        eventDisplay.addAll(Edb.getAllEvents());
        usernameAdapter.notifyDataSetChanged();
    }
}
