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
        int filesRenamed = obj.updateFileNames();

        if (filesRenamed != -1) {
            System.out.println("Updated [" + filesRenamed + "] File Names.");
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
 *  Change Log:
 *
 *  6th Commit:
 *  1. Bug Squashed : Handled Special Chars. in scrapedFName that were missing in currentFName
 *
 *  5th Commit:
 *  1. Test Commit - Unverified
 *
 *  4th Commit:
 *  1. Youtube Scraping support added for sorting Youtube Playlist Dirs.
 *  2. sortSingleDir updated for Youtube as well as LinkedIn Learning Playlist.
 *  3. Optimized entire project for better performance.
 *
 *  3rd Commit:
 *  1. Fixed Invalid Special Chars. in currentFileName as well as scrapedFName.
 *  2. Trimmed off all the occurrences of '-' char. (in both currentFileName & scrapedFName) to avoid any conflicts (Challenge & Solution).
 *
 *  2nd Commit:
 *  Update LinkedInLearning Playlist Directory.
 *  1. Using Web Scraping, extract all the file names off the url.
 *  2. Gather the file list of the playlist directory.
 *  3. Compare & Update the file names (including serial number).
 *
 *  Pending:
 *  1. Using Regex for optimized matching of the file names.
 *  2. Migrate it to the GUI Project.
 *
 *  Init Commit:
 *  1. Classes Added:
 *      Scraper, VideoTracker
 *  2. Provided URL of particular LinkedIn Learning Playlist is crawled over web & extracted out
 *     the Video Title, Time Length along with Serial Number with the help of VideoTracker class.
 *
 *  Code Developed By,
 *  ~K.O.H..!! ^__^
 */
