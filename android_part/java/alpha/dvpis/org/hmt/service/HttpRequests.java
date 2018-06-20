package alpha.dvpis.org.hmt.service;

import android.os.AsyncTask;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import alpha.dvpis.org.hmt.api.MeasurementSetting;
import alpha.dvpis.org.hmt.api.Reminder;

/**
 * Created by kangup on 2016-12-31.
 */

public class HttpRequests extends AsyncTask<String, String, String> {

    public interface AsyncResponse {
        void authenticateUser(String token, String[] primaryNames);
        void getMeasurementsByPeriod(int[] measurements);
        void getMeasurementsSetting(MeasurementSetting setting);
        void getReminder(Reminder reminder);
        void ResourceAccessException();
    }

    public AsyncResponse delegate = null;

    public HttpRequests(AsyncResponse delegate){
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        this.delegate = delegate;
    }

    RestTemplate restTemplate;
    HttpEntity<String> httpEntity;
    HttpEntity<Reminder> httpEntity_reminder;
    HttpHeaders header;
    String token;
    String[] primaryNames;
    int[] measurements;
    MeasurementSetting setting;
    Reminder reminder;
    Reminder reminder_httpentity;
    String baseUrl = "http://dvpis.rpbqq3term.eu-west-1.elasticbeanstalk.com/";


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        switch (s){
            case "authenticateUser":
                delegate.authenticateUser(token, primaryNames);
            case "getMeasurementsSetting":
                delegate.getMeasurementsSetting(setting);
            case "getMeasurementsByPeriod":
                delegate.getMeasurementsByPeriod(measurements);
            case "getReminder":
                delegate.getReminder(reminder);
            case "ResourceAccessException":
                delegate.ResourceAccessException();
            default:
                break;
        }
    }

    @Override
    protected String doInBackground(String... params) {

        switch (params[0]){
            case "authenticateUser":
                httpEntity = new HttpEntity<String>(new HttpHeaders());

                ResponseEntity<String[]> response_auth;

                try {
                    response_auth = restTemplate.exchange(baseUrl + "/authenticateUser/test/test", HttpMethod.GET, httpEntity, String[].class);
                }catch (ResourceAccessException e){
                    return "ResourceAccessException";
                }

                token = response_auth.getHeaders().getAuthorization();
                primaryNames = response_auth.getBody();
                return params[0];
            case "getMeasurementsSetting":
                header = new HttpHeaders();
                header.add("Authorization", params[1]);
                header.add("primaryUser", params[2]);
                httpEntity = new HttpEntity<String>(header);

                ResponseEntity<MeasurementSetting> response_setting = restTemplate.exchange(baseUrl+"/getMeasurementsSetting/", HttpMethod.GET, httpEntity, MeasurementSetting.class);

                setting = response_setting.getBody();
                return params[0];

            case "getMeasurementsByPeriod":
                System.out.println("parameters!: " + params[0] + " " + params[1] + " " + params[2] + " " + params[3] + " " + params[4] + " " + params[5]);
                header = new HttpHeaders();
                header.add("Authorization", params[1]);
                header.add("primaryUser", params[2]);
                httpEntity = new HttpEntity<String>(header);

                ResponseEntity<int[]> response_meas = restTemplate.exchange(baseUrl+"/getMeasurementsByPeriod/" + params[3] + "/" + params[4] + "/" + params[5], HttpMethod.GET, httpEntity, int[].class);
                measurements = response_meas.getBody();
                return params[0];

            case "setReminder":
                header = new HttpHeaders();
                header.add("Authorization", params[1]);
                header.setContentType(MediaType.APPLICATION_JSON);

                reminder_httpentity = new Reminder(Long.parseLong(params[2]), params[3]);

                httpEntity_reminder = new HttpEntity<Reminder>(reminder_httpentity, header);

                ResponseEntity<Reminder> response_setReminder = restTemplate.exchange(baseUrl+"/setReminder", HttpMethod.POST, httpEntity_reminder, Reminder.class);
                return params[0];

            case "getReminder":
                header = new HttpHeaders();
                header.add("Authorization", params[1]);
                httpEntity = new HttpEntity<String>(header);

                ResponseEntity<Reminder> response_reminder = restTemplate.exchange(baseUrl+"/getReminder", HttpMethod.GET, httpEntity, Reminder.class);
                reminder = response_reminder.getBody();
                return params[0];

            case "deleteReminder":
                header = new HttpHeaders();
                header.add("Authorization", params[1]);
                header.setContentType(MediaType.APPLICATION_JSON);

                reminder_httpentity = new Reminder(Long.parseLong(params[2]), params[3]);

                httpEntity_reminder = new HttpEntity<Reminder>(reminder_httpentity, header);

                ResponseEntity<Reminder> response_deleteReminder = restTemplate.exchange(baseUrl+"/deleteReminder", HttpMethod.POST, httpEntity_reminder, Reminder.class);
                return params[0];
        }
        return null;
    }
}
