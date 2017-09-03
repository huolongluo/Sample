package com.example.huolongluo.sample.huolongluo.bean;

/**
 * Created by Administrator on 2017/8/10 0010.
 */

public class ItemBean
{

    private String title;
    private String content;

    public ItemBean(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ItemBean{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
