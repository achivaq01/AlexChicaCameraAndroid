package com.example.androidcamera;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GalleryView extends AppCompatActivity {

    private List<File> imageFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        imageFiles = getImageFilesInDirectory(getFilesDir());

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ImageAdapter imageAdapter = new ImageAdapter(this, imageFiles);
        recyclerView.setAdapter(imageAdapter);
    }

    private List<File> getImageFilesInDirectory(File directory) {
        List<File> imageFiles = new ArrayList<>();

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File[] files = storageDir.listFiles();
        System.out.println(directory);
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && isImageFile(file)) {
                    System.out.println(file);
                    imageFiles.add(file);
                }
            }
        }

        return imageFiles;
    }

    private boolean isImageFile(File file) {
        String[] supportedExtensions = {".jpg", ".jpeg", ".png", ".gif", ".bmp"};

        for (String extension : supportedExtensions) {
            if (file.getName().toLowerCase().endsWith(extension)) {
                return true;
            }
        }

        return false;
    }

    private static class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

        private final Context context;
        private final List<File> imageFiles;

        public ImageAdapter(Context context, List<File> imageFiles) {
            this.context = context;
            this.imageFiles = imageFiles;
        }

        @NonNull
        @Override
        public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
            return new ImageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
            File imageFile = imageFiles.get(position);
            holder.imageView.setImageURI(Uri.fromFile(imageFile));

            holder.imageView.setOnClickListener(v -> onImageClicked(imageFile));
        }

        @Override
        public int getItemCount() {
            return imageFiles.size();
        }

        private void onImageClicked(File imageFile) {
            Toast.makeText(context, "Clicked: " + imageFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        }

        static class ImageViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            public ImageViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageView);
            }
        }
    }
}
