package ch.decent.dcore.java.example.examples;

import ch.decent.sdk.api.blocking.DCoreApi;
import ch.decent.sdk.crypto.Credentials;
import ch.decent.sdk.model.OperationHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HistoryExample {

    public final static int RESULTS_PER_PAGE = 10;

    @Autowired
    private ConnectionExample connectionExample;
    @Autowired
    private LoginExample loginExample;

    /**
     * Example of fetching history of my account by page numbers ordered from most recent to oldest.
     *
     * @param pageNumber - pageNumber (Starting from 1)
     * @return List of transactions for given page
     */
    public List<OperationHistory> getHistoryPaginated(int pageNumber) {

        final DCoreApi dcoreApi = connectionExample.connect();
        final Credentials credentials = loginExample.login();
        final int start = (pageNumber - 1) * RESULTS_PER_PAGE;

        return dcoreApi
            .getHistoryApi()
            .listOperationsRelative(credentials.getAccount(), start, RESULTS_PER_PAGE);
    }

    /**
     * Example of fetching full history of my account ordered from most recent to oldest.
     *
     * @return List of all operations for my account.
     */
    public List<OperationHistory> getFullHistory(int maxResults) {

        final DCoreApi dcoreApi = connectionExample.connect();
        final Credentials credentials = loginExample.login();
        final List<OperationHistory> result = new ArrayList<>();

        while (true) {

            final List<OperationHistory> currentPage = dcoreApi
                .getHistoryApi()
                .listOperationsRelative(credentials.getAccount(), result.size(), RESULTS_PER_PAGE);

            result.addAll(currentPage);

            if (currentPage.size() < RESULTS_PER_PAGE || result.size() >= maxResults) {
                break;
            }
        }

        return result;
    }
}
