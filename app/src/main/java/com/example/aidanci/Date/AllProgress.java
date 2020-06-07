package com.example.aidanci.Date;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import com.example.aidanci.R;
import com.hanks.htextview.base.HTextView;
import com.qmuiteam.qmui.widget.QMUIProgressBar;

import java.math.BigDecimal;

public class AllProgress extends AppCompatActivity {

    private QMUIProgressBar cet4;
    private QMUIProgressBar cet6;
    private QMUIProgressBar cet4_import;
    private QMUIProgressBar cet6_import;
    private QMUIProgressBar kaoyan;
    private QMUIProgressBar vocabulary;

    private HTextView hcet4;
    private HTextView hcet6;
    private HTextView hcet4_import;
    private HTextView hcet6_import;
    private HTextView hkaoyan;
    private HTextView hvocabulary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_progress);

        ActionBar actionBar  = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
        cet4        = findViewById(R.id.cet4);
        cet6        = findViewById(R.id.cet6);
        cet4_import = findViewById(R.id.cet4_import);
        cet6_import = findViewById(R.id.cet6_import);
        kaoyan      = findViewById(R.id.kaoyan);
        vocabulary  = findViewById(R.id.vocabulary);

        hcet4       = findViewById(R.id.hcet4);
        hcet4.animateText("四级");

        hcet6       = findViewById(R.id.hcet6);
        hcet6.animateText("六级");

        hcet4_import= findViewById(R.id.hcet4_import);
        hcet4_import.animateText("专四");

        hcet6_import= findViewById(R.id.hcet6_import);
        hcet6_import.animateText("专六");

        hkaoyan      = findViewById(R.id.hkaoyan);
        hkaoyan.animateText("考研");

        hvocabulary  = findViewById(R.id.hvocabulary);
        hvocabulary.animateText("专八");


        cet4        = initciku("cet4",cet4);
        cet6        = initciku("cet6",cet6);
        cet4_import = initciku("cet4_import",cet4_import);
        cet6_import = initciku("cet6_import",cet6_import);
        kaoyan      = initciku("kaoyan",kaoyan);
        vocabulary  = initciku("vocabulary",vocabulary);

    }

    private QMUIProgressBar initciku(String ciku,QMUIProgressBar qmuiProgressBar){
        Cursor cursor;
        int all,done;
        qmuiProgressBar.setTextColor(Color.parseColor("#00aeff"));
        qmuiProgressBar.setTextSize(50);
        qmuiProgressBar.setQMUIProgressBarTextGenerator(new QMUIProgressBar.QMUIProgressBarTextGenerator() {
            @Override
            public String generateText(QMUIProgressBar progressBar, int value, int maxValue) {
                int v = (int) ((new BigDecimal((float) value / maxValue).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())*100);
                String s = v+"%";
                return s;
            }
        });
        cursor = CursorGetAll.limitexecute(ciku);
        all  = cursor.getCount();
        cursor.close();
        cursor = CursorGetAll.GetlimitedCursor(ciku,"Selected is 1");
        done  = cursor.getCount();
        cursor.close();
        qmuiProgressBar.setProgress(done/all);
        return qmuiProgressBar;
    }
}
