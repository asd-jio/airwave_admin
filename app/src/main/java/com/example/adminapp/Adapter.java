package com.example.adminapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> implements View.OnClickListener{

    Context context;
    Intent intent;

    ArrayList<Messages> msgList;

    public Adapter(Context context, ArrayList<Messages> msgList) {
        this.context = context;
        this.msgList = msgList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.design1, parent, false);


        Button subButton = (Button) v.findViewById(R.id.subject);
        subButton.setOnClickListener(this);


        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Messages msgs = msgList.get(position);
        holder.subject.setText(msgs.getSubject());
        holder.senderNumber.setText(msgs.getSenderNumber());

    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }

    @Override
    public void onClick(View view) {
        intent = new Intent(context, Inbox.class);
        context.startActivity(intent);

    }

    public static class MyViewHolder extends ViewHolder {

        TextView senderNumber;
        Button subject;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            subject = itemView.findViewById(R.id.subject);
            senderNumber = itemView.findViewById(R.id.senderNumber);





        }
    }

}

