package alpha.dvpis.org.hmt.unused;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import alpha.dvpis.org.hmt.R;

public class PrimaryUsers_Frag extends Fragment {
    ListView listView;
    TestData testData;
    NameAdapter adapter;

    public static String[] puNames = {"Francisco Juliano", "Maria Perez", "Luisa Jimenez"};

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.activity_primary_users, container, false);

        /*ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, Menu);*/

        listView = (ListView) view.findViewById(R.id.listView);
        adapter = new NameAdapter();
        listView.setAdapter(adapter);

        listView.setChoiceMode(listView.CHOICE_MODE_SINGLE);
        listView.setDivider(new ColorDrawable(Color.BLACK));
        listView.setDividerHeight(2);

        //testData = new TestData();

        return view;
    }

    class NameAdapter extends BaseAdapter {
        String[] names = puNames;

        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public Object getItem(int position) {
            return names[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            TextView view = new TextView(getActivity());
            view.setText(names[position]);
            view.setTextSize(30f);
            view.setTextColor(Color.BLACK);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = (String) adapter.getItem(position);
                    Toast.makeText(getActivity(), "Selected name: " + name , Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), Data_Or_VideoChat.class);
                    startActivityForResult(intent, 1002);
                }
            });

            return view;
        }


    }

}
