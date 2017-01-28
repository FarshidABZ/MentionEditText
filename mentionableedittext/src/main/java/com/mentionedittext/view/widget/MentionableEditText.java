package com.mentionedittext.view.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.farshid.mentionableedittext.R;
import com.mentionedittext.model.MentionModel;
import com.mentionedittext.suggestion.RecyclerAdapter;
import com.mentionedittext.view.MainView;
import com.mentionedittext.view.OnRecyclerViewClickListener;

import java.util.ArrayList;

/**
 * @author FarshidAbz
 * @version 1.0
 * @since 9/9/2016
 */
public class MentionableEditText extends FrameLayout implements MainView, OnRecyclerViewClickListener {
    private CustomEditText customEditText;
    private RecyclerAdapter recyclerAdapter;
    private MainView mainView;
    private ListPopupWindow listPopupWindow;
    private RecyclerView recyclerView;

    boolean isPopUpWindowShowing = false;
    private PopupWindow popupWindow;

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
        recyclerView = new RecyclerView(context);
        recyclerAdapter = new RecyclerAdapter();
        recyclerAdapter.setOnClickListener(this);

        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    public void setInputList(ArrayList<MentionModel> inputList) {
        customEditText.setInputList(inputList);
        customEditText.setMainView(this);
    }

    @Override
    public void setSuggestionList(final ArrayList<MentionModel> suggestionList) {
        if (suggestionList.size() < 0)
            return;

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if (popupWindow == null) {
                    initPopupWindow();
                }

                if (suggestionList.size() <= 0) {
                    recyclerAdapter.removeAll();
                } else {
                    recyclerAdapter.setInputAdapter(suggestionList);
                    recyclerAdapter.notifyDataSetChanged();
                }

                popupWindow.setContentView(recyclerView);

                showPopupWindow();
            }
        });
    }

    private void initPopupWindow() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        popupWindow = new PopupWindow(inflater.inflate(R.layout.content_suggestion_list, null),
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setFocusable(false);
    }

    private void showPopupWindow() {
        if (!isPopUpWindowShowing) {
            popupWindow.dismiss();
            popupWindow.showAsDropDown(customEditText);
            isPopUpWindowShowing = true;
        }
    }

    @Override
    public void removeSuggestionList() {
        dismissPopupWindow();
    }

    @Override
    public void onItemClickListener(String text) {
        String currentText = customEditText.getText().toString();

        String textBeforeCursorPosition = getTextsBeforCursorPosition(currentText);
        String textAfterCursorPosition = getTextAfterCursorPosition(currentText);

        String finalText = createBlueTextWithMentionSignToShowAsMention(text, textBeforeCursorPosition, textAfterCursorPosition);

        customEditText.setText(Html.fromHtml(finalText));
        recyclerAdapter.removeAll();

        // set editText cursor position to the end of text
        customEditText.setSelection(customEditText.getText().length());

        dismissPopupWindow();
    }

    private String createBlueTextWithMentionSignToShowAsMention(String text,
                                                              String textBeforeCursorPosition,
                                                              String textAfterCursorPosition) {

        text = new StringBuilder(text).insert(0, '@').toString();

        String finalText = (new StringBuilder(textBeforeCursorPosition).reverse() + " " + text + " " + textAfterCursorPosition);

        customEditText.setText(finalText, TextView.BufferType.SPANNABLE);

        Spannable spannable = new SpannableString(finalText);

        spannable.setSpan(new ForegroundColorSpan(Color.BLUE),
                new StringBuilder(finalText).indexOf(text),
                new StringBuilder(finalText).indexOf(text) + text.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        String mentionRegex = "((^|)@([\\p{N}|\\p{L}|_]{2,64}))+";

        finalText = finalText.replaceAll(mentionRegex, "<font color='#0000ff'>" + "$0" + "</font>");

        final String finalText1 = finalText;
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), finalText1, Toast.LENGTH_SHORT);
            }
        };
        return finalText;
    }

    private String getTextsBeforCursorPosition(String currentText)
    {
        String textBeforeCursorPosition = "";

        for (int i = customEditText.currentCursorPosition - 1 - customEditText.getCurrentWord().length();
             i >= 0; i--) {
            textBeforeCursorPosition += currentText.charAt(i);
        }

        return textBeforeCursorPosition;
    }

    private String getTextAfterCursorPosition(String currentText)
    {
        String textAfterCursorPosition = "";

        for (int i = customEditText.currentCursorPosition + 1; i < currentText.length(); i++) {
            textAfterCursorPosition += currentText.charAt(i);
        }

        return textAfterCursorPosition;
    }

    private void dismissPopupWindow()
    {
        if(popupWindow == null)
            return;
        recyclerAdapter.removeAll();
        popupWindow.dismiss();
        isPopUpWindowShowing = false;
    }
}