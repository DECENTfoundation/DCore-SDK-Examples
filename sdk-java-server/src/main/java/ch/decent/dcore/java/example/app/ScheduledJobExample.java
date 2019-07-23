package ch.decent.dcore.java.example.app;

import ch.decent.dcore.java.example.examples.BalanceExample;
import ch.decent.sdk.model.AmountWithAsset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledJobExample {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledJobExample.class);

    @Autowired
    private BalanceExample balanceExample;

    /**
     * Example of recurrent job executing getMyBalance on my account every 1000 ms.
     */
    @Scheduled(fixedDelay = 1000)
    public void schedule() {
        final AmountWithAsset myBalance = balanceExample.getMyBalance();
        logger.info("My balance is: {}", myBalance.getAmount().getAmount());
    }
}
