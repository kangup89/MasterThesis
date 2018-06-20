package alpha.dvpis.org.hmt.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import alpha.dvpis.org.hmt.R;
import alpha.dvpis.org.hmt.api.MeasurementSetting;
import alpha.dvpis.org.hmt.api.Reminder;
import alpha.dvpis.org.hmt.service.HttpRequests;
import alpha.dvpis.org.hmt.unused.TestData;

public class Login extends AppCompatActivity {
    EditText text_username;
    EditText text_password;
    Button button_login;
    String username;
    String password;
    //AuthTokenInfo tokenInfo;
    String token;
    String[] primaryNames;

    HttpEntity<String> request;
    RestTemplate restTemplate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        text_username = (EditText) findViewById(R.id.text_username);
        text_password = (EditText) findViewById(R.id.text_password);
        button_login = (Button) findViewById(R.id.button_login);

        final TestData testData = new TestData();

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = text_username.getText().toString();
                password = text_password.getText().toString();

                //tokenInfo = httpRequest.authenticateUser_execute();
                /*httpRequest.authenticateUser.execute();
                tokenInfo = httpRequest.getTokenInfo();*/
                /*AuthenticateUser authenticateUser = new AuthenticateUser();
                authenticateUser.execute();*/

                HttpRequests httpRequests = new HttpRequests(new HttpRequests.AsyncResponse() {
                    /*public void processFinish(AuthTokenInfo output) {
                        tokenInfo = output;

                        if(tokenInfo != null){
                            System.out.println("Login: \n" + tokenInfo.toString());
                        }else{
                            System.out.println("Error in Login!");
                        }*/
                    @Override
                    public void getMeasurementsByPeriod(int[] measurements){}
                    @Override
                    public void getMeasurementsSetting(MeasurementSetting setting) {}
                    @Override
                    public void getReminder(Reminder reminder) {}
                    public void ResourceAccessException(){
                        //System.out.println("Time Out! The server is not started!");
                        //Toast.makeText(Login.this, "Time Out! The server is not started!", Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void authenticateUser(String token_out, String[] primaryNames_out) {
                        token = token_out;
                        primaryNames = primaryNames_out;

                        String testUsername = testData.testUsername;
                        String testPassword = testData.testPassword;

                        /*if(!username.equals("")) {
                            Toast.makeText(getApplicationContext(), "Incorrect Username", Toast.LENGTH_LONG).show();
                        } else if(!password.equals("")) {
                            Toast.makeText(getApplicationContext(), "Incorrect Password", Toast.LENGTH_LONG).show();*/
                        if(token == null){
                            System.out.println("Error in Login!");
                            Toast.makeText(getApplicationContext(), "Invalid Username or Password!", Toast.LENGTH_LONG).show();
                        } else {
                        /*Intent intent = new Intent(getApplicationContext(), PrimaryUsers.class);
                        startActivityForResult(intent, 1001);*/
                            System.out.println("Login: \n" + token);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(getApplicationContext(), PrimaryUsers.class);
                                    //intent.putExtra("access_token", tokenInfo.getAccess_token());
                                    intent.putExtra("token", token);
                                    System.out.println("token1: " + token);
                                    intent.putExtra("primaryNames", primaryNames);
                                    startActivityForResult(intent, 1001);
                                }
                            }, 200);

                        }
                    }
                });

                Log.i("NimbleDroidV1", "Scenario.begin login");

                httpRequests.execute("authenticateUser", username, password);

                Log.i("NimbleDroidV1", "Scenario.end login");

            }
        });
    }
}
