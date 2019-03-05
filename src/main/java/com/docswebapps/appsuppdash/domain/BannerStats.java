package com.docswebapps.appsuppdash.domain;

public class BannerStats {
    private Long p3Count;
    private Long p4Count;
    private Long problemCount;
    private Long riskCount;

    public Long getP3Count() {
        return p3Count;
    }

    public void setP3Count(Long p3Count) {
        this.p3Count = p3Count;
    }

    public Long getP4Count() {
        return p4Count;
    }

    public void setP4Count(Long p4Count) {
        this.p4Count = p4Count;
    }

    public Long getProblemCount() {
        return problemCount;
    }

    public void setProblemCount(Long problemCount) {
        this.problemCount = problemCount;
    }

    public Long getRiskCount() { return riskCount; }

    public void setRiskCount(Long riskCount) { this.riskCount = riskCount; }
}
