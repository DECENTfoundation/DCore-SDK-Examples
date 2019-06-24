package ch.decent.dcore.java.example.examples;

import ch.decent.sdk.DCoreApi;
import ch.decent.sdk.crypto.Credentials;
import ch.decent.sdk.model.Account;
import ch.decent.sdk.model.AmountWithAsset;
import ch.decent.sdk.model.OperationHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HistoryExample {

    private final static int RESULTS_PER_PAGE = 20;

    @Autowired
    private ApiInitializationExample apiInitializationExample;
    @Autowired
    private LoginExample loginExample;

    /**
     * Example of fetching history of my account by page numbers ordered from most recent to oldest.
     * @param pageNumber - pageNumber (Starting from 1)
     * @return List of transactions for given page
     */
    public List<OperationHistory> getHistoryPaginated(int pageNumber) {

        final DCoreApi dcoreApi = apiInitializationExample.connect();
        final Credentials credentials = loginExample.login();
        final int start = (pageNumber - 1) * RESULTS_PER_PAGE;

        return dcoreApi
            .getHistoryApi()
            .listOperationsRelative(credentials.getAccount(), start, RESULTS_PER_PAGE)
            .blockingGet();
    }

    /**
     * Example of fetching full history of my account ordered from most recent to oldest.
     * @return List of all operations for my account.
     */
    public List<OperationHistory> getFullHistory() {

        final DCoreApi dcoreApi = apiInitializationExample.connect();
        final Credentials credentials = loginExample.login();
        final List<OperationHistory> result = new ArrayList<>();

        int lastStart = 0;
        while (true) {

            final List<OperationHistory> currentPage = dcoreApi
                .getHistoryApi()
                .listOperationsRelative(credentials.getAccount(), lastStart, RESULTS_PER_PAGE)
                .blockingGet();

            lastStart += RESULTS_PER_PAGE;

            result.addAll(currentPage);

            if(currentPage.size() < RESULTS_PER_PAGE) {
                break;
            }
        }

        return result;
    }
}
