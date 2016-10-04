package com.farshid.mentionableedittext.tokenization;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.farshid.mentionableedittext.model.MentionModel;
import com.farshid.mentionableedittext.suggestion.SuggestionListBuilder;
import com.farshid.mentionableedittext.view.MainView;

/**
 * The type Tokenizer.
 *
 * @author FarshidAbz
 * @version 1.0
 * @since 8 /24/2016
 */
public class Tokenizer {
    private SuggestionListBuilder suggestionListBuilder;

    private ArrayList<String> tokens;
    private ArrayList<String> mentionableWords;

    private String currentWord;

    /**
     * Instantiates a new Tokenizer.
     *
     * @param inputList the input list
     */
    public Tokenizer(ArrayList<MentionModel> inputList, MainView mainView) {
        tokens = new ArrayList<>();
        mentionableWords = new ArrayList<>();
        suggestionListBuilder = new SuggestionListBuilder(inputList, mainView);
    }

    /**
     * Find tokens.
     *
     * @param charSequence   the char sequence
     * @param cursorPosition the cursor position
     */
    public ArrayList<String> findTokens(CharSequence charSequence, int cursorPosition) {
        findAllMentionableWords(charSequence.toString());
        extractCurrentWord(charSequence, cursorPosition);
        searchCurrentWordInMentionableList();

        suggestionListBuilder.suggest(tokens);

        return tokens;
    }

    /**
     * Find all words that they begin with "@" and their compliance the mention pattern.
     *
     * @param text the input text
     */
    private void findAllMentionableWords(String text) {
        mentionableWords.clear();

        String mentionRegex = "((^|)@([\\p{N}|\\p{L}|_]{2,64}))+";
        Pattern mentionPattern = Pattern.compile(mentionRegex, Pattern.MULTILINE);

        Matcher matcher = mentionPattern.matcher(text);

        while (matcher.find()) {
            mentionableWords.add(matcher.group());
        }

        removeDuplicateWords();
    }

    /**
     * remove duplicate words from mentionable words list to speed up search in future
     */
    private void removeDuplicateWords() {
        Set<String> tempList = new HashSet<>();
        tempList.addAll(mentionableWords);
        mentionableWords.clear();
        mentionableWords.addAll(tempList);
    }

    /***
     * extracting current word from text.
     * current word is started from last space (" ") and ended with cursor position
     *
     * @param charSequence   the string of edit text
     * @param cursorPosition the position of cursor
     */
    private void extractCurrentWord(CharSequence charSequence, int cursorPosition) {
        currentWord = "";
        if(charSequence.length() <= 0 )
            return;

        for (int i = cursorPosition - 1; i >= 0; i--) {
            if (charSequence.charAt(i) == ' ' || charSequence.charAt(i) == '\n')
                break;
            currentWord += charSequence.charAt(i);
        }

        currentWord = new StringBuilder(currentWord).reverse().toString();
    }

    /**
     * search for current word, to see is that match with any mentionable words
     */
    private void searchCurrentWordInMentionableList() {
        tokens.clear();

        if (currentWord.length() < 2)
            return;

        for (String mentionableWord : mentionableWords) {
            if (mentionableWord.contains(currentWord)) {
                tokens.add(mentionableWord);
            }
        }
    }

    public String getCurrentWord(CharSequence charSequence, int cursorPosition)
    {
        findAllMentionableWords(charSequence.toString());
        extractCurrentWord(charSequence, cursorPosition);
        return currentWord;
    }
}
