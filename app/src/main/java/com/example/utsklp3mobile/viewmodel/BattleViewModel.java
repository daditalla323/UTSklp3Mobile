package com.example.utsklp3mobile.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.utsklp3mobile.data.repository.BattleRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BattleViewModel extends AndroidViewModel {

    private final BattleRepository battleRepository;

    // LiveData untuk UI
    private final MutableLiveData<Integer> playerHp = new MutableLiveData<>(100);
    private final MutableLiveData<Integer> enemyHp = new MutableLiveData<>(100);
    private final MutableLiveData<String> battleResult = new MutableLiveData<>(); // "Menang" / "Kalah"
    private final MutableLiveData<Integer> poinDidapat = new MutableLiveData<>(0);
    private final MutableLiveData<Boolean> isBattleFinished = new MutableLiveData<>(false);

    private String currentUserName = "";
    private String enemyName = "";
    private String selectedMatkul = "";
    private int correctAnswers = 0;

    public BattleViewModel(@NonNull Application application) {
        super(application);
        battleRepository = new BattleRepository(application);
    }

    // ========== Getter LiveData ==========
    public LiveData<Integer> getPlayerHp() {
        return playerHp;
    }

    public LiveData<Integer> getEnemyHp() {
        return enemyHp;
    }

    public LiveData<String> getBattleResult() {
        return battleResult;
    }

    public LiveData<Integer> getPoinDidapat() {
        return poinDidapat;
    }

    public LiveData<Boolean> getIsBattleFinished() {
        return isBattleFinished;
    }

    // ========== Setup Battle ==========
    public void startBattle(String userName, String enemy, String mataKuliah) {
        this.currentUserName = userName;
        this.enemyName = enemy;
        this.selectedMatkul = mataKuliah;
        this.correctAnswers = 0;

        playerHp.setValue(100);
        enemyHp.setValue(100);
        isBattleFinished.setValue(false);
        battleResult.setValue(null);
        poinDidapat.setValue(0);
    }

    // ========== Logika Menjawab Soal ==========
    public void answerQuestion(boolean isCorrect) {
        if (Boolean.TRUE.equals(isBattleFinished.getValue())) return;

        int currentPlayerHp = playerHp.getValue() != null ? playerHp.getValue() : 100;
        int currentEnemyHp = enemyHp.getValue() != null ? enemyHp.getValue() : 100;

        if (isCorrect) {
            correctAnswers++;
            // Kurangi HP lawan
            currentEnemyHp -= 20;
            if (currentEnemyHp < 0) currentEnemyHp = 0;
            enemyHp.setValue(currentEnemyHp);
        } else {
            // Kurangi HP sendiri
            currentPlayerHp -= 15;
            if (currentPlayerHp < 0) currentPlayerHp = 0;
            playerHp.setValue(currentPlayerHp);
        }

        // Cek apakah battle sudah selesai
        checkBattleFinished(currentPlayerHp, currentEnemyHp);
    }

    private void checkBattleFinished(int pHp, int eHp) {
        if (pHp <= 0 || eHp <= 0) {
            boolean isWin = eHp <= 0;

            String hasil = isWin ? "Menang" : "Kalah";
            int poin = calculatePoin(isWin);

            battleResult.setValue(hasil);
            poinDidapat.setValue(poin);
            isBattleFinished.setValue(true);

            // Simpan ke database
            saveBattleResult(hasil, poin, isWin);
        }
    }

    private int calculatePoin(boolean isWin) {
        int poin = 0;
        if (isWin) {
            poin += 30; // bonus menang
        } else {
            poin += 5;  // partisipasi
        }
        poin += (correctAnswers * 2); // +2 per jawaban benar
        return poin;
    }

    private void saveBattleResult(String hasil, int poin, boolean isWin) {
        String tanggal = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());

        // Simpan history dengan mengaitkan nama user pemenang/pemain
        battleRepository.insertHistory(currentUserName, enemyName, selectedMatkul, hasil, poin, tanggal);

        // Update data user di database
        battleRepository.updateUserAfterBattle(currentUserName, isWin, poin);
    }

    // Dipanggil jika ingin mengakhiri battle secara manual (misal waktu habis)
    public void forceFinishBattle() {
        int pHp = playerHp.getValue() != null ? playerHp.getValue() : 0;
        int eHp = enemyHp.getValue() != null ? enemyHp.getValue() : 0;

        boolean isWin = pHp > eHp;
        String hasil = isWin ? "Menang" : "Kalah";
        int poin = calculatePoin(isWin);

        battleResult.setValue(hasil);
        poinDidapat.setValue(poin);
        isBattleFinished.setValue(true);

        saveBattleResult(hasil, poin, isWin);
    }
}