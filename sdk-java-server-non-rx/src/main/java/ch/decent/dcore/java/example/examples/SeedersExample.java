package ch.decent.dcore.java.example.examples;

import ch.decent.sdk.api.blocking.DCoreApi;
import ch.decent.sdk.model.Seeder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeedersExample {

    @Autowired
    private ConnectionExample connectionExample;

    /**
     * Example of listing seeders.
     *
     * @param numberOfSeeders Maximum number of seeders you would like to obtain.
     * @return List of seeders ordered by rating.
     */
    public List<Seeder> listSeeders(int numberOfSeeders) {

        final DCoreApi dcoreApi = connectionExample.connect();

        return dcoreApi
            .getSeederApi()
            .listByRating(numberOfSeeders);
    }
}
