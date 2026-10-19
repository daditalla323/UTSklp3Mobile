package com.example.utsklp3mobile.data.model;

public class BattleHistory {
    private int id;
    private String lawan;
    private String mataKuliah;
    private String hasil;
    private int poinDidapat;
    private String tanggal;

    public BattleHistory() {}

    public BattleHistory(int id, String lawan, String mataKuliah, String hasil, int poinDidapat, String tanggal) {
        this.id = id;
        this.lawan = lawan;
        this.mataKuliah = mataKuliah;
        this.hasil = hasil;
        this.poinDidapat = poinDidapat;
        this.tanggal = tanggal;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getLawan() { return lawan; }
    public void setLawan(String lawan) { this.lawan = lawan; }

    public String getMataKuliah() { return mataKuliah; }
    public void setMataKuliah(String mataKuliah) { this.mataKuliah = mataKuliah; }

    public String getHasil() { return hasil; }
    public void setHasil(String hasil) { this.hasil = hasil; }

    public int getPoinDidapat() { return poinDidapat; }
    public void setPoinDidapat(int poinDidapat) { this.poinDidapat = poinDidapat; }

    public String getTanggal() { return tanggal; }
    public void setTanggal(String tanggal) { this.tanggal = tanggal; }
}