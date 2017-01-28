package com.mentionedittext.suggestion;

import com.mentionedittext.model.MentionModel;
import com.mentionedittext.view.MainView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @author FarshidAbz
 * @version 1.0
 * @since 8/24/2016
 */
public class SuggestionListBuilder {

    ArrayList<MentionModel> suggestionList;
    ArrayList<MentionModel> inputList;
    MainView mainView;

    public SuggestionListBuilder(ArrayList<MentionModel> inputList, MainView mainView) {
        this.inputList = inputList;
        this.mainView = mainView;

        suggestionList = new ArrayList<>();
    }

    public void suggest(ArrayList<String> tokens) {
        searchWordsInInputList(tokens);
        showSuggestion();
    }

    private void searchWordsInInputList(ArrayList<String> tokens) {
        suggestionList.clear();
        for (MentionModel suggestionInput : inputList) {
            for (String token : tokens) {
                token = token.replaceAll("\\s", "");  //remove all spaces from current token (exp: " @java" | "  @java ")
                if (suggestionInput.getName().toLowerCase().contains(token.substring(1).toLowerCase())) // remove first @ character from token to search in input list
                {
                    suggestionList.add(suggestionInput);
                }
            }
        }
    }

    private void showSuggestion() {
        if (mainView != null) {
            removeDuplicatedSuggestions();
            if (suggestionList.size() > 0) {
                mainView.setSuggestionList(suggestionList);
            } else {
                mainView.removeSuggestionList();
            }
        }
    }

    private void removeDuplicatedSuggestions() {
        Set<MentionModel> tempList = new HashSet<>();
        tempList.addAll(suggestionList);
        suggestionList.clear();
        suggestionList.addAll(tempList);
    }
}
