package com.example.yqhd_app.QuanLy.Model;

import android.content.Context;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.ViewHolder>{
    Context context;
    List<DonHangModel> list;

//    private onClickButton onClickButton;

    FirebaseFirestore firestore;
    //    FirebaseAuth auth;
//    FirebaseUser user;
    String userID;
    String trangthaibiendoi;
    private onClickItem onClickItemListener;
//    public DonHangAdapter(Context context, List<DonHangModel> list, onClickButton onClickButton) {
//        this.context = context;
//        this.list = list;
//        this.onClickButton = onClickButton;
//    }
    public DonHangAdapter(Context context, List<DonHangModel> list, onClickItem onClickItemListener) {
        this.context = context;
        this.list = list;
        this.onClickItemListener = onClickItemListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
//        LayoutInflater inflater = LayoutInflater.from(context);
//        // nạp layout cho view biểu diễn phần từ gio hang item
//        View userView = inflater.inflate(R.layout.khachhangitem,parent,false);
//        //
//        ViewHolder viewHolder = new ViewHolder(userView);
//        return viewHolder;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.qldonhang_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {
        firestore = FirebaseFirestore.getInstance();
//        auth = FirebaseAuth.getInstance();
//        user = auth.getCurrentUser();
//        userID = auth.getCurrentUser().getUid();
        DonHangModel item = list.get(position);

//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        holder.mtvTenKH.setText(String.valueOf(item.getTen()));
        holder.mtvSdtKH.setText(String.valueOf(item.getSodienthoai()));
        holder.mtvDiaChiKH.setText(String.valueOf(item.getDiachi()));

//        holder.tongGia.setText(String.valueOf(item.getTongGia()));
//        holder.ngayMua.setText(item.getNgayMua());
//        holder.gioMua.setText(item.getThoigianMua());
        int trangthai = item.getTrangthai();
        if(trangthai == 0){
            trangthaibiendoi = "Đang chờ";
            holder.trangthai.setText(trangthaibiendoi);
            holder.trangthai.setTextColor(Color.parseColor("#F62D2B"));
        }else if(trangthai == 1){
            trangthaibiendoi = "Đã xác nhận";
            holder.trangthai.setText(trangthaibiendoi);
            holder.trangthai.setTextColor(Color.parseColor("#088948"));
        }else if(trangthai == 2){
            trangthaibiendoi = "Chưa Thanh Toán";
            holder.trangthai.setTextColor(Color.parseColor("#7C007C"));
            holder.trangthai.setText(trangthaibiendoi);
            holder.mbtnXacNhanDonHang.setEnabled(false);
            holder.mbtnXacNhanDonHang.setAlpha(0.2f);
        }else if(trangthai == 3){
            holder.trangthai.setText("Bánh đã có");
            holder.trangthai.setTextColor(Color.parseColor("#088948"));
        }else{
            holder.trangthai.setText("Từ chối");
            holder.trangthai.setTextColor(Color.parseColor("#DF0512"));
        }
        holder.mbtnXacNhanDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onClickButton.onClickToDetail(item.getMadonhang(), item.getMakhachhang(), item.getTongGia(), item.getTongSoLuong(), item.getNgayMua(),
//                        item.getThoigianMua(),
//                        item.getTrangthai());
                HashMap<String, Object> map = new HashMap<>();
                map.put("trangthai", 1);
                firestore.collection("ORDERS").document(item.getMadonhang())
                                .update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(v.getContext(), "Xác nhận thành công", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(v.getContext(), "Xác nhận thất bại", Toast.LENGTH_SHORT).show();
                            }
                        });

                list.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemListener.onClickToDetail(item.getMadonhang() ,item.getTongGia(), item.getNgayMua(), item.getThoigianMua(), trangthaibiendoi);
            }
        });
    }
//    public interface onClickButton{
//        void onClickToDetail(String madon, String makhach,int tonggia, int tongsoluong, String ngaymua, String giomua, int trangthai);
//    }
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
        TextView mtvDiaChiKH;
        Button mbtnXacNhanDonHang;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mtvTenKH = itemView.findViewById(R.id.tenkh);
            mtvSdtKH = itemView.findViewById(R.id.sodienthoaikh);
            mtvDiaChiKH = itemView.findViewById(R.id.diachikh);
            trangthai = itemView.findViewById(R.id.HistoryTrangThai);
            mbtnXacNhanDonHang = itemView.findViewById(R.id.btnXacNhanDonHang);

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

            mtvDiaChiKH.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            mtvDiaChiKH.setHorizontallyScrolling(true);
            mtvDiaChiKH.setMarqueeRepeatLimit(-1);
            mtvDiaChiKH.setSelected(true);
            mtvDiaChiKH.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        mtvDiaChiKH.post(new Runnable() {
                            @Override
                            public void run() {
                                if (mtvDiaChiKH.getWidth() < mtvDiaChiKH.getLayout().getLineWidth(0)) {
                                    mtvDiaChiKH.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                                    mtvDiaChiKH.setHorizontallyScrolling(true);
                                    mtvDiaChiKH.setMarqueeRepeatLimit(-1);
                                    mtvDiaChiKH.setSelected(true);
                                } else {
                                    mtvDiaChiKH.setEllipsize(null);
                                    mtvDiaChiKH.setHorizontallyScrolling(false);
                                    mtvDiaChiKH.setMarqueeRepeatLimit(0);
                                    mtvDiaChiKH.setSelected(false);
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
    public void add(DonHangModel SanPhamModel){
        list.add(SanPhamModel);
        notifyDataSetChanged();
    }
}
