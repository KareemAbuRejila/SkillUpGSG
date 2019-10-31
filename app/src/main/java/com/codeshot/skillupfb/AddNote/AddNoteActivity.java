package com.codeshot.skillupfb.AddNote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codeshot.skillupfb.DB.DBConnection;
import com.codeshot.skillupfb.Home.MainActivity;
import com.codeshot.skillupfb.MAPs.MapsActivity;
import com.codeshot.skillupfb.Model.Note;
import com.codeshot.skillupfb.R;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddNoteActivity extends AppCompatActivity {
    //View
    private EditText edtTitle,edtDes;
    private TextView tvLocation,tvTime;
    private CircleImageView imgAdd;
    //GetImageFromCamera
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_LOCATION_CAPTURE=2;
    private Bitmap mImageBitmap;
    private String mCurrentPhotoPath;
    private Note note;
    private boolean havePhoto=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        initializationsView();

        Button btnSave=findViewById(R.id.btnSave);
        tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddNoteActivity.this, MapsActivity.class));
            }
        });
        tvTime.setText(getCurrentTime());

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (havePhoto)return;
                getCameraAndStoragePermission();
                dispatchTakePictureIntent();
            }
        });
        if (getIntent().getStringExtra("currentLatLong")!=null){
            String currentLatLong=getIntent().getStringExtra("currentLatLong").toString();
            tvLocation.setText(currentLatLong);
        }
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();

            }
        });
    }
    private void initializationsView(){
        //View
        edtTitle=findViewById(R.id.edtTitle);
        edtDes=findViewById(R.id.edtDescription);
        tvLocation=findViewById(R.id.tvLocation);
        tvTime=findViewById(R.id.tvTime);
        imgAdd=findViewById(R.id.imgAdd);
    }

    private void saveNote() {
        String title,time,location,des;
        title=edtTitle.getText().toString().trim();
        des=edtDes.getText().toString().trim();
        time=tvTime.getText().toString();
        location=tvLocation.getText().toString();
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(AddNoteActivity.this,"Enter Note Title",Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(location)) {
            Toast.makeText(AddNoteActivity.this,"Get Note Location",Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(des)) {
            Toast.makeText(AddNoteActivity.this,"Enter Note Description",Toast.LENGTH_SHORT).show();
            return;
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        mImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte imageInByte[] = stream.toByteArray();
        note=new Note(title,location,time,des);
        DBConnection dbConnection=new DBConnection(this);
        dbConnection.addNote(note);
        sendToMainActivity();
    }

    private void sendToMainActivity() {
        Intent mainIntent=new Intent(AddNoteActivity.this, MainActivity.class);
        Log.i("Note",note.toString());
        startActivity(mainIntent);
    }

    private void getCameraAndStoragePermission() {
        if (ContextCompat.checkSelfPermission(AddNoteActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(AddNoteActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
                &&ContextCompat.checkSelfPermission(AddNoteActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            super.onActivityResult(requestCode, resultCode, data);
            Bundle extras = data.getExtras();
            if (extras!=null){
                mImageBitmap = (Bitmap) extras.get("data");
                // imageBitmap = (Bitmap) data.getExtras().get("data");
                imgAdd.setImageBitmap(mImageBitmap);
                havePhoto=true;
            }



        }
        if (requestCode == REQUEST_LOCATION_CAPTURE && resultCode == RESULT_OK) {
            super.onActivityResult(requestCode, resultCode, data);
            Bundle extras = data.getExtras();
            String location = (String) extras.get("data");
            tvLocation.setText(location);
        }
    }

    private String getCurrentTime() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        String saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        String saveCurrentTime = currentTime.format(calendar.getTime());

        return saveCurrentDate+" "+saveCurrentTime;
    }

}
