package com.example.yqhd_app.QuanLy.Model;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yqhd_app.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;

public class TaiKhoanKhachHangAdapter extends RecyclerView.Adapter<TaiKhoanKhachHangAdapter.ViewHolder>{
    Context context;
    List<TaiKhoanKhachHangModel> list;

    FirebaseFirestore firestore;
    //    FirebaseAuth auth;
//    FirebaseUser user;
    String userID;
    String trangthaibiendoi;
    int ptt;
    private onClickItem onClickItemListener;
    public TaiKhoanKhachHangAdapter(Context context, List<TaiKhoanKhachHangModel> list, onClickItem onClickItemListener) {
        this.context = context;
        this.list = list;
        this.onClickItemListener = onClickItemListener;
    }
    public TaiKhoanKhachHangAdapter(Context context, List<TaiKhoanKhachHangModel> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.qltaikhoankhachhang_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {
        firestore = FirebaseFirestore.getInstance();
//        auth = FirebaseAuth.getInstance();
//        user = auth.getCurrentUser();
//        userID = auth.getCurrentUser().getUid();
        TaiKhoanKhachHangModel item = list.get(position);

        ptt = holder.getAdapterPosition();

        holder.mtvTenKH.setText(String.valueOf(item.getFullname()));
        holder.mtvSdtKH.setText(String.valueOf(item.getPhone()));
        holder.mtvMailKH.setText(String.valueOf(item.getMail()));

        int trangthai = item.getTrangthai();
        if(trangthai == 0){
            trangthaibiendoi = "Active";
            holder.trangthai.setText(trangthaibiendoi);
            holder.trangthai.setTextColor(Color.parseColor("#088948"));
            holder.mbtnKhoaTK.setText("Khóa");
            holder.mbtnKhoaTK.setBackgroundColor(Color.parseColor("#F62D2B"));
            holder.mbtnKhoaTK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Khóa lại", Toast.LENGTH_SHORT).show();
//                    notifyItemChanged(ptt);
                    holder.mbtnKhoaTK.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#088948")));
                    holder.mbtnKhoaTK.setText("Mở");

//                    holder.mbtnKhoaTK.setBackgroundColor(Color.parseColor("#088948"));
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("trangthai", holder.mbtnKhoaTK.getText().toString().trim());
//                    firestore.collection("USERS").document(item.getIdUser()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void unused) {
//                            Toast.makeText(v.getContext(), "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(v.getContext(), "Xóa sản phẩm thất bại", Toast.LENGTH_SHORT).show();
//                        }
//                    });

                    notifyItemChanged(holder.getAdapterPosition());
                }
            });
        }else if(trangthai == 1){
            trangthaibiendoi = "Locked";
            holder.trangthai.setText(trangthaibiendoi);
            holder.trangthai.setTextColor(Color.parseColor("#F62D2B"));
            holder.mbtnKhoaTK.setText("Mở");
            holder.mbtnKhoaTK.setBackgroundColor(Color.parseColor("#088948"));
            holder.mbtnKhoaTK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Mở lại", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
    public interface onClickItem{
        void onClickToDetail(String madonhang, int tonggia, String ngaymua, String thoigianmua, String trangthai);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mtvTenKH;
        TextView trangthai;
        TextView mtvSdtKH;
        TextView mtvMailKH;
        Button mbtnKhoaTK;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mtvTenKH = itemView.findViewById(R.id.tentk);
            mtvSdtKH = itemView.findViewById(R.id.sdttk);
            mtvMailKH = itemView.findViewById(R.id.mailtk);
            trangthai = itemView.findViewById(R.id.trangthaitk);
            mbtnKhoaTK = itemView.findViewById(R.id.btnKhoaTaiKhoan);

            trangthai.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            trangthai.setHorizontallyScrolling(true);
            trangthai.setMarqueeRepeatLimit(-1);
            trangthai.setSelected(true);
            trangthai.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        trangthai.post(new Runnable() {
                            @Override
                            public void run() {
                                if (trangthai.getWidth() < trangthai.getLayout().getLineWidth(0)) {
                                    trangthai.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                                    trangthai.setHorizontallyScrolling(true);
                                    trangthai.setMarqueeRepeatLimit(-1);
                                    trangthai.setSelected(true);
                                } else {
                                    trangthai.setEllipsize(null);
                                    trangthai.setHorizontallyScrolling(false);
                                    trangthai.setMarqueeRepeatLimit(0);
                                    trangthai.setSelected(false);
                                }
                            }
                        });
                    }
                }
            });

            mtvMailKH.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            mtvMailKH.setHorizontallyScrolling(true);
            mtvMailKH.setMarqueeRepeatLimit(-1);
            mtvMailKH.setSelected(true);
            mtvMailKH.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        mtvMailKH.post(new Runnable() {
                            @Override
                            public void run() {
                                if (mtvMailKH.getWidth() < mtvMailKH.getLayout().getLineWidth(0)) {
                                    mtvMailKH.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                                    mtvMailKH.setHorizontallyScrolling(true);
                                    mtvMailKH.setMarqueeRepeatLimit(-1);
                                    mtvMailKH.setSelected(true);
                                } else {
                                    mtvMailKH.setEllipsize(null);
                                    mtvMailKH.setHorizontallyScrolling(false);
                                    mtvMailKH.setMarqueeRepeatLimit(0);
                                    mtvMailKH.setSelected(false);
                                }
                            }
                        });
                    }
                }
            });


            mtvTenKH.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            mtvTenKH.setHorizontallyScrolling(true);
            mtvTenKH.setMarqueeRepeatLimit(-1);
            mtvTenKH.setSelected(true);
            mtvTenKH.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        mtvTenKH.post(new Runnable() {
                            @Override
                            public void run() {
                                if (mtvTenKH.getWidth() < mtvTenKH.getLayout().getLineWidth(0)) {
                                    mtvTenKH.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                                    mtvTenKH.setHorizontallyScrolling(true);
                                    mtvTenKH.setMarqueeRepeatLimit(-1);
                                    mtvTenKH.setSelected(true);
                                } else {
                                    mtvTenKH.setEllipsize(null);
                                    mtvTenKH.setHorizontallyScrolling(false);
                                    mtvTenKH.setMarqueeRepeatLimit(0);
                                    mtvTenKH.setSelected(false);
                                }
                            }
                        });
                    }
                }
            });
        }
    }
    public void add(TaiKhoanKhachHangModel taiKhoanKhachHangModel){
        list.add(taiKhoanKhachHangModel);
        notifyDataSetChanged();
    }
}
