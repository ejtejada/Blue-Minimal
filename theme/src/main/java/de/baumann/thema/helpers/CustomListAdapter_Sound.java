package de.baumann.thema.helpers;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import de.baumann.thema.R;

public class CustomListAdapter_Sound extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemTITLE;
    private final String[] itemURL;
    private final String[] itemDES;
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private final String[] itemFN;

    public CustomListAdapter_Sound(Activity context, String[] itemTITLE, String[] itemURL, String[] itemDES, String[] itemFN) {
        super(context, R.layout.sound_item_list, itemTITLE);

        this.context=context;
        this.itemTITLE=itemTITLE;
        this.itemURL=itemURL;
        this.itemDES=itemDES;
        this.itemFN=itemFN;
    }

    @NonNull
    public View getView(int position, View rowView, @NonNull ViewGroup parent) {

        if (rowView == null) {
            LayoutInflater infInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = infInflater.inflate(R.layout.sound_item_list, parent, false);
        }

        TextView textTITLE = (TextView) rowView.findViewById(R.id.textView_title);
        TextView textURL = (TextView) rowView.findViewById(R.id.textView_url);
        TextView textDES = (TextView) rowView.findViewById(R.id.textView_des);

        textTITLE.setText(itemTITLE[position]);
        textURL.setText(itemURL[position]);
        textDES.setText(itemDES[position]);
        return rowView;
    }
}
