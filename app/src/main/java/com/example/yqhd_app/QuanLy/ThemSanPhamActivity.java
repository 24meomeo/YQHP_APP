package com.example.yqhd_app.QuanLy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yqhd_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.UUID;

public class ThemSanPhamActivity extends AppCompatActivity {
    FirebaseFirestore firestore;
    FirebaseStorage storage;
    StorageReference storageReference;


    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//    String userID = firebaseAuth.getCurrentUser().getUid();

    ImageView mimgSP;
    ImageButton mimgbtSPChange;
    EditText medttenSP, medtloaiSP, medtgiaSP, medtMoTaSP;
    Button mbtnThem, mbtnHuy;
    String ten, loai, gia, mota;
    Long gi;
    private Uri imageUri = null;
    private String imageURL; //đường dẫn ảnh
    private HashMap<String, Object> map;
//    Button mbtnXacNhan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_san_pham);

        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mimgSP = findViewById(R.id.imgSP);
        mimgbtSPChange = findViewById(R.id.imgbtSPChange);
        medttenSP = findViewById(R.id.edtTenSP);
        medtloaiSP = findViewById(R.id.edtLoaiSP);
        medtgiaSP = findViewById(R.id.edtGiaSP);
        medtMoTaSP = findViewById(R.id.edtMoTa);
        mbtnThem = findViewById(R.id.btnCapNhat);
        mbtnHuy = findViewById(R.id.btnHuy);

        mimgbtSPChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                CropImage.activity()
//                        .start(getContext(), addProductFragment.this);
//                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(addProductFragment.this.getActivity());
                Intent openGalleryintent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryintent, 1000);
            }
        });

        medttenSP.setText("Bánh xanh ngọc");
        medtgiaSP.setText("7072003");
        medtloaiSP.setText("Bánh kem");
//        medtSoLuongSP.setText("30");
        medtMoTaSP.setText("kaka");
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String id = "a";
        if (bundle != null) {
            id = bundle.getString("name");
            // String mota = bundle.getString("mota");
            // String gia = bundle.getString("gia");
            // String anh = bundle.getString("anh");
            // Sử dụng giá trị dữ liệu ở đây
        }
        medttenSP.setText(id);
        mbtnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ten = medttenSP.getText().toString().trim();
                loai = medtloaiSP.getText().toString().trim();
                gia = medtgiaSP.getText().toString().trim();
                gi = Long.parseLong(gia);
//                soluong = medtSoLuongSP.getText().toString().trim();
//                sl = Long.parseLong(soluong);
                mota = medtMoTaSP.getText().toString().trim();
                if (ten.isEmpty() || loai.isEmpty() || gia.isEmpty() || mota.isEmpty()
                        || imageUri == null)
                {
                    Toast.makeText(v.getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    uploadInfo();
                }
//                if ( /*medttenSP.getText().toString().trim().isEmpty() || medtloaiSP.getText().toString().trim().isEmpty() || medtgiaSP.getText().toString().trim().isEmpty()
//                        || medtSoLuongSP.getText().toString().trim().isEmpty() || medtMoTaSP.getText().toString().isEmpty()
//                        || */imageUri == null)
//                {
//                    Toast.makeText(v.getContext(), "Vui lòng thêm ảnh sản phẩm", Toast.LENGTH_SHORT).show();
//                } else {
////                    uploadImg();
//                    uploadInfo();
//
////                    Toast.makeText(v.getContext(),"Thêm thành công",Toast.LENGTH_SHORT).show();
//                }
            }
        });
        mbtnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

