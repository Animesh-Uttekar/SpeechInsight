package com.example.speechinsight;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.telecom.Call;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.*;

import com.example.speechinsight.Message;
import com.example.speechinsight.MessageAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;

public class ChatGPTActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Message> dialog;
    private MessageAdapter messageAdapter;
    private EditText promptInput;
    private Button sendButton;
    private OkHttpClient client;

    private Spinner promptSpinner; // Add Spinner declaration
    private ArrayAdapter<String> promptAdapter; // Adapter for Spinner
    private String[] promptOptions = {"",
            "Prompt 1: Explain a piece of code.",
            "Prompt 2: Describe the geography of birds",
            "Prompt 3: Elaborate on Quantum Computing",
            "Prompt 4: Create ideas for a new invention",
            "Prompt 5: Translate from English to Spanish"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatgpt);

        recyclerView = findViewById(R.id.message_box);
        promptInput = findViewById(R.id.promptInput);
        sendButton = findViewById(R.id.sendButton);

        // Initialize RecyclerView and Adapter
        dialog = new ArrayList<>();
        messageAdapter = new MessageAdapter(dialog);
        recyclerView.setAdapter(messageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize OkHttpClient
        client = new OkHttpClient();

        // Get the speech text from MainActivity and send it automatically
        String speechText = getIntent().getStringExtra("speechText");
        if (speechText != null && !speechText.isEmpty()) {
            promptInput.setText(speechText);
            addToChat(speechText, Message.SENT_BY_ME); // Add the text to the chat
            callAPI(speechText);  // Send the text to ChatGPT immediately
        }

        // Setup the send button for further manual inputs
        sendButton.setOnClickListener((v) -> {
            String prompt = promptInput.getText().toString().trim();
            if (!prompt.isEmpty()) {
                addToChat(prompt, Message.SENT_BY_ME);
                promptInput.setText("");
                callAPI(prompt);
            }
        });
    }

    // This method makes an API call to OpenAI
    void callAPI(String prompt) {
        JSONObject requestBody = createRequestJSON(prompt);
        okhttp3.RequestBody body = RequestBody.create(requestBody.toString(), MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/completions")
                .header("Authorization","Bearer sk-")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("choices");
                        String result = jsonArray.getJSONObject(0).getJSONObject("message").getString("content");
                        addToChat(result.trim(),Message.SENT_BY_BOT);
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }

                else {
                    addToChat("Failed to lead response due to "+response.body().string(),Message.SENT_BY_BOT);
                }
            }

            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                addToChat("Failed to load response due to "+e.getMessage(), Message.SENT_BY_BOT);
            }
        });
    }

    // Helper method to create the JSON request body
    private JSONObject createRequestJSON(String prompt) {
        // Implement this method to create the JSON request body
        // Example:
        JSONObject jsonObject = new JSONObject();
        JSONArray messages = new JSONArray();
        JSONObject msg = new JSONObject();
        JSONObject requestBody = new JSONObject();
        try {
            //jsonObject.put("prompt", prompt);
            requestBody.put("model","gpt-3.5-turbo");
            requestBody.put("temperature", 0);

            msg.put("role", "user");
            msg.put("content", prompt);
            messages.put(msg);
            requestBody.put("messages", messages);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestBody;
    }

    void addToChat(String message,String sentBy){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.add(new Message(message,sentBy));
                messageAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
            }
        });
    }

    // Helper method to create the JSON request body
//    private JSONObject createRequestJSON(String prompt) {
//        JSONObject requestBody = new JSONObject(); // Define requestBody here
//        JSONArray messages = new JSONArray();
//        JSONObject msg = new JSONObject();
//        try {
//            requestBody.put("model", "gpt-3.5-turbo");
//            requestBody.put("temperature", 0);
//
//            msg.put("role", "user");
//            msg.put("content", prompt);
//            messages.put(msg);
//            requestBody.put("messages", messages);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return requestBody;
//    }


}
