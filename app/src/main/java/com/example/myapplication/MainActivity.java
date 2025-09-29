package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private DatePicker datePicker1, datePicker2;
    private Button btnCalculate, btnToNewScreen;
    private TextView tvDays, tvHours, tvMinutes, tvSeconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datePicker1 = findViewById(R.id.datePicker1);
        datePicker2 = findViewById(R.id.datePicker2);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnToNewScreen = findViewById(R.id.btnToNewScreen);
        tvDays = findViewById(R.id.tvDays);
        tvHours = findViewById(R.id.tvHours);
        tvMinutes = findViewById(R.id.tvMinutes);
        tvSeconds = findViewById(R.id.tvSeconds);

        Calendar calendar = Calendar.getInstance();
        datePicker1.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), null);

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        datePicker2.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), null);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateDateDifference();
            }
        });

        btnToNewScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Переход на второй экран
                Intent intent = new Intent(MainActivity.this, MainActivity_branch2.class);
                startActivity(intent);
            }
        });

        calculateDateDifference();
    }

    private void calculateDateDifference() {
        try {
            Calendar cal1 = Calendar.getInstance();
            cal1.set(datePicker1.getYear(), datePicker1.getMonth(), datePicker1.getDayOfMonth());
            Date date1 = cal1.getTime();

            Calendar cal2 = Calendar.getInstance();
            cal2.set(datePicker2.getYear(), datePicker2.getMonth(), datePicker2.getDayOfMonth());
            Date date2 = cal2.getTime();

            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            String date1Str = sdf.format(date1);
            String date2Str = sdf.format(date2);

            long differenceMillis = Math.abs(date2.getTime() - date1.getTime());

            long seconds = TimeUnit.MILLISECONDS.toSeconds(differenceMillis);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(differenceMillis);
            long hours = TimeUnit.MILLISECONDS.toHours(differenceMillis);
            long days = TimeUnit.MILLISECONDS.toDays(differenceMillis);

            tvDays.setText(String.format("Дней между %s и %s: %d",
                    date1Str, date2Str, days));
            tvHours.setText(String.format("Часов между датами: %d", hours));
            tvMinutes.setText(String.format("Минут между датами: %d", minutes));
            tvSeconds.setText(String.format("Секунд между датами: %d", seconds));

        } catch (Exception e) {
            e.printStackTrace();
            tvDays.setText("Ошибка при расчете разницы дат");
        }
    }
}