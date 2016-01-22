package com.asdzheng.suitedrecyclerview.bean;

/**
 * Created by asdzheng on 2015/12/10.
 */
public class NewChannelInfoDetailChannelDto extends BaseDto
{
    private static final long serialVersionUID = 3914531728122301893L;
    private String author;
    private String book_times;
    private int cate;
    private String create_at;
    private String description;
    private String icon;
    private long id;
    public int is_private;
    private int mode;
    private String name;
    private String times;
    private String user_id;

    public String getAuthor() {
        return this.author;
    }

    public String getBook_times() {
        return this.book_times;
    }

    public int getCate() {
        return this.cate;
    }

    public String getCreate_at() {
        return this.create_at;
    }

    public String getDescription() {
        return this.description;
    }

    public String getIcon() {
        return this.icon;
    }

    public long getId() {
        return this.id;
    }

    public int getMode() {
        return this.mode;
    }

    public String getName() {
        return this.name;
    }

    public String getTimes() {
        return this.times;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }

    public void setBook_times(final String book_times) {
        this.book_times = book_times;
    }

    public void setCate(final int cate) {
        this.cate = cate;
    }

    public void setCreate_at(final String create_at) {
        this.create_at = create_at;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setIcon(final String icon) {
        this.icon = icon;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public void setMode(final int mode) {
        this.mode = mode;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setTimes(final String times) {
        this.times = times;
    }

    public void setUser_id(final String user_id) {
        this.user_id = user_id;
    }
}

