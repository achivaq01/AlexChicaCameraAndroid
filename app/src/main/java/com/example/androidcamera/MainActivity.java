package com.example.androidcamera;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static int RC_PHOTO_PICKER = 0;
    private ActivityResultLauncher<Intent> someActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);

        View view = new View(this);

        someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        Uri uri = data.getData();
                        ImageView imageView = findViewById(R.id.img);
                        imageView.setImageURI(uri);
                    }
                }
            });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSomeActivityForResult(view);
            }
        });
    }

    public void openSomeActivityForResult(View view) {
        someActivityResultLauncher.launch(new Intent(Intent.ACTION_GET_CONTENT)
                .setType("image/png")
                .putExtra(Intent.EXTRA_LOCAL_ONLY, true));
    }
}
