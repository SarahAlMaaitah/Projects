package utility;

import com.github.javafaker.Faker;
import java.util.Random;

public class RandomGenerator {
    static Faker faker = new Faker();
    public static String generateRandomName() {
        return faker.name().nameWithMiddle();
    }

    public static String generateRandomPhoneNumber() {
        StringBuilder phoneNumber = new StringBuilder("05");

        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            phoneNumber.append(random.nextInt(10));
            if (i == 2 || i == 5) {
                phoneNumber.append(" ");
            }
        }

        return phoneNumber.toString();
    }

    public static String generateFakeCreditCardNumber() {
        return "4222222222222";
    }

    public static String generateRandomExpiryYear() {
        return "12/28";
    }

    public static String generateRandomCVC() {
        return faker.number().digits(3);
    }

}

