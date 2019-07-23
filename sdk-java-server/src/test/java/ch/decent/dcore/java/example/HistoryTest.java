package ch.decent.dcore.java.example;

import ch.decent.dcore.java.example.examples.HistoryExample;
import ch.decent.sdk.model.OperationHistory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HistoryTest {

    @Autowired
    private HistoryExample historyExample;

    @Test
    public void getFullHistory() {
        final List<OperationHistory> result = historyExample.getFullHistory(1000);

        Assert.assertTrue( result.size() > 0);
    }

    @Test
    public void getHistoryPaginated() {
        final List<OperationHistory> result = historyExample.getHistoryPaginated(2);

        Assert.assertEquals(HistoryExample.RESULTS_PER_PAGE, result.size());
    }
}
