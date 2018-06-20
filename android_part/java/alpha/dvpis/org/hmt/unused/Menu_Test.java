package alpha.dvpis.org.hmt.unused;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import alpha.dvpis.org.hmt.R;

public class Menu_Test extends AppCompatActivity {

    static final String[] Menu = {"Prirmary Users", "Monitoring", "Video Chat", "Setting"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__test);

        /*ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Menu);

        ListFragment menuListFrgmt = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.menulistfragment);
        menuListFrgmt.setListAdapter(adapter);*/
    }
}
