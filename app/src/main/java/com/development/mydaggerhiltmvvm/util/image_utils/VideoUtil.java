package com.development.mydaggerhiltmvvm.util.image_utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class VideoUtil {
   static public MultipartBody.Part createMultipartAnyFile(
            String filename,
            Uri uri,
            String partName,
            Context context

    ) {
        File file = FileUtils.getFile(context, uri);

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.Companion.create(file,
                        MediaType.parse(Objects.requireNonNull(context.getContentResolver().getType(uri)))
                );

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);

    }
    /*public static String getFilePathFromURI(Context context, Uri contentUri) {
        //copy file and send new file path

        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        File copyFile = new File(wallpaperDirectory + File.separator + Calendar.getInstance()
                .getTimeInMillis()+".mp4");
        // create folder if not exists

        copy(context, contentUri, copyFile);
        Log.d("vPath--->",copyFile.getAbsolutePath());

        return copyFile.getAbsolutePath();

    }*/

    public static MultipartBody.Part createMultipartAnyFileNew(
            String filename,
            Uri uri,
            String partName,
            Context context
    ) {
//        File file = FileUtils.getFile(getActivity(), uri);

        // create RequestBody instance from file


        File file1 = new File(context.getCacheDir(), filename);
        OutputStream out = null;
        InputStream in = null;
        try {
            in = context.getContentResolver().openInputStream(uri);
            out = new FileOutputStream(file1);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        RequestBody requestFile =
                RequestBody.Companion.create(file1,
                        MediaType.parse(Objects.requireNonNull(context.getContentResolver().getType(uri)))
                );
        return MultipartBody.Part.createFormData(partName, file1.getName(), requestFile);

    }
}
