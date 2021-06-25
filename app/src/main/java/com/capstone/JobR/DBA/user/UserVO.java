package com.capstone.JobR.DBA.user;

import java.io.Serializable;

public class UserVO implements Serializable {
    private String id;
    private String password;
    private String email;
    private String nickname;
    private String jobSort;
    private String regDate;

    public UserVO() {
    }

    public UserVO(String id, String password, String email, String nickname, String jobSort) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.jobSort = jobSort;
    }

    public UserVO(String password) {
        this.password = password;
    }

    public UserVO(String nickname, String jobSort) {
        this.nickname = nickname;
        this.jobSort = jobSort;
    }

    public UserVO(String id, String email, String nickname, String jobSort) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.jobSort = jobSort;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", jobsort='" + jobSort + '\'' +
                ", userDate='" + regDate + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getJobSort() {
        return jobSort;
    }

    public void setJobSort(String jobSort) {
        this.jobSort = jobSort;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }
}
