package com.example.yqhd_app.QuanLy.Model;

public class DonHangChiTietModel {
    String madonhang;
    String machitietdonhang;
    public String anh;
    public String name;
    public int price;
    public int soluong;
    public String id;

    public DonHangChiTietModel() {
    }

    public DonHangChiTietModel(String madonhang, String machitietdonhang, String anh, String name, int price, int totalQuantity) {
        this.madonhang = madonhang;
        this.machitietdonhang = machitietdonhang;
        this.anh = anh;
        this.name = name;
        this.price = price;
        this.soluong = totalQuantity;
    }

    public DonHangChiTietModel(String madonhang, String machitietdonhang, String anh, String name, int price, int totalQuantity, String id) {
        this.madonhang = madonhang;
        this.machitietdonhang = machitietdonhang;
        this.anh = anh;
        this.name = name;
        this.price = price;
        this.soluong = totalQuantity;
        this.id = id;
    }

    public String getMadonhang() {
        return madonhang;
    }

    public void setMadonhang(String madonhang) {
        this.madonhang = madonhang;
    }

    public String getMachitietdonhang() {
        return machitietdonhang;
    }

    public void setMachitietdonhang(String machitietdonhang) {
        this.machitietdonhang = machitietdonhang;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
