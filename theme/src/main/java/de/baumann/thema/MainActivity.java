package de.baumann.thema;

import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private final static boolean DEBUG = false;
    private final static String TAG = "AppGetter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }

        Button start_button3 = (Button) findViewById(R.id.button3);
        assert start_button3 != null;
        start_button3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                start_request();

            }
        });

        Button start_button2 = (Button) findViewById(R.id.button2);
        assert start_button2 != null;
        start_button2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(de.baumann.thema.MainActivity.this, de.baumann.thema.Wallpaper.class);
                startActivity(intent3);
            }
        });

        Button start_button1 = (Button) findViewById(R.id.button);
        assert start_button1 != null;
        start_button1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                start_color();
            }
        });

    }

    private void start_request()
    {
        String pkg = getPackageName();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setComponent(new ComponentName(pkg,pkg+".RequestActivity"));
        startActivity(intent);

        if(DEBUG)Log.v(TAG,"Intent intent: "+intent);
    }

    private void start_color()
    {
        final CharSequence[] items = getResources().getStringArray(R.array.colors);
        final Context mContext = this;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.pick_color));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                int color = Color.BLACK;
                switch (item) {
                    case 0:
                        color = Color.parseColor("#b71c1c");
                        break;
                    case 1:
                        color = Color.parseColor("#1b5e20");
                        break;
                    case 2:
                        color = Color.parseColor("#0d47a1");
                        break;
                    case 3:
                        color = Color.parseColor("#4a148c");
                        break;
                    case 4:
                        color = Color.parseColor("#e65100");
                        break;
                    case 5:
                        color = Color.parseColor("#004d40");
                        break;
                    case 6:
                        color = Color.WHITE;
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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.exit) {
            moveTaskToBack(true);
            return false;
            }

        if (id == R.id.license) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle(getString(R.string.about_title))
                    .setMessage(getString(R.string.about_text))
                    .setPositiveButton(getString(R.string.about_yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/scoute-dich/Baumann_Thema"));
                                    startActivity(i);
                                    dialog.cancel();
                                }
                            })
                    .setNegativeButton(getString(R.string.about_no),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            }).show();
        }

        return super.onOptionsItemSelected(item);
    }

}
