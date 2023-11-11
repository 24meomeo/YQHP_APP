package com.example.yqhd_app.DonHang;

public class HistoryOrderModel {
    String madonhang;

    public String getMadonhang() {
        return madonhang;
    }

    public void setMadonhang(String madonhang) {
        this.madonhang = madonhang;
    }

    int tongSoLuong;
    String diachi;

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    String makhachhang;
    public String getMakhachhang() {
        return makhachhang;
    }

    public void setMakhachhang(String makhachhang) {
        this.makhachhang = makhachhang;
    }

    public HistoryOrderModel(String madonhang, String diachi, String makhachhang, int tongGia, String ngayMua, String thoigianMua, int trangthai) {
        this.madonhang = madonhang;
        this.diachi = diachi;
        this.makhachhang = makhachhang;
        this.tongGia = tongGia;
        this.ngayMua = ngayMua;
        this.thoigianMua = thoigianMua;
        this.trangthai = trangthai;
    }

    int tongGia;
    String ngayMua;
    String thoigianMua;

    int trangthai;

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

//    public HistoryOrderModel(int tongSoLuong, int tongGia, String ngayMua, String thoigianMua, int trangthai) {
//        this.tongSoLuong = tongSoLuong;
//        this.tongGia = tongGia;
//        this.ngayMua = ngayMua;
//        this.thoigianMua = thoigianMua;
//        this.trangthai = trangthai;
//    }
//
//    public HistoryOrderModel(int tongSoLuong, int tongGia, String ngayMua, String thoigianMua) {
//        this.tongSoLuong = tongSoLuong;
//        this.tongGia = tongGia;
//        this.ngayMua = ngayMua;
//        this.thoigianMua = thoigianMua;
//    }

    public HistoryOrderModel() {
    }

    public int getTongSoLuong() {
        return tongSoLuong;
    }

    public void setTongSoLuong(int tongSoLuong) {
        this.tongSoLuong = tongSoLuong;
    }

    public int getTongGia() {
        return tongGia;
    }

    public void setTongGia(int tongGia) {
        this.tongGia = tongGia;
    }

    public String getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(String ngayMua) {
        this.ngayMua = ngayMua;
    }

    public String getThoigianMua() {
        return thoigianMua;
    }

    public void setThoigianMua(String thoigianMua) {
        this.thoigianMua = thoigianMua;
    }
}
