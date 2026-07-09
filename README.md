# Batch Hitsound Replacer
This is a utility to replace entire hitsound sets (drum, soft, normal) in osu! with one hitsound. For example, if you have a specific hitsound/sound effect that you'd like to replace all hitsounds with, you can use this utility to do so without having to manually set every hitsound within the target skin.

## What does it do?
The program will first ask for the skin you'd like to change and make a copy of it to modify. The program will then ask for a new name for this skin. Then, you provide the hitsound you'd like to use. You can then select which hitsound sets you want to change, or "all" if you want to change every hitsound. 

## Why?
Cause making skins with a custom hitsound for every object is funny

## How do i run it?
Since this is not a GUI application, you must run the .jar file in the terminal. An easy way to do this is to go to the folder containing the program, hold shift, right click, then click "Open in terminal" (windows users). Then, run this command:
```
java -jar hitsound-renamer-1.1.1-all.jar
```
Download the .jar file from [here](https://github.com/approachcircle/BatchHitsoundReplacer/releases/latest). You must install Java 21 to run the utility. Alternatively, you can compile the project from source using Gradle.

## It doesn't work
If something is broken or not working, open an issue in the "Issues" tab and describe the problem. Open-source contributions are appreciated through pull requests.
