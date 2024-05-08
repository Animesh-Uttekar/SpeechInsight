package com.example.speechinsight;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity{

    private ImageView iv_mic;
    private TextView tv_Speech_to_text;

    private Button chatGPTButton;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_mic = findViewById(R.id.iv_mic);
        tv_Speech_to_text = findViewById(R.id.tv_speech_to_text);
        chatGPTButton = findViewById(R.id.chatGPTButton);

        iv_mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent
                        = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                        Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

                try {
                    startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
                }
                catch (Exception e) {
                    Toast.makeText(MainActivity.this, " " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        chatGPTButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start ChatGPTActivity
                Intent intent = new Intent(MainActivity.this, ChatGPTActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (result != null && !result.isEmpty()) {
                String recognizedText = result.get(0);
                tv_Speech_to_text.setText(recognizedText);

                String modifiedText = "Analyze the following text and provide a list of simpler, more effective words suitable for speech \"" + recognizedText +"\"";

                // Start ChatGPTActivity and pass the modified text
                Intent intent = new Intent(MainActivity.this, ChatGPTActivity.class);
                intent.putExtra("speechText", modifiedText);
                startActivity(intent);
            }
        }
    }

//    public void openChatGPTActivity(View view) {
//        Intent intent = new Intent(this, ChatGPTActivity.class);
//        startActivity(intent);
//    }
//
//    public void openAnalyticsActivity(View view) {
//        Intent intent = new Intent(this, AnalyticsActivity.class);
//        startActivity(intent);
//    }
}

