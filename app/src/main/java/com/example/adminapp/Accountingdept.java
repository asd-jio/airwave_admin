package com.example.adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Accountingdept extends AppCompatActivity {
    Activity context;
    Fragment fragment;
    Intent intent;
    ArrayList<Messages> listMsgs;
    Adapter myAdapter;
    RecyclerView recyclerView;
    private DatabaseReference reference;
    private FirebaseUser user;
    private String userID;
    Button viewcomplete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountingdept);

            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            reference = FirebaseDatabase.getInstance().getReference("Accounting Department");
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewcomplete = findViewById(R.id.completedTickets);
        viewcomplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.completedTickets:
                        intent = new Intent(Accountingdept.this, Completed.class);
                        startActivity(intent);

                        break;
                }
            }
        });


            listMsgs = new ArrayList();
            myAdapter = new Adapter(this, listMsgs);
            recyclerView.setAdapter(myAdapter);
            reference.addValueEventListener(new ValueEventListener() {

                public void onDataChange(DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        Messages msgs = dataSnapshot.getValue(Messages.class);
                        listMsgs.add(msgs);
                    }
                    myAdapter.notifyDataSetChanged();
                }

                public void onCancelled(DatabaseError error) {
                }
            });
    }
}