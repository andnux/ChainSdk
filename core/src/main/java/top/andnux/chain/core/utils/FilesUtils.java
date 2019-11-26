package top.andnux.chain.core.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FilesUtils {

    public static String readFileString(String fileName) {
        StringBuilder builder = new StringBuilder();
        try (FileReader reader = new FileReader(fileName);
             BufferedReader br = new BufferedReader(reader)) {
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}
