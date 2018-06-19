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

public class HaveliFragment extends Fragment {
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
        final ArrayList<Location> haveliArray = new ArrayList<>();
        haveliArray.add(new Location(R.drawable.ashish_mehta_heritage_home_1,R.string.ashish_mehta_heritage_home,R.string.ashish_mehta_heritage_home_address,R.raw.haveli_ashish_mehta_heritage_home));
        haveliArray.add(new Location(R.drawable.ashok_bhatt_haveli_1,R.string.ashok_bhatt_haveli,R.string.ashok_bhatt_haveli_address,R.raw.haveli_ashok_bhatt));
        haveliArray.add(new Location(R.drawable.chinubhai_baronet_house_1,R.string.chinubhai_baronet_house,R.string.chinubhai_baronet_house_address,R.raw.haveli_chinubhai_boronet_house));
        haveliArray.add(new Location(R.drawable.dayabhai_mehta_haveli_1,R.string.dayabhai_mehta_haveli,R.string.dayabhai_mehta_haveli_address,R.raw.haveli_dayabhai_mehta));
        haveliArray.add(new Location(R.drawable.diwanji_haveli_1,R.string.diwanji_haveli,R.string.diwanji_haveli_address,R.raw.haveli_diwanji));
        haveliArray.add(new Location(R.drawable.dodhia_haveli_1,R.string.dodhia_haveli,R.string.dodhia_haveli_address,R.raw.haveli_dodhia));
        haveliArray.add(new Location(R.drawable.french_haveli_1,R.string.french_haveli,R.string.french_haveli_address,R.raw.haveli_french));
        haveliArray.add(new Location(R.drawable.gaekwad_haveli_1,R.string.gaekwad_haveli,R.string.gaekwad_haveli_address,R.raw.haveli_gaekwad));
        haveliArray.add(new Location(R.drawable.harkunvar_baa_haveli_1,R.string.harkunvar_baa_haveli,R.string.harkunvar_baa_haveli_address,R.raw.haveli_harkunvar_baa));
        haveliArray.add(new Location(R.drawable.hatheesing_haveli_1,R.string.hatheesing_haveli,R.string.hatheesing_haveli_address,R.raw.haveli_hatheesing));
        haveliArray.add(new Location(R.drawable.jagdip_mehta_house_1,R.string.jagdip_mehta_house,R.string.jagdip_mehta_house_address,R.raw.haveli_jagdip_mehta));
        haveliArray.add(new Location(R.drawable.jethabhai_haveli_1,R.string.jethabhai_haveli,R.string.jethabhai_haveli_address,R.raw.haveli_jethabhai));
        haveliArray.add(new Location(R.drawable.magan_bhai_haveli_1,R.string.magan_bhai_haveli,R.string.magan_bhai_haveli_address,R.raw.haveli_maganbhai));
        haveliArray.add(new Location(R.drawable.mangaldas_haveli_1,R.string.mangaldas_haveli,R.string.mangaldas_haveli_address,R.raw.haveli_mangaldas));
        haveliArray.add(new Location(R.drawable.naiwado_heritage_home,R.string.naiwado_heritage_home,R.string.naiwado_heritage_home_address,R.raw.haveli_naiwado));
        haveliArray.add(new Location(R.drawable.ranjitbhai_vajubhai_haveli_1,R.string.ranjitbhai_vajubhai_divetia_haveli,R.string.ranjitbhai_vajubhai_divetia_haveli_address,R.raw.haveli_ranjitbhai_vajubhai));
        haveliArray.add(new Location(R.drawable.tankshal_haveli_1,R.string.tankshal_haveli,R.string.tankshal_haveli_address,R.raw.haveli_tankshal));
        haveliArray.add(new Location(R.drawable.zumama_ni_vadi_1,R.string.zumama_vadi,R.string.zumama_vadi_address,R.raw.haveli_zumama));

        View rootView = inflater.inflate(R.layout.activity_list, container, false);
        LocationAdapter locationAdapter = new LocationAdapter(getContext(), haveliArray);
        ListView listView = rootView.findViewById(R.id.list);
        listView.setAdapter(locationAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Location w = haveliArray.get(position);
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
