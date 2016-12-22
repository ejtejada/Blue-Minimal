package de.baumann.thema;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.ImageButton;
import android.widget.ImageView;

import de.baumann.thema.helpers.ColorPicker;

import java.io.IOException;

public class FragmentWallpaper extends Fragment {

    private int selectedColorRGB;
    private ImageView wallpaperPreview;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.wallpaper, container, false);

        setHasOptionsMenu(true);

        wallpaperPreview = (ImageView) rootView.findViewById(R.id.wallpaperPreview);

        wallpaper(rootView, R.id.wp13, R.drawable.wp_lp_amber);
        wallpaper(rootView, R.id.wp14, R.drawable.wp_lp_blue);
        wallpaper(rootView, R.id.wp15, R.drawable.wp_lp_green);
        wallpaper(rootView, R.id.wp16, R.drawable.wp_lp_grey);
        wallpaper(rootView, R.id.wp17, R.drawable.wp_lp_red);
        wallpaper(rootView, R.id.wp18, R.drawable.wp_lp_teal);

        wallpaper(rootView, R.id.wp7, R.drawable.wp_mm_amber);
        wallpaper(rootView, R.id.wp8, R.drawable.wp_mm_blue);
        wallpaper(rootView, R.id.wp9, R.drawable.wp_mm_green);
        wallpaper(rootView, R.id.wp10, R.drawable.wp_mm_grey);
        wallpaper(rootView, R.id.wp11, R.drawable.wp_mm_red);
        wallpaper(rootView, R.id.wp12, R.drawable.wp_mm_teal);

        wallpaper(rootView, R.id.wp1, R.drawable.wp_n_amber);
        wallpaper(rootView, R.id.wp2, R.drawable.wp_n_blue);
        wallpaper(rootView, R.id.wp3, R.drawable.wp_n_green);
        wallpaper(rootView, R.id.wp4, R.drawable.wp_n_grey);
        wallpaper(rootView, R.id.wp5, R.drawable.wp_n_red);
        wallpaper(rootView, R.id.wp6, R.drawable.wp_n_teal);
        
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_wp);
        fab.setImageResource(R.drawable.check);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (wallpaperPreview.getDrawable() == null) {
                    Snackbar.make(wallpaperPreview, R.string.check_wallpaper, Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(wallpaperPreview, R.string.applying, Snackbar.LENGTH_LONG).show();

                    WallpaperManager myWallpaperManager
                            = WallpaperManager.getInstance(getActivity());
                    try {
                        Bitmap bitmap=((BitmapDrawable)wallpaperPreview.getDrawable()).getBitmap();
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

    private void wallpaper(View view, int id, final int id2) {

        ImageButton image = (ImageButton) view.findViewById(id);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wallpaperPreview.setImageResource(id2);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.color) {
            final CharSequence[] items = getResources().getStringArray(R.array.colors);
            final Context mContext = getActivity();

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.cancel();
                }
            });
            builder.setTitle(getString(R.string.pick_color));
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    int color = Color.BLACK;
                    switch (item) {
                        case 0:
                            Snackbar.make(wallpaperPreview, R.string.applying, Snackbar.LENGTH_LONG).show();
                            color = Color.parseColor("#ff6f00");
                            break;
                        case 1:
                            Snackbar.make(wallpaperPreview, R.string.applying, Snackbar.LENGTH_LONG).show();
                            color = Color.parseColor("#0d47a1");
                            break;
                        case 2:
                            Snackbar.make(wallpaperPreview, R.string.applying, Snackbar.LENGTH_LONG).show();
                            color = Color.parseColor("#1b5e20");
                            break;
                        case 3:
                            Snackbar.make(wallpaperPreview, R.string.applying, Snackbar.LENGTH_LONG).show();
                            color = Color.parseColor("#212121");
                            break;
                        case 4:
                            Snackbar.make(wallpaperPreview, R.string.applying, Snackbar.LENGTH_LONG).show();
                            color = Color.parseColor("#b71c1c");
                            break;
                        case 5:
                            Snackbar.make(wallpaperPreview, R.string.applying, Snackbar.LENGTH_LONG).show();
                            color = Color.parseColor("#004d40");
                            break;
                        case 6:
                            final ColorPicker cp = new ColorPicker(getActivity());

                            /* Show color picker dialog */
                            cp.show();

                            FloatingActionButton fab = (FloatingActionButton) cp.findViewById(R.id.fab);
                            fab.setImageResource(R.drawable.check);
                            fab.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    /* Or the android RGB Color (see the android Color class reference) */
                                    selectedColorRGB = cp.getColor();

                                    try {
                                        WallpaperManager wm = WallpaperManager.getInstance(mContext);
                                        // Create 1x1 bitmap to store the color
                                        Bitmap bmp = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
                                        // Make a canvas with which we can draw to the bitmap
                                        Canvas canvas = new Canvas(bmp);
                                        // Fill with color and save
                                        canvas.drawColor(selectedColorRGB);
                                        canvas.save();

                                        wm.setBitmap(bmp);
                                        bmp.recycle();
                                    } catch (IOException e) {
                                        // oh lord!
                                    }
                                    cp.dismiss();
                                    Snackbar.make(wallpaperPreview, R.string.applying, Snackbar.LENGTH_LONG).show();
                                }
                            });
                            break;
                    }

                    try {
                        WallpaperManager wm = WallpaperManager.getInstance(mContext);
                        // Create 1x1 bitmap to store the color
                        Bitmap bmp = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
                        // Make a canvas with which we can draw to the bitmap
                        Canvas canvas = new Canvas(bmp);
                        // Fill with color and save
                        canvas.drawColor(color);
                        canvas.save();

                        wm.setBitmap(bmp);
                        bmp.recycle();
                    } catch (IOException e) {
                        // oh lord!
                    }
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
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