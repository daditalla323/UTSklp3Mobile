package com.example.utsklp3mobile.Data.Model;

public class Ranking {
    private int id;
    private String nama;
    private int totalPoin;
    private int jumlahMenang;

    public Ranking() {
    }

    public Ranking(int id, String nama, int totalPoin, int jumlahMenang) {
        this.id = id;
        this.nama = nama;
        this.totalPoin = totalPoin;
        this.jumlahMenang = jumlahMenang;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public int getTotalPoin() { return totalPoin; }
    public void setTotalPoin(int totalPoin) { this.totalPoin = totalPoin; }

    public int getJumlahMenang() { return jumlahMenang; }
    public void setJumlahMenang(int jumlahMenang) { this.jumlahMenang = jumlahMenang; }
}