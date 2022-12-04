package com.example.adminapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private long backPress;
    private Toast backToast;

    private Button itDept, accounting, technical, others;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        itDept = (Button) findViewById(R.id.itDept);
        itDept.setOnClickListener(this);

        accounting = (Button) findViewById(R.id.accountingDept);
        accounting.setOnClickListener(this);

        technical = (Button) findViewById(R.id.techDept);
        technical.setOnClickListener(this);

        others = (Button) findViewById(R.id.others);
        others.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        if (backPress + 2000 > System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
            return;
        }
        else {
            backToast = Toast.makeText(getApplicationContext(),"Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPress = System.currentTimeMillis();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.itDept):
                startActivity(new Intent(MainActivity.this, Itdept.class));
                break;

            case (R.id.accountingDept):
                startActivity(new Intent(MainActivity.this, Accountingdept.class));
                break;

            case (R.id.techDept):
                startActivity(new Intent(MainActivity.this, Technicaldept.class));
                break;

            case (R.id.others):
                startActivity(new Intent(MainActivity.this, Others.class));
                break;
        }
    }
}