package ch.decent.dcore.java.example.examples;

import ch.decent.sdk.DCoreApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UIAExample {

    @Autowired
    private ConnectionExample connectionExample;
    @Autowired
    private LoginExample loginExample;

    public void create() {
        final DCoreApi dcoreApi = connectionExample.connect();
        //TODO
    }

    public void issue() {
        //TODO
    }
}
