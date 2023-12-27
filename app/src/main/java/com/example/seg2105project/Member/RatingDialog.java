package com.example.seg2105project.Member;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.seg2105project.ClubOwner.EditProfile;
import com.example.seg2105project.DB.CDBHelper;
import com.example.seg2105project.R;

public class RatingDialog extends AppCompatDialogFragment {
    private com.example.seg2105project.Shared.Dialog.DialogListener listener;
    private CDBHelper cdb;
    private String clubName;
    EditText feedback;

    public RatingDialog(String clubName) {
        this.clubName = clubName;
    }

    @Override
    public AlertDialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_rating_dialog, null);

        cdb = new CDBHelper(getContext());

        builder.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Handle cancel
                    }
                })
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Retrieve the rating and feedback from your dialog UI components
                        RatingBar ratingBar = view.findViewById(R.id.ratingBar2);
                        float rating = ratingBar.getRating();
                        String feedbackStr = feedback.getText().toString();

                        // Add the rating and feedback to the ClubInfo object and database
                        if (cdb.addClubInfo(clubName, cdb.getInstagram(clubName), cdb.getContact(clubName), cdb.getPhoneNumber(clubName), rating, feedbackStr)) {
                            Toast.makeText(getContext(), "Rating submitted!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
         feedback = view.findViewById(R.id.commentEditText);

        return builder.create();
    }

    public void setListener(com.example.seg2105project.Shared.Dialog.DialogListener listener) {
        this.listener = listener;
    }
}
