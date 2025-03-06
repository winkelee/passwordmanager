package com.soft.passwordmanager;

import java.io.File;
import java.io.IO;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PasswordFileController {

    public static Path getAppDataPath(String fileName){
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

}
