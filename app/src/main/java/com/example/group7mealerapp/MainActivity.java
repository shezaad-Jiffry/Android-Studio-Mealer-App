package com.example.group7mealerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import UserJavaFiles.Administrator;
import UserJavaFiles.Client;
import UserJavaFiles.Cook;
import UserJavaFiles.UserPOJO;
//TODO: MUST REMAIN THIS TO LOGIN NOT MAINACTIVITY MAINACTIVITY IS WELCOMEPAGE
public class MainActivity extends AppCompatActivity {


    //create an instance for firebase
    FirebaseDatabase firebaseDatabase;
    //reference to the actual database in firebase
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //see comments in registration for explanation

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UserInfo");
    }
    //navigate to registration page
    public void btnRegisterClick(View view)
    {
        Intent intent = new Intent(getApplicationContext(),Registration.class);
        startActivityForResult(intent,0);//convert this method, it is depricated

    }
    /**
     * what is implemented now is just the grabbing of the user data and
     * storing it into a user object,
     * TODO: make sure to iterate and find the correct (if exists) email then check if password match
     * TODO: once done store in appropriate object dependent on type and send to next screen
     */
    public void btnLoginClick(View view){
        //text field variables
        EditText email =  findViewById(R.id.loginEmail);
        EditText password =  findViewById(R.id.loginPassword);
//        Button login = findViewById(R.id.btnLogin);

        //Ensuring both text fields are not blank
        if(email.getText().toString().isEmpty())
        {
            email.setError("This field cannot be empty");
        }

        if(password.getText().toString().isEmpty())
        {
            password.setError("This field cannot be empty");
        }
        //listens to the database in real time
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            //method called on start and whenever data is changed
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Variables for toast display
                boolean flag = false;
                Context context = getApplicationContext();
                String error = "Incorrect email or password";
                int duration = Toast.LENGTH_LONG;
                //calls an iterator on the children in the database IE all users stored
                Iterable<DataSnapshot> children = snapshot.getChildren();
                //going to iterate through the data and if email matches, login user and save it
                //in userPOJO
                UserPOJO user = new UserPOJO();
                UserPOJO temp = new UserPOJO();
                //this loop iterates through the DB under the userInfo block
                for (DataSnapshot child: children){
                    //no logic just stores the value onto user
                    temp = child.getValue(UserPOJO.class);
                    //comparing the email and password from the database with the inputted text fields
                    if (temp.getEmail().equals(email.getText().toString())
                            && temp.getPassword().equals(password.getText().toString()))
                    {
                        //since user and pass match the user logging in is this child from the database
                        user = temp;
                        //setting flag to true meaning the account matches the input
                        flag = true;
                        //Temporary code for debugging
                        email.setError("Correct credentials");
                        break;
                    }


                }

                if(!flag)
                {
                    email.setError("incorrect");
                    password.setError("incorrect");
                    Toast.makeText(context, error, duration).show();

                }
                //call either convert to client or convert to cook here or convert to admin
                if(user.getType().equals("Client"))
                {
                    Client currentUser = user.convertToClient();//surround in try and catch block
                }
                else if(user.getType().equals("Cook"))
                {
                    Cook currentUser = user.convertToCook();
                }

                else
                {
                    Administrator currentUser = user.convertToAdministrator();
                }


                //checks if it got the right data for debugging
//                System.out.println(currentUser.getEmail());
                }

            //no need for this function but must be overridden
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}