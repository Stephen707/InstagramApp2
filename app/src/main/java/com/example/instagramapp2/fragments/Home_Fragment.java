package com.example.instagramapp2.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.instagramapp2.Model.Post;
import com.example.instagramapp2.R;
import com.example.instagramapp2.adapter.PostAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class Home_Fragment extends Fragment {
    public static final String TAG = "HomeFragment";
    RecyclerView recyclerView;
    protected List<Post> post;
    protected PostAdapter adapter;
    Toolbar toolbar;
    SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_Posts);
        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        swipeRefreshLayout = view.findViewById(R.id.swipe);

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryPost();
            }
        });


        // Create the data source
        post = new ArrayList<>();
        // Create the adapter
        adapter = new PostAdapter(getContext(),post);
        // set the adapter on the recyclerView
        recyclerView.setAdapter(adapter);
        // set the layout manager on the recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        queryPost();
    }
    protected void queryPost() {
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.addDescendingOrder(Post.CREATED_AT);
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e!= null){
                    Log.e(TAG,"Error with query");
                    e.printStackTrace();
                    return;
                }
                post.addAll(objects);
                adapter.clear();
                adapter.addTweets(objects);
                swipeRefreshLayout.setRefreshing(false);
                for (int i = 0; i < objects.size(); i++){
                    Post post = objects.get(i);
                    Log.d(TAG,"Post: " + post.getDescription() + "username: " +post.getUser().getUsername());
                }

            }
        });
    }
}
