package net.approachcircle.app;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Utils.buildHitsoundNames();
        Utils.findOsu();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.error("setting look and feel for UI failed");
            return;
        }
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