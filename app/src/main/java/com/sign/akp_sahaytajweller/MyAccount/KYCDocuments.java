package com.sign.akp_sahaytajweller.MyAccount;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.sign.akp_sahaytajweller.Basic.Api_Urls;
import com.sign.akp_sahaytajweller.DashboardActivity;
import com.sign.akp_sahaytajweller.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;



public class KYCDocuments extends AppCompatActivity implements View.OnClickListener {
    ImageView imgBack;
    ProgressDialog progressDialog;
    String UserId = "", panPic = "", adharPic = "",AadharBackDoc = "", profilePic = "", passbookPic;
    EditText etPanNo, et_AdharNum, et_AccName, et_AccNo, et_ifscCode, et_BankName, et_BranchName;
    TextView tvPanPic, tvAdharPic,tvAadharBackDoc, tvProfile, tvCheckPass;
    boolean isPan, isAdhar,isAadharBackDoc, isProfile, isPassbook;
    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 400;
    private static final int IMAGE_PICK_GALLERY_CODE = 1000;
    private static final int IMAGE_PICK_CAMERA_CODE = 2001;

    String cameraPermission[];
    String storagePermission[];

    Uri image_uri;

    Button btnUpdateKyc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_k_y_c_documents);
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        UserId= sharedPreferences.getString("U_id", "");

        initViews();
    }

    private void initViews() {
        progressDialog = new ProgressDialog(this);

        imgBack = findViewById(R.id.imgBack);
        etPanNo = findViewById(R.id.etPanNo);
        tvPanPic = findViewById(R.id.tvPanPic);
        et_AdharNum = findViewById(R.id.et_AdharNum);
        tvAdharPic = findViewById(R.id.tvAdharPic);
        tvAadharBackDoc = findViewById(R.id.tvAadharBackDoc);
        tvProfile = findViewById(R.id.tvProfile);
        et_AccName = findViewById(R.id.et_AccName);
        et_AccNo = findViewById(R.id.et_AccNo);
        et_ifscCode = findViewById(R.id.et_ifscCode);
        et_BankName = findViewById(R.id.et_BankName);
        et_BranchName = findViewById(R.id.et_BranchName);
        tvCheckPass = findViewById(R.id.tvCheckPass);
        btnUpdateKyc = findViewById(R.id.btnUpdateKyc);

        imgBack.setOnClickListener(v -> finish());

        tvPanPic.setOnClickListener(this);
        tvAdharPic.setOnClickListener(this);
        tvAadharBackDoc.setOnClickListener(this);
        tvProfile.setOnClickListener(this);
        tvCheckPass.setOnClickListener(this);
        btnUpdateKyc.setOnClickListener(this);

        getKYCDetails();
    }

    private void getKYCDetails() {
        progressDialog.show();
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserId", UserId);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Api_Urls.KYCView, jsonObject, response -> {
            Gson gson = new Gson();
            progressDialog.dismiss();
            Log.e("TAG", "profileResponse: " + response);
            try {
                if (response.getBoolean("Status")) {
                    JSONArray jsonArray = response.getJSONArray("Response");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    if (!jsonObject1.getString("AadharCard").equals("null")) {
                        et_AdharNum.setText(jsonObject1.getString("AadharCard"));
                        et_AdharNum.setFocusable(false);
                        et_AdharNum.setEnabled(false);
                    }
                    if (!jsonObject1.getString("Pancard").equals("null")) {
                        etPanNo.setText(jsonObject1.getString("Pancard"));
                        etPanNo.setFocusable(false);
                        etPanNo.setEnabled(false);
                    }
                    if (!jsonObject1.getString("AccNumber").equals("null")) {
                        et_AccNo.setText(jsonObject1.getString("AccNumber"));
                        et_AccNo.setFocusable(false);
                        et_AccNo.setEnabled(false);
                    }
                    if (!jsonObject1.getString("AccName").equals("null")) {
                        et_AccName.setText(jsonObject1.getString("AccName"));
                        et_AccName.setFocusable(false);
                        et_AccName.setEnabled(false);
                    }
                    if (!jsonObject1.getString("BankName").isEmpty() || !jsonObject1.getString("BankName").equals("null")) {
                        et_BankName.setText(jsonObject1.getString("BankName"));
                        et_BankName.setFocusable(false);
                        et_BankName.setEnabled(false);
                    }
                    if (!jsonObject1.getString("IFSCcode").isEmpty() || !jsonObject1.getString("IFSCcode").equals("null")) {
                        et_ifscCode.setText(jsonObject1.getString("IFSCcode"));
                        et_ifscCode.setFocusable(false);
                        et_ifscCode.setEnabled(false);
                    }
                    if (!jsonObject1.getString("BranchName").isEmpty() || !jsonObject1.getString("BranchName").equals("null")) {
                        et_BranchName.setText(jsonObject1.getString("BranchName"));
                        et_BranchName.setFocusable(false);
                        et_BranchName.setEnabled(false);
                    }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvPanPic:
                isPan = true;
                isAdhar = false;
                isAadharBackDoc = false;
                isProfile = false;
                isPassbook = false;
                showImageImportDialog();
                break;

            case R.id.tvAdharPic:
                isPan = false;
                isAdhar = true;
                isAadharBackDoc = false;
                isProfile = false;
                isPassbook = false;
                showImageImportDialog();
                break;
            case R.id.tvAadharBackDoc:
                isPan = false;
                isAdhar = false;
                isAadharBackDoc = true;
                isProfile = false;
                isPassbook = false;
                showImageImportDialog();
                break;
            case R.id.tvProfile:
                isPan = false;
                isAdhar = false;
                isAadharBackDoc = false;
                isProfile = true;
                isPassbook = false;

                showImageImportDialog();
                break;

            case R.id.tvCheckPass:
                isPan = false;
                isAdhar = false;
                isAadharBackDoc = false;
                isProfile = false;
                isPassbook = true;
                showImageImportDialog();
                break;

            case R.id.btnUpdateKyc:
                updateKYC();
                break;
        }
    }

    private void updateKYC() {
        progressDialog.show();
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserId", UserId);
            jsonObject.put("AadharDoc", adharPic);
            jsonObject.put("AadharBackDoc", AadharBackDoc);
            jsonObject.put("PanDoc", panPic);
            jsonObject.put("ProfilePic", profilePic);
            jsonObject.put("PassbookDoc", passbookPic);
            jsonObject.put("AadharNo", et_AdharNum.getText().toString().trim());
            jsonObject.put("PanNo", etPanNo.getText().toString().trim());
            jsonObject.put("AccName", et_AccName.getText().toString().trim());
            jsonObject.put("BankName", et_BankName.getText().toString().trim());
            jsonObject.put("BranchName", et_BranchName.getText().toString().trim());
            jsonObject.put("AccNumber", et_AccNo.getText().toString().trim());
            jsonObject.put("IfscCode", et_ifscCode.getText().toString().trim());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Api_Urls.KYCUpdate, jsonObject, response -> {
            Gson gson = new Gson();
            progressDialog.dismiss();
            Log.e("TAG", "kycUpdateRes: " + response);
            try {
                if (response.getBoolean("Status")) {
                    Toast.makeText(this, "KYC updated Successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                    finishAffinity();
                } else {
                    Toast.makeText(this, "KYC updated Failed!", Toast.LENGTH_SHORT).show();
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

    private void showImageImportDialog() {
        String[] items = {"Camera", "Gallery"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Select Image");
        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    if (!checkCameraPermission()) {
                        //camera permission not allowed, request it
                        requestCameraPermission();
                    } else {
                        //permission allowed, take picture
                        pickCamera();
                    }
                }

                if (which == 1) {
                    if (!checkStoragePermission()) {
                        //storage permission not allowed, request it
                        requestStoragePermission();
                    } else {
                        //permission allowed, take picture
                        pickGallery();
                    }
                }
            }
        });
        dialog.create().show();
    }

    private void pickCamera() {
        //intent to take image from camera, it will also be save to storage to get high quality image
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "NewPick"); //title of the picture
        values.put(MediaStore.Images.Media.DESCRIPTION, "abc"); //title of the picture
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }

    private void pickGallery() {
        //intent to pick image from gallery
        Intent intent = new Intent(Intent.ACTION_PICK);
        //set intent type to image
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);
    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, storagePermission, STORAGE_REQUEST_CODE);
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, cameraPermission, CAMERA_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);

        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted && writeStorageAccepted) {
                        pickCamera();
                    } else {
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

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

    //handle image result
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

            if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                //got image from camera now crop it
                CropImage.activity(image_uri)
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
                if (isPan) {
                    panPic = encodeImage(selectedImage);
                    Log.e("TAG", "panPic64=" + panPic);
                    tvPanPic.setText("PAN Uploaded");
                } else if (isAdhar) {
                    adharPic = encodeImage(selectedImage);
                    Log.e("TAG", "adharPic64=" + adharPic);
                    tvAdharPic.setText("Adhar Uploaded");
                }
                else if (isAadharBackDoc) {
                    AadharBackDoc = encodeImage(selectedImage);
                    Log.e("TAG", "adharPic64=" + adharPic);
                    tvAadharBackDoc.setText("Adhar Back Uploaded");
                }

                else if (isProfile) {
                    profilePic = encodeImage(selectedImage);
                    Log.e("TAG", "profilePic=" + profilePic);
                    tvProfile.setText("Profile Uploaded");
                } else if (isPassbook) {
                    passbookPic = encodeImage(selectedImage);
                    Log.e("TAG", "passbookPic=" + passbookPic);
                    tvCheckPass.setText("Passbook Uploaded");
                }

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
