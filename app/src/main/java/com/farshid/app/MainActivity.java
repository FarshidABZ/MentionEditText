package com.farshid.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.farshid.mentionableedittext.view.MentionableEditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MentionableEditText mentionableEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> inputList = new ArrayList<>();
        inputList.add("Pepe");
        inputList.add("Ramos");
        inputList.add("Ronaldo");
        inputList.add("Navas");
        inputList.add("Benzema");
        inputList.add("Modric");
        inputList.add("Bale");
        inputList.add("Marcelo");
        inputList.add("Kroos");
        inputList.add("Carvajal");
        inputList.add("Carvajal");
        inputList.add("James");

        mentionableEditText = (MentionableEditText) findViewById(R.id.editText);
        mentionableEditText.setInputList(inputList);
    }
}
