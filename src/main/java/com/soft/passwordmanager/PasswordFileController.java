package com.soft.passwordmanager;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class PasswordFileController {

    public static Path getAppDataPath(String fileName){ //Returns the path WITH the filename already.
        String os = System.getProperty("os.name").toLowerCase();
        String home = System.getProperty("user.home");

        Path baseDirectory;
        if (os.contains("win")){
            baseDirectory = Paths.get(System.getenv("APPDATA"), "passwordmanager");
        } else{
            baseDirectory = Paths.get(home, ".passwordmanager");
        }
        baseDirectory.toFile().mkdirs();
        return baseDirectory.resolve(fileName); //SUPPLYING .ENC FILE EXTENSION IS REQUIRED.

    }

    public static Path getDocumentsPath(String fileName) {
        String home = System.getProperty("user.home");
        Path documentsDirectory;
        documentsDirectory = Paths.get(home, "Documents");
        documentsDirectory.toFile().mkdirs();
        return documentsDirectory.resolve(fileName);
    }


    public static void saveCredential(String domainName, Credentials credentials, String encryptedPassword, byte[] iv) throws IOException { //When using this method, DO NOT build the filename in it. The method will do it on its own.
        String randomString = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        Path filePath;
        if (domainName.contains(".enc")){
            domainName = domainName.substring(0, domainName.length() - 4);
            filePath = getAppDataPath(domainName + "_" + credentials.getUsername() + ".enc");
        } else{
            filePath = getAppDataPath(domainName + "_" + credentials.getUsername() + ".enc");
        }
        String fileContents = credentials.getHostUrl() + "\n" +
                credentials.getUsername() + "\n" +
                Base64.getEncoder().encodeToString(iv) + "\n" +
                encryptedPassword;

        Files.write(filePath, fileContents.getBytes(StandardCharsets.UTF_8));
    }

    public static boolean deleteCredential(String filename) throws IOException {
        Path filepath = getAppDataPath(filename);
        System.out.println("ATTEMPTING TO DELETE: " + filepath);
        return Files.deleteIfExists(filepath);
    }

    public static ObservableList<Credentials> readImportedData(File file) {
        ObservableList<Credentials> credentialsList = FXCollections.observableArrayList();
        Path path = Path.of(file.getAbsolutePath());
        try (CSVReader csvReader = new CSVReader(new FileReader(path.toFile()))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                if (values.length < 3) {
                    System.out.println("Skipping invalid row: " + String.join(", ", values));
                    continue;
                }
                String website = values[0];
                String username = values[1];
                String plaintextPassword = values[2];
                byte[] iv = PasswordCryptography.generateIV();
                String encryptedPassword = PasswordCryptography.encrypt(plaintextPassword, PasswordManager.key, iv);
                Credentials credentials = new Credentials(username, encryptedPassword, website, iv); //meow meow meow
                saveCredential(website + ".enc", credentials, encryptedPassword, iv);
                credentialsList.add(credentials);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return credentialsList;
    }

    public static void exportData() throws Exception {
        Path filepath = getDocumentsPath("exported.csv");
        File file = new File(filepath.toString());
        FileWriter fileWriter = new FileWriter(file);
        CSVWriter csvWriter = new CSVWriter(fileWriter);
        ObservableList<Credentials> credentials = getCredentialFiles();

        for (int i = 0; i < credentials.size(); i++){
            Credentials gottenCredential = credentials.get(i);
            String[] parsedCredential = {gottenCredential.getHostUrl(), gottenCredential.getUsername(), PasswordCryptography.decrypt(gottenCredential.getPassword(), PasswordManager.key, gottenCredential.getIv())};
            csvWriter.writeNext(parsedCredential);
        }

        csvWriter.close();
        PasswordManager.displayPopUp("Your data was successfully exported!", "Close");
    }

    public static Credentials readCredential(String fileName) throws IOException {
        Path filePath = getAppDataPath(fileName);
        if (!Files.exists(filePath)) {
            System.out.println("Error while trying to read file: " + fileName);
            System.out.println("Could not read credential because it does not exist.");
            return null;
        }
        List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        if (lines.size() < 4) {
            System.out.println("Error while trying to read file: " + fileName);
            System.out.println("Could not read file as it does not correspond to the stored credential format.");
            return null;
        }
        String website = lines.get(0);
        String username = lines.get(1);
        byte[] iv = Base64.getDecoder().decode(lines.get(2));
        String encryptedPassword = lines.get(3);
        return new Credentials(username, encryptedPassword, website, iv);
    }

    public static void saveMasterPassword(String masterPassword, Path filePath) throws Exception { //This method generates salt by itself, so no need to pass it as an argument.
        byte[] salt = PasswordCryptography.generateSalt();
        String hash = PasswordCryptography.hashPassword(masterPassword, salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        Files.write(filePath, (encodedSalt + ":" + hash).getBytes());
    }

    public static boolean verifyMasterPassword(String enteredPassword, Path filePath) throws Exception {
        String storedData = Files.readString(filePath);
        String[] parts = storedData.split(":");

        byte[] salt = Base64.getDecoder().decode(parts[0]);
        String expectedHash = parts[1];

        String computedHash = PasswordCryptography.hashPassword(enteredPassword, salt);
        return expectedHash.equals(computedHash);
    }

    public static byte[] getSalt(Path pathToMasterPassword) throws Exception{
        String storedData = Files.readString(pathToMasterPassword);
        String[] parts = storedData.split(":");
        return Base64.getDecoder().decode(parts[0]);
    }

    public static ObservableList<Credentials> getCredentialFiles() {
        ObservableList<Credentials> credentialItems = FXCollections.observableArrayList();
        try {
            Path directoryPath = getAppDataPath("");
            System.out.println(directoryPath);
            if (!Files.exists(directoryPath) || !Files.isDirectory(directoryPath)) {
                return credentialItems;
            }
            Files.list(directoryPath)
                    .filter(Files::isRegularFile)
                    .filter(filePath -> !filePath.getFileName().toString().equals("master.enc"))
                    .forEach(filePath -> {
                        Credentials credentials;
                        try {
                            credentials = PasswordFileController.readCredential(filePath.getFileName().toString());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        if (credentials != null) {
                            credentialItems.add(credentials);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return credentialItems;
    }

}
