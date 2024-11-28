package com.example.task01;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task01Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        //здесь вы можете вручную протестировать ваше решение, вызывая реализуемый метод и смотря результат
        // например вот так:

        System.out.println(extractSoundName(new File("task01/src/main/resources/3724.mp3")));
    }

    public static String extractSoundName(File file) throws IOException, InterruptedException {
        // your implementation here

        String[] command = {"C:\\Users\\Lirihiwa\\AppData\\Local\\Microsoft\\WinGet\\Links\\ffprobe.exe", "-v", "error", "-of", "flat", "-show_format", file.toPath().toString()};
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        String title = null;

        while ((line = reader.readLine()) != null) {
            Pattern pattern = Pattern.compile("format\\.tags\\.title=\"(.*?)\"");
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                title = matcher.group(1);
                break;
            }
        }

        reader.close();
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IOException();
        }

        return title;
    }
}
