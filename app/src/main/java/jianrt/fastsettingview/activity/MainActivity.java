package jianrt.fastsettingview.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import jianrt.fastsettingview.R;
import jianrt.fastsettingview.view.FastSettingView;

public class MainActivity extends Activity {


    private FastSettingView fastSettingView;
    private Button clear, sure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        sure = (Button) findViewById(R.id.sure);
        clear = (Button) findViewById(R.id.clear);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double[] result = fastSettingView.getResult();
                StringBuffer stringBuffer = new StringBuffer();
                for (double d : result) {
                    stringBuffer.append(d + " ");
                }
                Toast.makeText(MainActivity.this, stringBuffer.toString(), Toast.LENGTH_LONG).show();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fastSettingView.clearValues();
            }
        });
        fastSettingView = (FastSettingView) findViewById(R.id.fastSettingView);
        fastSettingView.setCancalViewListener(new FastSettingView.CancalViewListener() {
            @Override
            public void isEnable(boolean isok) {

                if (isok) {
                    clear.setEnabled(true);
                    clear.setTextColor(Color.WHITE);
                } else {
                    clear.setEnabled(false);
                    clear.setTextColor(Color.BLACK);
                }
            }
        });
    }
}
