package com.example.wehelp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class myprofile extends Fragment {
    ImageView i1;
    OutputStream outputStream;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.myprofile, container, false);

        Button b1 = (Button) inflate.findViewById(R.id.MyProfileChangePic);
        i1 = inflate.findViewById(R.id.MyProfilePicView);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCamera();
            }
        });

        return inflate;


    }

    void startCamera() {
        Intent ic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(ic, 777);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 777) {
            Bundle b1 = data.getExtras();
            Bitmap bm1 = (Bitmap) b1.get("data");
            File path= Environment.getExternalStorageDirectory();
            File folder =new File(path.getAbsolutePath()+"/WeCare");
            folder.mkdir();
            File file =new File(folder,System.currentTimeMillis()+".jpg");
            try
            {
                outputStream = new FileOutputStream(file);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
            bm1.compress(Bitmap.CompressFormat.JPEG,100,outputStream);

            try
            {
                outputStream.flush();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            try
            {
                outputStream.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            // i1.setImageBitmap(bm1);

        }
    }
}
