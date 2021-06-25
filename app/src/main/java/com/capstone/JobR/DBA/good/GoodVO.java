package com.capstone.JobR.DBA.good;

import java.io.Serializable;

public class GoodVO implements Serializable {
    private int boardNum;
    private String id;

    public GoodVO(int boardNum, String id) {
        this.boardNum = boardNum;
        this.id = id;
    }

    @Override
    public String toString() {
        return "GoodVO{" +
                "boardNum=" + boardNum +
                ", id='" + id + '\'' +
                '}';
    }

    public int getBoardNum() {
        return boardNum;
    }

    public void setBoardNum(int boardNum) {
        this.boardNum = boardNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
