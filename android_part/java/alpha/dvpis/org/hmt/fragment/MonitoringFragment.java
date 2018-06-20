package alpha.dvpis.org.hmt.fragment;

import android.app.DatePickerDialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import alpha.dvpis.org.hmt.R;
import alpha.dvpis.org.hmt.api.MeasurementSetting;
import alpha.dvpis.org.hmt.api.Reminder;
import alpha.dvpis.org.hmt.service.HttpRequests;
import alpha.dvpis.org.hmt.unused.TestData;

public class MonitoringFragment extends Fragment {
    TextView from_date;
    Button from_button;

    DatePickerDialog datePickerDialog;
    Date date;

    LineGraphSeries<DataPoint> series;
    GraphView graph;

    Spinner spinner;

    String[] measurementTypes;
    String primaryName;
    int[] measurement_values;

    TestData testData;

    String type;
    int type_int;
    String type_str;
    String curDate_str;

    MeasurementSetting setting;
    String token;

    Long curDateMilli;
    int curYear;
    int curMonth;
    int curDay;

    HttpRequests getMeasurementValues;
    HttpRequests getReminder;

    Date parseDate;
    Date[] days;
    String[] days_str;

    int oneDayMilli = 1000 * 60 * 60 * 24;

    public static MonitoringFragment newInstance(String token, String name) {
        
        Bundle args = new Bundle();

        MonitoringFragment fragment = new MonitoringFragment();

        args.putString("name", name);
        args.putString("token", token);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        token = getArguments().getString("token");
        primaryName = getArguments().getString("name");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.activity_monitoring, container, false);

        testData = new TestData();

        from_date = (TextView) view.findViewById(R.id.textView);
        from_button = (Button) view.findViewById(R.id.button);

        graph = (GraphView) view.findViewById(R.id.graph);

        spinner = (Spinner) view.findViewById(R.id.spinner);
        spinner.setPrompt("Measurement types");

