package com.example.instagramclone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instagramclone.Model.Post;
import com.example.instagramclone.R;

import java.util.List;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolderPH> {
    private Context context;
    private List<Post> postList;

    public PhotosAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public ViewHolderPH onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view= LayoutInflater.from(context).inflate(R.layout.store_post_item,parent,false);
        return new ViewHolderPH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPH holder, int position) {
        Post post=postList.get(position);
        Glide.with(context).load(post.getPostImage()).into(holder.post_image);

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class ViewHolderPH extends RecyclerView.ViewHolder{
        public ImageView post_image;

        public ViewHolderPH(@NonNull View itemView) {
            super(itemView);
            post_image=itemView.findViewById(R.id.post_pro_image);
        }
    }
}
