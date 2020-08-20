package com.example.wellnesstracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    public Button btn_signup, btn_login;
    public EditText et_username, et_password;

    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_signup = findViewById(R.id.btn_signup);
        final Context currentContext = this;
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);

        final Database database = Database.getInstance(currentContext);

        FirebaseDatabase firebaseInstance = FirebaseDatabase.getInstance();
        final DatabaseReference firebaseReference = firebaseInstance.getReference();

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et_username.getText().toString().isEmpty() && !et_password.getText().toString().isEmpty()) {

                    User existingUser = database.getUserDao().findUserByUsername(et_username.getText().toString());
                    if(existingUser==null){
                        //ROOM
                        final User newUser = new User(et_username.getText().toString(), et_password.getText().toString());
                        database.getUserDao().insertUser(newUser);

                        //Firebase
                        User userToAddInFirebaseFromROOM = database.getUserDao().findUserByUsername(newUser.getUsername());
                        firebaseReference.child("users").child(Integer.toString(userToAddInFirebaseFromROOM.getId())).setValue(userToAddInFirebaseFromROOM);
                    }
                     else {
                        Toast.makeText(currentContext, "This user already exists!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        //ROOM
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final User userByUsername = database.getUserDao().findUserByUsername(et_username.getText().toString());
                if (userByUsername != null) {
                    if(userByUsername.getPassword().compareTo(et_password.getText().toString()) == 0) {
                        Intent openMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                        openMainActivity.putExtra("passedUser", userByUsername.getId());
                        startActivity(openMainActivity);
                    }
                    else {
                        Toast.makeText(currentContext, "You entered a wrong password!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(currentContext, "You must Sign Up before Logging In!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Firebase
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = et_username.getText().toString();

                firebaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User newUser = new User();

                        for(DataSnapshot user : dataSnapshot.getChildren()){
                            if(user.child("username").getValue().equals(username)){
                                newUser = user.getValue(User.class);
                            }
                        }

                        if(newUser.getUsername() != null){
                            if(newUser.getPassword().equals(et_password.getText().toString())){
                                Intent openMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                                openMainActivity.putExtra("passedUser", newUser.getId());
                                startActivity(openMainActivity);
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "You entered a wrong password!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "You must Sign Up before Logging In! ", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

}
