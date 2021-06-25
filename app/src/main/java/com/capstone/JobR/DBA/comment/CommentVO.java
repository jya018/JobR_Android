package com.capstone.JobR.DBA.comment;

import java.io.Serializable;

public class CommentVO implements Serializable {
    private int boardNum;
    private int commentNum;
    private String nickname;  //
    private String commentCont;
    private String commentDate;

    public CommentVO(int boardNum, String nickname, String commentCont) {
        this.boardNum = boardNum;
        this.nickname = nickname;
        this.commentCont = commentCont;
    }


    @Override
    public String toString() {
        return "CommentVO{" +
                "boardNum=" + boardNum +
                ", commentNum=" + commentNum +
                ", nickname='" + nickname + '\'' +
                ", commentCont='" + commentCont + '\'' +
                ", commentDate='" + commentDate + '\'' +
                '}';
    }


    public void setBoardNum(int boardNum) {
        this.boardNum = boardNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setCommentCont(String commentCont) {
        this.commentCont = commentCont;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public int getBoardNum() {
        return boardNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public String getNickname() {
        return nickname;
    }

    public String getCommentCont() {
        return commentCont;
    }

    public String getCommentDate() {
        return commentDate;
    }
}
