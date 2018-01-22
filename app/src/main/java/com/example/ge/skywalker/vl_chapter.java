package com.example.ge.skywalker;

/**
 * Created by GE on 1/3/2018.
 */

public class vl_chapter {
    private String chapterId, chapterNameValue, chapterNumberValue;
    public vl_chapter() {
    }
    public vl_chapter(String chapterId, String chapterNameValue, String chapterNumberValue) {
        this.chapterId = chapterId;
        this.chapterNameValue = chapterNameValue;
        this.chapterNumberValue = chapterNumberValue;
    }

    public String getChapterId() {
        return chapterId;
    }

    public String getChapterNameValue() {
        return chapterNameValue;
    }

    public String getChapterNumberValue() {
        return chapterNumberValue;
    }


}
