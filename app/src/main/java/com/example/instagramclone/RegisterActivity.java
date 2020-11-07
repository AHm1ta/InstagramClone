package com.example.instagramclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.instagramclone.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    EditText username, email;
    TextInputEditText password;
    Button register;
    RadioGroup gender;

    FirebaseAuth auth;
    DatabaseReference reference;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username=findViewById(R.id.name);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        register=findViewById(R.id.btn_reg);
        gender=findViewById(R.id.gender_id);

        auth= FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               pd= new ProgressDialog(RegisterActivity.this);
               pd.setMessage("Please wait...");
               pd.show();

                String nameData= username.getText().toString().toLowerCase();
                String emailData= email.getText().toString();
                String passData= password.getText().toString();
                RadioButton check_btn=findViewById(gender.getCheckedRadioButtonId());
                String genderCheck=check_btn.getText().toString();

                if (TextUtils.isEmpty(nameData) || TextUtils.isEmpty(emailData) || TextUtils.isEmpty(passData) || TextUtils.isEmpty(genderCheck))
                {
                    Toast.makeText(RegisterActivity.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                }
                else if (passData.length()<6){
                    Toast.makeText(RegisterActivity.this,"Password must have 6 words", Toast.LENGTH_SHORT).show();
                }
                else {
                    Register(nameData,emailData,passData,genderCheck);
                }

            }
        });
    }

    public void Register(final String username, final String email, String password, final String gender){
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseuser = auth.getCurrentUser();
                            String userid= firebaseuser.getUid();

                            reference= FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

                            User user= new User();
                            user.setId(userid);
                            user.setUsername(username);
                            user.setEmail(email);
                            user.setGender(gender);
                            user.setImageurl("https://cdn1.vectorstock.com/i/1000x1000/23/70/default-avatar-profile-icon-vector-18942370.jpg");

//                            HashMap<String, Object> hashMap= new HashMap<>();
//                            hashMap.put("id", userid);
//                            hashMap.put("username", username);
//                            hashMap.put("email", email);
//                            hashMap.put("gender",gender);
//                            hashMap.put("imageurl", R.drawable.ic_profile);

                            reference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        pd.dismiss();
                                        Intent intent= new Intent(RegisterActivity.this, HomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }
                            });
                        }else {
                            pd.dismiss();
                            Toast.makeText(RegisterActivity.this,"You can't register with this email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}