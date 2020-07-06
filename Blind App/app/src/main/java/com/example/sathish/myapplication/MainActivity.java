package com.example.sathish.myapplication;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{

    TextToSpeech toSpeech;
    TextView editText;
    RelativeLayout g;
    String text,op;
    int result;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private Intent data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (TextView) findViewById(R.id.textView4);
        g=(RelativeLayout) findViewById(R.id.id3);
        AudioManager audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,20,0);

        toSpeech = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    result = toSpeech.setLanguage(Locale.UK);
                } else {
                    Toast.makeText(getApplicationContext(), "Feature not supported in your device", Toast.LENGTH_SHORT).show();
                }
            }
        });

         g.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 new Thread(new Runnable() {
                     @Override
                     public void run() {
                         g.post(new Runnable() {
                             @Override
                             public void run() {
                                 if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                     Toast.makeText(getApplicationContext(), "Feature not supported in your device", Toast.LENGTH_SHORT).show();
                                 } else {
                                     text = "one  is calculator two is money scanner three is exit. speake me one or two or three";
                                     toSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                                 }
                             }
                         });

                     }
                 }).start();
                 new Thread(new Runnable() {
                     @Override
                     public void run() {
                         g.postDelayed(new Runnable() {
                             @Override
                             public void run() {
                                 Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                 intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                         RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                 intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                                 intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                                         "Hi speak something");
                                 try {
                                     startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
                                 } catch (ActivityNotFoundException a) {

                                 }
                             }
                         },9000);
                     }
                 }).start();

             }
         });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (toSpeech!=null)
        {
            toSpeech.stop();
            toSpeech.shutdown();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    op=result.get(0);
                    editText.setText(op);
                    if(op.contentEquals("one") || op.contentEquals("1")) {
                        Intent i = new Intent(getApplicationContext(), Main2Activity.class);
                        startActivity(i);
                    }
                    if(op.contentEquals("two") || op.contentEquals("2")) {
                        Intent i = new Intent(getApplicationContext(), Main2Activity.class);
                        startActivity(i);
                    }
                    if(op.contentEquals("three") || op.contentEquals("3")) {
                        Intent i = new Intent(getApplicationContext(), Main2Activity.class);
                        startActivity(i);
                    }
                }
                break;
            }

        }



    }


}






