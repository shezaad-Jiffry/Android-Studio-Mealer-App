package com.example.group7mealerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.TextView;

import UserJavaFiles.Administrator;
import UserJavaFiles.Client;
import UserJavaFiles.Cook;
import UserJavaFiles.User;

public class WelcomePage extends AppCompatActivity {
    //sign out button
    Button button;
    TextView text;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        try{
            user = (Client) getIntent().getSerializableExtra("Client");
            user.getFirstName();
        }catch (Exception e){
            System.out.println("error here1 " + e);
            try{
                user = (Cook) getIntent().getSerializableExtra("Cook");
                user.getFirstName();
            }catch (Exception g){
                System.out.println("error here2 " + e);
                try{
                    user = (Administrator) getIntent().getSerializableExtra("Admin");
                    user.getFirstName();
                }catch (Exception h){
                    System.out.println("error here3 " + e);
                }
            }
        }
        text = (TextView)findViewById(R.id.textView6);
        if (user.getClass() == Cook.class ){
            text.setText("welcome," +user.getFirstName()+' '+user.getLastName()+ ", you are a cook.");
        }
        if (user.getClass() == Client.class ){
            text.setText("welcome," +user.getFirstName()+' '+user.getLastName()+ ", you are a client.");
        }
        if (user.getClass() == Administrator.class ){
            text.setText("welcome," +user.getFirstName()+' '+user.getLastName()+ ", you are the administrator.");
        }


        button = (Button)findViewById(R.id.btnSO);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogin();

            }
        });

    }
    //Method to take the user back to login page when they sign out
    public void openLogin(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

}