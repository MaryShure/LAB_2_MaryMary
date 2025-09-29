package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private Button btnShowSaturdays;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datePicker = findViewById(R.id.datePicker);
        btnShowSaturdays = findViewById(R.id.btnShowSaturdays);
        tvResult = findViewById(R.id.tvResult);

        btnShowSaturdays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSaturdays();
            }
        });
    }

    private void showSaturdays() {
        int year = datePicker.getYear();
        int month = datePicker.getMonth();

        List<Calendar> saturdays = getSaturdaysInMonth(year, month);
        String result = formatSaturdaysResult(saturdays, year, month);
        tvResult.setText(result);
    }

    private List<Calendar> getSaturdaysInMonth(int year, int month) {
        List<Calendar> saturdays = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);

        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        while (calendar.get(Calendar.MONTH) == month) {
            Calendar saturday = (Calendar) calendar.clone();
            saturdays.add(saturday);
            calendar.add(Calendar.DAY_OF_MONTH, 7);
        }

        return saturdays;
    }

    private String formatSaturdaysResult(List<Calendar> saturdays, int year, int month) {
        if (saturdays.isEmpty()) {
            return "в выбранном месяце нет суббот";
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        StringBuilder result = new StringBuilder();

        result.append("Субботы в ")
                .append(getMonthName(month))
                .append(" ")
                .append(year)
                .append(":\n\n");

        for (int i = 0; i < saturdays.size(); i++) {
            Calendar saturday = saturdays.get(i);
            result.append(i + 1)
                    .append(". ")
                    .append(formatter.format(saturday.getTime()))
                    .append("\n");
        }

        return result.toString();
    }

    private String getMonthName(int month) {
        String[] monthNames = {
                "Январе", "Феврале", "Марте", "Апреле", "Мае", "Июне",
                "Июле", "Августе", "Сентябре", "Октябре", "Ноября", "Декабре"
        };
        return monthNames[month];
    }
}