package alpha.dvpis.org.hmt.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import alpha.dvpis.org.hmt.R;
import alpha.dvpis.org.hmt.api.MeasurementSetting;
import alpha.dvpis.org.hmt.api.Reminder;
import alpha.dvpis.org.hmt.service.HttpRequests;

public class ReminderFragment extends DialogFragment {

    int year, month, dayOfMonth, hourOfDay, minute;
    Date date;
    long millis;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    Button button, button2;
    TextView textView, textView2;
    EditText editText;

    String dateStr;
    String timeStr;

    String description;

    String token;
    String state;

    HttpRequests setReminder;

    /*public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_setremind_dialog, container, false);

        return view;
    }*/

    public static ReminderFragment newInstance(String token, String state) {

        Bundle args = new Bundle();

        ReminderFragment fragment = new ReminderFragment();

        args.putString("token", token);
        args.putString("state", state);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(
                getActivity());
        LayoutInflater mLayoutInflater = getActivity().getLayoutInflater();
        token = getArguments().getString("token");
        state = getArguments().getString("state");

        if(state.equals("setReminder")) {
            System.out.println("Dialog set call");
            View view = mLayoutInflater.inflate(R.layout.activity_setremind_dialog, null);
            mBuilder.setView(view)
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Yes 버튼을 눌렀을때 발생하는 이벤트
                            description = editText.getText().toString();
                            if (description == null){
                                Toast.makeText(getContext(), "Please input description!", Toast.LENGTH_SHORT).show();

                            } else {
                                //Toast.makeText(getContext(), String.format("%d", millis), Toast.LENGTH_SHORT).show();
                                System.out.println("millis: " + millis+ "   description: " + description);
                                setReminder.execute("setReminder", token, String.format("%d", millis), description);
                            }
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        // No 버튼을 눌렀을때 발생하는 이벤트
                    }
            });
            mBuilder.setTitle("Reminder");

            button = (Button) view.findViewById(R.id.button);
            button2 = (Button) view.findViewById(R.id.button2);

            textView = (TextView) view.findViewById(R.id.textView);
            textView2 = (TextView) view.findViewById(R.id.textView2);

            editText = (EditText) view.findViewById(R.id.editText);

            Calendar cal=Calendar.getInstance();

            year=cal.get(Calendar.YEAR);
            month=cal.get(Calendar.MONTH);
            dayOfMonth=cal.get(Calendar.DAY_OF_MONTH);
            hourOfDay=cal.get(Calendar.HOUR_OF_DAY);
            minute=cal.get(Calendar.MINUTE);

            System.out.println(dayOfMonth + "/" + month+1 + "/" + year + "  " +  hourOfDay + ":" + minute);

            dateStr = String.format("%d/%d/%d", dayOfMonth, month+1, year);
            timeStr = String.format("%d:%d", hourOfDay, minute);

            date = new Date(year-1900, month, dayOfMonth, hourOfDay+1, minute);
            millis = date.getTime();

            System.out.println(millis);

            textView.setText(dateStr);
            textView2.setText(timeStr);

            datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int yearSet, int monthSet, int dayOfMonthSet) {
                    year = yearSet;
                    month = monthSet;
                    dayOfMonth = dayOfMonthSet;
                    dateStr = String.format("%d/%d/%d", dayOfMonth, month+1, year);
                    date = new Date(year-1900, month, dayOfMonth, hourOfDay+1, minute);
                    millis = date.getTime();

                    Toast.makeText(getContext(), dateStr, Toast.LENGTH_SHORT).show();
                    textView.setText(dateStr);
                }
            },year,month-1,dayOfMonth);

            timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDaySet, int minuteSet) {
                    hourOfDay = hourOfDaySet;
                    minute = minuteSet;
                    timeStr = String.format("%d:%d", hourOfDay, minute);
                    date = new Date(year-1900, month, dayOfMonth, hourOfDay+1, minute);
                    millis = date.getTime();

                    Toast.makeText(getContext(), timeStr, Toast.LENGTH_SHORT).show();
                    textView2.setText(timeStr);
                }
            },hourOfDay,minute,true);

            datePickerDialog.getDatePicker().setMinDate(millis);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    datePickerDialog.show();
                }
            });

            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timePickerDialog.show();
                }
            });
        } else {
            System.out.println("Dialog active call");
            View view = mLayoutInflater.inflate(R.layout.activity_activeremind_dialog, null);
            mBuilder.setView(view)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Yes 버튼을 눌렀을때 발생하는 이벤트
                        }
                    });
            mBuilder.setTitle("Reminder");
            textView = (TextView) view.findViewById(R.id.textView);

            textView.setText(state);
        }

        setReminder = new HttpRequests(new HttpRequests.AsyncResponse() {
            @Override
            public void authenticateUser(String token, String[] primaryNames) {}
            @Override
            public void getMeasurementsSetting(MeasurementSetting setting) {}
            @Override
            public void getMeasurementsByPeriod(int[] measurements) {}
            @Override
            public void getReminder(Reminder reminder) {}
            public void ResourceAccessException(){}

        });

        return mBuilder.create();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if(activity instanceof DialogInterface.OnDismissListener){
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }
}
