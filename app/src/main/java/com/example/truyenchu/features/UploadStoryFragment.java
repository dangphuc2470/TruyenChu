package com.example.truyenchu.features;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.truyenchu.R;
import com.example.truyenchu._class.ChapterClass;
import com.example.truyenchu._class.StoryClass;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UploadStoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadStoryFragment extends Fragment implements StoryCountListener
{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int storyCount;
    private ImageView imageView;

    @Override
    public void onStoryCountReceived(int storyCount)
    {
        this.storyCount = storyCount;
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddStoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UploadStoryFragment newInstance(String param1, String param2)
    {
        UploadStoryFragment fragment = new UploadStoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public UploadStoryFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload_story, container, false);

        TextInputEditText et_name_upload = view.findViewById(R.id.name_upload);
        TextInputEditText et_genres_upload = view.findViewById(R.id.genres_upload);
        SwitchMaterial sw_final = view.findViewById(R.id.switch_final);
        Button bt_chooseImage = view.findViewById(R.id.bt_chooseImage_upload);
        TextInputEditText et_description = view.findViewById(R.id.et_description);
        TextInputEditText et_chapter_content = view.findViewById(R.id.chapter_content_upload);
        Button bt_Upload = view.findViewById(R.id.btUpload_upload);
        Button bt_Draft = view.findViewById(R.id.btDraft_upload);
        imageView = view.findViewById(R.id.iv_cover1);

        bt_chooseImage.setOnClickListener(v ->
                openFileChooser());

        bt_Upload.setOnClickListener(v ->
        {
            DatabaseReference database = FirebaseDatabase.getInstance("https://truyenchu-89dd1-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
            DatabaseReference storyRef = database.child("stories");
            // Read from the database
            storyRef.child("storyCount").addValueEventListener(new ValueEventListener()
            {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    Integer value = dataSnapshot.getValue(Integer.class);
                    if (value == null)
                    {
                        storyRef.child("storyCount").setValue(0);
                        onStoryCountReceived(0);
                    } else
                        onStoryCountReceived(value);
                    Log.d("DB", "Number of existing stories: " + value);
                }

                @Override
                public void onCancelled(DatabaseError error)
                {
                    // Failed to read value
                    Log.w("DB", "Failed to read value.", error.toException());
                }
            });

            int id;
            String name;
            String author;
            String status;
            String time;
            String description;
            String content;
            int numberOfChapter;
            List<String> genres;
            int views;


            id = storyCount + 1;
            name = et_name_upload.getText().toString();
            author = getString(R.string.user_name);
            if (sw_final.isChecked())
                status = "Full";
            else
                status = "Đang cập nhật";
            time = LocalDate.now().toString();
            numberOfChapter = 1;
            String[] splittedStrings = Objects.requireNonNull(et_genres_upload.getText()).toString().split(",\\s*");
            genres = new ArrayList<>(Arrays.asList(splittedStrings));
            views = 0;
            description = et_description.getText().toString();
            content = et_chapter_content.getText().toString();

            StoryClass story = new StoryClass(id, name, time, author, status, description, numberOfChapter, genres, views);
            storyRef.child("story_" + id).setValue(story, (databaseError, databaseReference) ->
            {
                //Upload chapter
                storyRef.child("story_" + id).child("chapters").child("chapter_" + id + "_" + numberOfChapter).setValue(new ChapterClass(id + "_" + numberOfChapter, content));
                //Handle upload Story
                uploadImageToFirebase("story_" + id, imageUriStringFB ->
                {
                    storyRef.child("story_" + id).child("uri").setValue(imageUriStringFB);
                });
                if (databaseError != null)
                {
                    Log.i("DB", "Data could not be saved " + databaseError.getMessage());
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                } else
                {
                    storyRef.child("storyCount").setValue(storyCount + 1);
                    Log.i("DB", "Upload story success!");
                    Gson gson = new Gson();
                    String storyJson = gson.toJson(story); // Convert the object to JSON
                    Log.i("DB", "Data pushed: " + storyJson);
                    Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();

                }
            });


        });
        return view;
    }

    private void openFileChooser()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null)
        {
            imageUri = data.getData();
            imageView.setImageURI(imageUri); // Set the selected image to the ImageView
        }
    }

    private void uploadImageToFirebase(String imageName, ImageUploadCallback callback)
    {
        if (imageUri != null)
        {
            // Initialize Firebase Storage
            FirebaseStorage storage = FirebaseStorage.getInstance("gs://truyenchu-89dd1.appspot.com");
            StorageReference storageRef = storage.getReference().child("images");

            // Create a reference to the specific image file name
            StorageReference imageRef = storageRef.child(imageName + ".jpg");
            // Upload the image to Firebase Storage
            UploadTask uploadTask = imageRef.putFile(imageUri);

            // Monitor the upload task
            uploadTask.addOnSuccessListener(taskSnapshot ->
            {
                Log.i("DB", "uploadImageToFirebase: Success");
                imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String imageUriStringFB = uri.toString();
                    Log.i("DB", "Image URL: " + imageUriStringFB);
                    if (callback != null) {
                        callback.onImageUploaded(imageUriStringFB);
                    }
                });
            }).addOnFailureListener(exception ->
            {
                // Handle unsuccessful uploads
            });
        }
    }
}

