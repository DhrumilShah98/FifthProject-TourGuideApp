package com.example.dhrumilshah.tourguide;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class StreetFoodFragment extends Fragment{
    private MediaPlayer mMediaPlayer;
    private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };
    private AudioManager mAudioManager;

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mAudioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Location> streetFoodArray = new ArrayList<>();
        streetFoodArray.add(new Location(R.drawable.ambica_dalvada_1,R.string.ambica_dalvada,R.string.ambica_dalvada_address,R.raw.sf_ambica_dalvada));
        streetFoodArray.add(new Location(R.drawable.amod_kitchen_1,R.string.amod_kitchen,R.string.amod_kitchen_address,R.raw.sf_amods_kitchen));
        streetFoodArray.add(new Location(R.drawable.baghdad_fry_1,R.string.baghdad_fry_center,R.string.baghdad_fry_center_address,R.raw.sf_baghdad_fry));
        streetFoodArray.add(new Location(R.drawable.bajrang_chole_kulcha_1,R.string.bajrang_chole_kulcha,R.string.bajrang_chole_kulcha_address,R.raw.sf_bajrang_chole_kulcha));
        streetFoodArray.add(new Location(R.drawable.bera_samosa_1,R.string.bera_samosa,R.string.bera_samosa_address,R.raw.sf_bera_samosa));
        streetFoodArray.add(new Location(R.drawable.bhatiyar_gali_1,R.string.bhatiyar_gali,R.string.bhatiyar_gali_address,R.raw.sf_bhatiyar_gali));
        streetFoodArray.add(new Location(R.drawable.das_khaman_1,R.string.das_khaman,R.string.das_khaman_address,R.raw.sf_das_khaman_outlets));
        streetFoodArray.add(new Location(R.drawable.hl_college_road_1,R.string.hl_college_road,R.string.hl_college_road_address,R.raw.sf_hl_college_road));
        streetFoodArray.add(new Location(R.drawable.iscon_ganthiya_1,R.string.iscon_ganthiya,R.string.iscon_ganthiya_address,R.raw.sf_iscon_ganthiya));
        streetFoodArray.add(new Location(R.drawable.kitli_maskabun_1,R.string.kitli_maskaban,R.string.kitli_maskaban_address,R.raw.sf_roadside_kitli_maskaban));
        streetFoodArray.add(new Location(R.drawable.law_garden_1,R.string.law_garden,R.string.law_garden_address,R.raw.sf_law_garden));
        streetFoodArray.add(new Location(R.drawable.manek_chowk_1,R.string.manek_chowk,R.string.manek_chowk_address,R.raw.sf_manek_chowk));
        streetFoodArray.add(new Location(R.drawable.misal_pav_ridhhi_sidhhi,R.string.misal_pav_ridhhi_sidhhi,R.string.misal_pav_ridhhi_sidhhi_address,R.raw.sf_misal_pav));
        streetFoodArray.add(new Location(R.drawable.oshwal_1,R.string.oshwal,R.string.oshwal_address,R.raw.sf_oshwal));
        streetFoodArray.add(new Location(R.drawable.royal_punjab_paratha_1,R.string.royal_punjab_paratha,R.string.royal_punjab_paratha_address,R.raw.sf_royal_punjab_paratha));
        streetFoodArray.add(new Location(R.drawable.rukshmani_panipuri_1,R.string.rukshmani_panipuri,R.string.rukshmani_panipuri_address,R.raw.sf_rukshmani_panipuri));
        streetFoodArray.add(new Location(R.drawable.sabarmati_jail_bhajiya_1,R.string.sabarmati_jail_bhajiya,R.string.sabarmati_jail_bhajiya_address,R.raw.sf_sabarmati_jail_bhajiya));
        streetFoodArray.add(new Location(R.drawable.salim_burger_1,R.string.salim_burger,R.string.salim_burger_address,R.raw.sf_salim_bhais_burger));
        streetFoodArray.add(new Location(R.drawable.urban_chowk_1,R.string.urban_chowk,R.string.urban_chowk_address,R.raw.sf_urban_chowk));

        View rootView = inflater.inflate(R.layout.activity_list, container, false);
        LocationAdapter locationAdapter = new LocationAdapter(getContext(), streetFoodArray);
        ListView listView = rootView.findViewById(R.id.list);
        listView.setAdapter(locationAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Location w = streetFoodArray.get(position);
                releaseMediaPlayer();
                // Request audio focus for playback
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // Start playback
                    mMediaPlayer = MediaPlayer.create(getContext(),w.getAudioResourceId());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
                }

            }
        });
        return rootView;
    }
    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer(){
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}
