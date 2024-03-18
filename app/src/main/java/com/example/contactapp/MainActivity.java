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




// have to set layout manager
        binding.rvContacts.setLayoutManager(new LinearLayoutManager(this));


        contactList = new ArrayList<Contact>();
        contactsAdapter = new ContactsAdapter(contactList);

        binding.rvContacts.setAdapter(contactsAdapter);
        contactList.add(new Contact("Nguyen Van A", "012334556" , "A@gmail.com"));
        contactList.add(new Contact("Ngueyn Van B", "123455688", "B@gmail.com"));
        contactsAdapter.notifyDataSetChanged();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase = AppDatabase.getInstance(getApplicationContext());
                contactDao = appDatabase.contactDao();
                contactDao.insert(new Contact("Nguyen Van A", "012334556" , "A@gmail.com"));


            }
        });





    }

}
