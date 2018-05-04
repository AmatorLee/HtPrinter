package com.amator.htprinter.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.amator.htprinter.R;

/**
 * Created by AmatorLee on 2018/5/3.
 */
public class ScanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        if (getIntent().getStringExtra("result") == null) {
            finish();
        }
        ((Toolbar) findViewById(R.id.toolbar_scan)).setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        String result = getIntent().getStringExtra("result");
        ((TextView) findViewById(R.id.txt_scan_result)).setText(result);
    }
}
