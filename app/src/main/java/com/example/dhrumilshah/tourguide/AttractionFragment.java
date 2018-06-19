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

public class AttractionFragment extends Fragment{
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
        final ArrayList<Location> attractionArray = new ArrayList<>();
        attractionArray.add(new Location(R.drawable.aadivasi_museum_1,R.string.adivasi_museum,R.string.adivasi_museum_address,R.raw.attraction_aadivasi_museum));
        attractionArray.add(new Location(R.drawable.akshardham_temple_1,R.string.akshardham_temple,R.string.akshardham_temple_address,R.raw.attraction_akshardham_temple));
        attractionArray.add(new Location(R.drawable.auto_world_museum_1,R.string.autoworld_museum,R.string.autoworld_museum_address,R.raw.attraction_auto_world));
        attractionArray.add(new Location(R.drawable.calico_museum_of_textlies_1,R.string.calico_museum_of_textiles,R.string.calico_museum_of_textiles_address,R.raw.attraction_calico_museum));
        attractionArray.add(new Location(R.drawable.camp_hanuman_1,R.string.camp_hanuman,R.string.camp_hanuman_address,R.raw.attraction_camp_hanuman));
        attractionArray.add(new Location(R.drawable.gandhi_ashram_1,R.string.gandhi_ashram,R.string.gandhi_ashram_address,R.raw.attraction_gandhi_ashram));
        attractionArray.add(new Location(R.drawable.hussain_doshi_gufa_1,R.string.hussain_doshi_gufa,R.string.hussain_doshi_gufa_address,R.raw.attraction_hussain_doshi));
        attractionArray.add(new Location(R.drawable.kankaria_lake_1,R.string.kankaria_lake,R.string.kankaria_lake_address,R.raw.attraction_kankaria_lake));
        attractionArray.add(new Location(R.drawable.kite_museum_1,R.string.kite_museum,R.string.kite_museum_address,R.raw.attraction_kite_museum));
        attractionArray.add(new Location(R.drawable.lalbhai_dalpatbhai_museum_1,R.string.lalbhai_dalpatbhai_museum,R.string.lalbhai_dalpatbhai_museum_address,R.raw.attraction_laalbhai_dalpatbhai));
        attractionArray.add(new Location(R.drawable.sabarmati_riverfront_1,R.string.sabarmati_riverfront,R.string.sabarmati_riverfront_address,R.raw.attraction_sabarmati_riverfront));
        attractionArray.add(new Location(R.drawable.sardar_patel_national_memorial_1,R.string.sardar_patel_national_memorial,R.string.sardar_patel_national_memorial_address,R.raw.attraction_sardar_patel));
        attractionArray.add(new Location(R.drawable.science_city_1,R.string.science_city,R.string.science_city_address,R.raw.attraction_science_city));
        attractionArray.add(new Location(R.drawable.shreyas_museum_1,R.string.shreyas_museum,R.string.shreyas_museum_address,R.raw.attraction_shreyas_museum));
        attractionArray.add(new Location(R.drawable.vaishnodevi_temple_1,R.string.vaishnodevi_temple,R.string.vaishnodevi_temple_address,R.raw.attraction_vaishnodevi_temple));
        attractionArray.add(new Location(R.drawable.vastrapur_lake_1,R.string.vastrapur_lake,R.string.vastrapur_lake_address,R.raw.attraction_vastrapur_lake));

        View rootView = inflater.inflate(R.layout.activity_list, container, false);
        LocationAdapter locationAdapter = new LocationAdapter(getContext(), attractionArray);
        ListView listView = rootView.findViewById(R.id.list);
        listView.setAdapter(locationAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Location w = attractionArray.get(position);
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
