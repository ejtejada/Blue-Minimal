package de.baumann.thema.helpers;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import de.baumann.thema.R;

public class CustomListAdapter_Wallpaper extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemTITLE;
    private final String[] itemDES;
    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private final int[] itemPATH;
    private final int[] itemPATHPREV;

    public CustomListAdapter_Wallpaper(Activity context, String[] itemTITLE, String[] itemDES, int[] itemPATH, int[] itemPATHPREV) {
        super(context, R.layout.sound_item_list, itemTITLE);

        this.context=context;
        this.itemTITLE=itemTITLE;
        this.itemDES=itemDES;
        this.itemPATH=itemPATH;
        this.itemPATHPREV=itemPATHPREV;
    }

    @NonNull
    public View getView(int position, View rowView, @NonNull ViewGroup parent) {

        if (rowView == null) {
            LayoutInflater infInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = infInflater.inflate(R.layout.wallpaper_item_list, parent, false);
        }

        TextView textTITLE = (TextView) rowView.findViewById(R.id.textView_title);
        TextView textDES = (TextView) rowView.findViewById(R.id.textView_des);
        ImageView preview = (ImageView) rowView.findViewById(R.id.preview);

        textTITLE.setText(itemTITLE[position]);
        textDES.setText(itemDES[position]);
        preview.setImageResource(itemPATHPREV[position]);
        return rowView;
    }
}
