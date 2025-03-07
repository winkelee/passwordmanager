package com.soft.passwordmanager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

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
        return baseDirectory.resolve(fileName + ".enc");
    }

    public static void savePassword(String fileName, String encryptedPassword) throws IOException { //When providing filename for this and the next method,
        Path filePath = getAppDataPath(fileName);                                                   //DO NOT SUPPLY THE .ENC FILE EXTENSION,
        Files.write(filePath, encryptedPassword.getBytes());                                        //It is already supplied by getAppDataPath().
    }

    public static String readPassword(String filename) throws IOException{
        Path filePath = getAppDataPath(filename);
        if (Files.exists(filePath)){
            return Files.readString(filePath);
        } else{
            return null;
        }
    }

    public static void saveMasterPassword(String masterPassword, Path filePath) throws Exception {
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
