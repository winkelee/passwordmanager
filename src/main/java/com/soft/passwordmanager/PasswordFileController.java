package com.soft.passwordmanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
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

    public static void saveCredential(String fileName, Credentials credentials, String encryptedPassword, byte[] iv) throws IOException {
        Path filePath = getAppDataPath(fileName); //Knowing that the filename is the website name, we need to handle adding .enc in the filename
        String fileContents = credentials.getHostUrl() + "\n" +
                credentials.getUsername() + "\n" +
                Base64.getEncoder().encodeToString(iv) + "\n" +
                encryptedPassword;

        Files.write(filePath, fileContents.getBytes(StandardCharsets.UTF_8));
    }

    public static boolean deleteCredential(String filename) throws IOException {
        Path filepath = getAppDataPath(filename);
        return Files.deleteIfExists(filepath);
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
                            System.out.println(credentials.toString());
                            System.out.println(filePath.getFileName().toString());
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
