package com.ticket.bus.employeetaskplanner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

public class ListOftask extends AppCompatActivity implements SearchView.OnQueryTextListener{

    DatabaseReference reference;
    RecyclerView recyclerView;
    public static ArrayList<ModelTask> list;
    MyAdapter adapter;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    ProgressBar spinner;
    public static final String MY_PREFS_NAME = "PROFILE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("My Task");
        setContentView(R.layout.list_oftask);



        SearchView c_search = findViewById(R.id.c_search);
        c_search.setOnQueryTextListener(this);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();


        spinner = findViewById(R.id.progressBar);

        recyclerView = findViewById(R.id.myRecycler);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        reference = FirebaseDatabase.getInstance().getReference("tasks");

        prepopulateData();

        SharedPreferences mPrefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        String userIdTopic = mPrefs.getString("id", null);
        FirebaseMessaging.getInstance().subscribeToTopic(userIdTopic).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

//                        Toast.makeText(homeMenus.this, "Successfully Subscribe!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void prepopulateData(){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    ModelTask p = dataSnapshot1.getValue(ModelTask.class);
                    list.add(p);
                }
                adapter = new MyAdapter(ListOftask.this,list);
                spinner.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ListOftask.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();
        progressDialog().show();
        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog().dismiss();
                        startActivity(new Intent(ListOftask.this, MainActivity.class));
                        finish();
                    }
                });
    }

    private ProgressDialog progressDialog(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Logout Please wait...");
        progressDialog.setCancelable(false);
        return progressDialog;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.action_logout){
            signOut();
            return true;
        }
        if (id == R.id.action_account){
            startActivity(new Intent(getApplicationContext(), MyAccount.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        Query queries = reference.orderByChild("taskName").equalTo(query);

        queries.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                spinner.setVisibility(View.VISIBLE);
                list.clear();
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    list = new ArrayList<>();

                    for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                    {
                        ModelTask p = dataSnapshot1.getValue(ModelTask.class);
                        list.add(p);
                    }
                    adapter = new MyAdapter(ListOftask.this,list);
                    spinner.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        adapter.filter(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Query queries = reference.orderByChild("taskName").startAt(newText);

        queries.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                spinner.setVisibility(View.VISIBLE);
                list.clear();
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    list = new ArrayList<>();

                    for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                    {
                        ModelTask p = dataSnapshot1.getValue(ModelTask.class);
                        list.add(p);
                    }
                    adapter = new MyAdapter(ListOftask.this,list);
                    spinner.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Toast.makeText(getApplicationContext(), "My search: " + newText, Toast.LENGTH_SHORT).show();
        if("".equals(newText)){
            prepopulateData();
        }
        return false;
    }
}
