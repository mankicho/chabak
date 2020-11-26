package domain;

import java.awt.*;

public class Article {
    private int articleId;
    private String memberId;
    private String title;
    private String content;
    private String urlPath;
    private String createTime;

    public Article(int articleId, String memberId, String title, String content, String urlPath, String createTime) {
        this.articleId = articleId;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.urlPath = urlPath;
        this.createTime = createTime;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
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

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {

        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return articleId+","+title + "," + content + "," + urlPath;
    }
}
