package com.example.instagramapp2.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.instagramapp2.Model.Post;
import com.example.instagramapp2.R;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{
    private Context context;
    private List<Post> posts;

    public PostAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Post post = posts.get(i);
        viewHolder.bind(post);

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tUsername, tDescription;
        ImageView ivPostImage;
        CircleImageView ivUser ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tUsername = itemView.findViewById(R.id.tvusername);
            tDescription = itemView.findViewById(R.id.tv_description);
            ivPostImage = itemView.findViewById(R.id.iv_image);
            ivUser = itemView.findViewById(R.id.ivuser);
        }

        public void bind(Post post) {
            tUsername.setText(post.getUser().getUsername());
            ParseFile image = post.getImage();
            ParseUser user = post.getUser();
            Glide.with(context).load(image.getUrl()).into(ivPostImage);
             //.load(post.getParseUser("user").getParseFile("avatar").getUrl())
            if (user.getParseFile("image") != null ){
                Glide.with(context).load(user.getParseFile("image")).into(ivUser);
            }
            tDescription.setText(post.getDescription());

        }

    }
    public void clear(){
        posts.clear();
        notifyDataSetChanged();
    }

    public void addTweets(List<Post> mpost){
        posts.addAll(mpost);
        notifyDataSetChanged();
    }
}
