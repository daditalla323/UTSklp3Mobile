package com.example.utsklp3mobile.data.model;

public class Question {
    private int id;
    private String mataKuliah;
    private String soal;
    private String pilihanA;
    private String pilihanB;
    private String pilihanC;
    private String pilihanD;
    private String jawabanBenar;

    public Question() {}

    public Question(int id, String mataKuliah, String soal, String pilihanA, String pilihanB,
                    String pilihanC, String pilihanD, String jawabanBenar) {
        this.id = id;
        this.mataKuliah = mataKuliah;
        this.soal = soal;
        this.pilihanA = pilihanA;
        this.pilihanB = pilihanB;
        this.pilihanC = pilihanC;
        this.pilihanD = pilihanD;
        this.jawabanBenar = jawabanBenar;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMataKuliah() { return mataKuliah; }
    public void setMataKuliah(String mataKuliah) { this.mataKuliah = mataKuliah; }

    public String getSoal() { return soal; }
    public void setSoal(String soal) { this.soal = soal; }

    public String getPilihanA() { return pilihanA; }
    public void setPilihanA(String pilihanA) { this.pilihanA = pilihanA; }

    public String getPilihanB() { return pilihanB; }
    public void setPilihanB(String pilihanB) { this.pilihanB = pilihanB; }

    public String getPilihanC() { return pilihanC; }
    public void setPilihanC(String pilihanC) { this.pilihanC = pilihanC; }

    public String getPilihanD() { return pilihanD; }
    public void setPilihanD(String pilihanD) { this.pilihanD = pilihanD; }

    public String getJawabanBenar() { return jawabanBenar; }
    public void setJawabanBenar(String jawabanBenar) { this.jawabanBenar = jawabanBenar; }
}