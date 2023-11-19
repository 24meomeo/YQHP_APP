package com.example.yqhd_app.DonHang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yqhd_app.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class HistoryOrderDetailAdapter extends RecyclerView.Adapter<HistoryOrderDetailAdapter.ViewHolder>{
    Context context;
    List<HistoryOrderDetailModel> list;

    public HistoryOrderDetailAdapter(Context context, List<HistoryOrderDetailModel> list) {
        this.context = context;
        this.list = list;
    }

    public HistoryOrderDetailAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // nạp layout cho view biểu diễn phần từ thanhtoan
        View userView = inflater.inflate(R.layout.historyorderitem,parent,false);
        //
        ViewHolder viewHolder = new ViewHolder(userView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryOrderDetailModel item = list.get(position);
//        Glide.with(holder.ivBanh.getContext()).load(item.getImage()).into(holder.ivBanh);
        Glide.with(context).load(item.getAnh()).into(holder.mimvSP);
        holder.name.setText(item.getName());
        holder.price.setText(NumberFormat.getNumberInstance(Locale.US).format(item.getPrice())+" VNĐ");
        holder.totalQuantity.setText(String.valueOf(item.getSoluong()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mimvSP;
        TextView name, price, totalQuantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mimvSP = itemView.findViewById(R.id.ivThanhToanItem);
            name = itemView.findViewById(R.id.tvNameThanhToanItem);
            price = itemView.findViewById(R.id.tvPriceThanhToanItem);
            totalQuantity = itemView.findViewById(R.id.tvQuantityThanhToanItem);
        }
    }
    public void add(HistoryOrderDetailModel historyOrderDetailModel) {
        list.add(historyOrderDetailModel);
        notifyDataSetChanged();
    }
}
