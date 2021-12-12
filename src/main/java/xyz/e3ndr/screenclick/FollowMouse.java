package xyz.e3ndr.screenclick;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonObject;
import lombok.Getter;
import lombok.SneakyThrows;
import xyz.e3ndr.fastloggingframework.logging.FastLogger;

public class FollowMouse {
    private static final long POLL_INTERVAL = 175;

    private static @Getter FastLogger logger = new FastLogger();
    private static @Getter FollowMouse instance;

    private Map<Integer, MacroAction> keybinds = new HashMap<>();

    private GraphicsDevice[] screens;
    private Robot robot;

    private @Getter int currentScreenId = -1;
    private @Getter boolean shouldPress = true;

    @SneakyThrows
    public FollowMouse() {
        instance = this;

        // Init.
        this.screens = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        this.robot = new Robot();

        // Take the config and convert it to something useful.
        JsonObject config = Rson.DEFAULT.fromJson(new String(Files.readAllBytes(new File("config.json").toPath())), JsonObject.class);
        for (Map.Entry<String, JsonElement> entry : config.entrySet()) {
            Integer key = Integer.valueOf(entry.getKey());
            JsonElement value = entry.getValue();

            this.keybinds.put(key, MacroAction.from(value));
        }

        TrayHandler.tryCreateTray();

        logger.info("Ready!");

        // Main handler.
        while (true) {
            Thread.sleep(POLL_INTERVAL);
            this.pollMouse();
        }
    }

    public void setShouldPress(boolean state) {
        this.shouldPress = state;
        TrayHandler.updateEnabledCheckbox(this.shouldPress);
    }

    public @Nullable MacroAction getCurrentScreenMacro() {
        return this.keybinds.get(this.currentScreenId);
    }

    public GraphicsDevice getCurrentScreen() {
        return this.screens[this.currentScreenId];
    }

    @SneakyThrows
    public void pollMouse() {
        Point mouseLoc = MouseInfo.getPointerInfo().getLocation();
        boolean mouseMovedToDifferentScreen = false;

        // Loop through all the screens until we figure out where the mouse is.
        // Dirty? yes.
        // Works? absolutely!
        int id = 0;
        for (GraphicsDevice screen : this.screens) {
            if (screen.getDefaultConfiguration().getBounds().contains(mouseLoc)) {
                mouseMovedToDifferentScreen = this.currentScreenId != id; // If the ids mismatch
                this.currentScreenId = id;
                break;
            }

            id++;
        }

        if (mouseMovedToDifferentScreen) {
            logger.debug("Current screen: %s", this.getCurrentScreen().getIDstring());
            TrayHandler.updateCurrentScreen(this.currentScreenId);

            if (this.shouldPress) {
                MacroAction action = this.getCurrentScreenMacro();

                if (action != null) {
                    action.execute(this.robot);
                }
            }
        }
    }

}
