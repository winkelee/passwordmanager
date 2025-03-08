package com.soft.passwordmanager;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

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
        if (fileName.equals("")){
            return baseDirectory.resolve(fileName); //This is here because, if we do not pass anything as an argument, we want to return the path without any filename, and not the filename being a .enc
        } else {
            return baseDirectory.resolve(fileName + ".enc");
        }

    }

    public static void saveCredential(String fileName, Credentials credentials, String encryptedPassword, byte[] iv) throws IOException { //When providing filename for this and the next method,
        Path filePath = getAppDataPath(fileName);
        String fileContents = credentials.getHostUrl() + "\n" +                                                                             //DO NOT SUPPLY THE .ENC FILE EXTENSION,
                credentials.getUsername() + "\n" +                                                                                          //It is already supplied by getAppDataPath().
                Base64.getEncoder().encodeToString(iv) + "\n" +
                encryptedPassword;

        Files.write(filePath, fileContents.getBytes(StandardCharsets.UTF_8));
    }

    public static Credentials readCredential(String fileName) throws IOException {
        Path filePath = getAppDataPath(fileName);
        if (!Files.exists(filePath)) {
            return null;
        }
        List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        if (lines.size() < 4) {
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

}
