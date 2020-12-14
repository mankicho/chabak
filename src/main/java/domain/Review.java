package domain;

public class Review {
    private int placeId;
    private String memberId;
    private String nickName;
    private String review_content;
    private Double evaluation_point;
    private String eval_time;

    public Review(int placeId, String memberId, String nickName, String review_content, Double evaluation_point, String eval_time) {
        this.placeId = placeId;
        this.memberId = memberId;
        this.nickName = nickName;
        this.review_content = review_content;
        this.evaluation_point = evaluation_point;
        this.eval_time = eval_time;
    }

    public int getPlaceId() { return placeId; }
    public String getMemberId() { return memberId; }
    public String getNickName() { return nickName; }
    public String getReview_content() { return review_content; }
    public Double getEvaluation_point() { return evaluation_point; }
    public String getEval_time() { return eval_time; }
}
