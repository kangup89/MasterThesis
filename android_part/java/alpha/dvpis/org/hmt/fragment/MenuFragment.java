package alpha.dvpis.org.hmt.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import alpha.dvpis.org.hmt.R;
import alpha.dvpis.org.hmt.unused.Monitoring;
import alpha.dvpis.org.hmt.activity.PrimaryUsers;
import alpha.dvpis.org.hmt.unused.Pilot_Frag;
import alpha.dvpis.org.hmt.unused.VideoChat;

public class MenuFragment extends Fragment {

    static final String[] Menu = {"Prirmary Users", "Monitoring", "Video Chat", "Setting"};
    ListView listView;
    MenuAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.activity_menu_fragment, container, false);

        /*ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, Menu);*/

        listView = (ListView) view.findViewById(R.id.list);
        adapter = new MenuAdapter();
        listView.setAdapter(adapter);

        listView.setChoiceMode(listView.CHOICE_MODE_SINGLE);
        listView.setDivider(new ColorDrawable(Color.BLACK));
        listView.setDividerHeight(2);

        return view;
    }

    class MenuAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return Menu.length;
        }

        @Override
        public Object getItem(int position) {
            return Menu[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            TextView view = new TextView(getActivity());
            view.setText(Menu[position]);
            view.setTextSize(30f);
            view.setTextColor(Color.WHITE);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String menu = (String) adapter.getItem(position);
                    Toast.makeText(getActivity(), "Selected menu: " + menu, Toast.LENGTH_LONG).show();
                    if (position == 0) {
                        Intent intent = new Intent(getActivity(), PrimaryUsers.class);
                        startActivityForResult(intent, 1002);
                    } else if (position == 1) {
                        Intent intent = new Intent(getActivity(), Monitoring.class);
                        startActivityForResult(intent, 1002);
                    } else if (position == 2) {
                        Intent intent = new Intent(getActivity(), VideoChat.class);
                        startActivityForResult(intent, 1002);
                    } else if (position == 3) {
                        Intent intent = new Intent(getActivity(), Pilot_Frag.class);
                        startActivityForResult(intent, 1002);
                    }
                }
            });

            return view;
        }
    }

    /*@Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Toast.makeText(this.getActivity(), "Chose " + position, Toast.LENGTH_LONG).show();
    }*/

}
