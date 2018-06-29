package com.chigozie.quotes;

import com.chigozie.pojo.MarketData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class MarketDataBaseRateSelector implements QuoteSelector {

    private final Function<String, MarketData> mapToMarketData = (line) -> {
        String[] item = line.split(",");
        MarketData marketData;
        try {
            marketData = new MarketData(item[0], Double.valueOf(item[1]), Integer.valueOf(item[2]));
        } catch (NumberFormatException e) {
            throw new RuntimeException(String.format("Error in processing marketData %s", line), e);
        }
        return marketData;
    };

    public Optional<MarketData> getQuote(String filePath) throws IOException, NumberFormatException {
        Objects.requireNonNull(filePath, "file path cannot be null");
       return Files.lines(Paths.get(filePath))
               .skip(1)
               .map(mapToMarketData)
               .filter(marketData -> marketData.getAvailable() > 0)
               .min(Comparator.comparing((MarketData::getRate)));
    }
}
