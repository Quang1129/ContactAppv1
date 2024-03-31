package com.example.contactapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.contactapp.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private ArrayList<Contact> contactList;
    private ContactsAdapter contactsAdapter;
    private SearchView searchView;

    private AppDatabase appDatabase;
    private ContactDao contactDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);
        FloatingActionButton btnAdd = findViewById(R.id.btn_add);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                            Contact c = (Contact) data.getSerializableExtra("contact");
                            Log.d("DEBUG1", c.mobile +' '+ c.name);

//                            AsyncTask.execute(new Runnable() {
//                                @Override
//                                public void run() {
//                                    appDatabase = AppDatabase.getInstance(getApplicationContext());
//                                    contactDao = appDatabase.contactDao();
//
//                                    contactDao.insert(c);
//                                }
//                            });

                            contactList.add(c);
                            contactsAdapter.notifyDataSetChanged();


                        }
                    }
                });

        // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewContactActivity.class);
                activityResultLauncher.launch(intent);
            }
        });

// have to set layout manager
        binding.rvContacts.setLayoutManager(new LinearLayoutManager(this));


        contactList = new ArrayList<Contact>();
        contactsAdapter = new ContactsAdapter(contactList);

        binding.rvContacts.setAdapter(contactsAdapter);

        //always notify adapter that data has been changed when you modify the data

        appDatabase = AppDatabase.getInstance(getApplicationContext());
        contactDao = appDatabase.contactDao();



        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                List<Contact> contacts1=contactDao.getAll();
                for (Contact c: contacts1){
                    contactList.add(c);
                }
                contactsAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
//        searchView = findViewById(R.id.action_search);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                contactsAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                contactsAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}