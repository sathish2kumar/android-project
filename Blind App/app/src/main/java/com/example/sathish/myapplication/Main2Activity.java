package com.example.sathish.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;

public class Main2Activity extends AppCompatActivity {
    public TextToSpeech toSpeech;
    TextView t;
    String text;
    RelativeLayout h;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private Intent data;
    public int result,i,n,st=0,et=0;
    StringTokenizer s1;
    Float a,b,c;
    String val,v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        h = (RelativeLayout) findViewById(R.id.id4);
        t=(TextView) findViewById(R.id.textView3);

        toSpeech = new TextToSpeech(Main2Activity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    result = toSpeech.setLanguage(Locale.UK);
                } else {
                    Toast.makeText(getApplicationContext(), "Feature not supported in your device", Toast.LENGTH_SHORT).show();
                }
            }
        });
        h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        h.post(new Runnable() {
                            @Override
                            public void run() {
                                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                    Toast.makeText(getApplicationContext(), "Feature not supported in your device", Toast.LENGTH_SHORT).show();
                                } else {
                                    text = "now calculater is open.speak something and it provide a result";
                                    toSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                                }
                            }
                        });
                    }
                }).start();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        h.postDelayed(new Runnable() {
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
                        }, 8000);
                    }
                }).start();
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (toSpeech != null) {
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
                    v=result.get(0)+'+';
                    n = v.length();
                    val=v.charAt(0)+"";
                    for (i=1;i<n;i++)
                    {
                        if((v.charAt(i)=='+') || (v.charAt(i)=='-') || (v.charAt(i)=='*') || (v.charAt(i)=='/'))
                            {
                                    if (val.contains('+'+"")) {

                                        s1 = new StringTokenizer(val, "+");
                                        a = Float.parseFloat(s1.nextToken());
                                        b = Float.parseFloat(s1.nextToken());
                                        val = "";
                                        c=a+b;
                                        val=c+"";
                                    }
                                    if (val.contains('-'+"")) {
                                        s1 = new StringTokenizer(val,"-");
                                        a = Float.parseFloat(s1.nextToken());
                                        b = Float.parseFloat(s1.nextToken());
                                        val ="";
                                        c=a-b;
                                        val=c+"";
                                    }
                                    if (val.contains('*'+"")) {

                                        s1 = new StringTokenizer(val, "*");
                                        a = Float.parseFloat(s1.nextToken());
                                        b = Float.parseFloat(s1.nextToken());
                                        c=a*b;
                                        val = "";
                                        val=c+"";
                                    }
                                    if (val.contains('/'+"")) {

                                        s1 = new StringTokenizer(val, "divided");
                                        a = Float.parseFloat(s1.nextToken());
                                        b = Float.parseFloat(s1.nextToken());
                                        val = "";
                                        c=a/b;
                                        val = c+"";

                                    }


                            }
                            if(v.charAt(i)!=' ') {
                                    val = val + v.charAt(i);
                            }
                            }


                    }
                        t.setText(val.substring(0,val.length()-1));
                        toSpeech.speak(t.getText()+"", TextToSpeech.QUEUE_FLUSH, null);
                }
            }



        }

    }



