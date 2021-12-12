package xyz.e3ndr.screenclick;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.UIManager;

import lombok.Getter;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import xyz.e3ndr.fastloggingframework.FastLoggingFramework;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

@Command(name = "start", mixinStandardHelpOptions = true, version = "FollowMouse", description = "Starts FollowMouse")
public class Launcher implements Runnable {
    private static @Getter Image icon;

    @Option(names = {
            "-d",
            "--debug"
    }, description = "Whether or not to enable debug logging.")
    private boolean debug = false;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        icon = new ImageIcon(Launcher.class.getResource("/follow_mouse_no_mouse.png")).getImage();

        // Parse the cmd arguments
        new CommandLine(new FollowMouse()).execute(args); // Calls #run()
    }

    @Override
    public void run() {
        if (this.debug) {
            FastLoggingFramework.setDefaultLevel(LogLevel.DEBUG);
        }

        new FollowMouse();
    }

}
