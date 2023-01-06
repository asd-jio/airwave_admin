package com.example.adminapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class ViewMessage extends AppCompatActivity implements View.OnClickListener{


    Button send, send2;
    TextView tvTicket, tvSubject, tvMessage, tvSenderName, tvSenderNumber, tvSenderEmail, tvCategory, tvTime, tvStatus;
    EditText tvResponse;
    Button theButton;
    DatabaseReference reference;
    DatabaseReference reference1, reference2, reference3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_view_message);

        Intent intent = getIntent();



        String intentKey = intent.getStringExtra("keyID");
        String intentSubject = intent.getStringExtra("subjectID");
        String intentMessages = intent.getStringExtra("messageID");
        String intentName = intent.getStringExtra("nameID");
        String intentNumber = intent.getStringExtra("numberID");
        String intentEmail = intent.getStringExtra("emailID");
        String intentCategory = intent.getStringExtra("categoryID");
        String intentResponse = intent.getStringExtra("responseID");
        String intentTime = intent.getStringExtra("timeID");





        tvMessage = (TextView) findViewById(R.id.tvmessage);
        tvSubject = (TextView) findViewById(R.id.tvsubject);
        tvTicket = (TextView) findViewById(R.id.ticketNumberID);
        tvSenderName = (TextView) findViewById(R.id.name);
        tvSenderNumber = (TextView) findViewById(R.id.number);
        tvSenderEmail = (TextView) findViewById(R.id.email);
        tvCategory = (TextView) findViewById(R.id.category);
        tvResponse = (EditText) findViewById(R.id.response);
        tvTime = (TextView) findViewById(R.id.time);
        tvStatus = (TextView) findViewById(R.id.status);

        send = (Button) findViewById(R.id.toDeliver);
        send.setOnClickListener(this);

        tvMessage.setText("         "+intentMessages);
        tvSubject.setText(intentSubject);
        tvTicket.setText(intentKey);
        tvSenderName.setText(intentName);
        tvSenderNumber.setText(intentNumber);
        tvSenderEmail.setText(intentEmail);
        tvCategory.setText(intentCategory);
        tvTime.setText(intentTime);

        send2 = (Button)  findViewById(R.id.toFinish);
        send2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String subText = tvSubject.getText().toString();
                String msgMain = tvMessage.getText().toString();
                String sender = tvSenderName.getText().toString();
                String senderNum = tvSenderNumber.getText().toString();
                String email = tvSenderEmail.getText().toString();
                String status = "Completed";
                String key = tvTicket.getText().toString();
                String category = tvCategory.getText().toString();
                String response = tvResponse.getText().toString();
                String time = tvTime.getText().toString();

                completestatus(subText, msgMain, sender, senderNum, email, status, key, category, response, time);


            }

            private void completestatus(String subText, String msgMain, String sender, String senderNum, String email, String status, String key, String category, String response, String time) {
                HashMap messages = new HashMap();


                messages.put("sender", sender);

                messages.put("email", email);
                messages.put("status", status);
                messages.put("category", category);
                messages.put("response", response);
                messages.put("time", time);


                reference = FirebaseDatabase.getInstance().getReference(category);
                reference.child(category+key).updateChildren(messages).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {

                        if (task.isSuccessful()){

                            reference1 = FirebaseDatabase.getInstance().getReference("Completed Tickets").child(category+key);
                            Messages messages = new Messages(subText, msgMain, sender, senderNum, email, status, key, category, response, time);

                            reference1.setValue(messages);

                            reference = FirebaseDatabase.getInstance().getReference("Delivered Tickets").child(category+key);
                            reference2 = FirebaseDatabase.getInstance().getReference(category).child(category+key);
                            reference2.removeValue();
                            reference.removeValue();
                            Toast.makeText(ViewMessage.this, "Ticket Completed",  Toast.LENGTH_LONG).show();


                        }

                        else {
                            Toast.makeText(ViewMessage.this, "failed to update data",  Toast.LENGTH_LONG).show();
                        }

                    }
                });
                Intent intent = new Intent(ViewMessage.this, Itdept.class);
                startActivity(intent);
                finish();
            }
        });

    }
    public void onClick (View v) {

        String subText = tvSubject.getText().toString();
        String msgMain = tvMessage.getText().toString();
        String sender = tvSenderName.getText().toString();
        String senderNum = tvSenderNumber.getText().toString();
        String email = tvSenderEmail.getText().toString();
        String status = "delivered";
        String key = tvTicket.getText().toString();
        String category = tvCategory.getText().toString();
        String response = tvResponse.getText().toString();
        String time = tvTime.getText().toString();

        updateData(subText, msgMain, sender, senderNum, email, status, key, category, response, time);

    }

    private void updateData(String subText, String msgMain, String sender, String senderNum, String email, String status, String key, String category, String response, String time) {

        HashMap messages = new HashMap();

//        messages.put("subText", subText);
//        messages.put("msgMain", msgMain);
        messages.put("sender", sender);
//        messages.put("senderNum", senderNum);
        messages.put("email", email);
        messages.put("status", status);
        messages.put("category", category);
        messages.put("response", response);
        messages.put("time", time);


        reference = FirebaseDatabase.getInstance().getReference(category);
        reference.child(category+key).updateChildren(messages).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {

                if (task.isSuccessful()){

                        reference1 = FirebaseDatabase.getInstance().getReference("Delivered Tickets").child(category+key);
                        Messages messages = new Messages(subText, msgMain, sender, senderNum, email, status, key, category, response, time);
                        reference3 = FirebaseDatabase.getInstance().getReference(sender+"delivered").child(category+key);


                        reference1.setValue(messages);
                        reference3.setValue(messages);
                        reference = FirebaseDatabase.getInstance().getReference("New Tickets").child(category+key);
                        reference.removeValue();
                        Toast.makeText(ViewMessage.this, "Ticket marked as seen",  Toast.LENGTH_LONG).show();

                    }

                else {
                    Toast.makeText(ViewMessage.this, "failed to update data",  Toast.LENGTH_LONG).show();
                }

            }
        });
        finish();
    }




}