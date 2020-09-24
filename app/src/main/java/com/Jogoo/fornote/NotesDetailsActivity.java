package com.Jogoo.fornote;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Jogoo.fornote.DataBase.NoteRepository;
import com.Jogoo.fornote.Model.Note;
import com.Jogoo.fornote.Util.TimeClass;

import java.util.Objects;


public class NotesDetailsActivity extends AppCompatActivity implements
        View.OnTouchListener ,GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener,View.OnClickListener{

    private static final String TAG = "NotesActivity";
    private static final int EDIT_MODE_ENABLED = 1;
    private static final int EDIT_MODE_DISABLED = 0;


    // This Section is For Initialization of Views
    private TextView mTitleTextView;
    private TextView mDateTextView;
    private EditText mEditTitle;
    private com.jogoo.notes.LinedEditText mNoteLinedEdit;
    private RelativeLayout checkContainer , arrowContainer;
    private ImageButton arrowImage;
    private ImageButton  checkImage;
    private NoteRepository noteRepository;


    // This Section is For Initialization of Variables
    private Note notes;
    private Note finalNotes;
    private boolean mIsNewNote;
    private GestureDetector gestureDetector;
    //Controlling the view modes ENABLED OR DISABLED
    private int Mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_details);

        // Setting properties to their respective ID'S IN the Layout Xml
        mTitleTextView = findViewById(R.id.note_text_id);
        mDateTextView = findViewById(R.id.noteDateDetail);
        mEditTitle = findViewById(R.id.note_text_edit);
        mNoteLinedEdit = findViewById(R.id.noteContent);
        checkContainer = findViewById(R.id.container_check);
        arrowContainer = findViewById(R.id.container_arrow);
        checkImage = findViewById(R.id.check_note);
        arrowImage = findViewById(R.id.arrow_back);

        noteRepository = new NoteRepository(this);

        if(getIncomingIntentNote()){
            // we have a brand new not this is a note go to (edit Mode)
            //This show a new Note

            setPropertiesNewNote();
            //Call the Edit Mode Method to enter Edit Mode if it's not in View Mode
            enableEditMode();

        }else{
            // This is not a new note we go to (View Mode)
            getPropertiesNote();

            //This Method disables the text Input Interaction iN Note EditText in View Mode
           disableEditTextInputInteraction();
        }
        // CALLING THE SET LISTENERS IN THE onCREATE METHOD
        setListeners();
    }

    // Boolean is true if it's a new Note and false if it's not a new Note
    private boolean getIncomingIntentNote(){

        if(getIntent().hasExtra("selected_note")){
            notes = getIntent().getParcelableExtra("selected_note");

            finalNotes = new Note();
            finalNotes.setTitle(notes.getTitle());
            finalNotes.setContent(notes.getContent());
            finalNotes.setDate(notes.getDate());
            finalNotes.setTimeStamp(notes.getTimeStamp());
            finalNotes.setId(notes.getId());

         

            //else if it's not a new note return false
            //Mode is disable if it is not a new Note
            Mode = EDIT_MODE_DISABLED;
            mIsNewNote = false;
            return false;
        }

        //if it is a new note return true
        Mode = EDIT_MODE_ENABLED;  // Mode is enable if it is a new note
        mIsNewNote = true;
        return true;
    }

    // setting the properties if it's a new Note
    @SuppressLint("SetTextI18n")
    private void  setPropertiesNewNote(){
        mTitleTextView.setText("New Note");
        mEditTitle.setText("New Note");

        notes = new Note();
        finalNotes = new Note();

        notes.setTitle("Note Title");
        finalNotes.setTitle("Note Title");

    }

    // setting the properties if it's not a new note in Edit Mode
    private void getPropertiesNote(){
        mTitleTextView.setText(notes.getTitle());
        mEditTitle.setText(notes.getTitle());
        mNoteLinedEdit.setText(notes.getContent());
        mDateTextView.setText(notes.getDate());
    }

    // THIS method let's us Enter Edit Mode and hides View Mode
    private void enableEditMode(){
        checkContainer.setVisibility(View.VISIBLE);

        arrowContainer.setVisibility(View.GONE);

        mTitleTextView.setVisibility(View.GONE);

        mEditTitle.setVisibility(View.VISIBLE);


        Mode = EDIT_MODE_ENABLED;

        //This is a method that will let us Interact with the Content of Note EditText
       enableEditTextInputInteraction();
    }

    //This method disables our Edit Mode and goes into View Mode
    private void disableEditMode(){
        checkContainer.setVisibility(View.GONE);

        arrowContainer.setVisibility(View.VISIBLE);

        mTitleTextView.setVisibility(View.VISIBLE);

        mEditTitle.setVisibility(View.GONE);

        mTitleTextView.setText(mEditTitle.getText());


        Mode = EDIT_MODE_DISABLED;

        disableEditTextInputInteraction();

        String noteContent = Objects.requireNonNull(mNoteLinedEdit.getText()).toString().trim();
        String timestamp = TimeClass.getCurrentTimeStamp();
        String date = TimeClass.giveDate();

        // if the content in the LinedEditText is Greater Than 0 set The Properties to the Final Notes to be Saved
        if(noteContent.length() > 0){
            finalNotes.setTitle(mEditTitle.getText().toString());
            finalNotes.setContent(mNoteLinedEdit.getText().toString());
            finalNotes.setTimeStamp(timestamp);
            finalNotes.setDate(date);

            // if the title is different and if the Content is Different then Only do i want to Save the Note otherWise don't save Changes
            if(!finalNotes.getContent().equals(notes.getContent())
                    || !finalNotes.getTitle().equals(notes.getTitle())){

                saveChanges();
            }
        }
    }

    // This methods allows us to Take care of Listeners in our App in One Method
    private void setListeners(){
        mNoteLinedEdit.setOnTouchListener(this);
        gestureDetector =  new GestureDetector(this,this);
        mTitleTextView.setOnClickListener(this);
        checkImage.setOnClickListener(this);
        arrowImage.setOnClickListener(this);
    }

    // This method is Responsible for disabling EditText Content Interaction when we are in View Mode to View Content.
    private void disableEditTextInputInteraction(){
        mNoteLinedEdit.setKeyListener(null);
        mNoteLinedEdit.setFocusable(false);
        mNoteLinedEdit.setFocusableInTouchMode(false);
        mNoteLinedEdit.clearFocus();
    }

    // This method is Responsible for Enabling EditText Content Interaction when we are Edit Mode To Edit Notes Content.
    private void enableEditTextInputInteraction(){
        mNoteLinedEdit.setKeyListener(new EditText(this).getKeyListener());
        mNoteLinedEdit.setFocusable(true);
        mNoteLinedEdit.setFocusableInTouchMode(true);
        mNoteLinedEdit.requestFocus();
    }

    // This Method save or Updates our Note taking mIsNewNote as parameter
    private void saveChanges(){
        if(mIsNewNote){
            saveNewNotes();
        }else{
            updateNote();
        }
    }

    // This Method updates our Content in the db.
    private void updateNote(){
        noteRepository.updateNote(finalNotes);
    }

    private void saveNewNotes(){
        noteRepository.insertNote(finalNotes);
    }

    // This Method is Responsible For Hiding our KeyBoard Input After Check is Clicked and goes back to View Mode
    private void HideSoftKeyInput(){
        InputMethodManager inputMethodManager = (InputMethodManager)this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();

        if(view == null){
            view = new View(this);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    //This is the onDoubleTap fires when user Double Taps
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d(TAG, "onDoubleTap: Double Tapped !");
        enableEditMode();
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.check_note:
                HideSoftKeyInput();
                disableEditMode();
                break;

            case R.id.note_text_id:
                enableEditMode();
                mEditTitle.requestFocus();
                mEditTitle.setSelection(mEditTitle.length());
                break;

            case R.id.arrow_back :
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(Mode == EDIT_MODE_ENABLED){
          onClick(checkImage);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Mode",Mode);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Getting the Saved Mode Instance From the OnSavedInstanceState as AN getInt passing the Key value
        Mode = savedInstanceState.getInt("Mode");

        if(Mode == EDIT_MODE_ENABLED){
            enableEditMode();
        }
    }

}