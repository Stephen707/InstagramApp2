package com.example.instagramapp2.fragments;

import android.util.Log;

import com.example.instagramapp2.Model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class Profile_Fragment extends Home_Fragment {
    @Override
    protected void queryPost() {
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.addDescendingOrder(Post.CREATED_AT);
        postQuery.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e!= null){
                    Log.e(TAG,"Error with query");
                    e.printStackTrace();
                    return;
                }
                post.addAll(objects);
                adapter.notifyDataSetChanged();
                for (int i = 0; i < objects.size(); i++){
                    Post post = objects.get(i);
                    Log.d(TAG,"Post: " + post.getDescription() + "username: " +post.getUser().getUsername());
                }

            }
        });
    }
}
