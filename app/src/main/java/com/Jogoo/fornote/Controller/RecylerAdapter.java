package com.Jogoo.fornote.Controller;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Jogoo.fornote.Model.Note;
import com.Jogoo.fornote.R;
import com.Jogoo.fornote.Util.TimeClass;

import java.util.ArrayList;

public class RecylerAdapter  extends RecyclerView.Adapter<RecylerAdapter.viewHolder>{

    private static final String TAG = "ADAPTER";
    //setting the type of Data which we will retrieve from the Database
    private ArrayList<Note> mNotes = new ArrayList<>();
    private onNoteListener mOnNoteListener;

    //Builds a constructor for the Adapter and OnNoteListener interface
    public RecylerAdapter(ArrayList<Note> notes,onNoteListener onNoteListener) {
        this.mNotes = notes;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Gets the and set the Layout view  and it inflates the view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_notes_list,parent,false);
        return new viewHolder(view,mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        try{
            String month = mNotes.get(position).getTimeStamp().substring(0,2);
            month = TimeClass.getMonthToAbbreviate(month);

            String year = mNotes.get(position).getTimeStamp().substring(3);
            String timestamp = month + "\n" + year;
            holder.noteTimestamp.setText(timestamp);

            //This gets and set TextView According to what is stored in THE DS
            holder.noteTextView.setText(mNotes.get(position).getTitle());
            holder.noteDate.setText(mNotes.get(position).getDate());
        } catch (NullPointerException n){
            Log.d(TAG, "onBindViewHolder: + NullMessage " + n.getMessage());
        }
    }

    @Override
    public int getItemCount() {

        //This gets the count of items in DS and Returns It
        return mNotes.size();
    }

    public  class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //setting the Properties from custom List Layout
        public TextView noteTextView;
        public TextView noteTimestamp;
        //public TextView noteContent;
        public TextView noteDate;

        //Setting up the OnNote Interface
        private onNoteListener monNoteListener;

        public viewHolder(@NonNull View itemView, onNoteListener onNoteListener) {
            super(itemView);

            //Find the id of the views from Custom list View
            noteTextView = itemView.findViewById(R.id.note_text_title);
            noteDate = itemView.findViewById(R.id.date);
            noteTimestamp = itemView.findViewById(R.id.timestamp);

            //Refer to the onNoteListener in the viewHolder class
            this.monNoteListener = onNoteListener;

            //listening for click in the Entire RecyclerView of the View.OnClickListener interface
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            //This lets us setup the onNoteClick interface telling us the position of the item Clicked
            monNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    //Interface for Implementing the Click on RecyclerView
    public interface onNoteListener{
        //pass the int position of the item being Clicked it will the an int as position
        void onNoteClick(int position);
    }
}
