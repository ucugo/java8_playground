package com.chigozie.service;

import com.chigozie.pojo.MarketData;
import com.chigozie.quotes.MarketDataBaseRateSelector;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MarketDataBaseRateSelectorTest {

    private final MarketDataBaseRateSelector processor = new MarketDataBaseRateSelector();

    @Test
    public void rate_processor_to_return_least_market_rate() throws IOException {
        MarketData expectedMarketData = new MarketData("Jane", 0.069, 480);
        Optional<MarketData> marketData = processor.getQuote(getClass().getResource("/data/market_data.csv").getPath());
        assertTrue(marketData.isPresent());
        assertEquals(expectedMarketData, marketData.get());
    }

    @Test
    public void should_return_no_quote_when_there_is_unavailable_rate() throws IOException {
        Optional<MarketData> marketData = processor.getQuote(getClass().getResource("/data/market_data_with_no_available_rate.csv").getPath());
        assertFalse(marketData.isPresent());
    }

    @Test
    public void should_throw_exception_when_illegal_rate_column_contains_illegal_character() {
        assertThrows(RuntimeException.class, () -> processor.getQuote(getClass().getResource("/data/market_data_with_number_format_error.csv").getPath()));
    }
}