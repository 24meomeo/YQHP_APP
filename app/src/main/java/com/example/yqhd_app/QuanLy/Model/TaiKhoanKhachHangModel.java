package com.example.yqhd_app.QuanLy.Model;

public class TaiKhoanKhachHangModel {
    String idUser;
    String Fullname;
    String Mail;
    String Phone;
    String Address;
    int trangthai;
    int vaitro;

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public int getVaitro() {
        return vaitro;
    }

    public void setVaitro(int vaitro) {
        this.vaitro = vaitro;
    }

    public TaiKhoanKhachHangModel() {
    }

    public TaiKhoanKhachHangModel(String fullname, String mail, String phone, String address, int trangthai, int vaitro) {
        Fullname = fullname;
        Mail = mail;
        Phone = phone;
        Address = address;
        this.trangthai = trangthai;
        this.vaitro = vaitro;
    }

    public TaiKhoanKhachHangModel(String idUser, String fullname, String mail, String phone, String address, int trangthai, int vaitro) {
        this.idUser = idUser;
        Fullname = fullname;
        Mail = mail;
        Phone = phone;
        Address = address;
        this.trangthai = trangthai;
        this.vaitro = vaitro;
    }
}
