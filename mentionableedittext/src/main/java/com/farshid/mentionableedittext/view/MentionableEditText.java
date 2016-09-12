package com.farshid.mentionableedittext.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.farshid.mentionableedittext.R;

import java.util.ArrayList;

import com.farshid.mentionableedittext.suggestion.RecyclerAdapter;
import com.farshid.mentionableedittext.view.widget.CustomEditText;

/**
 * @author FarshidAbz
 * @version 1.0
 * @since 9/9/2016
 */
public class MentionableEditText extends LinearLayout implements MainView, OnRecyclerViewClickListener {
    private ArrayList<String> inputList;
    private CustomEditText customEditText;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private MainView mainView;

    public MentionableEditText(Context context) {
        super(context);
        initViews(context);
    }

    public MentionableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public MentionableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MentionableEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViews(context);
    }

    private void initViews(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.mentionabl_edit_text, this);

        customEditText = (CustomEditText) findViewById(R.id.met);
        recyclerView = (RecyclerView) findViewById(R.id.rvSuggestionList);

        recyclerAdapter = new RecyclerAdapter();
        recyclerAdapter.setOnClickListener(this);

        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    public void setInputList(ArrayList<String> inputList) {
        this.inputList = inputList;
        customEditText.setInputList(inputList);
        customEditText.setMainView(this);
    }

    @Override
    public void setSuggestionList(ArrayList<String> suggestionList) {
        if (suggestionList.size() <= 0) {
            recyclerAdapter.removeAll();
        } else {
            recyclerAdapter.setInputAdapter(suggestionList);
            recyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClickListener(String text) {
        String textBeforeCursorPosition = "";
        String textAfterCursorPosition = "";

        String currentText = customEditText.getText().toString();

        for (int i = customEditText.currentCursorPosition - 1 - customEditText.getCurrentWord().length();
             i >= 0; i--) {
            textBeforeCursorPosition += currentText.charAt(i);
        }

        for (int i = customEditText.currentCursorPosition + 1; i < currentText.length(); i++) {
            textAfterCursorPosition += currentText.charAt(i);
        }

        text = new StringBuilder(text).insert(0, '@').toString();

        String finalText = (new StringBuilder(textBeforeCursorPosition).reverse() + " " + text + " " + textAfterCursorPosition).substring(0);

        customEditText.setText(finalText, TextView.BufferType.SPANNABLE);

        Spannable spannable = new SpannableString(finalText);

        spannable.setSpan(new ForegroundColorSpan(Color.BLUE),
                new StringBuilder(finalText).indexOf(text),
                new StringBuilder(finalText).indexOf(text) + text.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        String mentionRegex = "((^|)@([\\p{N}|\\p{L}|_]{2,64}))+";

        finalText = finalText.replaceAll(mentionRegex, "<font color='#0000ff'>" + "$0" + "</font>");

        customEditText.setText(Html.fromHtml(finalText));
        recyclerAdapter.removeAll();

        customEditText.setSelection(customEditText.getText().length());
    }
}