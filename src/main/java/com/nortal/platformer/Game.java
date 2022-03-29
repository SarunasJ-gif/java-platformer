package com.nortal.platformer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

    public static void main(String[] args) throws IOException, URISyntaxException {
        Game game = new Game("platforms.csv");
        game.run();
    }

    private Integer points = 500;
    private Platform activePlatform;
    private final File gameFile;
    List<Platform> platforms;

    public Game(String gameFile) throws URISyntaxException {
        this.gameFile = new File(getClass().getClassLoader().getResource(gameFile).toURI());
    }

//    public void run() throws IOException {
//        platforms = readPlatforms();
//        activePlatform = platforms.get(0);
//        Platform nextPlatform = findNextPlatform(activePlatform);
//        boolean lastPlatform = false;
//        int i = 0;
//        int count = 0;
//        int previous = 0;
//        while (!lastPlatform) {
//            activePlatform = platforms.get(i);
//            nextPlatform = findNextPlatform(activePlatform);
//            int j = i;
//            if (points - nextPlatform.getCost() >= 0) {
//                i++;
//                if (previous >= i) {
//                    points += nextPlatform.getCost();
//                } else {
//                    points -= nextPlatform.getCost();
//                }
//            } else {
//                i--;
//                points += platforms.get(i).getCost();
//            }
//            count++;
//            previous = j;
//            if ((nextPlatform.getIndex()) == (platforms.size() - 1)) {
//                lastPlatform = true;
//            }
//        }
//        System.out.println("Congratulations! It took " + count + " jumps to reach the last platform");
//    }


    public void run() throws IOException {
        Scanner scanner = new Scanner(System.in);
        platforms = readPlatforms();
        activePlatform = platforms.get(0);
        Platform nextPlatform = findNextPlatform(activePlatform);
        boolean lastPLatform = false;
        int i = 0;
        int previous = 0;
        int count = 0;
        System.out.println("If you want go foward, press f");
        System.out.println("If you want go back, press b");
        while (!lastPLatform) {
            activePlatform = platforms.get(i);
            nextPlatform = findNextPlatform(activePlatform);
            String move = scanner.nextLine().toLowerCase();
            int j = i;
            switch (move) {
                case "f":
                    if (points - nextPlatform.getCost() >= 0) {
                        jumpTo(nextPlatform);
                        i++;
                        count++;
                        if (previous >= i) {
                            points += nextPlatform.getCost();
                        } else {
                            points -= nextPlatform.getCost();
                        }
                        System.out.println("You are in " + platforms.get(i).getIndex() + " platform");
                        System.out.println("You have " + points + " points");
                      } else {
                        System.out.println("You have not enough points go to next platform. Go to previous platform");
                    }
                    break;
                case "b":
                    i--;
                    points += platforms.get(i).getCost();
                    System.out.println("You are in " + platforms.get(i).getIndex() + " platform");
                    System.out.println("You have " + points + " points");
                    count++;
            }
            previous = j;
            if (nextPlatform.getIndex() == platforms.size() - 1) {
                lastPLatform = true;
                System.out.println("Congratulations! It took " + count + " jump to reach last platform");
            }
        }
    }



    private Platform findNextPlatform(Platform activePlatform) throws IOException {
        if ((activePlatform.getIndex()) != (platforms.size()-1)) {
            return platforms.get(activePlatform.getIndex() + 1);
        }
        return activePlatform;
    }
    /**
     * Reads platforms from csv file and returns the as list.
     *
     * @return platforms - Platforms as list
     */
    private List<Platform> readPlatforms() throws IOException {
        List<Platform> list = new ArrayList<>();
        BufferedReader reader = null;
        String line = "";
        reader = new BufferedReader((new FileReader(gameFile)));
        int lineCount = 0;
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            if (lineCount != 0) {
                list.add(new Platform(Integer.parseInt(values[0]), Integer.parseInt(values[1].strip())));
            }
            lineCount++;
        }
        return list;
    }
    /**
     * Invoke this function to jump to next platform.
     *
     * @param platform - Platform that you are going to jump to.
     */
   public void jumpTo(Platform platform) {
      activePlatform = platform;
    }

}