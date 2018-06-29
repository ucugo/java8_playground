package com.chigozie.pojo;

import static java.lang.Double.compare;
import static java.util.Objects.hash;

public class LoanResponse {

    private final double requestedAmount;
    private final double rate;
    private final double monthlyRepayment;
    private final double totalRepayment;

    private LoanResponse(Builder builder) {
        this.requestedAmount = builder.requestedAmount;
        this.rate = builder.rate;
        this.monthlyRepayment = builder.monthlyRepayment;
        this.totalRepayment = builder.totalRepayment;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoanResponse)) return false;
        LoanResponse that = (LoanResponse) o;
        return compare(that.requestedAmount, requestedAmount) == 0
                && compare(that.rate, rate) == 0
                && compare(that.monthlyRepayment, monthlyRepayment) == 0
                && compare(that.totalRepayment, totalRepayment) == 0;
    }

    @Override
    public int hashCode() {
        return hash(requestedAmount, rate, monthlyRepayment, totalRepayment);
    }

    @Override
    public String toString() {
        return "Requested amount: £" + requestedAmount +
                "\nRate: " + rate +"%"+
                "\nMonthly repayment: £" + monthlyRepayment +
                "\nTotal repayment: £" + totalRepayment ;
    }

    public static class Builder {
        private double requestedAmount;
        private double rate;
        private double monthlyRepayment;
        private double totalRepayment;

        public Builder withRequestedAmount(double requestedAmount) {
            this.requestedAmount = requestedAmount;
            return this;
        }

        public Builder withRate(double monthlyRate) {
            this.rate = monthlyRate;
            return this;
        }

        public Builder withMonthlyRepayment(double monthlyRepayment) {
            this.monthlyRepayment = monthlyRepayment;
            return this;
        }

        public Builder withTotalRepayment(double totalRepayment) {
            this.totalRepayment = totalRepayment;
            return this;
        }

        public LoanResponse build() {
            return new LoanResponse(this);
        }
    }
}
