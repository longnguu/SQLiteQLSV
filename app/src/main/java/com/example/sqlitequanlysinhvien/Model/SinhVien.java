package com.example.sqlitequanlysinhvien.Model;

public class SinhVien {
    String masv,tensv,email,diachi,ngaysinh,gioitinh,malop;

    public SinhVien(String masv, String tensv, String email, String diachi, String ngaysinh, String gioitinh, String malop) {
        this.masv = masv;
        this.tensv = tensv;
        this.email = email;
        this.diachi = diachi;
        this.ngaysinh = ngaysinh;
        this.gioitinh = gioitinh;
        this.malop = malop;
    }

    public SinhVien(String masv, String tensv, String malop) {
        this.masv = masv;
        this.tensv = tensv;
        this.malop = malop;
    }

    public String getMasv() {
        return masv;
    }

    public void setMasv(String masv) {
        this.masv = masv;
    }

    public String getTensv() {
        return tensv;
    }

    public void setTensv(String tensv) {
        this.tensv = tensv;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getMalop() {
        return malop;
    }

    public void setMalop(String malop) {
        this.malop = malop;
    }
}
