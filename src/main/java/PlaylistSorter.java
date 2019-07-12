import java.io.File;
import java.util.ArrayList;

class PlaylistSorter {

    private String basePath;
    private String url;
    private ArrayList<File> fileList;
    private ArrayList<VideoTracker> videoTrackers;
    int fileCount;
    private String urlCategory;
    private static final String SPECIAL_CHAR_PIPE = "|";
    private static final String regexChars = "[/\\\\:\"?<>*|]";


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

    int updateFileNames() {

        int successfulRenameCount = 0;

        for (VideoTracker v : videoTrackers) {

            String scrapedFName = v.getVideoName();

            for (File currentFile : fileList) {
                String currentFileName = currentFile.getName();

                /*
                    Match the currentFileName with the scrapedFName & when matched then
                    rename the currentFile in following format:
                    serialNumber. currentFileName.extension

                    (Replace specific special chars. with '-')
                 */

                if (doesNamesMatch(scrapedFName, currentFileName)
                        || doesNamesMatch2(scrapedFName, currentFileName.substring(0, currentFileName.lastIndexOf('.')))
                ) {
                    String updatedFName = acquireCorrectFileName(v.getSerialNumber(),
                            scrapedFName, currentFileName.substring(currentFileName.lastIndexOf('.')));
                    boolean renamedSuccessfully = currentFile.renameTo(
                            new File(basePath + "/" + updatedFName));
                    if (!renamedSuccessfully) return -1;
                    else {
                        successfulRenameCount++;
                        break;
                    }
                }
            }

        }
        return successfulRenameCount;
    }

    /*
        Time Stamp: 13th July 2K19, 00:06 AM..!!

        Case: scrapedFName special chars. has been replaced by empty string char. i.e. "" in currentFName

        Return true if currentFName contains scrapedFName substring
        Matching is done in following way:
            1. Initialize i, i2, j, j2 = 0
            2. Find special chars. in scrapedFName & Store that pos. in j2    |   i2 = j2
               if there ain't any special char. in the scrapedFName
               then j2 = length of scrapedFName
               & if i2 > length of currentFName,
                    i2 = length of currentFName
            3. Match currentFName substring from i to i2 & scrapedFName substring from j to j2
               if not matched then returns false
            4. i = j2   |   j = j2 + 1  |   j2 = j2 + 1
            5. Repeat steps 2,3,4 while ( j < length of scrapedFName )
            6. Return true

        Example:
            scrapedFName = "abc:xyz"
            currentFName = "abcxyz"
            returns true
     */

    private boolean doesNamesMatch2(String scrapedFName, String currentFName) {

        String regexChars = "[/\\\\:\"?<>*|]";
        scrapedFName = scrapedFName.replaceAll(regexChars, "|");
        int i = 0, i2, j2 = 0, j = 0;
        int cLen = currentFName.length(), sLen = scrapedFName.length();

        do {
            //  Find index of '|' from j2 index, if not found then j2 = length of scrapedFName
            j2 = scrapedFName.indexOf('|', j2);
            if (j2 == -1) j2 = sLen;

            i2 = j2;
            if (i2 > cLen)
                i2 = cLen;

            /*
                Compare left side of both the strings,
                starting from index i till next index of '|' i.e. j2
                Return false if currentFName doesn't contains scrapedFName
             */
            if (!scrapedFName.substring(j, j2).equals(currentFName.substring(i, i2))
//                    || currentFName.substring(j2, j2 + 1).matches("[a-zA-z0-9]")
            ) {
//                System.out.println(scrapedFName + " != " + currentFName);
                return false;
            }

            //  Assign i to the immediate next char. of '|'
            //  Increment the j2 to next char. pos.
            i = j2;
            j2++;
            j = j2;

            //  Repeat till j2 is smaller than the length of scrapedFName
        } while (j2 < sLen);

        return true;
    }

    /*
    Time Stamp: 13th July 2K19, 00:18 AM..!!

    Case: scrapedFName special chars. has been replaced by "-", " ", or any single char. in currentFName

    Return true if currentFName contains scrapedFName substring
    For this case, length of currentFName will always be greater than length of scrapedFName
    if(sLen > cLen) return false;

    Matching is done in following way:
        1. Initialize i, j = 0
        2. Find special chars. in scrapedFName & Store that pos. in j2    |   i2 = j2
           if there ain't any special char. in the scrapedFName
           then j = length of scrapedFName
        3. Match currentFName substring from i to j & scrapedFName substring from i to j
           if not matched then return false
        4. i = j + 1   |   j = j + 1
        5. Repeat steps 2,3,4 while ( j < length of scrapedFName )
        6. Return true

    Example:
        scrapedFName = "abc:xyz"
        currentFName = "abc xyz"
        returns true
 */
    private boolean doesNamesMatch(String scrapedFName, String currentFName) {

        int cLen = currentFName.length(), sLen = scrapedFName.length();
        if (sLen > cLen) return false;

        String regexChars = "[/\\\\:\"?<>*|]";
        scrapedFName = scrapedFName.replaceAll(regexChars, "|");
        int i = 0, j = 0;


        do {
            //  Find index of '|' from j index, if not found then j = length of scrapedFName
            j = scrapedFName.indexOf('|', j);
            if (j == -1) j = sLen;

            /*
                Compare left side of both the strings,
                starting from index i till next index of '|' i.e. j
                Return false if currentFName doesn't contains scrapedFName
             */
            if (!scrapedFName.substring(i, j).equals(currentFName.substring(i, j))
//                    || currentFName.substring(j, j + 1).matches("[a-zA-z0-9]")
            ) {
//                System.out.println(scrapedFName + " != " + currentFName);
                return false;
            }

            //  Assign i to the immediate next char. of '|'
            //  Increment the j to next char. pos.
            j++;
            i = j;

            //  Repeat till j is smaller than the length of scrapedFName
        } while (j < sLen);

        return true;
    }

    private String replaceSpecialCharsWithHyphen(String fName) {

        String updatedFName = "";
//        String regexSpecialSymbolFilter = "[\\-/\\\\:\"?<>*|]";
        String regexSpecialSymbolFilter = "[/\\\\:\"?<>*|]";
        updatedFName = fName.replaceAll(regexSpecialSymbolFilter, PlaylistSorter.SPECIAL_CHAR_PIPE);
        return updatedFName;
    }

    private String acquireCorrectFileName(int serialNumber, String scrapedFName, String extension) {

//        StringBuilder result = new StringBuilder();
//        result.append(serialNumber).append(". ").append(scrapedFName).append(extension);

        String result;

        result = serialNumber + ". ";
        result += scrapedFName.replaceAll(regexChars, "-");
        result += extension;

        return result;
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




