import java.util.ArrayList;

public class Main {

    Scraper scraper;
    ArrayList<String> fileNames;
    ArrayList<VideoTracker> videoTrackers;

    Main() {
        scraper = new Scraper();
        videoTrackers = new ArrayList<>();
        fileNames = new ArrayList<>();

        initializeFileNames();

    }

    private void initializeFileNames() {
        fileNames.add("Gradle from the bottom up");
        fileNames.add("What you should know");
        fileNames.add("The purpose of Gradle");
    }

    public static void main(String[] args) {

        String url = "https://www.linkedin.com/learning/gradle-for-java-developers";

        Main obj = new Main();
        obj.scraper.scrapLinkedInLearningPlaylist(url, obj.videoTrackers);

        System.out.println(obj.videoTrackers + "\n");

    }

}
