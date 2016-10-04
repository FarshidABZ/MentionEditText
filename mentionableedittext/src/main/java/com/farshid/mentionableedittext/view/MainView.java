package com.farshid.mentionableedittext.view;

import com.farshid.mentionableedittext.model.MentionModel;

import java.util.ArrayList;

/**
 * @author FarshidAbz
 * @version 1.0
 * @since 9/9/2016
 */
public interface MainView {
    void setSuggestionList(ArrayList<MentionModel> suggestionList);
    void removeSuggestionList();
}
