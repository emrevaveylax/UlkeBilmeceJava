import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class main {

    private static Map<String, String> countries = new HashMap<>();

    public static void main(String[] args) {
        loadCountriesFromFile("countries.txt");

        if (countries.isEmpty()) {
            System.out.println("Ülke verileri yüklenemedi.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        boolean playAgain = true;
        while (playAgain) {
            playGame(scanner);
            System.out.print("Tekrar oynamak ister misiniz? (Evet/Hayır): ");
            String response = scanner.nextLine().trim();
            playAgain = response.equalsIgnoreCase("Evet");
        }

        System.out.println("Oyun bitti. Teşekkürler!");
        scanner.close();
    }

    private static void playGame(Scanner scanner) {
        Random random = new Random();

        Object[] capitals = countries.values().toArray();
        String randomCapital = (String) capitals[random.nextInt(capitals.length)];
        String correspondingCountry = getKeyByValue(countries, randomCapital);

        System.out.println("Ülke Tahmin Etmece Oyununa Hoşgeldiniz!");
        System.out.println("Aşağıdaki başkent hangi ülkeye aittir?");
        System.out.println("Başkent: " + randomCapital);

        boolean guessedCorrectly = false;
        int attempts = 0;
        int maxAttempts = 3;

        while (attempts < maxAttempts && !guessedCorrectly) {
            System.out.print("Tahmininiz: ");
            String countryGuess = scanner.nextLine().trim();
            attempts++;

            if (correspondingCountry.equalsIgnoreCase(countryGuess)) {
                guessedCorrectly = true;
                System.out.println("Tebrikler! Doğru tahmin ettiniz. Ülke: " + correspondingCountry + ", Başkent: " + randomCapital);
            } else {
                System.out.println("Yanlış tahmin. Tekrar deneyin. Kalan hak: " + (maxAttempts - attempts));
            }
        }

        if (!guessedCorrectly) {
            System.out.println("Üzgünüz, doğru tahmin edemediniz. Ülke: " + correspondingCountry + ", Başkent: " + randomCapital);
        }

        System.out.println("Oyunu " + attempts + " denemede bitirdiniz.");
    }

    private static void loadCountriesFromFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    countries.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.out.println("Dosya okunurken bir hata oluştu: " + e.getMessage());
        }
    }

    private static String getKeyByValue(Map<String, String> map, String value) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(value.trim())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
