package com.example.utsklp3mobile.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.utsklp3mobile.data.model.BattleHistory;
import com.example.utsklp3mobile.data.model.Ranking;
import com.example.utsklp3mobile.data.model.User;

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

    // Tabel Friends
    private static final String TABLE_FRIENDS = "friends";
    private static final String COL_FRIEND_ID = "id";
    private static final String COL_FRIEND_USER_NAME = "user_name";
    private static final String COL_FRIEND_NAME = "friend_name";

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

        String createFriends = "CREATE TABLE IF NOT EXISTS " + TABLE_FRIENDS + " (" +
                COL_FRIEND_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_FRIEND_USER_NAME + " TEXT, " +
                COL_FRIEND_NAME + " TEXT)";
        db.execSQL(createFriends);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIENDS);
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

    // ========== FRIENDS & SEARCH ==========
    public List<User> searchUsersByNickname(String query, String currentUserName) {
        List<User> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        
        // Buat tabel teman jika belum ada
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_FRIENDS + " (" +
                COL_FRIEND_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_FRIEND_USER_NAME + " TEXT, " +
                COL_FRIEND_NAME + " TEXT)");

        String selection = COL_USER_NAMA + " LIKE ? AND " + COL_USER_NAMA + " != ?";
        String[] selectionArgs = new String[]{"%" + query + "%", currentUserName};
        
        Cursor cursor = db.query(TABLE_USERS, null, selection, selectionArgs, null, null, COL_USER_NAMA + " ASC");
        if (cursor != null && cursor.moveToFirst()) {
            do {
                User user = new User(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_USER_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_NAMA)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_USER_POIN)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_USER_MENANG)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_USER_KALAH)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_USER_MAIN))
                );
                list.add(user);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    public boolean addFriend(String currentUserName, String friendName) {
        if (isFriend(currentUserName, friendName)) {
            return false;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_FRIEND_USER_NAME, currentUserName);
        values.put(COL_FRIEND_NAME, friendName);
        long res = db.insert(TABLE_FRIENDS, null, values);
        return res != -1;
    }

    public boolean isFriend(String currentUserName, String friendName) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_FRIENDS + " (" +
                COL_FRIEND_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_FRIEND_USER_NAME + " TEXT, " +
                COL_FRIEND_NAME + " TEXT)");

        Cursor cursor = db.query(TABLE_FRIENDS, null,
                COL_FRIEND_USER_NAME + "=? AND " + COL_FRIEND_NAME + "=?",
                new String[]{currentUserName, friendName}, null, null, null);
        boolean exists = (cursor != null && cursor.getCount() > 0);
        if (cursor != null) cursor.close();
        return exists;
    }
}