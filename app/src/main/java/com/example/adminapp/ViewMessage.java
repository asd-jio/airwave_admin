package com.example.adminapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;


public class ViewMessage extends AppCompatActivity implements View.OnClickListener{


    Button send, send2;
    TextView tvTicket, tvSubject, tvMessage, tvSenderName, tvSenderNumber, tvSenderEmail,
            tvCategory, tvTime, tvStatus, tvImage1, tvImage2, tvImage3;
    ImageView imageView1, imageView2, imageView3;
    EditText tvResponse;
    Button theButton;
    DatabaseReference reference;
    DatabaseReference reference1, reference2, reference3;

    private StorageReference storageReference;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private Uri imageUri, imageUri2, imageUri3;

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
        String intentImage1 = intent.getStringExtra("image1ID");
        String intentImage2 = intent.getStringExtra("image2ID");
        String intentImage3 = intent.getStringExtra("image3ID");

        System.out.println(intentImage1);
        System.out.println(intentImage2);
        System.out.println(intentImage3);

        imageView1 = (ImageView)findViewById(R.id.addImage1);
        imageView2 = (ImageView)findViewById(R.id.addImage2);
        imageView3 = (ImageView)findViewById(R.id.addImage3);

        if(intentImage1 != null) {
            storageReference = storage.getReferenceFromUrl("gs://loginwithauth-20b07.appspot.com/").child("images/" + intentImage1);
            try {
                final File file1 = File.createTempFile("image", "jpg");
                storageReference.getFile(file1).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Bitmap bitmap1 = BitmapFactory.decodeFile(file1.getAbsolutePath());
                        imageView1.setImageBitmap(bitmap1);

                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if(intentImage2 != null) {
            storageReference = storage.getReferenceFromUrl("gs://loginwithauth-20b07.appspot.com/").child("images/" + intentImage2);
            try {
                final File file2 = File.createTempFile("image", "jpg");
                storageReference.getFile(file2).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Bitmap bitmap2 = BitmapFactory.decodeFile(file2.getAbsolutePath());
                        imageView2.setImageBitmap(bitmap2);

                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if(intentImage3 != null) {
            storageReference = storage.getReferenceFromUrl("gs://loginwithauth-20b07.appspot.com/").child("images/" + intentImage3);
            try {
                final File file3 = File.createTempFile("image", "jpg");
                storageReference.getFile(file3).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Bitmap bitmap3 = BitmapFactory.decodeFile(file3.getAbsolutePath());
                        imageView3.setImageBitmap(bitmap3);

                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }





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
        tvImage1 = (TextView) findViewById(R.id.image1);
        tvImage2 = (TextView) findViewById(R.id.image2);
        tvImage3 = (TextView) findViewById(R.id.image3);


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
        tvImage1.setText(intentImage1);
        tvImage2.setText(intentImage2);
        tvImage3.setText(intentImage3);



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
                String image1 = tvImage1.getText().toString();
                String image2 = tvImage2.getText().toString();
                String image3 = tvImage3.getText().toString();



                completestatus(subText, msgMain, sender, senderNum, email, status, key, category, response, time, image1, image2, image3);


            }

            private void completestatus(String subText, String msgMain, String sender, String senderNum, String email, String status,
                                        String key, String category, String response, String time, String image1, String image2, String image3) {
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
                            Messages messages = new Messages(subText, msgMain, sender, senderNum, email, status, key, category, response, time, image1, image2, image3);

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
        String status = "Pending";
        String key = tvTicket.getText().toString();
        String category = tvCategory.getText().toString();
        String response = tvResponse.getText().toString();
        String time = tvTime.getText().toString();
        String image1 = tvImage1.getText().toString();
        String image2 = tvImage2.getText().toString();
        String image3 = tvImage3.getText().toString();


        updateData(subText, msgMain, sender, senderNum, email, status, key, category, response, time, image1, image2, image3);

    }

    private void updateData(String subText, String msgMain, String sender, String senderNum, String email, String status, String key, String category, String response, String time, String image1, String image2, String image3) {

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
                        Messages messages = new Messages(subText, msgMain, sender, senderNum, email, status, key, category, response, time, image1, image2, image3);
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