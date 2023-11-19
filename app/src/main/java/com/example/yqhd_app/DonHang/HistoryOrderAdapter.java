package com.example.yqhd_app.DonHang;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yqhd_app.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class HistoryOrderAdapter extends RecyclerView.Adapter<HistoryOrderAdapter.ViewHolder>{

    Context context;
    List<HistoryOrderModel> list;
    private onClickItem onClickItemListener;
    public HistoryOrderAdapter(Context context, List<HistoryOrderModel> list, onClickItem onClickItemListener) {
        this.context = context;
        this.list = list;
        this.onClickItemListener = onClickItemListener;
    }

    public HistoryOrderAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // nạp layout cho view biểu diễn phần từ history order item
        View userView = inflater.inflate(R.layout.donhang_acitivy_historyorderitem,parent,false);
        //
        ViewHolder viewHolder = new ViewHolder(userView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryOrderModel item = list.get(position);

//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        holder.diachi.setText(item.getDiachi());
        holder.tongGia.setText(NumberFormat.getNumberInstance(Locale.US).format(item.getTongGia()) + " VNĐ");
        holder.ngayMua.setText(item.getNgayMua());
        holder.gioMua.setText(item.getThoigianMua());
        int trangthai = item.getTrangthai();
        if(trangthai == 0){
            holder.trangthai.setText("Đang Chờ");
            holder.trangthai.setTextColor(Color.parseColor("#F62D2B"));
        }else if(trangthai == 1){
            holder.trangthai.setText("Đã Xác Nhận");
            holder.trangthai.setTextColor(Color.parseColor("#088948"));
        }else if(trangthai == 2){
            holder.trangthai.setText("Chưa Thanh Toán");
            holder.trangthai.setTextColor(Color.parseColor("#088948"));
        }else if(trangthai == 3){
            holder.trangthai.setText("Gửi Yêu Cầu Hủy");
            holder.trangthai.setTextColor(Color.parseColor("#088948"));
        }else{
            holder.trangthai.setText("Đã Hủy");
            holder.trangthai.setTextColor(Color.parseColor("#DF0512"));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemListener.onClickToDetail(item.getMadonhang(), item.getMakhachhang(), item.getTrangthai());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView diachi, tongGia, ngayMua, gioMua, trangthai;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            diachi = itemView.findViewById(R.id.HistoryDiaChi);
            tongGia = itemView.findViewById(R.id.HistoryTongGia);
            ngayMua = itemView.findViewById(R.id.HistoryNgayMua);
            gioMua = itemView.findViewById(R.id.HistoryGioMua);
            trangthai = itemView.findViewById(R.id.HistoryTrangThai);
        }
    }
    public interface onClickItem{
        void onClickToDetail(String madonhang, String makhachhang, int trangthai);
    }
    public void add(HistoryOrderModel SanPhamModel){
        list.add(SanPhamModel);
        notifyDataSetChanged();
    }
}
