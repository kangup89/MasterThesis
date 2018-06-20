package alpha.dvpis.org.hmt.unused;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Date;

import alpha.dvpis.org.hmt.R;

public class Monitoring extends AppCompatActivity {
    TextView from_date;
    Button from_button;

    DatePickerDialog datePickerDialog;
    Date date;

    LineGraphSeries<DataPoint> series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring);

        from_date = (TextView) findViewById(R.id.textView);
        from_button = (Button) findViewById(R.id.button);

        date = new Date();

        from_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(Monitoring.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        from_date.setText("Date: " + year + "." + month + "." + dayOfMonth);
                    }
                }, date.getYear()+1900, date.getMonth(), date.getDay());
                datePickerDialog.show();

                int[] y;
                int x;
                //x = -5.0;

                //y = new int[]{75,120,100,70,115,100,80,125,120,82,130,110,92,150,130,90,145,100,95,170,110,92,158,106,128};
                y = new int[]{75,120,100,70,115,100,80,125,120,82,130,110};

                GraphView graph = (GraphView) findViewById(R.id.graph);
                series = new LineGraphSeries<DataPoint>();
                /*for(int i = 0; i < 500; i++) {
                    x = x + 0.1;
                    y = Math.sin(x);
                    series.appendData(new DataPoint(x, y), true, 500);
                }*/
                for(int i = 0; i < 12; i++) {
                    series.appendData(new DataPoint(i, y[i]), true, 180);
                }
                graph.addSeries(series);

            }
        });



    }


}
