package cat.stratosphere.isprime;

import android.content.ClipboardManager;
import android.content.ClipData;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        final TextView tv = findViewById(R.id.sample_text);
        final EditText et = findViewById(R.id.editText);
        final NumberPicker np1 = findViewById(R.id.np1);
        final NumberPicker np2 = findViewById(R.id.np2);
        final NumberPicker np3 = findViewById(R.id.np3);
        final Calendar cld = Calendar.getInstance();
        Button bt = findViewById(R.id.button);

        update(cld, tv);

        np1.setMinValue(0);
        np2.setMinValue(1);
        np3.setMinValue(1);

        np1.setMaxValue(3000);
        np2.setMaxValue(12);
        np3.setMaxValue(cld.getActualMaximum(Calendar.DAY_OF_MONTH));

        np1.setWrapSelectorWheel(true);
        np2.setWrapSelectorWheel(true);
        np3.setWrapSelectorWheel(true);

        np1.setValue(cld.get(Calendar.YEAR));
        np2.setValue(cld.get(Calendar.MONTH) + 1);
        np3.setValue(cld.get(Calendar.DATE));

        //tv.setText(stringFromJNI());

        np1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                cld.set (Calendar.YEAR, newVal);
                update(cld, tv);
            }
        });
        np2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                cld.set (Calendar.MONTH, newVal - 1);
                np3.setMaxValue(cld.getActualMaximum(Calendar.DAY_OF_MONTH));
                update(cld, tv);
            }
        });
        np3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                cld.set (Calendar.DATE, newVal);
                update(cld, tv);
            }
        });

        bt.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar today = Calendar.getInstance();
                String s;
                int num = today.get(Calendar.YEAR) * 10000
                        + (today.get(Calendar.MONTH) + 1) * 100 + today.get(Calendar.DATE);
                IsPrime isp = new IsPrime(num);
                if (isp.isPrime()) {
                    s = "#今天是质数吗# 今天，是一个质数。\n";
                } else {
                    s = "#今天是质数吗# 今天，不是一个质数。\n";
                }
                s += isp.toString();
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("isPrime", s);
                clipboardManager.setPrimaryClip(clipData);
            }
        });

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                if (Long.valueOf(et.getText().toString())!= 0) {
                    update(et, tv);
                }
            }
        });
    }
    public void update (Calendar cld, TextView tv) {
        String s = new IsPrime (cld.get(Calendar.YEAR) * 10000
                + (cld.get(Calendar.MONTH) + 1) * 100 + cld.get(Calendar.DATE)).toString();
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("isPrime", s);
        clipboardManager.setPrimaryClip(clipData);
        tv.setText(s);
    }
    public void update (EditText et, TextView tv) {
        String s = new IsPrime (Long.valueOf(et.getText().toString())).toString();
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("isPrime", s);
        clipboardManager.setPrimaryClip(clipData);
        tv.setText(s);
    }
}
