package com.example.utsklp3mobile.Data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.utsklp3mobile.Data.model.BattleHistory;
import com.example.utsklp3mobile.Data.model.Ranking;
import com.example.utsklp3mobile.Data.model.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "QuizBattleArena.db";
    private static final int DATABASE_VERSION = 1;

    // Tabel Users
    private static final String TABLE_USERS = "users";
    private static final String COL_USER_ID = "id";
    private static final String COL_USER_NAMA = "nama";
    private static final String COL_USER_POIN = "total_poin";
    private static final String COL_USER_MENANG = "jumlah_menang";
    private static final String COL_USER_KALAH = "jumlah_kalah";
    private static final String COL_USER_MAIN = "jumlah_main";

    // Tabel History
    private static final String TABLE_HISTORY = "battle_history";
    private static final String COL_HISTORY_ID = "id";
    private static final String COL_HISTORY_LAWAN = "lawan";
    private static final String COL_HISTORY_MATKUL = "mata_kuliah";
    private static final String COL_HISTORY_HASIL = "hasil";
    private static final String COL_HISTORY_POIN = "poin_didapat";
    private static final String COL_HISTORY_TANGGAL = "tanggal";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsers = "CREATE TABLE " + TABLE_USERS + " (" +
                COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USER_NAMA + " TEXT, " +
                COL_USER_POIN + " INTEGER DEFAULT 0, " +
                COL_USER_MENANG + " INTEGER DEFAULT 0, " +
                COL_USER_KALAH + " INTEGER DEFAULT 0, " +
                COL_USER_MAIN + " INTEGER DEFAULT 0)";
        db.execSQL(createUsers);

        String createHistory = "CREATE TABLE " + TABLE_HISTORY + " (" +
                COL_HISTORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_HISTORY_LAWAN + " TEXT, " +
                COL_HISTORY_MATKUL + " TEXT, " +
                COL_HISTORY_HASIL + " TEXT, " +
                COL_HISTORY_POIN + " INTEGER, " +
                COL_HISTORY_TANGGAL + " TEXT)";
        db.execSQL(createHistory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        onCreate(db);
    }

    // ========== USER ==========
    public long insertUser(String nama) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_NAMA, nama);
        values.put(COL_USER_POIN, 0);
        values.put(COL_USER_MENANG, 0);
        values.put(COL_USER_KALAH, 0);
        values.put(COL_USER_MAIN, 0);
        return db.insert(TABLE_USERS, null, values);
    }

    public User getUserByName(String nama) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null, COL_USER_NAMA + "=?", new String[]{nama}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            User user = new User(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_USER_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_NAMA)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_USER_POIN)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_USER_MENANG)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_USER_KALAH)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_USER_MAIN))
            );
            cursor.close();
            return user;
        }
        return null;
    }

    public void updateUserAfterBattle(String nama, boolean isWin, int poinTambahan) {
        SQLiteDatabase db = this.getWritableDatabase();
        User user = getUserByName(nama);
        if (user != null) {
            ContentValues values = new ContentValues();
            values.put(COL_USER_POIN, user.getTotalPoin() + poinTambahan);
            values.put(COL_USER_MAIN, user.getJumlahMain() + 1);
            if (isWin) {
                values.put(COL_USER_MENANG, user.getJumlahMenang() + 1);
            } else {
                values.put(COL_USER_KALAH, user.getJumlahKalah() + 1);
            }
            db.update(TABLE_USERS, values, COL_USER_NAMA + "=?", new String[]{nama});
        }
    }

    // ========== RANKING ==========
    public List<Ranking> getAllRanking() {
        List<Ranking> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " ORDER BY " + COL_USER_POIN + " DESC", null);
        if (cursor.moveToFirst()) {
            do {
                Ranking ranking = new Ranking(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_USER_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_NAMA)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_USER_POIN)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_USER_MENANG))
                );
                list.add(ranking);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // ========== HISTORY ==========
    public void insertHistory(String lawan, String mataKuliah, String hasil, int poin, String tanggal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_HISTORY_LAWAN, lawan);
        values.put(COL_HISTORY_MATKUL, mataKuliah);
        values.put(COL_HISTORY_HASIL, hasil);
        values.put(COL_HISTORY_POIN, poin);
        values.put(COL_HISTORY_TANGGAL, tanggal);
        db.insert(TABLE_HISTORY, null, values);
    }

    public List<BattleHistory> getAllHistory() {
        List<BattleHistory> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_HISTORY + " ORDER BY " + COL_HISTORY_ID + " DESC", null);
        if (cursor.moveToFirst()) {
            do {
                BattleHistory history = new BattleHistory(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_HISTORY_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_HISTORY_LAWAN)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_HISTORY_MATKUL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_HISTORY_HASIL)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_HISTORY_POIN)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_HISTORY_TANGGAL))
                );
                list.add(history);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}