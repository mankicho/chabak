package domain;

import java.awt.*;

public class Article {
    private String memberId;
    private String title;
    private String content;
    private Image image;
    private String createTime;

    public Article(String memberId, String title, String content, Image image, String createTime) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.image = image;
        this.createTime = createTime;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
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

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
