package com.example.calc2_8_16_numb8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.LogPrinter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    String[] numeralSystem = { "2", "8", "16"};
    EditText value;
    int val;
    String resString;
    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        value = (EditText) findViewById(R.id.value);


        spinner = findViewById(R.id.numeral);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, numeralSystem);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);

        AsyncCaller asyncCaller = new AsyncCaller();
        asyncCaller.execute();
    }

    public class AsyncCaller  extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... parameter) {
            int myProgress = 0;
            // [... Выполните задачу в фоновом режиме, обновите переменную myProgress...]
            publishProgress(myProgress);
            // [... Продолжение выполнения фоновой задачи ...]
            // Верните значение, ранее переданное в метод onPostExecute
            //value
            value.addTextChangedListener(new TextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {
                    // Прописываем то, что надо выполнить после изменения текста
                    try {
                        val = Integer.parseInt(String.valueOf(s));
                        String val2 = String.format(s.toString());
                        TextView  resultText2 = (TextView) findViewById(R.id.resultText2);
                        resultText2.setText(val2);
                    } catch (final NumberFormatException e) {
                    }
                    onPostExecute(resString);
                    Log.d("dodo","NEW");
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
            });

            //spinner
            AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    // Получаем выбранный объект
                    String item = (String) parent.getItemAtPosition(position);
                    if (item.equals("2")) {
                        resString = Integer.toBinaryString(val);
                        onPostExecute(resString);
                        Log.d("dodo", "2");
                    } else if (item.equals("8")) {
                        resString = Integer.toOctalString(val);
                        onPostExecute(resString);
                        Log.d("dodo", "8");
                    } else if (item.equals("16")) {
                        resString = Integer.toHexString(val);
                        onPostExecute(resString);
                        Log.d("dodo", "16");
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            };
            spinner.setOnItemSelectedListener(itemSelectedListener);

            return resString;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // [... Обновите индикатор хода выполнения, уведомления или другой
            // элемент пользовательского интерфейса ...]
        }

        @Override
        protected void onPostExecute(String result) {
            // [... Сообщите о результате через обновление пользовательского
            // интерфейса, диалоговое окно или уведомление ...]

            TextView  resultText = (TextView) findViewById(R.id.resultText);
            resultText.setText(result);
            Log.d("dodo", "POST");
        }
    }
}