//        mbtnXacNhan = findViewById(R.id.btnXacNhan);
//        mbtnXacNhan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//            }
//        });


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){

//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == Activity.RESULT_OK){

                imageUri = data.getData();
//                if(result.getUri().equals(null)){
//                    System.out.println("No");
//                }else{
//                    imageUri = result.getUri();
//                    System.out.println("No2");
//                }

//                imageUri = data.getData();
                Glide.with(ThemSanPhamActivity.this).load(imageUri)
                        .override(512, 512).centerCrop().into(mimgSP);
//                mimgUserChange.setImageURI(imageUri);
//                uploadImg(imageUri);
            }
        }else {
            System.out.println("OhNo");
        }
    }
    private void uploadInfo(){
        if(imageUri != null){
            StorageReference myRef = storageReference.child("Phone/" + imageUri.getLastPathSegment()); //Lấy tên file ảnh cuối cùng
            myRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    myRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            if (uri != null){
                                imageURL = uri.toString();
                                if(imageURL == null){
                                    Toast.makeText(ThemSanPhamActivity.this,"Chưa tìm thấy đường dẫn hình ảnh",Toast.LENGTH_SHORT).show();
                                }else {
                                    CollectionReference collectionReference = firestore.collection("PRODUCTS");
                                    String id = UUID.randomUUID().toString();
                                    map = new HashMap<>();
                                    map.put("name", ten);
                                    map.put("category", loai);
                                    map.put("price", gi);
                                    map.put("image", imageURL);
                                    map.put("description", mota);
                                    map.put("id", id);
                                    collectionReference.document(id).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(ThemSanPhamActivity.this,"Thêm thành công",Toast.LENGTH_SHORT).show();
                                            finish();
                                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(ThemSanPhamActivity.this,"Thêm thất bại",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                            else{
                                Toast.makeText(ThemSanPhamActivity.this,"Không tìm thấy uri",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ThemSanPhamActivity.this, "Lỗi lấy đường link ảnh", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ThemSanPhamActivity.this, "Lỗi tải ảnh lên Firebase Storage", Toast.LENGTH_SHORT).show();
                }
            });
        }
//        String ten = medttenSP.getText().toString().trim();
//        String loai = medtloaiSP.getText().toString().trim();
//        String gia = medtgiaSP.getText().toString().trim();
//        int gi = Integer.parseInt(gia);
//        String soluong = medtSoLuongSP.getText().toString().trim();
//        int sl = Integer.parseInt(soluong);
//        String mota = medtMoTaSP.getText().toString().trim();
//        if (ten.isEmpty() || loai.isEmpty() || gia.isEmpty() || soluong.isEmpty() || mota.isEmpty()
//                || imageURL == null)
//        {
//            Toast.makeText(v.getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
//        } else {
//                            user.updatePassword(medtdialogPass.getText().toString());
//            String imageName = UUID.randomUUID().toString() + ".jpg"; // tạo tên ảnh ngẫu nhiên
//            storageReference = FirebaseStorage.getInstance().getReference().child("images/" + imageName); // tham chiếu đến đường dẫn lưu trữ ảnh trên Firebase Storage
//            storageReference.putFile(imageUri) // tải ảnh lên Firebase Storage
//                    .addOnSuccessListener(taskSnapshot -> {
//                        // Lấy đường dẫn tới ảnh đã tải lên Firebase Storage
//                        storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
//                            // Sử dụng thư viện Glide để hiển thị ảnh
//                            // Lưu đường dẫn ảnh vào Firestore để có thể lấy lại ảnh sau này
//                            String imageUrl = uri.toString();
//                            // thêm đường dẫn ảnh vào map
//                            map.put("image", imageUrl);
//                        });
//                    })
//                    .addOnFailureListener(e -> Toast.makeText(addFragment.this.getActivity(), "Lỗi tải ảnh lên Firebase Storage", Toast.LENGTH_SHORT).show());
//            if(imageURL == null){
//                Toast.makeText(addFragment.this.getActivity(),"Chưa tìm thấy đường dẫn hình ảnh",Toast.LENGTH_SHORT);
//            }else {
//                CollectionReference collectionReference = firestore.collection("SP");
//                String id = UUID.randomUUID().toString();
//                map = new HashMap<>();
//                map.put("name", ten);
//                map.put("category", loai);
//                map.put("price", gi);
//                map.put("soluong", sl);
//                map.put("image", imageURL);
//                map.put("description", mota);
//                map.put("id", id);
////                    if (imageUrl != null) {
////                        map.put("imageUrl", imageUrl);
////                    }
//                collectionReference.document(id).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        Toast.makeText(addFragment.this.getActivity(),"Thêm thành công",Toast.LENGTH_SHORT).show();
//                        getParentFragmentManager().popBackStack();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(addFragment.this.getActivity(),"Thêm thất bại",Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }

//                    dismiss();
//        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}