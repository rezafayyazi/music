package com.example.music_player;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
MediaPlayer mediaPlayer;
TextView start;
SeekBar seekBar;
Timer timer;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

setupplayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
         mediaPlayer.release();
         timer.purge();
         timer.cancel();
    }

    private void setupplayer() {
        mediaPlayer=new MediaPlayer();
        mediaPlayer=MediaPlayer.create(this,R.raw.sss);
                setupview();

            }





    private void setupview() {
          imageView=findViewById(R.id.play);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    imageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_play,null));

                }
                else {
                    mediaPlayer.start();
                    imageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_pause,null));

                }
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                imageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_play, null));
                mediaPlayer.seekTo(0);
            }
        });
        TextView end=findViewById(R.id.end);
        end.setText(formatduration(mediaPlayer.getDuration()));
        start=findViewById(R.id.start);
        start.setText(formatduration(0));
        ImageView forward=findViewById(R.id.forward);
        ImageView rewin=findViewById(R.id.rewin);
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+5000);

            }
        });
        rewin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()-5000);

            }
        });
        seekBar=findViewById(R.id.seekBar2);
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){

                    mediaPlayer.seekTo(progress);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
timer=new Timer();
timer.schedule(new maintime(),0,1000);
    }

    private String formatduration(long duration) {
        int second= (int) (duration/1000);
        int min=second/60;
        second=second%60;
        return String.format(Locale.ENGLISH,"%02d",min)+":"+String.format(Locale.ENGLISH,"%02d",second);
    }


    private class maintime extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    start.setText(formatduration(mediaPlayer.getCurrentPosition()));
                }
            });
        }
    }
}
