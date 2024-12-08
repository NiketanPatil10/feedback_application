package com.example.interviewtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.interviewtest.databinding.ActivityFeedbackBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FeedbackActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    private DatabaseReference databaseReference;

    ActivityFeedbackBinding binding;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedbackBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize Firebase Database reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("User feedback");

    /*    binding.addfeebacakdbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.userfeedbackEdittext.setVisibility(View.VISIBLE);
                binding.submitfeedbackbtn.setVisibility(View.VISIBLE);


            }
        });  */



       



        binding.addfeebacakdbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String userfeedback = binding.userfeedbackEdittext.getText().toString();


                // Get current system date and time
                String date = java.text.DateFormat.getDateTimeInstance().format(new java.util.Date());


                // Get the currently logged-in user
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();



                if (userfeedback.isEmpty()){
                    Toast.makeText(FeedbackActivity.this, "Please enter your feeback", Toast.LENGTH_SHORT).show();

                }
                else  {
                   // binding.Dispay.setText(userfeedback);
                    // store user feedback into the firebase

                 databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {




                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User feedback");




                            // Generate a new numeric ID as count + 1
                            long count = snapshot.getChildrenCount();
                            String id = String.valueOf(count + 1);


                            Feedback reference = new Feedback(userfeedback,id,date);

                           // DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User feedback");

                            databaseReference.child(firebaseUser.getUid()).setValue(reference).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    String userfeedback = binding.userfeedbackEdittext.getText().toString();

                                    if(!userfeedback.equals("niketanpatil1012@gmail.com")){
                                         startActivity(new Intent(FeedbackActivity.this, closeActivity.class));
                                        Toast.makeText(FeedbackActivity.this, "Your feedback successfully added", Toast.LENGTH_SHORT).show();

                                    }

                                    else if (task.isSuccessful()){
                                        Toast.makeText(FeedbackActivity.this, "Your feedback successfully added", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(FeedbackActivity.this,MainActivity.class));
                                    }
                                }
                            });



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
            }
        });






        binding.editfeedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userfeedback = binding.userfeedbackEdittext.getText().toString();
                String date = java.text.DateFormat.getDateTimeInstance().format(new java.util.Date());
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                if (userfeedback.isEmpty()) {
                    Toast.makeText(FeedbackActivity.this, "Please enter your feedback", Toast.LENGTH_SHORT).show();
                } else  {

                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User feedback");

                        // Retrieve and update feedback for the current user
                        databaseReference.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String id = snapshot.child("id").getValue(String.class); // Use existing ID
                                if (id == null) {
                                    id = String.valueOf(snapshot.getChildrenCount() + 1); // Generate new ID if not present
                                }

                                Feedback reference = new Feedback(userfeedback, id, date);



                                databaseReference.child(firebaseUser.getUid()).setValue(reference).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(FeedbackActivity.this, "Your feedback has been updated", Toast.LENGTH_SHORT).show();
                                           // startActivity(new Intent(FeedbackActivity.this, MainActivity.class));
                                        } else {
                                            Toast.makeText(FeedbackActivity.this, "Failed to update feedback. Try again.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(FeedbackActivity.this, "Error retrieving feedback: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });




                }
            }
        });







    }




    // Retrieve feedback when activity starts
 



    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
       /* if (firebaseUser != null) {
            Toast.makeText(FeedbackActivity.this, "No user logged in", Toast.LENGTH_SHORT).show();
            return;
        }   */

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User feedback");

        // Fetch feedback when the activity starts
        databaseReference.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // If feedback exists, display it in the EditText
                    Feedback feedback = snapshot.getValue(Feedback.class);
                    if (feedback != null) {
                       // binding.userfeedbackEdittext.setText(feedback.getUserfeedback());
                    }
                } else {
                    // If no feedback exists, clear the EditText
                    binding.userfeedbackEdittext.setText("");
                    //Toast.makeText(FeedbackActivity.this, "No feedback found. Please add feedback.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FeedbackActivity.this, "Error loading feedback: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




}