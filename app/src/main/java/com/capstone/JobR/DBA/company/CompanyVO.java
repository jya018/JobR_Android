package com.capstone.JobR.DBA.company;

import java.io.Serializable;

public class CompanyVO implements Serializable {
    private String compName;	// 회사이름
    private String compSort;    // 회사계열사
    private String compCeo;     // 회사대표 이름
    private String compEst; 	// 회사 설립일
    private String compType;    // 회사 업종
    private String compIpo;		// 회사 상장일
    private String compSales;   // 회사 매출액
    private String compEmp;     // 회사 직원수
    private String compAvgSal;  // 회사 평균연봉
    private String compPhNum;   // 회사 번호
    private String compLoc;     // 회사 주소

    public CompanyVO(String compName, String compSort, String compCeo, String compEst, String compType, String compIpo, String compSales, String compEmp, String compAvgSal, String compPhNum, String compLoc) {
        this.compName = compName;
        this.compSort = compSort;
        this.compCeo = compCeo;
        this.compEst = compEst;
        this.compType = compType;
        this.compIpo = compIpo;
        this.compSales = compSales;
        this.compEmp = compEmp;
        this.compAvgSal = compAvgSal;
        this.compPhNum = compPhNum;
        this.compLoc = compLoc;
    }

    @Override
    public String toString() {
        return "CompanyVO{" +
                "compName='" + compName + '\'' +
                ", compSort='" + compSort + '\'' +
                ", compCeo='" + compCeo + '\'' +
                ", compEst='" + compEst + '\'' +
                ", compType='" + compType + '\'' +
                ", compIpo='" + compIpo + '\'' +
                ", compSales='" + compSales + '\'' +
                ", compEmp='" + compEmp + '\'' +
                ", compAvgSal='" + compAvgSal + '\'' +
                ", compPhNum='" + compPhNum + '\'' +
                ", compLoc='" + compLoc + '\'' +
                '}';
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getCompSort() {
        return compSort;
    }

    public void setCompSort(String compSort) {
        this.compSort = compSort;
    }

    public String getCompCeo() {
        return compCeo;
    }

    public void setCompCeo(String compCeo) {
        this.compCeo = compCeo;
    }

    public String getCompEst() {
        return compEst;
    }

    public void setCompEst(String compEst) {
        this.compEst = compEst;
    }

    public String getCompType() {
        return compType;
    }

    public void setCompType(String compType) {
        this.compType = compType;
    }

    public String getCompIpo() {
        return compIpo;
    }

    public void setCompIpo(String compIpo) {
        this.compIpo = compIpo;
    }

    public String getCompSales() {
        return compSales;
    }

    public void setCompSales(String compSales) {
        this.compSales = compSales;
    }

    public String getCompEmp() {
        return compEmp;
    }

    public void setCompEmp(String compEmp) {
        this.compEmp = compEmp;
    }

    public String getCompAvgSal() {
        return compAvgSal;
    }

    public void setCompAvgSal(String compAvgSal) {
        this.compAvgSal = compAvgSal;
    }

    public String getCompPhNum() {
        return compPhNum;
    }

    public void setCompPhNum(String compPhNum) {
        this.compPhNum = compPhNum;
    }

    public String getCompLoc() {
        return compLoc;
    }

    public void setCompLoc(String compLoc) {
        this.compLoc = compLoc;
    }
}