        final HttpRequests getMeasurementsSetting = new HttpRequests(new HttpRequests.AsyncResponse() {
            @Override
            public void getMeasurementsByPeriod(int[] measurements) {}
            @Override
            public void authenticateUser(String token_out, String[] primaryNames_out) {}
            @Override
            public void getReminder(Reminder reminder) {}
            public void ResourceAccessException(){}

            @Override
            public void getMeasurementsSetting(MeasurementSetting setting_out) {
                setting = setting_out;

                measurementTypes = setting.getDescriptions();
                type = measurementTypes[0];
                type_int = 0;

                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        getActivity(),
                        android.R.layout.simple_list_item_1,
                        measurementTypes
                );

                spinner.setAdapter(adapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        type = (String) spinner.getSelectedItem();
                        type_int = position;
                        deleteGraph();
                        datePickerDialog.getDatePicker().setMinDate(setting.getStartDates()[position]);
                        datePickerDialog.getDatePicker().setMaxDate(setting.getLastDates()[position]);

                        type_str = String.valueOf(setting.getGroups()[type_int]);
                        curDate_str = String.valueOf(curDateMilli);

                        getMeasurementValues = new HttpRequests(new HttpRequests.AsyncResponse() {

                            @Override
                            public void authenticateUser(String token, String[] primaryNames) {}
                            @Override
                            public void getMeasurementsSetting(MeasurementSetting setting) {}
                            @Override
                            public void getReminder(Reminder reminder) {}
                            public void ResourceAccessException(){}

                            @Override
                            public void getMeasurementsByPeriod(int[] measurements) {
                                measurement_values = measurements;
                                days = new Date[5];
                                days_str = new String[5];
                                Date d = parseDate;

                                for(int i = 0; i < 5; i++){
                                    days_str[i] = String.valueOf(d.getDate());
                                    days[i] = d;
                                    d = new Date(d.getTime() + oneDayMilli);
                                }
                                deleteGraph();
                                drawGraph(measurement_values, curDay, days, days_str);
                            }
                        });
                        getMeasurementValues.execute("getMeasurementsByPeriod", token, primaryName, type_str, curDate_str, "5");

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                date = new Date();
                Calendar cal=Calendar.getInstance();
                cal.setTimeInMillis(setting.getLastDates()[0]);
                curDateMilli = setting.getLastDates()[0] - (oneDayMilli * 4);
                parseDate = new Date(curDateMilli);

                curYear = parseDate.getYear() + 1900;
                curMonth = parseDate.getMonth() + 1;
                curDay = parseDate.getDate();

                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String dateStr = dayOfMonth + "/" + month + "/" + year;
                        from_date.setText(dateStr);

                        curYear = year;
                        curMonth = month;
                        curDay = dayOfMonth;

                        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        parseDate = null;
                        try {
                            parseDate = formatter.parse(dateStr);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        curDateMilli = parseDate.getTime();
                        while(curDateMilli >= (setting.getLastDates()[type_int]-(oneDayMilli * 4))){
                            curDateMilli = curDateMilli - oneDayMilli;
                        }
                        type_str = String.valueOf(setting.getGroups()[type_int]);
                        curDate_str = String.valueOf(curDateMilli);

                        getMeasurementValues = new HttpRequests(new HttpRequests.AsyncResponse() {
                            @Override
                            public void authenticateUser(String token, String[] primaryNames) {}
                            @Override
                            public void getMeasurementsSetting(MeasurementSetting setting) {}
                            @Override
                            public void getReminder(Reminder reminder) {}
                            public void ResourceAccessException(){}
                            @Override
                            public void getMeasurementsByPeriod(int[] measurements) {
                                measurement_values = measurements;
                                days = new Date[5];
                                days_str = new String[5];
                                Date d = new Date(curDateMilli);
                                for(int i = 0; i < 5; i++){
                                    days_str[i] = String.valueOf(d.getDate());
                                    days[i] = d;
                                    d = new Date(d.getTime() + oneDayMilli);
                                }
                                deleteGraph();
                                drawGraph(measurement_values, curDay, days, days_str);
                            }

                        });
                        getMeasurementValues.execute("getMeasurementsByPeriod", token, primaryName, type_str, curDate_str, "5");

                    }
                }, date.getYear()+1900, date.getMonth(), date.getDay());

                datePickerDialog.getDatePicker().setMinDate(setting.getStartDates()[0]);
                datePickerDialog.getDatePicker().setMaxDate(setting.getLastDates()[0]);

                datePickerDialog.updateDate(curYear, curMonth, curDay);
                from_date.setText(curDay + "/" + curMonth + "/" + curYear);

                from_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datePickerDialog.show();
                    }
                });

                getReminder = new HttpRequests(new HttpRequests.AsyncResponse() {

                    @Override
                    public void authenticateUser(String token, String[] primaryNames) {}
                    @Override
                    public void getMeasurementsSetting(MeasurementSetting setting) {}
                    @Override
                    public void getMeasurementsByPeriod(int[] measurements) {}
                    public void ResourceAccessException(){}
                    @Override
                    public void getReminder(Reminder reminder) {}
                });
            }
        });

        getMeasurementsSetting.execute("getMeasurementsSetting", token, primaryName);

        return view;
    }

    public void drawGraph(int[] measurement_values, int startDate, Date[] days, String[] days_str){

        String[] ylabel = new String[measurement_values.length];

        for(int i = 0; i < measurement_values.length; i++){
            ylabel[i] = days_str[i];
        }

        series = new LineGraphSeries<DataPoint>();
        for(int i = 0; i < measurement_values.length; i++) {
            series.appendData(new DataPoint(i, measurement_values[i]), true, 180);
        }

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(ylabel);
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        graph.getGridLabelRenderer().setNumHorizontalLabels(ylabel.length);

        graph.addSeries(series);
    }

    public void deleteGraph(){
        graph.removeAllSeries();
    }

}
