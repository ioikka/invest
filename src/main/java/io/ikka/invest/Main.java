package io.ikka.invest;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Main {
    @SneakyThrows
    public static void main2(String[] args) {
        String urlStr = "https://ya.ru";
        String outputFilePath = "ll.html";

        Consumer<Runnable> monitorExecTime = timeMonitoredConsumer();

        monitorExecTime.accept(() -> saveDataFromUrlToFile(urlStr, outputFilePath));
        monitorExecTime.accept(() -> getDataFromUrlAsString(urlStr));

        monitorExecTime.accept(() -> saveDataFromUrlToFile(urlStr, outputFilePath));
        monitorExecTime.accept(() -> getDataFromUrlAsString(urlStr));
    }

    @NotNull
    public static Consumer<Runnable> timeMonitoredConsumer() {
        return r -> {
            long start = System.currentTimeMillis();
            r.run();
            System.out.println(System.currentTimeMillis() - start);
        };
    }


    @SneakyThrows
    public static void saveDataFromUrlToFile(String urlStr, String outputFilePath) {
        URL url = new URL(urlStr);
        try (InputStream inputStream = url.openStream();
             ReadableByteChannel readableByteChannel = Channels.newChannel(inputStream);
             FileOutputStream fileOutputStream = new FileOutputStream(outputFilePath);
        ) {
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        }
    }

    @SneakyThrows
    public static String getDataFromUrlAsString(String url) {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream())) {
            return new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
        }
    }
}
