package domain;

public class Article {
    private int articleId;
    private String memberId;
    private String nickName;
    private String title;
    private String content;
    private String urlPath;
    private String createTime;

    public Article(int articleId, String memberId, String nickName, String title, String content, String urlPath, String createTime) {
        this.articleId = articleId;
        this.memberId = memberId;
        this.nickName = nickName;
        this.title = title;
        this.content = content;
        this.urlPath = urlPath;
        this.createTime = createTime;
    }

    public int getArticleId() { return articleId; }
    public String getMemberId() { return memberId; }
    public String getNickName() { return nickName; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getUrlPath() { return urlPath; }
    public String getCreateTime() { return createTime; }

    @Override
    public String toString() { return articleId+","+title + "," + content + "," + urlPath; }
}
