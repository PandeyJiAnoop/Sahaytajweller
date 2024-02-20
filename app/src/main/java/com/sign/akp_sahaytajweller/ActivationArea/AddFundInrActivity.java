package com.sign.akp_sahaytajweller.ActivationArea;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.sign.akp_sahaytajweller.Basic.Api_Urls;
import com.sign.akp_sahaytajweller.DashboardActivity;
import com.sign.akp_sahaytajweller.MyAccount.MyVolleySingleton;
import com.sign.akp_sahaytajweller.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddFundInrActivity extends AppCompatActivity {
    ImageView imgBack,imgAddInr;
    ProgressDialog progressDialog;
    String UserId = "", encodedImage="";
    TextView tvReceiptPic;
    private static final int STORAGE_REQUEST_CODE = 400;
    private static final int IMAGE_PICK_GALLERY_CODE = 1000;
    String storagePermission[];

    EditText etAmount, et_Transno,et_UtrNo;
    Button btnAddFund;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fund_inr);
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference", MODE_PRIVATE);
        UserId = sharedPreferences.getString("U_id", "");
        initViews();
    }

    private void initViews() {
        progressDialog = new ProgressDialog(this);

        imgBack = findViewById(R.id.imgBack);
        imgAddInr = findViewById(R.id.imgAddInr);
        etAmount = findViewById(R.id.etAmount);
        et_Transno = findViewById(R.id.et_Transno);
        et_UtrNo = findViewById(R.id.et_UtrNo);
        tvReceiptPic = findViewById(R.id.tvReceiptPic);
        btnAddFund = findViewById(R.id.btnAddFund);

        //storage permission
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

//        Glide.with(AddFundInrActivity.this).load("http://sahaytajewellers.org/Images/INR.jpeg").into(imgAddInr);

        imgBack.setOnClickListener(v -> finish());

        tvReceiptPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkStoragePermission()) {
                    //storage permission not allowed, request it
                    requestStoragePermission();
                } else {
                    //permission allowed, take picture
                    pickGallery();
                }
            }
        });

        btnAddFund.setOnClickListener(v -> {
            if (etAmount.getText().toString().trim().isEmpty()){
                Toast.makeText(this, "Please Enter amount!", Toast.LENGTH_SHORT).show();
            } else if(et_Transno.getText().toString().trim().isEmpty()){
                Toast.makeText(this, "Please Enter transaction no!", Toast.LENGTH_SHORT).show();
            } else if(et_UtrNo.getText().toString().trim().isEmpty()){
                Toast.makeText(this, "Please Enter UTR No. !", Toast.LENGTH_SHORT).show();
            } else if(encodedImage.isEmpty()){
                Toast.makeText(this, "Please Upload Receipt!", Toast.LENGTH_SHORT).show();
            } else {
                addFundInr();
            }
        });
    }

    private void addFundInr() {
        progressDialog.show();
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserId", UserId);
            jsonObject.put("Amount", etAmount.getText().toString().trim());
            jsonObject.put("TransactionNo", et_Transno.getText().toString().trim());
            jsonObject.put("UtrNo", et_UtrNo.getText().toString().trim());
            jsonObject.put("ReceiptFile", encodedImage);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Api_Urls.AddFundinInr, jsonObject, response -> {
            Gson gson = new Gson();
            progressDialog.dismiss();
            Log.e("TAG", "profileUpdateRes: " + response);
            try {
                if (response.getBoolean("Status")) {
                    Toast.makeText(this, "Add Fund Successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                    finishAffinity();
                } else {
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }, error -> {
            progressDialog.dismiss();
            Log.e("TAG", "onErrorResponse: " + error.toString());
        });
        MyVolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, storagePermission, STORAGE_REQUEST_CODE);
    }

    private void pickGallery() {
        //intent to pick image from gallery
        Intent intent = new Intent(Intent.ACTION_PICK);
        //set intent type to image
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case STORAGE_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean writeStorageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (writeStorageAccepted) {
                        pickGallery();
                    } else {
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                //got image from gallery now crop it
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON) //enable image guid lines
                        .start(this);
            }
        }

        //get cropped image
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                assert result != null;
                Uri resultUri = result.getUri();//get image uri
                final InputStream imageStream;
                try {
                    imageStream = getContentResolver().openInputStream(resultUri);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                encodedImage = encodeImage(selectedImage);
                Log.e("TAG", "ImageBase64=" + encodedImage);
                tvReceiptPic.setText("Receipt Uploaded");

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                //if there is any error show it
                Exception error = result.getError();
                Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encImage;
    }
}