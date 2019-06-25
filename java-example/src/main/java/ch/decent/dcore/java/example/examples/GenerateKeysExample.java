package ch.decent.dcore.java.example.examples;

import ch.decent.sdk.DCoreApi;
import ch.decent.sdk.crypto.Credentials;
import ch.decent.sdk.model.ChainObject;
import ch.decent.sdk.model.ContentKeys;
import ch.decent.sdk.model.Seeder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GenerateKeysExample {

    @Autowired
    private ConnectionExample connectionExample;
    @Autowired
    private SeedersExample seedersExample;

    public ContentKeys generateKeys() {
        final DCoreApi dcoreApi = connectionExample.connect();
        // TODO login as valid seeder
        final List<ChainObject> seeders = seedersExample.listSeeders(10)
            .stream()
            .map(Seeder::getId).collect(Collectors.toList());

        return dcoreApi
            .getContentApi()
            .generateKeys(seeders)
            .blockingGet();
    }
}
