package com.example.seg2105project.ClubOwner;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.seg2105project.DB.EDBHelper;
import com.example.seg2105project.R;
import com.example.seg2105project.Shared.Event;

public class ClubEventDialog extends AppCompatDialogFragment {
    // These are instance variables declared in the `Dialog` class.
    private EditText eventName;
    private com.example.seg2105project.Shared.Dialog.DialogListener listener;
    EDBHelper Edb;
    String[] item = {"Time Trial", "Road Race", "Group Ride"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;
    private Event oldEvent = null;
    private String clubName;
    int participants;
    String selectedDate;


    /**
     * The onCreateDialog function creates and returns an AlertDialog with a custom layout and handles
     * the positive and negative button clicks.
     *
     * @param savedInstanceState The savedInstanceState parameter is a Bundle object that contains the
     * data that was saved in the onSaveInstanceState() method. It is used to restore the state of the
     * dialog when it is recreated, such as after a configuration change.
     * @return The method is returning an AlertDialog.
     */
    @Override
    public AlertDialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // This code block is creating and configuring an AlertDialog.
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_club_event_dialog, null);
        Edb = new EDBHelper(getContext());

        SeekBar seekbar = view.findViewById(R.id.participantSeekBar);
        if (seekbar != null) {
            seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    Toast.makeText(getContext(), String.valueOf(seekBar.getProgress()), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                   participants = seekbar.getProgress();
                }
            });
        }

        CalendarView calendarView = view.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // Handle the selected date
                selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                Toast.makeText(getContext(), "Selected Date: " + selectedDate, Toast.LENGTH_SHORT).show();
            }
        });

        builder.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //String clubStr = getOldEvent().getClubName();
                        if(getOldEvent() != null){
                            if (Edb.deleteClubEvent(getOldEvent().getEventName(), getOldEvent().getAge(), getOldEvent().getPace(), getOldEvent().getLevel(), getOldEvent().getClubName())){
                                Toast.makeText(getContext(), "Successful deletion", Toast.LENGTH_SHORT).show();
                            }
                        }
                        String eventNameStr = eventName.getText().toString();

                        if (participants == 0 || eventNameStr.isEmpty()) {
                            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                        } else {
                            // Use a new instance of EDBHelper
                            Edb.addEvent(getOldEvent().getEventName(), eventNameStr, getOldEvent().getAge(), getOldEvent().getLevel(), getOldEvent().getPace(), getOldEvent().getClubName(), getParticipants(), getDate());

                            Toast.makeText(getContext(), eventNameStr + " Updated", Toast.LENGTH_SHORT).show();

                            // Notify the listener that an event has been added
                            if (listener != null) {
                                listener.onEventAdded();
                            }
                        }
                    }
                });

        eventName = view.findViewById(R.id.eventName);

        return builder.create();
    }
    /**
     * The function sets the value of the "oldEvent" variable to the provided "oldEvent" object.
     *
     * @param oldEvent The parameter "oldEvent" is of type "Event". It is used to set the value of the
     * instance variable "oldEvent" in the current object.
     */
    public void setOldEvent(Event oldEvent){
        this.oldEvent = oldEvent;
    }
    /**
     * The function "getOldEvent" returns the old event.
     *
     * @return The method is returning the value of the variable "oldEvent", which is of type "Event".
     */
    public Event getOldEvent(){
        return this.oldEvent;
    }

    // The `public interface DialogListener` is defining an interface called `DialogListener`. This
    // interface has a single method called `onEventAdded()`, which does not take any parameters and
    // does not return any value.
    public interface DialogListener {
        void onEventAdded();
    }

    /**
     * The function sets a listener for a dialog.
     *
     * @param listener The listener parameter is an object that implements the DialogListener interface.
     * It is used to set a listener for the dialog.
     */
    public void setListener(com.example.seg2105project.Shared.Dialog.DialogListener listener) {
        this.listener = listener;
    }

    public void setClubName(String clubName){
        this.clubName = clubName;
    }

    public String getClubName(){
        return this.clubName;
    }

    public int getParticipants() { return this.participants; }

    public String getDate() { return this.selectedDate; }
}