package com.example.yqhd_app.QuanLy.Model;

public class DonHangModel {
    String madonhang;

    public String getMadonhang() {
        return madonhang;
    }

    public void setMadonhang(String madonhang) {
        this.madonhang = madonhang;
    }

    int tongSoLuong;

    String makhachhang;
    public String getMakhachhang() {
        return makhachhang;
    }

    public void setMakhachhang(String makhachhang) {
        this.makhachhang = makhachhang;
    }


    int tongGia;
    String ngayMua;
    String thoigianMua;

    int trangthai;
    String ten;
    String sodienthoai;
    String diachi;
    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public DonHangModel(int tongSoLuong, int tongGia, String ngayMua, String thoigianMua, int trangthai) {
        this.tongSoLuong = tongSoLuong;
        this.tongGia = tongGia;
        this.ngayMua = ngayMua;
        this.thoigianMua = thoigianMua;
        this.trangthai = trangthai;
    }

    public DonHangModel(String madonhang, String makhachhang, int tongGia, String ngayMua, String thoigianMua, int trangthai, String ten, String sodienthoai, String diachi) {
        this.madonhang = madonhang;
        this.makhachhang = makhachhang;
        this.tongGia = tongGia;
        this.ngayMua = ngayMua;
        this.thoigianMua = thoigianMua;
        this.trangthai = trangthai;
        this.ten = ten;
        this.sodienthoai = sodienthoai;
        this.diachi = diachi;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public DonHangModel(int tongSoLuong, int tongGia, String ngayMua, String thoigianMua) {
        this.tongSoLuong = tongSoLuong;
        this.tongGia = tongGia;
        this.ngayMua = ngayMua;
        this.thoigianMua = thoigianMua;
    }

    public DonHangModel() {
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
