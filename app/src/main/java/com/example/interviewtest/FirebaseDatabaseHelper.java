package com.example.interviewtest;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseDatabaseHelper {

    private DatabaseReference databaseReference;

    public FirebaseDatabaseHelper() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("User feedback");

        //DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User feedback");
    }

    // Add new feedback with a generated date
    public void addFeedback(String userfeedback) {
        String id = databaseReference.push().getKey();
        String date = java.text.DateFormat.getDateTimeInstance().format(new java.util.Date());
        Feedback feedback = new Feedback(id, userfeedback, date);

        if (id != null) {
            databaseReference.child(id).setValue(feedback);
        }
    }

    // Update feedback text
    public void updateFeedback(String id, String newFeedbackText) {
        databaseReference.child(id).child("feedbackText").setValue(newFeedbackText);
    }

    // Delete feedback entry
    public void deleteFeedback(String id) {
        databaseReference.child(id).removeValue();
    }

    // Fetch feedback list
    public void getFeedbackList(ValueEventListener listener) {
        databaseReference.addValueEventListener(listener);
    }




}

