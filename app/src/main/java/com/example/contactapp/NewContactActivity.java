package com.example.contactapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.contactapp.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class NewContactActivity extends AppCompatActivity {
    private EditText editTextName, editTextPhone, editTextEmail;
    private MenuItem saveItem;

//    private ArrayList<Contact> contactList;
//    private ContactsAdapter contactsAdapter;
    private MenuItem closeItem;
    private AppDatabase appDatabase;
    private ContactDao contactDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
//        contactList = new ArrayList<>();
//        contactsAdapter = new ContactsAdapter(contactList);

        editTextName = findViewById(R.id.edit_text_name);
        editTextPhone = findViewById(R.id.edit_text_phone);
        editTextEmail = findViewById(R.id.edit_text_email);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.save) {

            String name = editTextName.getText().toString();
            String phone = editTextPhone.getText().toString();
            String email = editTextEmail.getText().toString();
            Contact c = new Contact(name, phone, email);

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    appDatabase = AppDatabase.getInstance(getApplicationContext());
                    contactDao = appDatabase.contactDao();


//                    contactList.add(new Contact(name, phone, email ));
                    contactDao.insert(new Contact (name, phone, email));
//                    contactsAdapter.notifyDataSetChanged();
                }
            });

            Intent intent = new Intent();
            intent.putExtra("contact", c);

            setResult(RESULT_OK, intent);
            finish();
        }
        if (id == R.id.close) {
            finish();
        }
        return super.onOptionsItemSelected(item);

    }

}
