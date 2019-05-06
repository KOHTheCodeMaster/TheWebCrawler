import java.io.File;
import java.util.ArrayList;

public class PlaylistSorter {

    private String basePath;
    private String url;
    private ArrayList<File> fileList;
    private ArrayList<VideoTracker> videoTrackers;
    int fileCount;
    private String urlCategory;

    PlaylistSorter(String basePath, String url) {
        this.basePath = basePath;
        this.url = url;
        this.fileCount = 0;

        videoTrackers = new ArrayList<>();
        fileList = new ArrayList<>();

        validateBasePath();
        initializeUrlCategory();

        initializeFileNames();
        initializeVideoTrackers();
        verifyCount();
    }

    private void validateBasePath() {

        //  Validate basePath
        File file = new File(this.basePath);
        boolean isValidBasePath = (file.isDirectory() && file.exists());

        if (!isValidBasePath) {
            System.out.println("Invalid Base Path...");
            System.exit(-201);
        }

    }

    private void initializeUrlCategory() {

        //  Initialize urlCategory on the basis of initial substring of the url
        this.urlCategory = Scraper.extractUrlCategory(this.url);

        if (this.urlCategory == null) {
            System.out.println("Not a Valid Youtube/LinkedInLearning URL");
            System.exit(-202);
        }
    }

    private void initializeFileNames() {

        File[] files = new File(this.basePath).listFiles();

        if (files == null) {
            System.out.println("No Files Found in source dir.");
            System.exit(-203);
        }
//        assert files != null;
        for (File f : files) {
            if (f.isFile()) {
                fileList.add(f);
                fileCount++;
            }
        }

    }

    private void initializeVideoTrackers() {

        Scraper scraper = new Scraper(this.url, this.urlCategory);

        boolean scrappingSuccessful = scraper.scrapPlaylist();
        if (scrappingSuccessful) this.videoTrackers = scraper.getVideoList();
        else {
            System.out.println("Invalid LinkedIn Learning URL...");
            System.exit(-205);
        }

    }

    private void verifyCount() {
        boolean isSizeEqual = (videoTrackers.size() == fileList.size());

        if (!isSizeEqual) {
            System.out.println("Video Trackers : " + videoTrackers.size());
            System.out.println("FileList : " + fileList.size());
            System.out.println("Files count of BasePath Directory doesn't matches with the playlist.");
            System.exit(-206);
        }
    }

    boolean updateFileNames() {

        for (VideoTracker v : videoTrackers) {

            String scrapedFName = v.getVideoName();
            scrapedFName = replaceSpecialCharsWithHyphen(scrapedFName);

            for (File currentFile : fileList) {
                String currentFileName = currentFile.getName();

                //  Replace specific special chars. with '-'
                currentFileName = replaceSpecialCharsWithHyphen(currentFileName);

                /*
                    Match the currentFileName with the scrapedFName & when matched then
                    rename the currentFile in following format:
                    serialNumber. scrapedFName .extension
                 */
                if (currentFileName.contains(scrapedFName)) {
                    String updatedFName = acquireCorrectFileName(v.getSerialNumber(),
                            scrapedFName, currentFileName);
                    boolean renamedSuccessfully = currentFile.renameTo(
                            new File(basePath + "/" + updatedFName));
                    if (!renamedSuccessfully) {
//                        System.out.println("Renaming Failed...");
//                        System.exit(-207);
                        return false;
                    }
                    break;
                }
            }

        }
        return true;
    }

    private String replaceSpecialCharsWithHyphen(String fName) {

        String updatedFName = "";
//        String regexSpecialSymbolFilter = "[\\-/\\\\:\"?<>*|]";
        String regexSpecialSymbolFilter = "[/\\\\:\"?<>*|]";
        updatedFName = fName.replaceAll(regexSpecialSymbolFilter, "");
        return updatedFName;
    }

    private String acquireCorrectFileName(int serialNumber, String scrapedFName, String currentFileName) {
        String result;

        result = serialNumber + ". ";
        result += scrapedFName;
        String extension = extractExtension(currentFileName);
        result += extension;

//        System.out.println("Result : " + result);
        return result;
    }

    private String extractExtension(String currentFileName) {

        //  Pending: Handle invalid Extension check.
        return currentFileName.substring(currentFileName.lastIndexOf("."));
    }

/*
    //    deprecated & slow approach for fixing file names,
    //    alternative & faster approach has already been implemented above
    private String fixFName2(String fName) {

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

*/

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




