package de.baumann.thema;

import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.SpannableString;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import java.io.IOException;

import de.baumann.thema.helpers.CustomListAdapter_Wallpaper;


public class FragmentWallpaper extends Fragment {

    private ImageView imgHeader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.wallpaper, container, false);

        setHasOptionsMenu(true);

        final String[] itemTITLE ={
                getString(R.string.amber) + " - Lollipop",
                getString(R.string.blue) + " - Lollipop",
                getString(R.string.green) + " - Lollipop",
                getString(R.string.grey) + " - Lollipop",
                getString(R.string.red) + " - Lollipop",
                getString(R.string.teal) + " - Lollipop",

                getString(R.string.amber) + " - Marshmallow",
                getString(R.string.blue) + " - Marshmallow",
                getString(R.string.green) + " - Marshmallow",
                getString(R.string.grey) + " - Marshmallow",
                getString(R.string.red) + " - Marshmallow",
                getString(R.string.teal) + " - Marshmallow",

                getString(R.string.amber) + " - Nougat",
                getString(R.string.blue) + " - Nougat",
                getString(R.string.green) + " - Nougat",
                getString(R.string.grey) + " - Nougat",
                getString(R.string.red) + " - Nougat",
                getString(R.string.teal) + " - Nougat",
        };

        final String[] itemDES ={
                "CC license: https://github.com/scoute-dich/Blue-Minimal/blob/master/theme/src/main/res/drawable-nodpi/wp_lp_amber.png",
                "CC license: https://github.com/scoute-dich/Blue-Minimal/blob/master/theme/src/main/res/drawable-nodpi/wp_lp_blue.png",
                "CC license: https://github.com/scoute-dich/Blue-Minimal/blob/master/theme/src/main/res/drawable-nodpi/wp_lp_green.png",
                "CC license: https://github.com/scoute-dich/Blue-Minimal/blob/master/theme/src/main/res/drawable-nodpi/wp_lp_grey.png",
                "CC license: https://github.com/scoute-dich/Blue-Minimal/blob/master/theme/src/main/res/drawable-nodpi/wp_lp_red.png",
                "CC license: https://github.com/scoute-dich/Blue-Minimal/blob/master/theme/src/main/res/drawable-nodpi/wp_lp_teal.png",

                "CC license: https://github.com/scoute-dich/Blue-Minimal/blob/master/theme/src/main/res/drawable-nodpi/wp_mm_amber.png",
                "CC license: https://github.com/scoute-dich/Blue-Minimal/blob/master/theme/src/main/res/drawable-nodpi/wp_mm_blue.png",
                "CC license: https://github.com/scoute-dich/Blue-Minimal/blob/master/theme/src/main/res/drawable-nodpi/wp_mm_green.png",
                "CC license: https://github.com/scoute-dich/Blue-Minimal/blob/master/theme/src/main/res/drawable-nodpi/wp_mm_grey.png",
                "CC license: https://github.com/scoute-dich/Blue-Minimal/blob/master/theme/src/main/res/drawable-nodpi/wp_mm_red.png",
                "CC license: https://github.com/scoute-dich/Blue-Minimal/blob/master/theme/src/main/res/drawable-nodpi/wp_mm_teal.png",

                "CC license: https://github.com/scoute-dich/Blue-Minimal/blob/master/theme/src/main/res/drawable-nodpi/wp_n_amber.png",
                "CC license: https://github.com/scoute-dich/Blue-Minimal/blob/master/theme/src/main/res/drawable-nodpi/wp_n_blue.png",
                "CC license: https://github.com/scoute-dich/Blue-Minimal/blob/master/theme/src/main/res/drawable-nodpi/wp_n_green.png",
                "CC license: https://github.com/scoute-dich/Blue-Minimal/blob/master/theme/src/main/res/drawable-nodpi/wp_n_grey.png",
                "CC license: https://github.com/scoute-dich/Blue-Minimal/blob/master/theme/src/main/res/drawable-nodpi/wp_mm_red.png",
                "CC license: https://github.com/scoute-dich/Blue-Minimal/blob/master/theme/src/main/res/drawable-nodpi/wp_mm_teal.png",
        };

        final int[] itemPATH ={
                R.drawable.wp_lp_amber,
                R.drawable.wp_lp_blue,
                R.drawable.wp_lp_green,
                R.drawable.wp_lp_grey,
                R.drawable.wp_lp_red,
                R.drawable.wp_lp_teal,

                R.drawable.wp_mm_amber,
                R.drawable.wp_mm_blue,
                R.drawable.wp_mm_green,
                R.drawable.wp_mm_grey,
                R.drawable.wp_mm_red,
                R.drawable.wp_mm_teal,

                R.drawable.wp_n_amber,
                R.drawable.wp_n_blue,
                R.drawable.wp_n_green,
                R.drawable.wp_n_grey,
                R.drawable.wp_n_red,
                R.drawable.wp_n_teal,
        };


        final int[] itemPATHPREV ={
                R.drawable.wp_lp_amber_preview,
                R.drawable.wp_lp_blue_preview,
                R.drawable.wp_lp_green_preview,
                R.drawable.wp_lp_grey_preview,
                R.drawable.wp_lp_red_preview,
                R.drawable.wp_lp_teal_preview,

                R.drawable.wp_mm_amber_preview,
                R.drawable.wp_mm_blue_preview,
                R.drawable.wp_mm_green_preview,
                R.drawable.wp_mm_grey_preview,
                R.drawable.wp_mm_red_preview,
                R.drawable.wp_mm_teal_preview,

                R.drawable.wp_n_amber_preview,
                R.drawable.wp_n_blue_preview,
                R.drawable.wp_n_green_preview,
                R.drawable.wp_n_grey_preview,
                R.drawable.wp_n_red_preview,
                R.drawable.wp_n_teal_preview,
        };

        imgHeader = (ImageView) rootView.findViewById(R.id.imageView_header);

        if(imgHeader != null) {
            TypedArray images = getResources().obtainTypedArray(R.array.splash_images);
            int choice = (int) (Math.random() * images.length());
            imgHeader.setImageResource(images.getResourceId(choice, R.drawable.wp_lp_amber));
            images.recycle();
        }

        CustomListAdapter_Wallpaper adapter=new CustomListAdapter_Wallpaper(getActivity(), itemTITLE, itemDES, itemPATH, itemPATHPREV);
        ListView listView = (ListView)rootView.findViewById(R.id.bookmarks);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                int Selecteditem= itemPATH[+position];
                assert imgHeader != null;
                imgHeader.setImageResource(Selecteditem);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final String SelecteditemUrl = itemDES[+position].substring(12);

                Uri uri = Uri.parse(SelecteditemUrl); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

                return true;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_wp);
        fab.setImageResource(R.drawable.check);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((imgHeader != null ? imgHeader.getDrawable() : null) == null) {
                    Snackbar.make(imgHeader, R.string.check_wallpaper, Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(imgHeader, R.string.applying, Snackbar.LENGTH_LONG).show();

                    WallpaperManager myWallpaperManager
                            = WallpaperManager.getInstance(getActivity());
                    try {
                        Bitmap bitmap=((BitmapDrawable)imgHeader.getDrawable()).getBitmap();
                        if(bitmap!=null)
                            myWallpaperManager.setBitmap(bitmap);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });

        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.color) {
            ColorPickerDialogBuilder
                    .with(getActivity())
                    .initialColor(0xff2196f3)
                    .setTitle(R.string.custom)
                    .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                    .density(9)
                    .setPositiveButton(R.string.yes, new ColorPickerClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                            try {
                                WallpaperManager wm = WallpaperManager.getInstance(getActivity());
                                // Create 1x1 bitmap to store the color
                                Bitmap bmp = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
                                // Make a canvas with which we can draw to the bitmap
                                Canvas canvas = new Canvas(bmp);
                                // Fill with color and save
                                canvas.drawColor(selectedColor);
                                canvas.save();

                                wm.setBitmap(bmp);
                                bmp.recycle();
                                Snackbar.make(imgHeader, R.string.applying, Snackbar.LENGTH_LONG).show();
                            } catch (IOException e) {
                                // oh lord!
                            }
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .build()
                    .show();
            return false;
        }

        if (id == R.id.help) {

            SpannableString s;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                s = new SpannableString(Html.fromHtml(getString(R.string.help_wallpaper),Html.FROM_HTML_MODE_LEGACY));
            } else {
                //noinspection deprecation
                s = new SpannableString(Html.fromHtml(getString(R.string.help_wallpaper)));
            }
            Linkify.addLinks(s, Linkify.WEB_URLS);

            final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.title_wallpaper)
                    .setMessage(s)
                    .setPositiveButton(getString(R.string.yes), null);
            dialog.show();

            return false;
        }
        return super.onOptionsItemSelected(item);
    }
}
