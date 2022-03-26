package com.example.phonekart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.phonekart.Adapter.UploadListAdapter;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import io.paperdb.Paper;

public class AdminAddPhoneActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;
//    private RecyclerView mUploadList;

    private List<String> fileNameList;
    private List<String> fileDoneList;

    private ArrayList<String> ImageList;

    private UploadListAdapter uploadListAdapter;

    private StorageReference mStorage;

    private String Image1;
    private Uri UImage1;

    String CategoryName, ProductName, ProductKeyward, storeCurruntDate, storeCurruntTime;
    String ColorT1 = "A", ColorT2 = "A", ColorT3 = "A", ColorT4 = "A", ColorT5 = "A", ColorT6 = "A", ColorT7 = "A", ColorT8 = "A", ColorT9 = "A", ColorT10 = "A",
            SnRT1 = "A", SnRT2 = "A", SnRT3 = "A", SnRT4 = "A", SnRT5 = "A", SnRT6 = "A", SnRT7 = "A", SnRT8 = "A", SnRT9 = "A", SnRT10 = "A";
    String Table_Name, Table_Detail, Table_Brand;
    EditText productName, productKeyeard;
    EditText Color1, Color2, Color3, Color4, Color5, Color6, Color7, Color8, Color9, Color10, SnR1, SnR2, SnR3, SnR4, SnR5, SnR6, SnR7, SnR8, SnR9, SnR10;
    EditText Table_N, Table_D, Table_B;
    ImageView picPhoto1;
    Button addProduct, AddPic;
    Bitmap BImageUri1;
    DatabaseReference ProductRef;
    String productRandomKey, downloadImageUri1;
    StorageReference ProductImageRef;
    int dd = 0, ii = 0;
    private Dialog LoadingBar;
    private String Chh;
    private static final int GallaryPic = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_phone);

        Chh = getIntent().getStringExtra("loik");

        mStorage = FirebaseStorage.getInstance().getReference();

        fileNameList = new ArrayList<>();
        fileDoneList = new ArrayList<>();

        ImageList = new ArrayList<String>();

        Paper.init(AdminAddPhoneActivity.this);

        productName = findViewById(R.id.ProductName);
        productKeyeard = findViewById(R.id.ProductKeyward);

        AddPic = findViewById(R.id.ButtonIm);

        picPhoto1 = findViewById(R.id.PicPhoto1);

        Table_N = findViewById(R.id.name_T_A);
        Table_D = findViewById(R.id.Detail_T_A);
        Table_B = findViewById(R.id.brand_T_A);

        Color1 = findViewById(R.id.ProductColor1);
        Color2 = findViewById(R.id.ProductColor2);
        Color3 = findViewById(R.id.ProductColor3);
        Color4 = findViewById(R.id.ProductColor4);
        Color5 = findViewById(R.id.ProductColor5);
        Color6 = findViewById(R.id.ProductColor6);
        Color7 = findViewById(R.id.ProductColor7);
        Color8 = findViewById(R.id.ProductColor8);
        Color9 = findViewById(R.id.ProductColor9);
        Color10 = findViewById(R.id.ProductColor10);

        SnR1 = findViewById(R.id.ProductSR1);
        SnR2 = findViewById(R.id.ProductSR2);
        SnR3 = findViewById(R.id.ProductSR3);
        SnR4 = findViewById(R.id.ProductSR4);
        SnR5 = findViewById(R.id.ProductSR5);
        SnR6 = findViewById(R.id.ProductSR6);
        SnR7 = findViewById(R.id.ProductSR7);
        SnR8 = findViewById(R.id.ProductSR8);
        SnR9 = findViewById(R.id.ProductSR9);
        SnR10 = findViewById(R.id.ProductSR10);

        LoadingBar = new Dialog(AdminAddPhoneActivity.this);
        LoadingBar.setContentView(R.layout.loading_dialog);
        LoadingBar.setCancelable(false);
        addProduct = findViewById(R.id.AddProduct);

        CategoryName = getIntent().getStringExtra("category");
        ProductImageRef = FirebaseStorage.getInstance().getReference().child("Product Image");
        ProductRef = FirebaseDatabase.getInstance().getReference().child("ProductsPhones");


        AddPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ActivityCompat.checkSelfPermission(AdminAddPhoneActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions(AdminAddPhoneActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                    return;
                }

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), RESULT_LOAD_IMAGE);

            }
        });

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProduct();
            }
        });

    }

    private void ValidateProduct() {

        try {

            ProductName = productName.getText().toString();
            ProductKeyward = productKeyeard.getText().toString();
            Table_Name = Table_N.getText().toString();
            Table_Detail = Table_D.getText().toString();
            Table_Brand = Table_B.getText().toString();

            ColorT1 = Color1.getText().toString();
            ColorT2 = Color2.getText().toString();
            ColorT3 = Color3.getText().toString();
            ColorT4 = Color4.getText().toString();
            ColorT5 = Color5.getText().toString();
            ColorT6 = Color6.getText().toString();
            ColorT7 = Color7.getText().toString();
            ColorT8 = Color8.getText().toString();
            ColorT9 = Color9.getText().toString();
            ColorT10 = Color10.getText().toString();

            SnRT1 = SnR1.getText().toString();
            SnRT2 = SnR2.getText().toString();
            SnRT3 = SnR3.getText().toString();
            SnRT4 = SnR4.getText().toString();
            SnRT5 = SnR5.getText().toString();
            SnRT6 = SnR6.getText().toString();
            SnRT7 = SnR7.getText().toString();
            SnRT8 = SnR8.getText().toString();
            SnRT9 = SnR9.getText().toString();
            SnRT10 = SnR10.getText().toString();

            if (TextUtils.isEmpty(ProductName)) {
                productName.setError("Please Enter");
            } else if (TextUtils.isEmpty(ProductKeyward)) {
                productKeyeard.setError("Please Enter");
            } else if (TextUtils.isEmpty(Table_Name)) {
                Table_N.setError("Please Enter");
            } else if (TextUtils.isEmpty(Table_Brand)) {
                Table_B.setError("Please Enter");
            } else if (TextUtils.isEmpty(Table_Detail)) {
                Table_D.setError("Please Enter");
            } else if (ColorT1.isEmpty()) {
                Color1.setError("Please Enter");
            } else if (SnRT1.isEmpty()) {
                SnR1.setError("Please Enter");
            } else if (ColorT2.isEmpty()) {
                Color2.setError("Please Enter");
            } else if (SnRT2.isEmpty()) {
                SnR2.setError("Please Enter");
            } else {

                if (ColorT3.isEmpty()) {
                    ColorT3 = "A";
                }
                if (ColorT4.isEmpty()) {
                    ColorT4 = "A";
                }
                if (ColorT5.isEmpty()) {
                    ColorT5 = "A";
                }
                if (ColorT6.isEmpty()) {
                    ColorT6 = "A";
                }
                if (ColorT7.isEmpty()) {
                    ColorT7 = "A";
                }
                if (ColorT8.isEmpty()) {
                    ColorT8 = "A";
                }
                if (ColorT9.isEmpty()) {
                    ColorT9 = "A";
                }
                if (ColorT10.isEmpty()) {
                    ColorT10 = "A";
                }
                if (SnRT3.isEmpty()) {
                    SnRT3 = "A";
                }
                if (SnRT4.isEmpty()) {
                    SnRT4 = "A";
                }
                if (SnRT5.isEmpty()) {
                    SnRT5 = "A";
                }
                if (SnRT6.isEmpty()) {
                    SnRT6 = "A";
                }
                if (SnRT7.isEmpty()) {
                    SnRT7 = "A";
                }
                if (SnRT8.isEmpty()) {
                    SnRT8 = "A";
                }
                if (SnRT9.isEmpty()) {
                    SnRT9 = "A";
                }
                if (SnRT10.isEmpty()) {
                    SnRT10 = "A";
                }

                LoadingBar.show();

                getON(ColorT1, ColorT2, ColorT3, ColorT4, ColorT5, ColorT6, ColorT7, ColorT8, ColorT9, ColorT10,
                        SnRT1, SnRT2, SnRT3, SnRT4, SnRT5, SnRT6, SnRT7, SnRT8, SnRT9, SnRT10);
            }

        }
        catch (Exception e){
            Toast.makeText(AdminAddPhoneActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void getON(String colorT1, String colorT2, String colorT3, String colorT4, String colorT5, String colorT6, String colorT7,
                       String colorT8, String colorT9, String colorT10, String snRT1, String snRT2, String snRT3, String snRT4,
                       String snRT5, String snRT6, String snRT7, String snRT8, String snRT9, String snRT10) {

        int o = 0;
        int n = 0;

        if (!colorT1.equals("A") && colorT2.equals("A") && colorT3.equals("A") && colorT4.equals("A") && colorT5.equals("A") &&
                colorT6.equals("A") && colorT7.equals("A") && colorT8.equals("A") && colorT9.equals("A") && colorT10.equals("A")) {

            o = 1;

        }
        if (!colorT1.equals("A") &&!colorT2.equals("A") && colorT3.equals("A") && colorT4.equals("A") && colorT5.equals("A") &&
                colorT6.equals("A") && colorT7.equals("A") && colorT8.equals("A") && colorT9.equals("A") && colorT10.equals("A")) {

            o = 2;

        }
        if (!colorT1.equals("A") && !colorT2.equals("A") && !colorT3.equals("A") && colorT4.equals("A") && colorT5.equals("A") &&
                colorT6.equals("A") && colorT7.equals("A") && colorT8.equals("A") && colorT9.equals("A") && colorT10.equals("A")) {

            o = 3;

        }
        if (!colorT1.equals("A") && !colorT2.equals("A") && !colorT3.equals("A") && !colorT4.equals("A") && colorT5.equals("A") &&
                colorT6.equals("A") && colorT7.equals("A") && colorT8.equals("A") && colorT9.equals("A") && colorT10.equals("A")) {

            o = 4;

        }
        if (!colorT1.equals("A") && !colorT2.equals("A") && !colorT3.equals("A") && !colorT4.equals("A") && !colorT5.equals("A") &&
                colorT6.equals("A") && colorT7.equals("A") && colorT8.equals("A") && colorT9.equals("A") && colorT10.equals("A")) {

            o = 5;

        }
        if (!colorT1.equals("A") && !colorT2.equals("A") && !colorT3.equals("A") && !colorT4.equals("A") && !colorT5.equals("A") &&
                !colorT6.equals("A") && colorT7.equals("A") && colorT8.equals("A") && colorT9.equals("A") && colorT10.equals("A")) {

            o = 6;

        } else if (!colorT1.equals("A") && !colorT2.equals("A") && !colorT3.equals("A") && !colorT4.equals("A") && !colorT5.equals("A") &&
                !colorT6.equals("A") && !colorT7.equals("A") && colorT8.equals("A") && colorT9.equals("A") && colorT10.equals("A")) {

            o = 7;

        }
        if (!colorT1.equals("A") && !colorT2.equals("A") && !colorT3.equals("A") && !colorT4.equals("A") && !colorT5.equals("A") &&
                !colorT6.equals("A") && !colorT7.equals("A") && !colorT8.equals("A") && colorT9.equals("A") && colorT10.equals("A")) {

            o = 8;

        }
        if (!colorT1.equals("A") && !colorT2.equals("A") && !colorT3.equals("A") && !colorT4.equals("A") && !colorT5.equals("A") &&
                !colorT6.equals("A") && !colorT7.equals("A") && !colorT8.equals("A") && !colorT9.equals("A") && colorT10.equals("A")) {

            o = 9;

        }
        if (!colorT1.equals("A") && !colorT2.equals("A") && !colorT3.equals("A") && !colorT4.equals("A") && !colorT5.equals("A") &&
                !colorT6.equals("A") && !colorT7.equals("A") && !colorT8.equals("A") && !colorT9.equals("A") && !colorT10.equals("A")) {

            o = 10;

        }


        if (!snRT1.equals("A") && snRT2.equals("A") && snRT3.equals("A") && snRT4.equals("A") && snRT5.equals("A") &&
                snRT6.equals("A") && snRT7.equals("A") && snRT8.equals("A") && snRT9.equals("A") && snRT10.equals("A")) {

            n = 1;

        }
        if (!snRT1.equals("A") && !snRT2.equals("A") && snRT3.equals("A") && snRT4.equals("A") && snRT5.equals("A") &&
                snRT6.equals("A") && snRT7.equals("A") && snRT8.equals("A") && snRT9.equals("A") && snRT10.equals("A")) {

            n = 2;

        }
        if (!snRT1.equals("A") && !snRT2.equals("A") && !snRT3.equals("A") && snRT4.equals("A") && snRT5.equals("A") &&
                snRT6.equals("A") && snRT7.equals("A") && snRT8.equals("A") && snRT9.equals("A") && snRT10.equals("A")) {

            n = 3;

        }
        if (!snRT1.equals("A") && !snRT2.equals("A") && !snRT3.equals("A") && !snRT4.equals("A") && snRT5.equals("A") &&
                snRT6.equals("A") && snRT7.equals("A") && snRT8.equals("A") && snRT9.equals("A") && snRT10.equals("A")) {

            n = 4;

        }
        if (!snRT1.equals("A") && !snRT2.equals("A") && !snRT3.equals("A") && !snRT4.equals("A") && !snRT5.equals("A") &&
                snRT6.equals("A") && snRT7.equals("A") && snRT8.equals("A") && snRT9.equals("A") && snRT10.equals("A")) {

            n = 5;

        }
        if (!snRT1.equals("A") && !snRT2.equals("A") && !snRT3.equals("A") && !snRT4.equals("A") && !snRT5.equals("A") &&
                !snRT6.equals("A") && snRT7.equals("A") && snRT8.equals("A") && snRT9.equals("A") && snRT10.equals("A")) {

            n = 6;

        }
        if (!snRT1.equals("A") && !snRT2.equals("A") && !snRT3.equals("A") && !snRT4.equals("A") && !snRT5.equals("A") &&
                !snRT6.equals("A") && !snRT7.equals("A") && snRT8.equals("A") && snRT9.equals("A") && snRT10.equals("A")) {

            n = 7;

        }
        if (!snRT1.equals("A") && !snRT2.equals("A") && !snRT3.equals("A") && !snRT4.equals("A") && !snRT5.equals("A") &&
                !snRT6.equals("A") && !snRT7.equals("A") && !snRT8.equals("A") && snRT9.equals("A") && snRT10.equals("A")) {

            n = 8;

        }
        if (!snRT1.equals("A") && !snRT2.equals("A") && !snRT3.equals("A") && !snRT4.equals("A") && !snRT5.equals("A") &&
                !snRT6.equals("A") && !snRT7.equals("A") && !snRT8.equals("A") && !snRT9.equals("A") && snRT10.equals("A")) {

            n = 9;

        }
        if (!snRT1.equals("A") && !snRT2.equals("A") && !snRT3.equals("A") && !snRT4.equals("A") && !snRT5.equals("A") &&
                !snRT6.equals("A") && !snRT7.equals("A") && !snRT8.equals("A") && !snRT9.equals("A") && !snRT10.equals("A")) {

            n = 10;

        }

        AddImage1(ColorT1, ColorT2, ColorT3, ColorT4, ColorT5, ColorT6, ColorT7, ColorT8, ColorT9, ColorT10,
                SnRT1, SnRT2, SnRT3, SnRT4, SnRT5, SnRT6, SnRT7, SnRT8, SnRT9, SnRT10, o, n);

    }

    private void AddImage1(String colorT1, String colorT2, String colorT3, String colorT4, String colorT5, String colorT6, String colorT7,
                           String colorT8, String colorT9, String colorT10, String snRT1, String snRT2, String snRT3, String snRT4, String snRT5,
                           String snRT6, String snRT7, String snRT8, String snRT9, String snRT10, int o, int n) {

        try{

            final StorageReference filePath = ProductImageRef.child(UImage1.getLastPathSegment().substring(0, 4) + UUID.randomUUID()
                    .toString().substring(0,4) + ".jpg");

            final UploadTask uploadTask = filePath.putFile(UImage1);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    String massage = e.toString();
                    Toast.makeText(AdminAddPhoneActivity.this,"Error! " + massage,Toast.LENGTH_LONG).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                LoadingBar.dismiss();
                                throw task.getException();
                            }

                            downloadImageUri1 = filePath.getDownloadUrl().toString();
                            return filePath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if(task.isSuccessful()){

                                String i1 = task.getResult().toString();

                                SaveProductInfo(colorT1, colorT2, colorT3, colorT4, colorT5, colorT6, colorT7, colorT8, colorT9, colorT10, snRT1,
                                          snRT2, snRT3, snRT4, snRT5, snRT6, snRT7, snRT8, snRT9, snRT10, o, n, i1);
                            }
                        }
                    });
                }
            });

        }catch(Exception e){
            Toast.makeText(AdminAddPhoneActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void SaveProductInfo(String colorT1, String colorT2, String colorT3, String colorT4, String colorT5, String colorT6, String colorT7,
                                 String colorT8, String colorT9, String colorT10, String snRT1, String snRT2, String snRT3, String snRT4,
                                 String snRT5, String snRT6, String snRT7, String snRT8, String snRT9, String snRT10, int o, int n,
                                 String image1) {

        try {

            Calendar calendar = Calendar.getInstance();

            SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
            storeCurruntDate = currentDate.format(calendar.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat(" hh:mm:ss a");
            storeCurruntTime = currentTime.format(calendar.getTime());

            productRandomKey = storeCurruntDate + storeCurruntTime;

            String UID = UUID.randomUUID().toString().substring(0, 35);

            HashMap<String, Object> productMap = new HashMap<>();

            productMap.put("Pid", productRandomKey);
            productMap.put("Date", storeCurruntDate);
            productMap.put("Time", storeCurruntTime);
            productMap.put("TName", Table_Name);
            productMap.put("TDetail", Table_Detail);
            productMap.put("TBrand", Table_Brand);
            productMap.put("SearchP", UID);
            productMap.put("Keyward", ProductKeyward);
            productMap.put("Color1", colorT1);
            productMap.put("Color2", colorT2);
            productMap.put("Color3", colorT3);
            productMap.put("Color4", colorT4);
            productMap.put("Color5", colorT5);
            productMap.put("Color6", colorT6);
            productMap.put("Color7", colorT7);
            productMap.put("Color8", colorT8);
            productMap.put("Color9", colorT9);
            productMap.put("Color10", colorT10);
            productMap.put("SnR1", snRT1);
            productMap.put("SnR2", snRT2);
            productMap.put("SnR3", snRT3);
            productMap.put("SnR4", snRT4);
            productMap.put("SnR5", snRT5);
            productMap.put("SnR6", snRT6);
            productMap.put("SnR7", snRT7);
            productMap.put("SnR8", snRT8);
            productMap.put("SnR9", snRT9);
            productMap.put("SnR10", snRT10);
            productMap.put("NOCo", String.valueOf(o));
            productMap.put("NOSnR", String.valueOf(n));
            productMap.put("Image1", image1);
            productMap.put("ProductName", ProductName);

            ProductRef.child(productRandomKey).updateChildren(productMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent i = new Intent(AdminAddPhoneActivity.this, AdminMainActivity.class);
                                startActivity(i);
                                LoadingBar.dismiss();
                                Toast.makeText(AdminAddPhoneActivity.this, "Product is added successfully.......", Toast.LENGTH_LONG).show();
                            } else {
                                String massage = task.getException().toString();
                                Toast.makeText(AdminAddPhoneActivity.this, "Error...." + massage, Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        }
        catch (Exception e){
            Toast.makeText(AdminAddPhoneActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void OpenGallary() {
        dd++;
        Intent gallaryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallaryIntent, GallaryPic);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK){

            if(data.getClipData() != null){
                Toast.makeText(AdminAddPhoneActivity.this, "Please Select a Single File", Toast.LENGTH_SHORT).show();
//                int totalItemsSelected = data.getClipData().getItemCount();
//
//                List<Bitmap> bitmaps = new ArrayList<>();
//                ClipData clipData = data.getClipData();
//
//                if(clipData != null) {
//                    for (int i = 0; i < clipData.getItemCount(); i++) {
//                        Uri imageUri = clipData.getItemAt(i).getUri();
//
//                        try {
//                            InputStream is = getContentResolver().openInputStream(imageUri);
//                            Bitmap bitmap = BitmapFactory.decodeStream(is);
//                            bitmaps.add(bitmap);
//                        } catch (FileNotFoundException e) {
//                            Toast.makeText(AdminAddPhoneActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                }else{
//
//                    Uri imageUri = data.getData();
//
//                    try {
//                        InputStream is = getContentResolver().openInputStream(imageUri);
//                        Bitmap bitmap = BitmapFactory.decodeStream(is);
//                        bitmaps.add(bitmap);
//                    } catch (FileNotFoundException e) {
//                        Toast.makeText(AdminAddPhoneActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//
//
//                int o = 0;
//
//                for (Bitmap bm : bitmaps) {
//                    o++;
//                    addProduct.setVisibility(View.VISIBLE);
//                    if(o == 1){
//                        UImage1 = getImageUri(AdminAddPhoneActivity.this, bm);
//                        Toast.makeText(this, UImage1.toString(), Toast.LENGTH_SHORT).show();
//                        picPhoto1.setImageBitmap(bm);
//                    }
//
//                }
            } else if (data.getData() != null){

                UImage1 = data.getData();
                picPhoto1.setImageURI(UImage1);
                addProduct.setVisibility(View.VISIBLE);

            }
        }
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "IMG_" + Calendar.getInstance().getTime(), null);
        return  Uri.parse(path);
    }

    public String getImageString(Bitmap inImage){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        byte[] b = bytes.toByteArray();
        String path = Base64.encodeToString(b, Base64.DEFAULT);
        return path;
    }

}