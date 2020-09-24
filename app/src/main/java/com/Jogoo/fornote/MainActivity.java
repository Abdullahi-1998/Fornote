package com.Jogoo.fornote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.Jogoo.fornote.Activities.PrivacyPolicyActivity;
import com.Jogoo.fornote.Activities.TermConditions;
import com.Jogoo.fornote.Controller.RecylerAdapter;
import com.Jogoo.fornote.DataBase.NoteRepository;
import com.Jogoo.fornote.Model.Note;
import com.Jogoo.fornote.Util.verticalSpacing;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecylerAdapter.onNoteListener, View.OnClickListener{

    //UI COMPONENTS

    private RecyclerView recyclerView;
    private NoteRepository noteRepository;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private TextView textView;




    //Ui Variables

    private ArrayList<Note> mNotes = new ArrayList<>();
    private RecylerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializing the Views to Their ID'S
        recyclerView = findViewById(R.id.recyclerView);
        FloatingActionButton floatingActionButton = findViewById(R.id.FAB);
        navigationView = findViewById(R.id.navId);
        drawerLayout = findViewById(R.id.DrawerId);
        toolbar = findViewById(R.id.toolbarMain);

        toolbar.setTitle(R.string.app_name);
        floatingActionButton.setOnClickListener(this);


        // creating a new Object of NoteREPOSITIORY
        noteRepository = new NoteRepository(this);

        // Setting up Note RecyclerView Method
        setUpRecyclerView();

        // Retrieving the All Notes from NotesDatabase Method
        retrieveAllNotes();

        // Set's up Our ActionDrawer
        ActionBarD();

        // This methods lets us execute onClick in navigation items
        navigationViewItems();

    }

    private void navigationViewItems() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.settings :
                        Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.home:
                        if(drawerLayout.isDrawerVisible(GravityCompat.START)){
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }
                        break;
                    case R.id.addNote :
                        Intent intentAddNote = new Intent(MainActivity.this,NotesDetailsActivity.class);
                        startActivity(intentAddNote);
                        if(drawerLayout.isDrawerVisible(GravityCompat.START)){
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }
                        break;
                    case R.id.privacy_policies :
                        Intent intentPrivacy = new Intent(MainActivity.this, PrivacyPolicyActivity.class);
                        startActivity(intentPrivacy);
                        break;
                    case R.id.Terms_Condition :
                        Intent termsIntent = new Intent(MainActivity.this, TermConditions.class);
                        startActivity(termsIntent);
                        break;
                    case R.id.contact_support :
                        sendMailSupport();
                        break;
                    case R.id.About :
//                        rateUs();
                        break;
                }
                return true;
            }
        });
    }

    private void ActionBarD() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // Setting up and Initialization of RecyclerView with Adapter and LinearLayout Manager
    private void setUpRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecylerAdapter(mNotes,this);
        verticalSpacing verticalSpacing = new verticalSpacing(20);
        recyclerView.addItemDecoration(verticalSpacing);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView); // Attaching the ItemTouchHelper to Our RecyclerView
        recyclerView.setAdapter(adapter);

    }

    // A method which implements the method getAllNotes from Repository which extends
    // the Query("SELECT ALL FROM List in Dao interface )
    private void retrieveAllNotes(){
        noteRepository.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                if(mNotes.size() > 0){
                    mNotes.clear();
                }

                if(notes !=null){
                    mNotes.addAll(notes);
                }

//                if (mNotes.isEmpty()) {
//                    recyclerView.setVisibility(View.GONE);
//                    textView.setVisibility(View.VISIBLE);
//                }
//                else {
//                    recyclerView.setVisibility(View.VISIBLE);
//                    textView.setVisibility(View.GONE);
//                }

                // Notifying The Data in Adapter that it Has Changed
                adapter.notifyDataSetChanged();
            }
        });
    }


    // An interface for Our Onclick In RecyclerView Items in The RecyclerAdapter
    @Override
    public void onNoteClick(int position) {
        //If you want to get the Note that was Selected get and position is as below
//        mNotes.get(position);
        Intent intent = new Intent(this,NotesDetailsActivity.class);
        intent.putExtra("selected_note",mNotes.get(position));
        startActivity(intent);
    }

    // This interface Implements The Onclick Method to FloatingActionButton
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.FAB) {
            Intent intent = new Intent(MainActivity.this, NotesDetailsActivity.class);
            startActivity(intent);
        }

    }

//    private void rateUs() {
//        try{
//            startActivity(new Intent (Intent.ACTION_VIEW,Uri.parse("market://details?id=" + getPackageName())));
//        } catch (ActivityNotFoundException e){
//            startActivity(new Intent (Intent.ACTION_VIEW,Uri.parse("http://play.google.com/store/apps/details?id=" +  getPackageName())));
//        }
//    }

    // This Functionality helps us mange our Contact Us Page opening i
    private void sendMailSupport() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + "recipient@example.com"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "My email's subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "My email's body");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send email using..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "No email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    // Creating a Method to simulate Delete From RecyclerView By Swiping Right
    private void deleteNote(Note note){
        mNotes.remove(note);
        adapter.notifyDataSetChanged(); //Notify The Adapter that the Data Has Changed
        noteRepository.deleteNote(note);
    }

    // This Method Implements ItemTouch ON RecyclerView to Help Us Swipe or Move In this Case To Help us Swipe Right
    private ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            deleteNote(mNotes.get(viewHolder.getAdapterPosition()));
            Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerVisible(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
}