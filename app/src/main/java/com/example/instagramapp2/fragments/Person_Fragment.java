package com.example.instagramapp2.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.instagramapp2.LoginActivity;
import com.example.instagramapp2.MainActivity;
import com.example.instagramapp2.Model.Post;
import com.example.instagramapp2.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class Person_Fragment extends Fragment {
    private final String TAG = "Fragment_Compose";
    private final String APP_TAG = "Big";
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
    public String photoFileName = "photo.jpg";
    private File photoFile;
    TextView Logout,tUsername;
    CircleImageView image;
    Button btn_edit;
    Toolbar toolbar;
    Post post;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.person_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        Logout = view.findViewById(R.id.logout);
        image = view.findViewById(R.id.ivuser);
        tUsername = view.findViewById(R.id.tvusername);
        btn_edit = view.findViewById(R.id.btn_edit);

        tUsername.setText(ParseUser.getCurrentUser().getUsername());
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCamera();
            }
        });

        //post = new Post();
        //tUsername.setText(post.getUser().getUsername());

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
    private void launchCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = getPhotoFileUri(photoFileName);

        Uri fileProvider = FileProvider.getUriForFile(getContext(),"com.codepath.fileprovider",photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,fileProvider);
        if (intent.resolveActivity(getContext().getPackageManager()) != null ){
            startActivityForResult(intent,CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }

    }

    public File getPhotoFileUri(String fileName){
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),APP_TAG);

        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(APP_TAG,"failled to create Directory");
        }
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);
        return file;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                image.setImageBitmap(takenImage);
                saveImage(photoFile);
            } else { // Result was a failure
                Toast.makeText(getContext(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveImage(File photoFile) {
        final ParseFile file = new ParseFile(photoFile);
        file.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null){
                    Log.e(APP_TAG,"Error img"+e.getMessage());
                    e.printStackTrace();
                    return;
                }
                //ParseUser user = new ParseUser();
                ParseUser user = ParseUser.getCurrentUser();
                user.put("image",file);

            }
        });
    }


}
