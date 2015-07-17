package com.emiketic.logodesigner.preview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.emiketic.logodesigner.R;

import org.apache.http.protocol.HTTP;

import java.io.File;
import java.io.FileOutputStream;

public class PreviewActivity extends AppCompatActivity
        implements PreviewActivityFragment.OnExportSelectedListener {

    private static final String LOG_TAG ="PreviewActivity";

    public static final String ARG_NAME_LOGO = "name";
    public static final String ARG_BYTE_LOGO = "byte";

    byte[] mData;
    String mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        mData = getIntent().getByteArrayExtra(ARG_BYTE_LOGO);
        mName = getIntent().getStringExtra(ARG_NAME_LOGO);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            PreviewActivityFragment fragment = PreviewActivityFragment.newInstance(mData);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment).commit();
        }
    }

    @Override
    public void onExportSelected() {
        String filename = mName + ".png";
        File file;
        if (isExternalStorageWritable()) {
            file = new File(getDocumentStorageDir("Logo designer"), filename);
        } else {
            file = new File(this.getFilesDir(), filename);
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(mData);
            outputStream.close();
            startActivity(createEmailIntent(Uri.fromFile(file)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Intent createEmailIntent(Uri uri) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        // The intent does not have a URI, so declare the "text/plain" MIME type
        emailIntent.setType(HTTP.PLAIN_TEXT_TYPE);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Logo design");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Here attached the logo created with ExentriqLogoDesigner android app. Thank you.");
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);

        return emailIntent;
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public File getDocumentStorageDir(String name) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), name);
        if (!file.mkdirs()) {
            Log.e("Preview Activity", "Directory exists or not created");
        }
        return file;
    }
}
