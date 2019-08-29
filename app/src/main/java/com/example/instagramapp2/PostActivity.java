package com.example.instagramapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instagramapp2.Model.Post;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.io.FileOutputStream;

public class PostActivity extends AppCompatActivity {
    TextView tvpost;
    EditText et_Desc;
    ImageView iv_picture,Back,takepicture;
    ProgressBar progressBar;
    Toolbar toolbar;
    byte[] image = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        et_Desc = findViewById(R.id.et_description);
        iv_picture = findViewById(R.id.picture);
        progressBar = findViewById(R.id.prog);
        tvpost = findViewById(R.id.post);
        takepicture = findViewById(R.id.picture);
        Back = findViewById(R.id.iv_back);
        progressBar.setVisibility(View.INVISIBLE);

        byte[] image = getIntent().getByteArrayExtra("image");
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        final File saveImage = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "photo.jpg");
        try {
            FileOutputStream outputStream = new FileOutputStream(saveImage.getPath());
            outputStream.write(image);
            outputStream.close();
            // Create a bitmap
            //Bitmap result = BitmapFactory.decodeByteArray(array, 0, array.length);
            Bitmap result = BitmapFactory.decodeFile(saveImage.getAbsolutePath());
            iv_picture.setImageBitmap(result);

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        tvpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = et_Desc.getText().toString();
                if (description.isEmpty()){
                    Toast.makeText(PostActivity.this, "Enter a description", Toast.LENGTH_SHORT).show();

                }else {
                    ParseUser user = ParseUser.getCurrentUser();

                    savePost(description,user,saveImage);
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    private void savePost (String description, ParseUser parseUser, File photoFile){
        Post post = new Post();
        post.setDescription(description);
        post.setUser(parseUser);
        post.setImage(new ParseFile(photoFile));
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Toast.makeText(PostActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    return;
                }
                et_Desc.setText("");
                iv_picture.setImageResource(0);
                Intent intent = new Intent(PostActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

}
