package com.example.wellnesstracker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    ImageView iv_avatar;
    Button btnChooseAvatar, btnRestoreAvatar;
    FloatingActionButton floatingBtn_saveAvatar;
    TextView tv_hello;
    private static final int GET_FROM_GALLERY = 1;
    Uri selectedPhoto;
    FirebaseDatabase firebase;
    DatabaseReference firebaseReference;

    FirebaseStorage storage;
    StorageReference storageReference;

    public static final int MAPS = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            selectedPhoto = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedPhoto);
                iv_avatar.setImageBitmap(bitmap);
                float aspectRatio = bitmap.getWidth() / (float) bitmap.getHeight();
                int width = 480;
                int height = Math.round(width / aspectRatio);
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, false);
                iv_avatar.setImageBitmap(rotatedBitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeImageToFirebase() {
        if (selectedPhoto != null) {
            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            ref.putFile(selectedPhoto).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getApplicationContext(), "Avatar saved to Firebase!", Toast.LENGTH_SHORT).show();
                }
            });
            ref.putFile(selectedPhoto).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Avatar failed to save to Firebase!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnBMI = (Button) findViewById(R.id.btn_bmiActivity);
        Button btnCalendar = (Button) findViewById(R.id.btn_calendarActivity);
        Button btnWorkout = (Button) findViewById(R.id.btn_workoutActivity);
        Button btnNutrition = (Button) findViewById(R.id.btn_nutritionActivity);
        Button btnAllUsers = findViewById(R.id.btn_allUsers);
        Button btnAllNotes = findViewById(R.id.btn_allNotes);
        Database database = Database.getInstance(getApplicationContext());
        btnChooseAvatar = findViewById(R.id.btn_setAvatar);
        iv_avatar = findViewById(R.id.iv_userAvatar);
//        btnRestoreAvatar = findViewById(R.id.btn_restoreAvatar);
        floatingBtn_saveAvatar = findViewById(R.id.floatingBtn_saveAvatar);

        firebase = FirebaseDatabase.getInstance();
        firebaseReference = firebase.getReference();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        int passedId = getIntent().getIntExtra("passedUser", -1);
        tv_hello = findViewById(R.id.tv_helloUser);
        firebaseReference.child("users").child(Integer.toString(passedId)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User retrievedUser = dataSnapshot.getValue(User.class);
                if (retrievedUser != null) {
                    tv_hello.append(retrievedUser.getUsername());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        floatingBtn_saveAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeImageToFirebase();
            }
        });

        StorageReference selectedPhotoRef = storage.getReference("images/avatar.png");
        selectedPhotoRef.getBytes(2048 * 2048).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                iv_avatar.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "The avatar couldn't be restored!", Toast.LENGTH_SHORT).show();
            }
        });

        btnChooseAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(openGallery, GET_FROM_GALLERY);

            }
        });

//        btnRestoreAvatar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                StorageReference selectedPhotoRef =  storage.getReference("images/avatar.png");
//                selectedPhotoRef.getBytes(2048*2048).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//                    @Override
//                    public void onSuccess(byte[] bytes) {
//                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                        iv_avatar.setImageBitmap(bitmap);
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(getApplicationContext(), "The avatar couldn't be restored!", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });


        btnBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openBMI = new Intent(getApplicationContext(), BMIActivity.class);
                //openBMI.putExtra("Name", "Ana");
                startActivity(openBMI);

            }
        });

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent openCalendar = new Intent(getApplicationContext(), CalendarActivity.class);
                openCalendar.putExtra("passedUserById", getIntent().getIntExtra("passedUser", -1));
                startActivity(openCalendar);
            }
        });

        btnWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openWorkout = new Intent(getApplicationContext(), WorkoutActivity.class);
                startActivity(openWorkout);
            }
        });

        btnNutrition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openNutrition = new Intent(getApplicationContext(), NutritionActivity.class);
                startActivity(openNutrition);
            }
        });

        btnAllUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openUsersReport = new Intent(getApplicationContext(), ExercisesReportActivity.class);
                startActivity(openUsersReport);
            }
        });

        btnAllNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openNotesReport = new Intent(getApplicationContext(), NotesReportActivity.class);
                startActivity(openNotesReport);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MAPS, 1, "Open Google Maps");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == MAPS){
            Intent openGoogleMaps = new Intent(getApplicationContext(), GoogleMapsActivity.class);
            startActivity(openGoogleMaps);

            return true;
        }

        return false;
    }
}
