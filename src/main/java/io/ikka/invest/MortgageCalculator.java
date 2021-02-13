package io.ikka.invest;

public class MortgageCalculator {

    public static void main2(String[] args) {
        var rate = 9.5;

        double amount = 500_000d;
        int times = 79;

        double payment = getPayment(amount, rate, times);
        System.out.println(payment);
    }

    /**
     * @param amount
     * @param rate             as in 1.2%
     * @param numberOfPayments
     * @return
     */
    static double getPayment(double amount, double rate, double numberOfPayments) {
        double monthlyRate = (rate / 100) / 12;
        return payment(amount, monthlyRate, numberOfPayments);
    }

    /**
     * @param p principal
     * @param r rate
     * @param n number of payments
     * @return
     */
    static double payment(double p, double r, double n) {
        return (p * r) / (1 - Math.pow(1 + r, -n));
    }
}
