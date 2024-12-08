package com.example.interviewtest.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interviewtest.Feedback;
import com.example.interviewtest.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder> {
    private List<Feedback> feedbackList;
    //private FirebaseDatabaseHelper dbHelper;
    private Context context;;

    public FeedbackAdapter(List<Feedback> feedbackList, Context context) {
        this.feedbackList = feedbackList;
        this.context = context;
    }

    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feedback, parent, false);
        return new FeedbackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
        Feedback feedback = feedbackList.get(position);
        holder.textId.setText(feedback.getId());
        holder.textDate.setText(feedback.getDate());
        holder.textFeedback.setText(feedback.getUserfeedback());

        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get a reference to the specific item in Firebase using its key
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User feedback");

                // Delete the item from Firebase
                databaseReference.removeValue().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Remove the item from the list and notify the adapter
                        feedbackList.remove(position);
                        notifyItemRemoved(position);
                        Toast.makeText(context, "Data deleted successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Failed to delete data", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });









    }

    @Override
    public int getItemCount() {
        return feedbackList.size();
    }

    public static class FeedbackViewHolder extends RecyclerView.ViewHolder {



        TextView textId, textFeedback, textDate;
        Button deletebtn,editbtn;

        public FeedbackViewHolder(@NonNull View itemView) {
            super(itemView);

            textId = itemView.findViewById(R.id.textId);
            textFeedback = itemView.findViewById(R.id.textFeedback);
            textDate = itemView.findViewById(R.id.textDate);
           deletebtn = itemView.findViewById(R.id.delete);
            editbtn = itemView.findViewById(R.id.edit);

        }
    }
}

