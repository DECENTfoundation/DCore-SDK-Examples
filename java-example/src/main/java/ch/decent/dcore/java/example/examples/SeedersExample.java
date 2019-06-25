package ch.decent.dcore.java.example.examples;

import ch.decent.sdk.DCoreApi;
import ch.decent.sdk.model.Seeder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeedersExample {

    @Autowired
    private ConnectionExample connectionExample;

     public List<Seeder> listSeeders(int numberOfSeeders) {

         final DCoreApi dcoreApi = connectionExample.connect();

         return dcoreApi
             .getSeedersApi()
             .listByUpload(numberOfSeeders)
             .blockingGet();
     }
}
