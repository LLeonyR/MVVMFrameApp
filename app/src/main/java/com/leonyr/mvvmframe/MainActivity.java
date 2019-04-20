package com.leonyr.mvvmframe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvMain;
    MAdapter mAdapter;
    Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvMain = findViewById(R.id.rv_main);
        rvMain.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MAdapter();
        rvMain.setAdapter(mAdapter);

        random = new Random();

        findViewById(R.id.btn_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int result = random.nextInt(100);
                rvMain.scrollToPosition(0);
                mAdapter.addItem(String.valueOf(result));
            }
        });
    }


}
