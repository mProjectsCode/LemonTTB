package io.github.mProjectsCode.LemonTTB.springboot;

import com.google.gson.Gson;
import io.github.mProjectsCode.LemonTTB.App;
import io.github.mProjectsCode.LemonTTB.Logger.Logger;
import io.github.mProjectsCode.LemonTTB.events.Event;
import io.github.mProjectsCode.LemonTTB.events.EventHandler;
import io.github.mProjectsCode.LemonTTB.events.EventListener;
import io.github.mProjectsCode.LemonTTB.events.EventType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The type Watch controller.
 */
@RestController
@RequestMapping("/api/startUp")
public class WatchController {
    private static final Logger LOGGER = Logger.getLogger(TimeController.class);
    private final Gson gson = new Gson();

    /**
     * Instantiates a new Watch controller.
     */
    public WatchController() {

    }

    /**
     * Start bot response entity.
     *
     * @return the response entity
     */
    @ResponseBody
    @RequestMapping(value = "/startBot", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> startBot() {
        App.startBot();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Stop bot response entity.
     *
     * @return the response entity
     */
    @ResponseBody
    @RequestMapping(value = "/stopBot", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> stopBot() {
        App.shutdownBot("Shut down from web interface");
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Is bot online boolean.
     *
     * @return the boolean
     */
    @CrossOrigin
    @GetMapping("/botOnline")
    public boolean isBotOnline() {
        return App.isBotOnline;
    }
}
