package xyz.e3ndr.screenclick;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import co.casterlabs.rakurai.json.validation.JsonValidationException;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import xyz.e3ndr.reflectionlib.ReflectionLib;

@NoArgsConstructor
@JsonClass(exposeAll = true)
public class MacroAction {
    private String key;
    private boolean shift = false;
    private boolean alt = false;
    private boolean control = false;

    public MacroAction(String key) {
        this.key = key;
    }

    public void execute(@NonNull Robot robot) throws InterruptedException {
        int keyCode = getKeyCodeForString(this.key);

        if (keyCode != -1) {
            if (this.shift) {
                robot.keyPress(KeyEvent.VK_SHIFT);
            }

            if (this.alt) {
                robot.keyPress(KeyEvent.VK_ALT);
            }

            if (this.control) {
                robot.keyPress(KeyEvent.VK_CONTROL);
            }

            robot.keyPress(keyCode);
            Thread.sleep(50);
            robot.keyRelease(keyCode);

            if (this.shift) {
                robot.keyRelease(KeyEvent.VK_SHIFT);
            }

            if (this.alt) {
                robot.keyRelease(KeyEvent.VK_ALT);
            }

            if (this.control) {
                robot.keyRelease(KeyEvent.VK_CONTROL);
            }
        }
    }

    public static MacroAction from(JsonElement e) throws JsonValidationException, JsonParseException {
        if (e.isJsonString()) {
            return new MacroAction(e.getAsString());
        } else if (e.isJsonObject()) {
            return Rson.DEFAULT.fromJson(e, MacroAction.class);
        } else {
            throw new IllegalArgumentException(e + " is not a valid config entry.");
        }
    }

    private static int getKeyCodeForString(String key) {
        if (key != null) {
            key = "VK_" + key.toUpperCase();

            try {
                return ReflectionLib.getStaticValue(KeyEvent.class, key);
            } catch (Exception ignored) {}
        }

        return -1;
    }

}
