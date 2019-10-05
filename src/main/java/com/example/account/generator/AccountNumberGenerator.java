package com.example.account.generator;

import java.util.Random;

public class AccountNumberGenerator {

    public static String generateAccountNumber() {
        Random rnd = new Random();
        int accountNumber = 1000000 + rnd.nextInt(9000000);
        return String.valueOf(accountNumber);
    }
}
