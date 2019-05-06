import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Main obj = new Main();
        obj.major();
    }

    private void major() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Youtube / LinkedIn Learning Playlist Directory Path: ");
        String basePath = scanner.nextLine();
        System.out.println("Enter Youtube / LinkedIn Learning Playlist URL: ");
        String url = scanner.nextLine();

        sortSingleDir(basePath, url);

    }

    private void sortSingleDir(String basePath, String url) {

        PlaylistSorter obj = new PlaylistSorter(basePath, url);
        boolean isDirUpdated = obj.updateFileNames();

        if (isDirUpdated) {
            System.out.println("Updated [" + obj.fileCount + "] File Names.");
            System.out.println(basePath + " Sorted successfully.");
        } else {
            System.out.println("Renaming Failed...");
            System.exit(-207);
        }
    }

/*

    private static void complete2() {

        Helper helper = new Helper();
        helper.begin();
    }

    private static ArrayList<File> initializeDirList(String rootPath) {
        File[] tempDir = new File(rootPath).listFiles();
        ArrayList<File> rootDir = new ArrayList<>();

//        assert tempDir != null;
        for (File f : tempDir) {
            if (f.isDirectory())
                rootDir.add(f);
        }
        return rootDir;
    }
*/

}

/*
 *  Time Stamp: 6th May 2K19, 06:36 PM..!!
 *
 *  Latest Updates:
 *  1. Youtube Scraping support added for sorting Youtube Playlist Dirs.
 *  2. sortSingleDir updated for Youtube as well as LinkedIn Learning Playlist.
 *  3. Optimized entire project for better performance.
 *
 *  Change Log:
 *  1. sortSingleDir working fine.
 *  2. Pending: sortMultipleLLDirs.
 *
 */
