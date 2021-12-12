package xyz.e3ndr.screenclick;

import java.awt.AWTException;
import java.awt.CheckboxMenuItem;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.image.BufferedImage;

public class TrayHandler {
    private static CheckboxMenuItem enabledCheckbox;
    private static MenuItem currentScreenText;
    private static SystemTray tray;

    private static TrayIcon icon;

    private static boolean canCreateCustomIcon = true;

    public static void tryCreateTray() {
        if (tray == null) {
            // Check the SystemTray support
            if (!SystemTray.isSupported()) {
                throw new IllegalStateException("Cannot add TrayIcon.");
            }

            tray = SystemTray.getSystemTray();
            PopupMenu popup = new PopupMenu();

            // Create the popup menu components
            currentScreenText = new MenuItem("Current Screen: 0");
            enabledCheckbox = new CheckboxMenuItem("Enabled");
            MenuItem itemExit = new MenuItem("Exit");

//            currentScreenText.setEnabled(false);

            enabledCheckbox.setState(
                FollowMouse.getInstance().isShouldPress()
            );

            // Add components to popup menu
            popup.add(currentScreenText);
            popup.addSeparator();
            popup.add(enabledCheckbox);
            popup.add(itemExit);

            enabledCheckbox.addItemListener((ItemEvent e) -> {
                FollowMouse.getInstance().setShouldPress(
                    !FollowMouse.getInstance().isShouldPress()
                );
            });

            itemExit.addActionListener((ActionEvent e) -> {
                destroy();
                System.exit(0);
            });

            // Setup the tray icon.
            icon = new TrayIcon(Launcher.getIcon());

            icon.setToolTip("FollowMouse");
            icon.setImageAutoSize(true);
            icon.setPopupMenu(popup);

            try {
                tray.add(icon);
            } catch (AWTException e) {}

            // Remove the icon on shutdown.
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    try {
                        tray.remove(icon);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            throw new IllegalStateException("Tray handler is already initialized.");
        }
    }

    public static void updateEnabledCheckbox(boolean newState) {
        if (enabledCheckbox != null) {
            enabledCheckbox.setState(newState);
        }
    }

    public static void destroy() {
        if (tray != null) {
            tray.remove(icon);
        }
    }

    public static void updateCurrentScreen(int currentScreenId) {
        currentScreenText.setLabel("Current Screen: " + currentScreenId);

        if (canCreateCustomIcon) {
            try {
                String text = String.valueOf(currentScreenId);

                // We set the size and scale it up to get clearer text.
                // Also helps on MacOS.
                final int scale = 2;
                final int size = 256 * scale;
                int fontSize = (int) (size * .525);
                Rectangle iconScreenArea = new Rectangle(10 * scale, 50 * scale, 232 * scale, 130 * scale);

                BufferedImage newIcon = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
                Graphics g = newIcon.createGraphics();

                // Update the text specs.
                g.setColor(Color.WHITE);
                g.setFont(new Font("default", Font.BOLD, fontSize));

                // Add the icon
                g.drawImage(Launcher.getIcon(), 0, 0, size, size, null);

                // Draw the text
                drawCenteredString(g, text, iconScreenArea);
//                g.fillRect(textArea.x, textArea.y, textArea.width, textArea.height);

                // Cleanup and set.
                g.dispose();
                icon.setImage(newIcon);
            } catch (Exception e) {
                canCreateCustomIcon = false;
                FollowMouse.getLogger().severe("Could not create custom tray icon with the screen number on it, won't try again.\n%s", e);
            }
        }
    }

    /**
     * Draw a String centered in the middle of a Rectangle.
     *
     * @param  g    The Graphics instance.
     * @param  text The String to draw.
     * @param  rect The Rectangle to center the text in.
     * 
     * @author      https://stackoverflow.com/a/27740330
     */
    public static void drawCenteredString(Graphics g, String text, Rectangle rect) {
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();

        g.drawString(text, x, y);
    }

}
