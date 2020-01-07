package com.example.sdl.multimediatest;

/* import android.content.pm.ActivityInfo; import del
setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); para evitar que gire desde el oncreate*/
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.VideoView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    VideoView myVideoView;
    MediaPlayer myAudioFile;// this will andle our mp3 file
    Button textoButton;
    SeekBar myVolumeBar, myAudioProgressBar;
    AudioManager myAudioManager;// this will let us handle the cell volumes in this case Media volume



    public void playVideoF(View n){
        myVideoView.start();
    }// enf playVideoF

    public void pauseVideoF(View n){
        myVideoView.pause();
    }//end pause


    public void audioPlayPause(View n){
        textoButton = (Button) n;
        if(myAudioFile.isPlaying()){
            myAudioFile.pause();
            textoButton.setText(R.string.play);
        }else{
            myAudioFile.start();
            textoButton.setText(R.string.pause);
        }
    }// end play pause audio


    public void audioStop(View n){
        textoButton = findViewById(R.id.btnPlay);
        textoButton.setText(R.string.play);
        myAudioFile.stop();
        myAudioFile = MediaPlayer.create(this, R.raw.rainforest);
    }// end stop

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//*setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); para evitar que gire,,
// pero en esta app esta desde el Manifest
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


/////VIDEO ******
        myVideoView = findViewById(R.id.vvFF);
        myVideoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.ff_xv);
//*myVideoView.setVideoPath("https://www.youtube.com/watch?v=eDZQSOBMrUc"); //content provider?

        final MediaController myMediaController = new MediaController(this);
        //myMediaController.setAnchorView(myVideoView); //y esto para que es entonces??
        myVideoView.setMediaController(myMediaController);



/////AUDIO ******
        myAudioFile = MediaPlayer.create(this, R.raw.rainforest);//asignando archivo al M/P

//detectando fin de track para cambiar texto de boton xD
        myAudioFile.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                textoButton.setText(R.string.play);
            }
        });// end OncompletionListener



/////VOLUME ******
        myVolumeBar = findViewById(R.id.sbVolume);
        myAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);// para contener y sacar info de servicios de audio
        int maxVolume = myAudioManager.getStreamMaxVolume(myAudioManager.STREAM_MUSIC);//obtener volumen max al que se puede stremear musica o audio
        int currentVolume = myAudioManager.getStreamVolume(myAudioManager.STREAM_MUSIC);//valor actual (o volumen) al que el cel estaria reproduciendo actualmente
        myVolumeBar.setMax(maxVolume);
        myVolumeBar.setProgress(currentVolume);

        myVolumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("the SeekBar Int value: ", Integer.toString(progress));
                myAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);//this zero flag could be more info.. ?
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });// setOnSeekBarChangeListener end




///// AUDIO PROGRESS BAR ****** barra progresa con el audio,, perola parte
    //comentada en el onProgressChanged es la que hace que el audio se reproduzca en el lugar
    // seleccionado en la barra, pero suena mal,,, es de corregir,,
        myAudioProgressBar = findViewById(R.id.sbAudioProgress);

        //int maxDuration = myAudioFile.getDuration();
        //myAudioProgressBar.setMax(maxDuration); estas 2 lineas son esta otra:
        myAudioProgressBar.setMax(myAudioFile.getDuration());

// esto mueve la progress barr segun el tiempo del timar
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                myAudioProgressBar.setProgress(myAudioFile.getCurrentPosition());
            }
        }, 0, 100);

        myAudioProgressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //myAudioFile.seekTo(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        }); // end set on seek bar


    }// oncreate end

}// Main end




