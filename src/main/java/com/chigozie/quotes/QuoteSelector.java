package com.chigozie.quotes;

import com.chigozie.pojo.MarketData;

import java.io.IOException;
import java.util.Optional;

public interface QuoteSelector {

    Optional<MarketData> getQuote(String filePath) throws IOException;
}
