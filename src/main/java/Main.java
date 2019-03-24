import java.util.ArrayList;

public class Main {

    private String basePath;
    private String url;
    private Scraper scraper;
    private ArrayList<String> fileNames;
    private ArrayList<VideoTracker> videoTrackers;

    Main() {
//        scraper = new Scraper();
        videoTrackers = new ArrayList<>();
        fileNames = new ArrayList<>();

        initializeFileNames();

    }

    private static void complete() {

        String basePath = "I:\\0\\LinkedIn Learning\\Gradle For Java Developers";
        String url = "https://www.linkedin.com/learning/gradle-for-java-developers";

        UpdateLLDir obj = new UpdateLLDir(basePath, url);
        obj.displayVideoNameList();
        obj.displayFileNameList();
        obj.updateFileNames();


    }

    public static void main(String[] args) {


        complete();

/*
        obj.scraper.scrapLinkedInLearningPlaylist(url, obj.videoTrackers);

        boolean ans = checkVideoExists(obj.fileNames, obj.videoTrackers);

        System.out.println(obj.videoTrackers + "\n");*/

    }

    private static boolean checkVideoExists(ArrayList<String> fileNames, ArrayList<VideoTracker> videoTrackers) {
        return false;
    }

    private void initializeFileNames() {
        fileNames.add("Gradle from the bottom up");
        fileNames.add("What you should know");
        fileNames.add("The purpose of Gradle");
    }

}

/*
 * Time Stamp: 21st March
 *
 */




