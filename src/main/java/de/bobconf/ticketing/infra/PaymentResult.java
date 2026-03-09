package de.bobconf.ticketing.infra;

public record PaymentResult(boolean success, String transactionId) {

    public static PaymentResult ok(String txId) {
        return new PaymentResult(true, txId);
    }

    public static PaymentResult failed() {
        return new PaymentResult(false, null);
    }
}
