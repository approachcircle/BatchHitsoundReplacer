package net.approachcircle.app;

import org.apache.commons.lang3.SystemUtils;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils {
    public static final Scanner scanner = new Scanner(System.in);
    public static final String[] sets = {"drum", "normal", "soft"};
    public static final String[] supportedExtensions = {"wav", "mp3", "ogg"};

    public static final List<String> hitsoundNames = List.of(
            "hitnormal",
            "hitclap",
            "hitfinish",
            "hitwhistle",
            "slidertick",
            "sliderslide",
            "sliderwhistle"
    );

    public static final List<String> drumSet = new ArrayList<>();

    public static final List<String> normalSet = new ArrayList<>();

    public static final List<String> softSet = new ArrayList<>();

    public static final List<String> allSets = new ArrayList<>();

    public static File osuPath;

    public static void buildHitsoundNames() {
        for (String hitsound : hitsoundNames) {
            drumSet.add("drum-" +  hitsound);
            normalSet.add("normal-" +  hitsound);
            softSet.add("soft-" +  hitsound);
        }
        allSets.addAll(drumSet);
        allSets.addAll(normalSet);
        allSets.addAll(softSet);
    }

    public static void findOsu() {
        if (SystemUtils.IS_OS_WINDOWS) {
            osuPath = new File(String.format("%s\\..\\Local\\osu!\\", System.getenv("APPDATA")));
        } else {
            osuPath = null;
            // throw new RuntimeException("only windows is supported (my bad OG)");
        }
        if (osuPath == null || !osuPath.exists()) {
            Logger.info("idk where i'm looking to find osu!, it's not in the default path");
            Logger.info("please select your osu! folder now");
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setDialogTitle("Select your osu! directory");
            int result = chooser.showOpenDialog(null);
            if (result != JFileChooser.APPROVE_OPTION) {
                Logger.error("i need your osu! folder twin...");
                System.exit(0);
            }
            osuPath = chooser.getSelectedFile();
            if (!osuPath.exists()) {
                Logger.info("the path you provided does not exist, try again");
                findOsu();
            }
            boolean skinFolderFound = false;
            boolean executableFound = false;
            File[] filesInDirectory = osuPath.listFiles();
            if (filesInDirectory == null) {
                throw new RuntimeException("an error occurred accessing the osu! directory");
            }
            for (File file : filesInDirectory) {
                if (file.getName().equalsIgnoreCase("skins")) {
                    skinFolderFound = true;
                }
                if (file.getName().equalsIgnoreCase("osu!.exe")) {
                    executableFound = true;
                }
            }
            if (!(skinFolderFound && executableFound)) {
                Logger.info("this folder does not contain an osu! installation");
                findOsu();
            }
            Logger.info("found osu! in this directory!");
            Logger.info("osu! directory: " + osuPath.getAbsolutePath());
        }
    }
}
