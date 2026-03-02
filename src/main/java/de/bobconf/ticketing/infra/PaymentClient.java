package de.bobconf.ticketing.infra;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PaymentClient {

    public boolean charge(String customerEmail, int totalPriceInCents) {
        try {
            // BLOCKING: simuliert langsame externe API
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
        // Immer erfolgreich für die Demo
        return true;
    }
}
