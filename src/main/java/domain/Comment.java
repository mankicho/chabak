package domain;

public class Comment {
    private int commentId;
    private int articleId;
    private String memberId;
    private String nickName;
    private String content;
    private String createTime;

    public Comment(int commentId, int articleId, String memberId, String nickName, String content, String createTime) {
        this.articleId = articleId;
        this.memberId = memberId;
        this.commentId = commentId;
        this.nickName = nickName;
        this.content = content;
        this.createTime = createTime;
    }

    public int getArticleId() { return articleId; }

    public String getMemberId() { return memberId; }

    public int getCommentId() { return commentId; }

    public String getNickName() { return nickName; }

    public String getContent() { return content; }

    public String getCreateTime() { return createTime; }

}
