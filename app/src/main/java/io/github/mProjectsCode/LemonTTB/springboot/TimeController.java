package io.github.mProjectsCode.LemonTTB.springboot;

import io.github.mProjectsCode.LemonTTB.Logger.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/time")
public class TimeController {
    private static final Logger LOGGER = Logger.getLogger(TimeController.class);
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @PostConstruct
    public void init() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            executor.shutdown();
            try {
                executor.awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                LOGGER.logError(e.toString());
            }
        }));
    }


    @GetMapping("/time")
    @CrossOrigin
    public SseEmitter streamDateTime() {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);

        sseEmitter.onCompletion(() -> LOGGER.logInfo("SseEmitter is completed"));

        sseEmitter.onTimeout(() -> LOGGER.logInfo("SseEmitter is timed out"));

        sseEmitter.onError((ex) -> {
            LOGGER.logInfo("SseEmitter got error:");
            LOGGER.logError(ex);
        });

        executor.execute(() -> {
            while (true) {
                try {
                    sseEmitter.send(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")));
                    sleep(1, sseEmitter);
                } catch (IOException e) {
                    e.printStackTrace();
                    sseEmitter.completeWithError(e);
                }
            }
            // sseEmitter.complete();
        });

        LOGGER.logInfo("Controller exits");
        return sseEmitter;
    }

    private void sleep(int seconds, SseEmitter sseEmitter) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
            sseEmitter.completeWithError(e);
        }
    }
}
