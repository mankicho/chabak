package domain.member;

public class Member {
    private String id;
    private String nickName;
    private String pw;

    public Member() {
    }

    public Member(String id, String nickName, String pw) {
        this.id = id;
        this.nickName = nickName;
        this.pw = pw;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String toString() {
        return id + "," + nickName + "," + pw;
    }
}
