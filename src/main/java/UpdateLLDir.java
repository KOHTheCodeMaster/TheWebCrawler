import java.io.File;
import java.util.ArrayList;

public class UpdateLLDir {

    private String basePath;
    private String url;
    private boolean readyFlag;
    private ArrayList<File> fileList;
    private ArrayList<VideoTracker> videoTrackers;

    public UpdateLLDir(String basePath, String url) {
        this.basePath = basePath;
        this.url = url;
        this.readyFlag = false;

        videoTrackers = new ArrayList<>();
        fileList = new ArrayList<>();

        verifyAttributes();

        initializeFileNames();
        initializeVideoTrackers();
        verifyCount();
        readyFlag = true;
    }

    private static boolean checkVideoExists(ArrayList<String> fileNames, ArrayList<VideoTracker> videoTrackers) {
        return false;
    }

    private boolean verifyAttributes() {

        boolean b = validateBasePath();
        if (!b) {
            System.out.println("Invalid Base Path...");
            System.exit(-104);
        }

        b = Scraper.validateLinkedInLearningUrl(this.url);
        if (!b) {
            System.out.println("Invalid URL...");
            System.exit(-105);
        }

        return true;
    }

    private boolean validateBasePath() {
        return (new File(this.basePath).isDirectory());
    }

    private void initializeFileNames() {

        File[] files = new File(this.basePath).listFiles();

//        assert files != null;
        for (File f : files) {
            if (f.isFile())
                fileList.add(f);
        }

    }

    private void initializeVideoTrackers() {

        Scraper scraper = new Scraper(this.url);
        boolean b = scraper.scrapLinkedInLearning();
        if (b)
            this.videoTrackers = scraper.getVideoList();
        else {
            System.out.println("Invalid LinkedIn Learning URL...");
            System.exit(-103);
        }

    }

    public void displayVideoNameList() {

        for (VideoTracker v : videoTrackers) {
            System.out.println(v.getVideoName());
        }

    }

    public void displayFileNameList() {

        for (File f : fileList) {
            System.out.println(f.getName());
        }

    }

    private void verifyCount() {
        boolean sizeEqual = (videoTrackers.size() != fileList.size());

        if (sizeEqual) {
            System.out.println("Files count of BasePath Directory doesn't matches with the playlist.");
            System.exit(-107);
        }
    }

    void updateFileNames() {

        for (VideoTracker v : videoTrackers) {

            for (int i = 0; i < fileList.size(); i++) {
                File currentFile = fileList.get(i);
                String currentFileName = currentFile.getName();
                String correctFName = v.getVideoName();

                if (currentFileName.contains(correctFName)) {
                    String temp = fixFileName(currentFileName, correctFName, v.getSerialNumber());
                    boolean b = currentFile.renameTo(new File(basePath + "/" + temp));
                    if (!b) {
                        System.out.println("Renaming Failed...");
                        System.exit(-108);
                    }
                    break;
                }
            }

        }

    }

    private String fixFileName(String currentFileName, String correctFName, int serialNumber) {
        String result;

        String temp = correctFName;

        result = serialNumber + ". ";
        result += correctFName;
        String extension = extractExtension(currentFileName);
        result += extension;

//        System.out.println("Result : " + result);
        return result;
    }

    private String extractExtension(String currentFileName) {

        return currentFileName.substring(currentFileName.lastIndexOf("."));

    }

}

/*
 *  Time Stamp: 24th March 2K19, 02:22 PM..!!
 *
 *  Update LinkedInLearning Playlist Directory.
 *  1. Using Web Scraping, extract all the file names off the url.
 *  2. Gather the file list of the playlist directory.
 *  3. Compare & Update the file names (including serial number).
 *
 *  Code Developed By,
 *  ~K.O.H..!! ^__^
 */




