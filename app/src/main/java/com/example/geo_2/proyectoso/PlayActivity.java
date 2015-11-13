package com.example.geo_2.proyectoso;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class PlayActivity extends AppCompatActivity {

    private final String MEDIA_PATH = new String("storage/sdcard0/Download/owari-no-seraph-ending-full.mp3");
    private static final float VISUALIZER_HEIGHT_DIP = 50f;
    //media player
    MediaPlayer mp;
    //Visualization
    private Visualizer mVisualizer;
    private LinearLayout mLinearLayout;
    private VisualizerView mVisualizerView;
    private ImageView btnPlay;
    private boolean flag = true;
    private TextView clientName;
    Toolbar toolbar;
    //Controles
    private SeekBar sBar;
    private TextView songDuration;
    private double timeStart = 0;
    private double finalTime = 0;
    private int nextTime = 2000;
    private int prevTime = 2000;
    private Handler durationHan = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ///
        btnPlay = (ImageView) findViewById(R.id.play_Music);
        sBar = (SeekBar) findViewById(R.id.seekBarPlay);
        songDuration = (TextView) findViewById(R.id.TxtSDuration);
        sBar.setMax((int) finalTime);
        sBar.setClickable(false);
        switchButton();

        //clientName = (TextView) findViewById(R.id.textName);

        //hacer layout para contener visualizador
        mLinearLayout = (LinearLayout)findViewById(R.id.linear_layout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TypedValue typedValueColorPrimaryDark = new TypedValue();
        PlayActivity.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);
        }

       // receiveName();
        mp = new MediaPlayer();
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mp.setDataSource(MEDIA_PATH); // set data source our URL defined
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {   //tell your player to go to prepare state
            mp.prepare();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //Start your stream / player
        mp.start();
        //setup your Vizualizer - call method
        setupVisualizerFxAndUI();
        //enable vizualizer
        mVisualizer.setEnabled(true);
    }

    public void receiveName(){
        Bundle b = getIntent().getExtras();
        String name = b.getString("Name");
        clientName.setText(name);
    }
    //Metodo del visualizer
    public void visualizerMusic(){

    }

    //Metodo Runnable que se encarga de reproducir canciones
    private  Runnable upSeekBarTime = new Runnable(){
        public void run(){
            timeStart = mp.getCurrentPosition();
            sBar.setProgress((int) timeStart);
            double timeRemain = finalTime - timeStart;
            songDuration.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes((long) timeRemain),
                    TimeUnit.MILLISECONDS.toSeconds((long) timeRemain) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemain))));
            durationHan.postDelayed(this, 100);
        }
    };
    //Metodo play
    public void playTrack(View view){
        //mp.start();
        timeStart = mp.getCurrentPosition();
        sBar.setProgress((int) timeStart);
        durationHan.postDelayed(upSeekBarTime, 100);
        //visualizerMusic();
    }

    //Metodo pause
    public void pauseTrack(View view){
        mp.pause();
    }

    //Metodo previous track
    public void prevTrack(View view){
        if((timeStart + nextTime) <= finalTime){
            timeStart = timeStart - prevTime;
            mp.seekTo((int) timeStart);
        }
    }

    //Metodo next track
    public void nextTrack(View view){
        if((timeStart - prevTime) > 0){
            timeStart = timeStart - prevTime;
            mp.seekTo((int) timeStart);
        }
    }



    //Metodo que hace el cambio del icono del boton play/pause
    public void switchButton(){
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    btnPlay.setImageResource(R.mipmap.ic_pause);
                    //playTrack(v);
                    flag = false;
                } else {
                    btnPlay.setImageResource(R.mipmap.ic_play);
                    //pauseTrack(v);
                    flag = true;
                }
            }
        });
    }

    private void setupVisualizerFxAndUI() {
        // Crear visualizador
        mVisualizerView = new VisualizerView(this);
        mVisualizerView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                (int) (VISUALIZER_HEIGHT_DIP * getResources().getDisplayMetrics().density)));
        mLinearLayout.addView(mVisualizerView);
        // Crear visualizador y pegarlo al media player
        mVisualizer = new Visualizer(mp.getAudioSessionId());
        mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
        mVisualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] bytes,
                                              int samplingRate) {
                mVisualizerView.updateVisualizer(bytes);
            }

            public void onFftDataCapture(Visualizer visualizer, byte[] bytes, int samplingRate) {
            }
        }, Visualizer.getMaxCaptureRate() / 2, true, false);
    }

    // clase que me permite dibujar las ondas de bytes.
    class VisualizerView extends View {
        private byte[] mBytes;
        private float[] mPoints;
        private Rect mRect = new Rect();

        private Paint mForePaint = new Paint();

        public VisualizerView(Context context) {
            super(context);
            init();
        }

        private void init() {
            mBytes = null;

            mForePaint.setStrokeWidth(1f);
            mForePaint.setAntiAlias(true);
            mForePaint.setColor(Color.rgb(0, 128, 255));
        }

        public void updateVisualizer(byte[] bytes) {
            mBytes = bytes;
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            if (mBytes == null) {
                return;
            }

            if (mPoints == null || mPoints.length < mBytes.length * 4) {
                mPoints = new float[mBytes.length * 4];
            }

            mRect.set(0, 0, getWidth(), getHeight());

            for (int i = 0; i < mBytes.length - 1; i++) {
                mPoints[i * 4] = mRect.width() * i / (mBytes.length - 1);
                mPoints[i * 4 + 1] = mRect.height() / 2
                        + ((byte) (mBytes[i] + 128)) * (mRect.height() / 2) / 128;
                mPoints[i * 4 + 2] = mRect.width() * (i + 1) / (mBytes.length - 1);
                mPoints[i * 4 + 3] = mRect.height() / 2
                        + ((byte) (mBytes[i + 1] + 128)) * (mRect.height() / 2) / 128;
            }

            canvas.drawLines(mPoints, mForePaint);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (isFinishing() && mp != null) {
            mVisualizer.release();
            //mEqualizer.release();
            mp.release();
            mp = null;
        }
    }


}
