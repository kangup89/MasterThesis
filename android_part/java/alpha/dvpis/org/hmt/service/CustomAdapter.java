package alpha.dvpis.org.hmt.service;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import alpha.dvpis.org.hmt.R;

/**
 * Created by kangup on 2016-12-12.
 */

public class CustomAdapter extends LinearLayout {
    TextView txtTitle;
    ImageView imgIcon;

    public CustomAdapter(Context context) {
        super(context);

        init(context);
    }

    public CustomAdapter(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.row, this, true);

        txtTitle = (TextView) findViewById(R.id.txtTitle);
        imgIcon = (ImageView) findViewById(R.id.imgIcon);
    }

    public void setText(String text){
        txtTitle.setText(text);
    }
}
