package com.farshid.mentionableedittext.view.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import java.util.ArrayList;

import com.farshid.mentionableedittext.model.MentionModel;
import com.farshid.mentionableedittext.tokenization.Tokenizer;
import com.farshid.mentionableedittext.view.MainView;

/**
 * @author FarshidAbz
 * @version 1.0
 * @since 9/9/2016
 */
public class CustomEditText extends EditText implements TextWatcher {
    ArrayList<MentionModel> inputList;
    Tokenizer tokenizer;

    public int currentCursorPosition;
    private MainView mainView;
    private String currentText;

    public CustomEditText(Context context) {
        super(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setInputList(ArrayList<MentionModel> inputList) {
        this.inputList = inputList;
    }

    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }

    public String getCurrentWord() {
        return tokenizer.getCurrentWord(getText(), currentCursorPosition);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);

        if (selStart == 0 || selStart != selEnd ||
                inputList == null || inputList.size() <= 0 ||
                mainView == null)
            return;

        if (tokenizer == null) {
            tokenizer = new Tokenizer(inputList, mainView);
        }
        currentCursorPosition = selStart;
        tokenizer.findTokens(currentText, currentCursorPosition);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

    @Override
    public void onTextChanged(CharSequence text, int var2, int var3, int var4) {
        currentText = text.toString();
    }
}