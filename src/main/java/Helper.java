import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

//  Incomplete | Not Verified | Uncertain Attempt...
public class Helper {


    private ArrayList<File> dirList;
    private ArrayList<String> urlList;
    private String baseDirPath;
    private String urlFilePath;

    Helper() {

        this.dirList = new ArrayList<>();
        this.urlList = new ArrayList<>();

    }

    public void begin() {

        //  Prompt User for input of baseDirPath & urlFilePath.
        input();

        //  Initialize the dirList with all the directories present inside baseDirPath
        //  & the urlList with all the URLs present inside the urlFilePath.
        init(baseDirPath, urlFilePath);

    }

    private void input() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter LinkedIn Learning Playlist Root Directory Path: ");
        baseDirPath = scanner.nextLine();
        System.out.println("Enter File Path that contains LinkedIn Learning Playlist URLs: ");
        urlFilePath = scanner.nextLine();
    }

    private void init(String rootPath, String urlFilePath) {

        this.initializeDirList(rootPath);
        System.out.println(this.getDirList());

        this.initializeUrlList(urlFilePath);
        System.out.println(this.getUrlList());
    }

    void initializeDirList(String rootPath) {
        File[] tempDir = new File(rootPath).listFiles();

//        assert tempDir != null;
        for (File f : tempDir)
            if (f.isDirectory())
                this.dirList.add(f);
    }

    ArrayList<String> initializeUrlList(String urlFilePath) {

        File f = new File(urlFilePath);
        ArrayList<String> urlList = new ArrayList<>();
        /*if(f.isFile()){
            try (FileReader fileReader = new FileReader(f)){

                String temp = "";
                while (fileReader.ready()) {

                int i = fileReader.read();
                if((char) i == '\n'){
                    urlList.add(temp);
                    temp = "";
                    continue;
                }
                    temp += (char) i;
                    i = fileReader.read();
                    System.out.println("t: " + temp);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
        return urlList;
    }

    private void sortMultipleLLDirs() {
        int totalFileCount = 0;
        for (File f : this.dirList) {
            int i = 0;
            if (!f.isDirectory()) {
                System.out.println(f.getName() + " ain't a directory!\nContinuing with the next dir.");
                continue;
            }

            String basePath = f.getAbsolutePath();
            String currentUrl = "";

            for (String url : urlList) {

                //  Extract Playlist Name off the url.

                //  Trim off the

                if (f.getName().contains(url))
                    currentUrl = url;

            }

            String url = urlList.get(i++);

            PlaylistSorter obj = new PlaylistSorter(basePath, url);
            int filesRenamed = obj.updateFileNames();
            if (filesRenamed != -1) {
                System.out.println(f.getName() + " Updated Successfully. [" + filesRenamed + " Files]");
                totalFileCount += obj.fileCount;
            } else {
                System.out.println("Update Operation Failed...");
                System.exit(-108);
            }

        }

        System.out.println("\n-------------------------------");
        System.out.println("Total LinkedIn Learning Playlist Directories Updated: " + this.dirList.size());
        System.out.println("Total Files Updated: " + totalFileCount);
    }


    public ArrayList<File> getDirList() {
        return dirList;
    }

    public void setDirList(ArrayList<File> dirList) {
        this.dirList = dirList;
    }

    public ArrayList<String> getUrlList() {
        return urlList;
    }

    public void setUrlList(ArrayList<String> urlList) {
        this.urlList = urlList;
    }
}
