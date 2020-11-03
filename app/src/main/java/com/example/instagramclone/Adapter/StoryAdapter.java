package com.example.instagramclone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramclone.R;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.Story_ViewHolder> {
    Context context;
    String data1[];
    int images[];

    public StoryAdapter(Context ct, String[] s1, int[] img) {
        this.context = ct;
        this.data1 = s1;
        this.images = img;
    }

    @NonNull
    @Override
    public StoryAdapter.Story_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.item_story_list,parent,false);
        return new Story_ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull StoryAdapter.Story_ViewHolder holder, int position) {
        holder.username.setText(data1[position]);
        holder.img.setImageResource(images[position]);


    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class Story_ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView username;
        public Story_ViewHolder(@NonNull View itemView) {
            super(itemView);

            img= itemView.findViewById(R.id.image);
            username=itemView.findViewById(R.id.username);
        }
    }
}
