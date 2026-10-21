package com.example.utsklp3mobile.ui.battle;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import com.example.utsklp3mobile.R;
import com.example.utsklp3mobile.data.model.Question;
import com.example.utsklp3mobile.viewmodel.BattleViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class BattleActivity extends AppCompatActivity {

    private BattleViewModel battleViewModel;

    private TextView tvMatkulBadge, tvQuestionCount, btnExitBattle;
    private TextView tvPlayerAvatar, tvPlayerName, tvPlayerHp;
    private TextView tvEnemyAvatar, tvEnemyName, tvEnemyHp;
    private ProgressBar pbPlayerHp, pbEnemyHp;

    private CardView cardFeedbackBanner;
    private TextView tvFeedbackText;

    private CardView cardQuestionContainer;
    private TextView tvTopicBadge, tvQuestionText;
    private LinearLayout layoutOptA, layoutOptB, layoutOptC, layoutOptD;
    private TextView badgeOptA, badgeOptB, badgeOptC, badgeOptD;
    private TextView tvOptA, tvOptB, tvOptC, tvOptD;

    private MaterialButton btnNextQuestion;
    private CardView cardBattleResult;
    private TextView tvResultStatus, tvResultPoints;
    private MaterialButton btnFinishBattle;

    private String currentUserName = "Pemain";
    private String enemyName = "Rina";
    private String selectedMatkul = "Pemrograman Mobile";
    private String battleMode = "RANKED";

    private List<Question> questionList = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private String selectedAnswer = "";
    private boolean isAnswered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        if (getIntent() != null) {
            if (getIntent().hasExtra("USER_NAME")) {
                currentUserName = getIntent().getStringExtra("USER_NAME");
            }
            if (getIntent().hasExtra("MATKUL")) {
                selectedMatkul = getIntent().getStringExtra("MATKUL");
            }
            if (getIntent().hasExtra("BATTLE_MODE")) {
                battleMode = getIntent().getStringExtra("BATTLE_MODE");
            }
        }

        // Tentukan Nama Lawan Berdasarkan Mode
        setupEnemyName();

        initViews();
        setupQuestions();

        battleViewModel = new ViewModelProvider(this).get(BattleViewModel.class);
        battleViewModel.startBattle(currentUserName, enemyName, selectedMatkul);

        observeViewModel();

        btnExitBattle.setOnClickListener(v -> finish());

        btnFinishBattle.setOnClickListener(v -> finish());

        btnNextQuestion.setOnClickListener(v -> {
            if (!isAnswered) {
                Toast.makeText(this, "Pilih salah satu jawaban terlebih dahulu", Toast.LENGTH_SHORT).show();
                return;
            }

            currentQuestionIndex++;
            if (currentQuestionIndex < questionList.size()) {
                displayQuestion(currentQuestionIndex);
            } else {
                battleViewModel.forceFinishBattle();
            }
        });

        setupOptionClickListeners();

        // Display first question
        displayQuestion(0);
    }

    private void setupEnemyName() {
        if ("AI_CHALLENGE".equalsIgnoreCase(battleMode)) {
            String[] aiNames = new String[]{"Bot123", "AI-Master", "QuizBot3000", "CyberBot-X", "RoboQuiz"};
            int randomIndex = (int) (Math.random() * aiNames.length);
            enemyName = aiNames[randomIndex];
        } else {
            String[] humanNames = new String[]{"Rina", "Budi", "Siti", "Andi", "Dewi", "Reza", "Fikri", "Ayu"};
            int randomIndex = (int) (Math.random() * humanNames.length);
            enemyName = humanNames[randomIndex];
        }
    }

    private void initViews() {
        tvMatkulBadge = findViewById(R.id.tvMatkulBadge);
        tvQuestionCount = findViewById(R.id.tvQuestionCount);
        btnExitBattle = findViewById(R.id.btnExitBattle);

        tvPlayerAvatar = findViewById(R.id.tvPlayerAvatar);
        tvPlayerName = findViewById(R.id.tvPlayerName);
        tvPlayerHp = findViewById(R.id.tvPlayerHp);
        pbPlayerHp = findViewById(R.id.pbPlayerHp);

        tvEnemyAvatar = findViewById(R.id.tvEnemyAvatar);
        tvEnemyName = findViewById(R.id.tvEnemyName);
        tvEnemyHp = findViewById(R.id.tvEnemyHp);
        pbEnemyHp = findViewById(R.id.pbEnemyHp);

        cardFeedbackBanner = findViewById(R.id.cardFeedbackBanner);
        tvFeedbackText = findViewById(R.id.tvFeedbackText);

        cardQuestionContainer = findViewById(R.id.cardQuestionContainer);
        tvTopicBadge = findViewById(R.id.tvTopicBadge);
        tvQuestionText = findViewById(R.id.tvQuestionText);

        layoutOptA = findViewById(R.id.layoutOptA);
        layoutOptB = findViewById(R.id.layoutOptB);
        layoutOptC = findViewById(R.id.layoutOptC);
        layoutOptD = findViewById(R.id.layoutOptD);

        badgeOptA = findViewById(R.id.badgeOptA);
        badgeOptB = findViewById(R.id.badgeOptB);
        badgeOptC = findViewById(R.id.badgeOptC);
        badgeOptD = findViewById(R.id.badgeOptD);

        tvOptA = findViewById(R.id.tvOptA);
        tvOptB = findViewById(R.id.tvOptB);
        tvOptC = findViewById(R.id.tvOptC);
        tvOptD = findViewById(R.id.tvOptD);

        btnNextQuestion = findViewById(R.id.btnNextQuestion);
        cardBattleResult = findViewById(R.id.cardBattleResult);
        tvResultStatus = findViewById(R.id.tvResultStatus);
        tvResultPoints = findViewById(R.id.tvResultPoints);
        btnFinishBattle = findViewById(R.id.btnFinishBattle);

        String modeLabel = "AI_CHALLENGE".equalsIgnoreCase(battleMode) ? "AI Challenge" : "Ranked Arena";
        tvMatkulBadge.setText(selectedMatkul + " · " + modeLabel);
        tvPlayerName.setText(currentUserName);
        if (currentUserName != null && !currentUserName.isEmpty()) {
            tvPlayerAvatar.setText(currentUserName.substring(0, 1).toUpperCase());
        }

        tvEnemyName.setText(enemyName);
        if (enemyName != null && !enemyName.isEmpty()) {
            tvEnemyAvatar.setText(enemyName.substring(0, 1).toUpperCase());
        }
    }

    private void setupQuestions() {
        questionList.clear();

        if ("Jaringan Komputer".equalsIgnoreCase(selectedMatkul)) {
            questionList.add(new Question(1, selectedMatkul,
                    "Protokol mana yang bekerja pada Transport Layer dan berorientasi koneksi (connection-oriented)?",
                    "UDP", "TCP", "HTTP", "IP", "B"));
            questionList.add(new Question(2, selectedMatkul,
                    "Alamat IP 192.168.1.1 termasuk dalam kelas alamat IP berapa?",
                    "Kelas A", "Kelas B", "Kelas C", "Kelas D", "C"));
            questionList.add(new Question(3, selectedMatkul,
                    "Topologi jaringan yang menghubungkan semua komputer ke satu perangkat pusat (Hub/Switch)?",
                    "Topologi Bus", "Topologi Star", "Topologi Ring", "Topologi Mesh", "B"));
            questionList.add(new Question(4, selectedMatkul,
                    "Perangkat jaringan yang berfungsi menghubungkan dua atau lebih jaringan berbeda segmen?",
                    "Switch", "Hub", "Router", "Repeater", "C"));
            questionList.add(new Question(5, selectedMatkul,
                    "Berapa nomor port default yang digunakan oleh protokol HTTPS?",
                    "80", "21", "22", "443", "D"));

        } else if ("Sistem Operasi".equalsIgnoreCase(selectedMatkul)) {
            questionList.add(new Question(1, selectedMatkul,
                    "Bagian utama sistem operasi yang mengelola memori, proses, dan perangkat keras adalah?",
                    "Shell", "Kernel", "Compiler", "Driver", "B"));
            questionList.add(new Question(2, selectedMatkul,
                    "Kondisi ketika dua atau lebih proses saling menunggu sumber daya secara tak terbatas disebut?",
                    "Starvation", "Paging", "Deadlock", "Thrashing", "C"));
            questionList.add(new Question(3, selectedMatkul,
                    "Algoritma penjadwalan CPU yang melayani proses berdasarkan urutan kedatangan adalah?",
                    "FCFS", "SJF", "Round Robin", "Priority Scheduling", "A"));
            questionList.add(new Question(4, selectedMatkul,
                    "Memori virtual utama yang digunakan sistem operasi saat RAM fisik penuh disebut?",
                    "Cache", "Swap / Paging", "ROM", "Register", "B"));
            questionList.add(new Question(5, selectedMatkul,
                    "Sistem berkas (file system) default yang digunakan oleh OS Windows modern adalah?",
                    "FAT32", "ext4", "NTFS", "APFS", "C"));

        } else if ("Basis Data".equalsIgnoreCase(selectedMatkul)) {
            questionList.add(new Question(1, selectedMatkul,
                    "Perintah SQL yang digunakan untuk mengambil data dari satu atau lebih tabel adalah?",
                    "UPDATE", "INSERT", "DELETE", "SELECT", "D"));
            questionList.add(new Question(2, selectedMatkul,
                    "Primary Key dalam sebuah tabel fungsi utamanya adalah?",
                    "Mengurutkan baris", "Mengidentifikasi baris secara unik", "Menghapus data ganda", "Mencegah nilai NULL pada semua kolom", "B"));
            questionList.add(new Question(3, selectedMatkul,
                    "Perintah DDL SQL untuk menghapus seluruh tabel beserta strukturnya dari database adalah?",
                    "DROP TABLE", "DELETE TABLE", "REMOVE TABLE", "TRUNCATE TABLE", "A"));
            questionList.add(new Question(4, selectedMatkul,
                    "Klausa SQL 'GROUP BY' biasanya digunakan bersama dengan fungsi agregat seperti?",
                    "COUNT / SUM", "LIKE / BETWEEN", "JOIN / UNION", "ORDER BY / LIMIT", "A"));
            questionList.add(new Question(5, selectedMatkul,
                    "Kunci yang menghubungkan baris pada satu tabel dengan baris pada tabel lain disebut?",
                    "Primary Key", "Foreign Key", "Super Key", "Candidate Key", "B"));

        } else {
            // Default: Pemrograman Mobile
            questionList.add(new Question(1, selectedMatkul,
                    "Widget apa yang paling tepat untuk menyusun tampilan secara vertikal di Flutter?",
                    "Row", "Column", "Stack", "Container", "B"));
            questionList.add(new Question(2, selectedMatkul,
                    "Komponen Android manakah yang digunakan sebagai UI container / screen utama?",
                    "Activity", "Service", "Broadcast Receiver", "Content Provider", "A"));
            questionList.add(new Question(3, selectedMatkul,
                    "Layout manakah yang menyusun view secara berurutan dalam satu baris atau kolom?",
                    "LinearLayout", "RelativeLayout", "FrameLayout", "ConstraintLayout", "A"));
            questionList.add(new Question(4, selectedMatkul,
                    "Method lifecycle manakah yang dipanggil pertama kali saat Activity dibuat?",
                    "onStart()", "onCreate()", "onResume()", "onInit()", "B"));
            questionList.add(new Question(5, selectedMatkul,
                    "Bahasa pemrograman resmi yang direkomendasikan Google untuk pengembangan Android native saat ini?",
                    "Java", "Kotlin", "C++", "Dart", "B"));
        }
    }

    private void displayQuestion(int index) {
        if (index >= questionList.size()) return;

        Question q = questionList.get(index);
        tvQuestionCount.setText("Soal " + (index + 1) + "/" + questionList.size());
        tvTopicBadge.setText("Quiz · " + selectedMatkul);
        tvQuestionText.setText(q.getSoal());

        tvOptA.setText(q.getPilihanA());
        tvOptB.setText(q.getPilihanB());
        tvOptC.setText(q.getPilihanC());
        tvOptD.setText(q.getPilihanD());

        resetOptionStyles();
        isAnswered = false;
        tvFeedbackText.setText("Pilih jawaban yang benar untuk menyerang HP lawan!");
        cardFeedbackBanner.setCardBackgroundColor(Color.parseColor("#FFF0F2"));
    }

    private void resetOptionStyles() {
        resetSingleOption(layoutOptA, badgeOptA, tvOptA);
        resetSingleOption(layoutOptB, badgeOptB, tvOptB);
        resetSingleOption(layoutOptC, badgeOptC, tvOptC);
        resetSingleOption(layoutOptD, badgeOptD, tvOptD);
    }

    private void resetSingleOption(LinearLayout layout, TextView badge, TextView text) {
        layout.setBackgroundResource(R.drawable.bg_option_default);
        badge.setBackgroundResource(R.drawable.bg_option_badge);
        badge.setTextColor(Color.parseColor("#718096"));
        text.setTextColor(Color.parseColor("#718096"));
    }

    private void setupOptionClickListeners() {
        layoutOptA.setOnClickListener(v -> selectAnswer("A", layoutOptA, badgeOptA, tvOptA));
        layoutOptB.setOnClickListener(v -> selectAnswer("B", layoutOptB, badgeOptB, tvOptB));
        layoutOptC.setOnClickListener(v -> selectAnswer("C", layoutOptC, badgeOptC, tvOptC));
        layoutOptD.setOnClickListener(v -> selectAnswer("D", layoutOptD, badgeOptD, tvOptD));
    }

    private void selectAnswer(String optionKey, LinearLayout layout, TextView badge, TextView text) {
        if (isAnswered) return;
        isAnswered = true;
        selectedAnswer = optionKey;

        Question currentQuestion = questionList.get(currentQuestionIndex);
        boolean isCorrect = optionKey.equalsIgnoreCase(currentQuestion.getJawabanBenar());

        if (isCorrect) {
            layout.setBackgroundResource(R.drawable.bg_option_selected_correct);
            badge.setBackgroundResource(R.drawable.bg_option_badge_correct);
            badge.setTextColor(Color.parseColor("#065F46"));
            text.setTextColor(Color.parseColor("#065F46"));

            tvFeedbackText.setText("Jawaban Benar! Lawan kehilangan 20 HP.");
            cardFeedbackBanner.setCardBackgroundColor(Color.parseColor("#ECFDF5"));
        } else {
            layout.setBackgroundResource(R.drawable.bg_option_selected_wrong);
            badge.setTextColor(Color.parseColor("#991B1B"));
            text.setTextColor(Color.parseColor("#991B1B"));

            tvFeedbackText.setText("Jawaban Salah! Kamu kehilangan 15 HP.");
            cardFeedbackBanner.setCardBackgroundColor(Color.parseColor("#FEF2F2"));

            // Highlight correct answer in green
            highlightCorrectAnswer(currentQuestion.getJawabanBenar());
        }

        battleViewModel.answerQuestion(isCorrect);
    }

    private void highlightCorrectAnswer(String correctOptionKey) {
        switch (correctOptionKey) {
            case "A":
                layoutOptA.setBackgroundResource(R.drawable.bg_option_selected_correct);
                break;
            case "B":
                layoutOptB.setBackgroundResource(R.drawable.bg_option_selected_correct);
                break;
            case "C":
                layoutOptC.setBackgroundResource(R.drawable.bg_option_selected_correct);
                break;
            case "D":
                layoutOptD.setBackgroundResource(R.drawable.bg_option_selected_correct);
                break;
        }
    }

    private void observeViewModel() {
        battleViewModel.getPlayerHp().observe(this, hp -> {
            pbPlayerHp.setProgress(hp);
            tvPlayerHp.setText(hp + " HP");
        });

        battleViewModel.getEnemyHp().observe(this, hp -> {
            pbEnemyHp.setProgress(hp);
            tvEnemyHp.setText(hp + " HP");
        });

        battleViewModel.getIsBattleFinished().observe(this, isFinished -> {
            if (isFinished != null && isFinished) {
                cardQuestionContainer.setVisibility(View.GONE);
                btnNextQuestion.setVisibility(View.GONE);
                cardBattleResult.setVisibility(View.VISIBLE);

                String result = battleViewModel.getBattleResult().getValue();
                Integer poin = battleViewModel.getPoinDidapat().getValue();

                if ("Menang".equalsIgnoreCase(result)) {
                    tvResultStatus.setText("🎉 KAMU MENANG!");
                    tvResultStatus.setTextColor(Color.parseColor("#10B981"));
                } else {
                    tvResultStatus.setText("💔 KAMU KALAH!");
                    tvResultStatus.setTextColor(Color.parseColor("#EF4444"));
                }

                tvResultPoints.setText("Poin Didapat: +" + (poin != null ? poin : 0));
            }
        });
    }
}