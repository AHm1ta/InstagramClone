package com.example.instagramclone.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instagramclone.Adapter.PhotosAdapter;
import com.example.instagramclone.EditProfileActivity;
import com.example.instagramclone.HomeActivity;
import com.example.instagramclone.MainActivity;
import com.example.instagramclone.Model.Post;
import com.example.instagramclone.Model.User;
import com.example.instagramclone.PostActivity;
import com.example.instagramclone.R;
import com.example.instagramclone.RegisterActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ProfileFragment extends Fragment {
    FirebaseUser firebaseUser;
    ImageView option_log, profile_pic;
    TextView username;
    Button edit_pro;
    ImageButton post, save_post;
    String profileid;
    RecyclerView recyclerView;
    PhotosAdapter photosAdapter;
    List<Post> postList;


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        SharedPreferences preferences = getContext().getSharedPreferences("PREPS", Context.MODE_PRIVATE);
        profileid = preferences.getString("profileid", "none");


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        option_log = view.findViewById(R.id.option_log);
        profile_pic = view.findViewById(R.id.pro_pic);
        edit_pro = view.findViewById(R.id.edit_profile);
        username = view.findViewById(R.id.username_pro);
        post = view.findViewById(R.id.post_id);
        save_post = view.findViewById(R.id.save_post);
        recyclerView=view.findViewById(R.id.view1);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(layoutManager);
        postList= new ArrayList<>();
        photosAdapter= new PhotosAdapter(getContext(),postList);
        recyclerView.setAdapter(photosAdapter);

        userInfo();
        myPhoto();
        if (profileid.equals(firebaseUser.getUid())) {
            edit_pro.setText("Edit Profile");
        }else {
            checkFollow();
            save_post.setVisibility(View.GONE);
        }

        option_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();
            }
        });

        edit_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String btn = edit_pro.getText().toString();
                if (btn.equals("Edit Profile")) {
                    //go to profile
                    startActivity(new Intent(getContext(), EditProfileActivity.class));
                } else if (btn.equals("Follow")) {
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                            .child("Following").child(profileid).setValue(true);
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(profileid)
                            .child("Followers").child(firebaseUser.getUid()).setValue(true);
                } else if (btn.equals("Following")) {
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                            .child("Following").child(profileid).removeValue();
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(profileid)
                            .child("Followers").child(firebaseUser.getUid()).removeValue();
                }
            }
        });
        return view;
    }

    private void userInfo() {
        if (profileid != null){
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(profileid);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   if (firebaseUser!=null){
                        User user=dataSnapshot.getValue(User.class);
                        Glide.with(getContext()).load(user.getImageurl()).into(profile_pic);
                        username.setText(user.getUsername());
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    private void checkFollow() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid()).child("Following");
        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(profileid).exists()) {
                    edit_pro.setText("Following");
                } else {
                    edit_pro.setText("Follow");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void myPhoto() {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        Post post= snapshot.getValue(Post.class);
                        if (post.getPublisher().equals(profileid)){
                            postList.add(post);
                        }
                    }
                    Collections.reverse(postList);
                    photosAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
    }
}

