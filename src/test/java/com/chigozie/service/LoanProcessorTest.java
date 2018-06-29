package com.chigozie.service;

import com.chigozie.error.NoAvailableRateException;
import com.chigozie.error.UnfulfilledRequestException;
import com.chigozie.pojo.LoanRequest;
import com.chigozie.pojo.LoanResponse;
import com.chigozie.pojo.MarketData;
import com.chigozie.quotes.MarketDataBaseRateSelector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Optional;

import static com.chigozie.util.RoundingUtil.round;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class LoanProcessorTest {

    @Mock
    private MarketDataBaseRateSelector marketDataBaseRateSelector;
    private LoanProcessor loanProcessor;

    @BeforeEach
    public void setUp() {
        loanProcessor = new LoanProcessor(marketDataBaseRateSelector);
    }

    @Test
    public void loan_processor_returns_monthly_rate_when_principal_is_valid() throws IOException, NoAvailableRateException {

        Mockito.when(marketDataBaseRateSelector.getQuote(ArgumentMatchers.any(String.class))).thenReturn(dummyMarketData());
        LoanRequest request = LoanRequest.builder().withMarketDataFilePath("path").withPrincipal(1000).withTermInMonths(36).build();
        LoanResponse expectedResponse = LoanResponse
                .builder()
                .withMonthlyRepayment(30.83)
                .withRequestedAmount(request.getPrincipal())
                .withTotalRepayment(round(30.83 * request.getTermInMonths(), 2))
                .withRate(round(dummyMarketData().get().getRate() * 100, 1))
                .build();
        assertEquals(expectedResponse, loanProcessor.getQuote(request));
    }

    @Test
    public void should_fail_when_principal_is_not_within_range() {

        LoanRequest request = LoanRequest.builder().withMarketDataFilePath("path").withPrincipal(900).withTermInMonths(36).build();
        assertThrows(UnfulfilledRequestException.class, () -> loanProcessor.getQuote(request));
    }

    @Test
    public void should_fail_when_principal_is_not_an_increment_of_hundred_from_least_amount() {
        LoanRequest request = LoanRequest.builder().withMarketDataFilePath("any_path").withPrincipal(1050).withTermInMonths(36).build();
        assertThrows(UnfulfilledRequestException.class, () -> loanProcessor.getQuote(request));
    }

    @Test
    public void should_throw_unavailable_quote_exception() throws IOException, NoAvailableRateException {
        Mockito.when(marketDataBaseRateSelector.getQuote(ArgumentMatchers.any(String.class))).thenReturn(Optional.empty());
        LoanRequest request = LoanRequest.builder().withMarketDataFilePath("path").withPrincipal(1000).withTermInMonths(36).build();
        Assertions.assertThrows(NoAvailableRateException.class, ()-> loanProcessor.getQuote(request));
    }

    private Optional<MarketData> dummyMarketData() {
         MarketData marketData = new MarketData("lender", 0.069, 10);
         return Optional.of(marketData);
    }
}