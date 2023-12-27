/**
 * The `Dialog` class is a custom dialog that allows the user to add or edit an event, and notifies a
 * listener when an event is added.
 */
package com.example.seg2105project.Shared;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.seg2105project.DB.EDBHelper;
import com.example.seg2105project.R;

public class Dialog extends AppCompatDialogFragment {
   // These are instance variables declared in the `Dialog` class.
    private EditText age, pace, level;
    private DialogListener listener;
    EDBHelper Edb;
    String[] item = {"Time Trial", "Road Race", "Group Ride"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;
    private Event oldEvent = null;
    private String clubName;
    private Boolean isEditing = false;
    public Boolean deleted = false;


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
        View view = inflater.inflate(R.layout.layout_dialog, null);
        Edb = new EDBHelper(getContext());
        builder.setView(view)
                .setTitle("Event Type")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //String clubStr = getOldEvent().getClubName();

                        if (isEditing) {
                            Edb.deleteEvent(getOldEvent().getEventName(), getOldEvent().getAge(), getOldEvent().getPace(), getOldEvent().getLevel());
                            deleted = true;
                        }

                        String ageStr = age.getText().toString();
                        String paceStr = pace.getText().toString();
                        String levelStr = level.getText().toString();
                        String selectedEventType = autoCompleteTextView.getText().toString();

                        if (ageStr.isEmpty() || paceStr.isEmpty() || levelStr.isEmpty() || selectedEventType.isEmpty()) {
                            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                        } else {
                            // Use a new instance of EDBHelper
                            Edb.addEvent(selectedEventType, null, ageStr, levelStr, paceStr, getClubName(), 0, null);
                            Toast.makeText(getContext(), selectedEventType + " added " + getClubName(), Toast.LENGTH_SHORT).show();

                            // Notify the listener that an event has been added
                            if (listener != null) {
                                listener.onEventAdded();
                            }
                        }
                    }
                });

        age = view.findViewById(R.id.ageEditText);
        pace = view.findViewById(R.id.paceEditText);
        level = view.findViewById(R.id.levelEditText);

        autoCompleteTextView = view.findViewById(R.id.auto_complete_txt);
        adapterItems = new ArrayAdapter<>(getContext(), R.layout.list_item, item);
        autoCompleteTextView.setAdapter(adapterItems);

        return builder.create();
    }

    public void setEditStatus(Boolean isEditing) {
        this.isEditing = isEditing;
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
    public void setListener(DialogListener listener) {
        this.listener = listener;
    }

    public void setClubName(String clubName){
        this.clubName = clubName;
    }

    public String getClubName(){
        return this.clubName;
    }
}