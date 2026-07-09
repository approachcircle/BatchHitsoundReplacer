package net.approachcircle.app;

import javax.swing.*;
import java.awt.*;
import java.io.Console;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.error("setting look and feel for UI failed");
            return;
        }
        Console console = System.console();
        if (console == null && !GraphicsEnvironment.isHeadless()){
            JOptionPane.showMessageDialog(null,
                    "Don't just double click this file, run it from the terminal! Refer to the GitHub page for more info.",
                    "Silly billy",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        Utils.buildHitsoundNames();
        Utils.findOsu();
        SkinModifier.findSkinFolder();
        while (true) {
            Logger.info("enter a name for the new skin: ");
            System.out.print(">");
            String skinName = Utils.scanner.nextLine();
            if (!SkinModifier.makeSkinCopy(skinName)) {
                Logger.error("operation failed!");
                continue;
            }
            break;
        }
        SkinModifier.selectHitSound();
        SkinModifier.chooseHitsoundSets();
        SkinModifier.modifyHitsounds();
    }
}