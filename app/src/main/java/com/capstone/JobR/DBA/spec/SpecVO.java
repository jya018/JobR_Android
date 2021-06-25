package com.capstone.JobR.DBA.spec;

import java.io.Serializable;

public class SpecVO implements Serializable {
    private String id;
    private String companyName;
    private int toeic;
    private int toefl;
    private int teps;
    private int opic;
    private int tos;
    private boolean internship;
    private String degree;
    private float score;

    public SpecVO() {
    }

    public SpecVO(String id, String specCompanyName, String spectoeic, String spectoefl, String specteps, String specopic, String spectos, boolean specinternship, String specdegree, String specscore) {
        this.id = id;
        this.companyName = specCompanyName;
        this.toeic = Integer.parseInt(spectoeic);
        this.toefl = Integer.parseInt(spectoefl);
        this.teps = Integer.parseInt(specteps);
        this.opic = Integer.parseInt(specopic);
        this.tos = Integer.parseInt(spectos);
        this.internship = specinternship;
        this.degree = specdegree;
        this.score = Float.parseFloat(specscore);
    }

    public SpecVO(String specCompanyName, String spectoeic, String spectoefl, String specteps, String specopic, String spectos, boolean specinternship, String specdegree, String specscore) {
        this.companyName = specCompanyName;
        this.toeic = Integer.parseInt(spectoeic);
        this.toefl = Integer.parseInt(spectoefl);
        this.teps = Integer.parseInt(specteps);
        this.opic = Integer.parseInt(specopic);
        this.tos = Integer.parseInt(spectos);
        this.internship = specinternship;
        this.degree = specdegree;
        this.score = Float.parseFloat(specscore);
    }

    @Override
    public String toString() {
        return "SpecVO{" +
                "id='" + id + '\'' +
                ", companyName='" + companyName + '\'' +
                ", toeic=" + toeic +
                ", toefl=" + toefl +
                ", teps=" + teps +
                ", opic=" + opic +
                ", tos=" + tos +
                ", intership=" + internship +
                ", degree='" + degree + '\'' +
                ", score=" + score +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getToeic() {
        return toeic;
    }

    public void setToeic(int toeic) {
        this.toeic = toeic;
    }

    public int getToefl() {
        return toefl;
    }

    public void setToefl(int toefl) {
        this.toefl = toefl;
    }

    public int getTeps() {
        return teps;
    }

    public void setTeps(int teps) {
        this.teps = teps;
    }

    public int getOpic() {
        return opic;
    }

    public void setOpic(int opic) {
        this.opic = opic;
    }

    public int getTos() {
        return tos;
    }

    public void setTos(int tos) {
        this.tos = tos;
    }

    public boolean isIntership() {
        return internship;
    }

    public void setInternship(boolean internship) {
        this.internship = internship;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
