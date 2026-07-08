package net.approachcircle.app;

import org.apache.commons.io.FileUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SkinModifier {
    public static File skinFolder;
    public static File skinCopyFolder;
    public static File newHitsoundFile;
    public static String[] hitsoundSets;

    public static void findSkinFolder() {
        Logger.info("select the skin that you'd like to modify (a copy will be made)");
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setDialogTitle("Select the skin you'd like to use");
        chooser.setCurrentDirectory(Utils.osuPath);
        int result = chooser.showOpenDialog(null);
        if (result != JFileChooser.APPROVE_OPTION) {
            System.exit(0);
        }
        skinFolder = chooser.getSelectedFile();
    }

    public static boolean makeSkinCopy(String name) {
        Logger.info("making a copy of the skin you chose...");
        skinCopyFolder = new File(Utils.osuPath + "\\Skins\\" + name);
        if (skinCopyFolder.exists()) {
            Logger.error("a folder with this name already exists!");
            return false;
        }
        try {
            FileUtils.copyDirectory(skinFolder, skinCopyFolder);
        } catch (IOException e) {
            e.printStackTrace(System.err);
            Logger.error("an error occurred making a copy of the skin, it may have been partially copied or not at all.");
            return false;
        }
        Logger.info("done!");
        return true;
    }

    public static void selectHitSound() {
        Logger.info("select the sound that you'd like to use as a hitsound");
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setDialogTitle("Select the sound file to use as a hitsound");
        FileNameExtensionFilter extFilter = new FileNameExtensionFilter(
                "osu! supported audio files",
                "wav",
                "mp3",
                "ogg"
        );
        chooser.setFileFilter(extFilter);
        int result = chooser.showOpenDialog(null);
        if (result != JFileChooser.APPROVE_OPTION) {
            System.exit(0);
        }
        newHitsoundFile = chooser.getSelectedFile();
    }

    public static void chooseHitsoundSets() {
        Logger.info("which hitsound sets would you like to modify?");
        Logger.info("enter them separated by spaces out of: soft, normal, drum or just 'all'");
        System.out.print(">");
        String hitsoundSetsString = Utils.scanner.nextLine();
        hitsoundSets = hitsoundSetsString.split(" ");
        hitsoundSets = Arrays.stream(hitsoundSets).filter((s) ->
                (s.equals("all") || s.equals("soft") || s.equals("drum") || s.equals("normal"))
        ).toArray(String[]::new);
        if (hitsoundSets.length == 0) {
            Logger.error("enter valid hitsound sets!");
            chooseHitsoundSets();
        }
    }

    public static void modifyHitsounds() {
        List<String> hitsoundSetsList = new ArrayList<>(Arrays.stream(hitsoundSets).toList());
        if (hitsoundSetsList.contains("all")) {
            hitsoundSetsList.add("drum");
            hitsoundSetsList.add("normal");
            hitsoundSetsList.add("soft");
            hitsoundSetsList.remove("all");
        }
        for (String set : hitsoundSetsList) {
            Logger.info(String.format("deleting %s set hitsounds...", set));
            if (!clearHitsounds(set)) {
                Logger.error("error deleting hitsound, stopping...");
                return;
            }
            Logger.info(String.format("copying %s set hitsounds...", set));
            if (!copyHitsounds(set)) {
                Logger.error("error copying hitsound, stopping...");
                return;
            }
        }
    }

    public static boolean clearHitsounds(String set) {
        for (String hitsound : Utils.hitsoundNames) {
            for (String extension : Utils.supportedExtensions) {
                String hitsoundFileName = String.format("%s-%s.%s", set, hitsound, extension);
                File hitsoundFile = new File(hitsoundFileName);
                if (hitsoundFile.exists()) {
                    if (!hitsoundFile.delete()) {
                        Logger.error(String.format("failed to delete hitsound file %s", hitsoundFileName));
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean copyHitsounds(String set) {
        for (String hitsound : Utils.hitsoundNames) {
            // yucky
            String extension = Arrays.stream(newHitsoundFile.getAbsolutePath().split("\\.")).toList().getLast();
            String fullHitsound = String.format("%s-%s.%s", set, hitsound, extension);
            try {
                FileUtils.copyFile(newHitsoundFile, new File(skinCopyFolder, fullHitsound));
            } catch (IOException e) {
                e.printStackTrace(System.err);
                Logger.info("failed to create hitsound " + fullHitsound);
                return false;
            }
        }
        return true;
    }
}
