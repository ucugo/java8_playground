package com.chigozie.service;

import com.chigozie.error.NoAvailableRateException;
import com.chigozie.error.UnfulfilledRequestException;
import com.chigozie.pojo.LoanRequest;
import com.chigozie.pojo.LoanResponse;
import com.chigozie.pojo.MarketData;
import com.chigozie.quotes.QuoteSelector;

import java.io.IOException;
import java.util.function.Predicate;

import static com.chigozie.pojo.LoanResponse.builder;
import static com.chigozie.util.RoundingUtil.round;

public class LoanProcessor {

    private static final double MIN_LOAN_AMOUNT = 1000;
    private static final double MAX_LOAN_AMOUNT = 15000;
    private static final double MONTHS_IN_A_YEAR = 12;
    private static final double MAX_PERCENTAGE_RATE = 100;

    private final QuoteSelector quoteSelector;

    public LoanProcessor(QuoteSelector quoteSelector) {
        this.quoteSelector = quoteSelector;
    }

    public LoanResponse getQuote(LoanRequest loanRequest) throws IOException, NoAvailableRateException {

        validateLoanAmount(loanRequest.getPrincipal());
        MarketData bestMarketRate = quoteSelector.getQuote(loanRequest.getMarketDataFilePath()).orElseThrow(NoAvailableRateException::new);
        final double monthlyPayment = getMonthlyPayment(loanRequest, bestMarketRate);

        return builder()
                .withMonthlyRepayment(monthlyPayment)
                .withRequestedAmount(loanRequest.getPrincipal())
                .withTotalRepayment(round(monthlyPayment * loanRequest.getTermInMonths(), 2))
                .withRate(round(bestMarketRate.getRate() * MAX_PERCENTAGE_RATE, 1))
                .build();
    }

    private double getMonthlyPayment(LoanRequest loanRequest, MarketData bestMarketRate) {
        final double ratePerMonth =  bestMarketRate.getRate() / MONTHS_IN_A_YEAR;
        double monthlyPayment = (loanRequest.getPrincipal() * ratePerMonth) / (1 - Math.pow(1 + ratePerMonth, -loanRequest.getTermInMonths()));
        return round(monthlyPayment, 2);
    }

    private void validateLoanAmount(double principal) {
        validate(t -> principal >= MIN_LOAN_AMOUNT && principal <= MAX_LOAN_AMOUNT, principal);
        validate(t -> principal % MAX_PERCENTAGE_RATE == 0, principal);
    }

    private void validate(Predicate<Number> predicate, Number val) {
        if(!predicate.test(val)) {
            throw new UnfulfilledRequestException("Loan amount must be between £1000.0 and £15000 and should be in a multiple of 100");
        }
    }
}
