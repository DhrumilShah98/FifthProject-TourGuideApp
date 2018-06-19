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

public class HistoryFragment extends Fragment {
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
        final ArrayList<Location> historyArray = new ArrayList<>();
        historyArray.add(new Location(R.drawable.adalaj_vav_1,R.string.adalaj_stepwell,R.string.adalaj_stepwell_address,R.raw.history_adalaj_vav));
        historyArray.add(new Location(R.drawable.astodia_gate_1,R.string.astodia_gate,R.string.astodia_gate_address,R.raw.history_astodia_gate));
        historyArray.add(new Location(R.drawable.dada_harir_vav_1,R.string.dada_harir_vav,R.string.dada_harir_vav_address,R.raw.history_dada_harir_vav));
        historyArray.add(new Location(R.drawable.hutheesinh_temple_1,R.string.hutheesing_jain_temple,R.string.hutheesing_jain_temple_address,R.raw.history_hatheesing_temple));
        historyArray.add(new Location(R.drawable.jama_masjid_1,R.string.jama_masjid,R.string.jama_masjid_address,R.raw.history_jama_masjid));
        historyArray.add(new Location(R.drawable.jhulta_minar_1,R.string.jhulta_minar,R.string.jhulta_minar_address,R.raw.history_jhulta_minar));
        historyArray.add(new Location(R.drawable.queen_mosque_sarangpur_1,R.string.queen_mosque_sarangpur,R.string.queen_mosque_sarangpur_address,R.raw.history_queen_mosque));
        historyArray.add(new Location(R.drawable.sarkhej_roza,R.string.sarkhej_roza,R.string.sarkhej_roza_address,R.raw.history_sarkhej_roza));
        historyArray.add(new Location(R.drawable.sidi_saeed_mosque_1,R.string.sidi_saeed_mosque,R.string.sidi_saeed_mosque_address,R.raw.history_sidi_saeed_mosque));
        historyArray.add(new Location(R.drawable.teen_darwaza_1,R.string.teen_darwaza,R.string.teen_darwaza_address,R.raw.history_teen_darwaja));

        View rootView = inflater.inflate(R.layout.activity_list, container, false);
        LocationAdapter locationAdapter = new LocationAdapter(getContext(), historyArray);
        ListView listView = rootView.findViewById(R.id.list);
        listView.setAdapter(locationAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Location w = historyArray.get(position);
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
