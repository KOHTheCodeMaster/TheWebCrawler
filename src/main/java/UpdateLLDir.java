import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateLLDir {

    private String basePath;
    private String url;
    private ArrayList<File> fileList;
    private ArrayList<VideoTracker> videoTrackers;
    int fileCount;

    UpdateLLDir(String basePath, String url) {
        this.basePath = basePath;
        this.url = url;
        this.fileCount = 0;

        videoTrackers = new ArrayList<>();
        fileList = new ArrayList<>();

        verifyAttributes();

        initializeFileNames();
        initializeVideoTrackers();
        verifyCount();
    }

    private void verifyAttributes() {

        boolean success = validateBasePath();
        if (!success) {
            System.out.println("Invalid Base Path...");
            System.exit(-104);
        }
        //  url is verified by scraper.
        /*
        success = Scraper.validateLinkedInLearningUrl(this.url);
        if (!success) {
            System.out.println("Invalid URL...");
            System.exit(-105);
        }*/

    }

    private boolean validateBasePath() {
        return (new File(this.basePath).isDirectory());
    }

    private void initializeFileNames() {

        File[] files = new File(this.basePath).listFiles();

//        assert files != null;
        for (File f : files) {
            if (f.isFile()) {
                fileList.add(f);
                fileCount++;
            }
        }

    }

    private void initializeVideoTrackers() {

        Scraper scraper = new Scraper(this.url);
        boolean success = scraper.scrapLinkedInLearning();
        if (success)
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
        boolean isSizeEqual = (videoTrackers.size() == fileList.size());

        if (!isSizeEqual) {
            System.out.println("Files count of BasePath Directory doesn't matches with the playlist.");
            System.exit(-107);
        }
    }

    boolean updateFileNames() {

        for (VideoTracker v : videoTrackers) {

            for (File currentFile : fileList) {
                String currentFileName = currentFile.getName();
                String scrapedFName = v.getVideoName();

                //  Trim off all the special chars. including '-' from the currentFileName, scrapedFName.
                currentFileName = fixFName(currentFileName);
                scrapedFName = fixFName(scrapedFName);

                /*
                    Match the currentFileName with the scrapedFName & when matched then
                    rename the currentFile in following format:
                    serialNumber. scrapedFName .extension
                 */
                if (currentFileName.contains(scrapedFName)) {
                    String updatedFName = fixFileName(currentFileName, scrapedFName, v.getSerialNumber());
                    boolean renamedSuccessfully = currentFile.renameTo(new File(basePath + "/" + updatedFName));
                    if (!renamedSuccessfully) {
                        System.out.println("Renaming Failed...");
                        System.exit(-108);
                    }
                    break;
                }
            }

        }
        return true;
    }

    private String fixFileName(String currentFileName, String correctFName, int serialNumber) {
        String result;

        result = serialNumber + ". ";
        result += correctFName;
        String extension = extractExtension(currentFileName);
        result += extension;

//        System.out.println("Result : " + result);
        return result;
    }

    private String extractExtension(String currentFileName) {

        //  Pending: Handle invalid Extension check.
        return currentFileName.substring(currentFileName.lastIndexOf("."));
    }

    private String fixFName(String fName) {

        String updatedFName = "";
        String regexSpecialSymbolFilter = "[\\-/\\\\:\"?<>*|]";
        char replacementCharacter = '$';

        //  illegal chars. for file name.
        Pattern p = Pattern.compile(regexSpecialSymbolFilter);
        Matcher m = p.matcher(fName);

        //  Replace all the matched characters with replacementCharacter => '$'.
        while (m.find()) {
            fName = fName.replace(fName.charAt(m.start()), replacementCharacter);
        }
        updatedFName = removeParticularChar(fName, replacementCharacter);

        return updatedFName;
    }

    private String removeParticularChar(String scrapedFName, char replacementCharacter) {
        String temp = "";
        while (scrapedFName.contains(replacementCharacter + "")) {

            int i = scrapedFName.indexOf(replacementCharacter);

            //  Skip ith position & copy rest of the string to temp.
            temp = scrapedFName.substring(0, i);
            temp += scrapedFName.substring(i + 1);

            //  Update the original string after removal of the char.
            scrapedFName = temp;
        }
        return scrapedFName;
    }

}

/*
 *  Time Stamp: 26th March 2K19, 01:47 PM..!!
 *
 *  Latest Updates:
 *   1. Fixed Invalid Special Chars. in currentFileName as well as scrapedFName.
 *   2. Trimmed off all the occurrences of '-' char. to avoid any conflicts.
 *
 *  Change Log:
 *
 *  2nd Commit:
 *  Update LinkedInLearning Playlist Directory.
 *  1. Using Web Scraping, extract all the file names off the url.
 *  2. Gather the file list of the playlist directory.
 *  3. Compare & Update the file names (including serial number).
 *
 *  Code Developed By,
 *  ~K.O.H..!! ^__^
 */




