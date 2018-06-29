package com.chigozie.pojo;

public class LoanRequest {

    private final String marketDataFilePath;
    private final int termInMonths;
    private final double principal;

    private LoanRequest(Builder builder) {
        this.termInMonths = builder.termInMonths;
        this.principal = builder.principal;
        this.marketDataFilePath = builder.marketDataFilePath;
    }

    public double getPrincipal() {
        return principal;
    }

    public int getTermInMonths() {
        return termInMonths;
    }

    public String getMarketDataFilePath() {
        return marketDataFilePath;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int termInMonths;
        private double principal;
        private String marketDataFilePath;

        public Builder withTermInMonths(int termInMonths) {
            this.termInMonths = termInMonths;
            return this;
        }

        public Builder withPrincipal(double principal) {
            this.principal = principal;
            return this;
        }

        public Builder withMarketDataFilePath(String marketDataFilePath) {
            this.marketDataFilePath = marketDataFilePath;
            return this;
        }

        public LoanRequest build() {
            return new LoanRequest(this);
        }
    }
}
