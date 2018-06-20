package alpha.dvpis.org.hmt.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import alpha.dvpis.org.hmt.api.AuthTokenInfo;
import alpha.dvpis.org.hmt.service.CustomAdapter;
import alpha.dvpis.org.hmt.R;
import alpha.dvpis.org.hmt.unused.TestData;

public class PrimaryUsers extends AppCompatActivity {
    ListView listView;
    TestData testData;
    NameAdapter adapter;
    String[] primaryNames;
    int icon;
    AuthTokenInfo tokenInfo;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary_users);

        setTitle("Primary User List");

        Intent intent = getIntent();
        //tokenInfo = (AuthTokenInfo) intent.getParcelableExtra("tokenInfo");
        token = intent.getStringExtra("token");
        System.out.println("token2: " + token);

        primaryNames = intent.getStringArrayExtra("primaryNames");

        testData = new TestData();
        //names = testData.puNames;
        icon = R.drawable.icon_home;

        listView = (ListView) findViewById(R.id.listView);
        adapter = new NameAdapter();
        listView.setAdapter(adapter);

        listView.setChoiceMode(listView.CHOICE_MODE_SINGLE);
        listView.setDivider(new ColorDrawable(Color.WHITE));
        listView.setDividerHeight(2);

        /*CustomAdapter adapter = new CustomAdapter(this, names, icon);
        listView.setAdapter(adapter);*/
    }

    class NameAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return primaryNames.length;
        }

        @Override
        public Object getItem(int position) {
            return primaryNames[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            /*TextView view = new TextView(getApplicationContext());
            view.setText(names[position]);
            view.setTextSize(30f);
            view.setTextColor(Color.BLACK);*/

            CustomAdapter view = new CustomAdapter(getApplicationContext());
            view.setText(primaryNames[position]);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String primaryName = (String) adapter.getItem(position);
                    //Toast.makeText(getApplicationContext(), "Selected name: " + primaryName , Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), Navi_Main.class);
                    intent.putExtra("token", token);
                    System.out.println("token3: " + token);

                    intent.putExtra("primaryName", primaryName);
                    intent.putExtra("primaryNames", primaryNames);
                    startActivityForResult(intent, 1002);

                }
            });

            return view;
        }


    }

}
