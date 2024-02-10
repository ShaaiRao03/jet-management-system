package com.example.programming2_c1.UserClasses;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @Test
    void checkGetAndUpdateClientOperation() throws InterruptedException {

        Client client = Client.getClientGivenUsername("test");
        assertEquals("Test",client.getFirst_name());
        assertEquals("Test",client.getJob());

        Client.updateClient("Dummy","Dummy","test");

        Client client1 = Client.getClientGivenUsername("test");
        assertEquals("Dummy",client1.getJob());

        Client.updateClient("Test","Test","test");
        assertEquals("Test",client.getJob());
    }

}