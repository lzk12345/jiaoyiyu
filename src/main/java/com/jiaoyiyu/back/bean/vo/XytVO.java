package com.jiaoyiyu.back.bean.vo;

/**
 * XytVO class
 *
 * @author maochaoying
 * @date 2020/4/27
 */

public class XytVO {
    private Integer id;
    private String title;

    private Integer uid;

    private String cata1Name;

    private String cata2Name;

    private String coverUrl;

    private Integer comments;

    private Integer likeNum;

    private Integer pageView;

    private Integer days;

    private String nickName;

    private String headPic;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCata1Name() {
        return cata1Name;
    }

    public void setCata1Name(String cata1Name) {
        this.cata1Name = cata1Name;
    }

    public String getCata2Name() {
        return cata2Name;
    }

    public void setCata2Name(String cata2Name) {
        this.cata2Name = cata2Name;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Integer getPageView() {
        return pageView;
    }

    public void setPageView(Integer pageView) {
        this.pageView = pageView;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }
}
