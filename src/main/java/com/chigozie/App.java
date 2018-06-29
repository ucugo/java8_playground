package com.chigozie;

import com.chigozie.error.NoAvailableRateException;
import com.chigozie.error.UnfulfilledRequestException;
import com.chigozie.pojo.LoanRequest;
import com.chigozie.pojo.LoanResponse;
import com.chigozie.quotes.QuoteSelector;
import com.chigozie.service.LoanProcessor;
import com.chigozie.quotes.MarketDataBaseRateSelector;

import java.io.IOException;

public class App {

    public static void main(String[] args) {
        String filePath = args[0];
        String loanAmount = args[1];

        try {
        LoanRequest loanRequest = LoanRequest.builder()
                .withMarketDataFilePath(filePath)
                .withPrincipal(Integer.valueOf(loanAmount))
                .withTermInMonths(36)
                .build();

        QuoteSelector quoteSelector = new MarketDataBaseRateSelector();
        LoanResponse loanResponse = new LoanProcessor(quoteSelector).getQuote(loanRequest);
        System.out.println(loanResponse);
        } catch (NumberFormatException e) {
            System.err.println(String.format("Wrong format entered for loan amount: %s", loanAmount));
        } catch (UnfulfilledRequestException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(String.format("Error with market-data file: %s", filePath));
        } catch (NoAvailableRateException e) {
            System.out.println("No available quote at this time");
        }
    }
}
