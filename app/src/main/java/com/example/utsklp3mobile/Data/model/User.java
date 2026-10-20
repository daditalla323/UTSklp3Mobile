package com.example.utsklp3mobile.data.model;

public class User {
    private int id;
    private String nama;
    private int totalPoin;
    private int jumlahMenang;
    private int jumlahKalah;
    private int jumlahMain;

    public User() {}

    public User(int id, String nama, int totalPoin, int jumlahMenang, int jumlahKalah, int jumlahMain) {
        this.id = id;
        this.nama = nama;
        this.totalPoin = totalPoin;
        this.jumlahMenang = jumlahMenang;
        this.jumlahKalah = jumlahKalah;
        this.jumlahMain = jumlahMain;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public int getTotalPoin() { return totalPoin; }
    public void setTotalPoin(int totalPoin) { this.totalPoin = totalPoin; }

    public int getJumlahMenang() { return jumlahMenang; }
    public void setJumlahMenang(int jumlahMenang) { this.jumlahMenang = jumlahMenang; }

    public int getJumlahKalah() { return jumlahKalah; }
    public void setJumlahKalah(int jumlahKalah) { this.jumlahKalah = jumlahKalah; }

    public int getJumlahMain() { return jumlahMain; }
    public void setJumlahMain(int jumlahMain) { this.jumlahMain = jumlahMain; }
}