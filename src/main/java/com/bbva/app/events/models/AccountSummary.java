package com.bbva.app.events.models;

public class AccountSummary {
    private String accountId;
    private double totalCredits;
    private double totalDebits;
    private double balance;
    private int eventsCount;

    public AccountSummary(String accountId, double totalCredits, double totalDebits, int eventsCount) {
        this.accountId = accountId;
        this.totalCredits = totalCredits;
        this.totalDebits = totalDebits;
        this.balance = totalCredits - totalDebits;
        this.eventsCount = eventsCount;
    }

    // Getters
    public String getAccountId() {
        return accountId;
    }

    public double getTotalCredits() {
        return totalCredits;
    }

    public double getTotalDebits() {
        return totalDebits;
    }

    public double getBalance() {
        return balance;
    }

    public int getEventsCount() {
        return eventsCount;
    }
}