# MentionableEditText
> An Android View to easily add social-media-esque mention

### Usage:

### 1. Add MentionableEditText in your XML:
```xml
<com.mentionedittext.view.widget.MentionableEditText
    android:id="@+id/editText"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"/>
```

### 2. Bind it and set input list in your view:
```java
ArrayList<MentionModel> inputList = new ArrayList<>();
inputList.addAll(your_data);
mentionableEditText = (MentionableEditText) findViewById(R.id.editText);
mentionableEditText.setInputList(inputList);
```
