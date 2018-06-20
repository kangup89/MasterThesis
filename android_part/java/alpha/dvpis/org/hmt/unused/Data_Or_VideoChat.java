package alpha.dvpis.org.hmt.unused;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import alpha.dvpis.org.hmt.R;

public class Data_Or_VideoChat extends AppCompatActivity {

    Button button1;
    Button button2;
    //WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_or_video_chat);

        button1 = (Button) findViewById(R.id.button_to_data);
        button2 = (Button) findViewById(R.id.button_to_videochat);
        //webview = (WebView) findViewById(R.id.webView);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Monitoring.class);
                startActivityForResult(intent, 1003);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), VideoChat.class);
                startActivityForResult(intent, 1004);
            }
        });

        //webview.loadUrl("file:///android_asset/www/index.html");


    }
}
