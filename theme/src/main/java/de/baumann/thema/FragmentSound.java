package de.baumann.thema;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import de.baumann.thema.helpers.CustomListAdapter;


public class FragmentSound extends Fragment {

    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sound, container, false);

        setHasOptionsMenu(true);

        if (android.os.Build.VERSION.SDK_INT >= 23) {
            boolean canDo =  Settings.System.canWrite(getActivity());
            if (!canDo) {
                new AlertDialog.Builder(getActivity())
                        .setMessage(R.string.permissions_2)
                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (android.os.Build.VERSION.SDK_INT >= 23) {
                                    Intent grantIntent = new   Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                                    startActivity(grantIntent);
                                }
                            }
                        })
                        .setNegativeButton(getString(R.string.no), null)
                        .show();
            }
        }

        final String[] itemTITLE ={
                "What friends are for" + " | " + getString(R.string.duration) + " 00:05",
                "Ouverture - Hymne" + " | " + getString(R.string.duration) + " 01:49",
                "Canon and Gigue in D major" + " | " + getString(R.string.duration) + "  00:45",
                "Isn't it" + " | " + getString(R.string.duration) + " 00:01",
                "Jingle Bells Sms" + " | " + getString(R.string.duration) + " 00:04",
                "Loving You" + " | " + getString(R.string.duration) + " 00:35",
                "Good Morning" + " | " + getString(R.string.duration) + " 00:07",
                "Oringz w442" + " | " + getString(R.string.duration) + "  00:08",
        };

        final String[] itemURL ={
                Environment.getExternalStorageDirectory()  + "/Android/data/de.baumann.thema/what_friends_are_for.mp3",
                Environment.getExternalStorageDirectory()  + "/Android/data/de.baumann.thema/hymne.mp3",
                Environment.getExternalStorageDirectory()  + "/Android/data/de.baumann.thema/canon.mp3",
                Environment.getExternalStorageDirectory()  + "/Android/data/de.baumann.thema/isnt_it.mp3",
                Environment.getExternalStorageDirectory()  + "/Android/data/de.baumann.thema/jingle_bells_sms.mp3",
                Environment.getExternalStorageDirectory()  + "/Android/data/de.baumann.thema/loving_you.mp3",
                Environment.getExternalStorageDirectory()  + "/Android/data/de.baumann.thema/good_morning.mp3",
                Environment.getExternalStorageDirectory()  + "/Android/data/de.baumann.thema/oringz_w442.mp3",
        };

        final String[] itemDES ={
                "CC license: https://notificationsounds.com/wake-up-tones/what-friends-are-for-507",
                "CC license: https://www.jamendo.com/track/1004091/ouverture-hymne?language=fr",
                "CC license: https://musopen.org/music/2672/johann-pachelbel/canon-and-gigue-in-d-major/",
                "CC license: https://notificationsounds.com/standard-ringtones/isnt-it-524",
                "CC license: https://notificationsounds.com/message-tones/jingle-bells-sms-523",
                "CC license: https://notificationsounds.com/wake-up-tones/loving-you-509",
                "CC license: https://notificationsounds.com/wake-up-tones/good-morning-502",
                "CC license: https://notificationsounds.com/message-tones/oringz-w442-357",
        };


        CustomListAdapter adapter=new CustomListAdapter(getActivity(), itemTITLE, itemURL, itemDES);
        listView = (ListView)rootView.findViewById(R.id.bookmarks);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                String Selecteditem= itemURL[+position];
                MediaPlayer mp = MediaPlayer.create(getActivity(), Uri.parse(Selecteditem));
                mp.start();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final String Selecteditem= itemURL[+position];
                final CharSequence[] options = {getString(R.string.set_ringtone), getString(R.string.set_notification), getString(R.string.set_alarm)};

                new AlertDialog.Builder(getActivity())
                        .setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int item) {

                                if (options[item].equals (getString(R.string.set_ringtone))) {
                                    File k = new File(Selecteditem);
                                    Uri newUri = Uri.fromFile(k);

                                    RingtoneManager.setActualDefaultRingtoneUri(
                                            getActivity(),
                                            RingtoneManager.TYPE_RINGTONE,
                                            newUri
                                    );
                                    Snackbar.make(listView, getString(R.string.set_ringtone_suc), Snackbar.LENGTH_LONG).show();

                                } else if (options[item].equals (getString(R.string.set_notification))) {
                                    File k = new File(Selecteditem);
                                    Uri newUri = Uri.fromFile(k);

                                    RingtoneManager.setActualDefaultRingtoneUri(
                                            getActivity(),
                                            RingtoneManager.TYPE_NOTIFICATION,
                                            newUri
                                    );
                                    Snackbar.make(listView, getString(R.string.set_notification_suc), Snackbar.LENGTH_LONG).show();

                                } else if (options[item].equals (getString(R.string.set_alarm))) {
                                    File k = new File(Selecteditem);
                                    Uri newUri = Uri.fromFile(k);

                                    RingtoneManager.setActualDefaultRingtoneUri(
                                            getActivity(),
                                            RingtoneManager.TYPE_ALARM,
                                            newUri
                                    );
                                    Snackbar.make(listView, getString(R.string.set_alarm_suc), Snackbar.LENGTH_LONG).show();
                                }
                            }
                        }).show();
                return true;
            }
        });

        return rootView;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.color).setVisible(false);
    }


}
