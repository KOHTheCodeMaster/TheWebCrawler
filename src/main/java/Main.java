import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static void sortMultipleLLDirs(ArrayList<File> rootDir, ArrayList<String> urlList) {
        int totalFileCount = 0;
        for (File f : rootDir) {
            int i = 0;
            if (!f.isDirectory()) {
                System.out.println(f.getName() + " ain't a directory!\nContinuing with the next dir.");
                continue;
            }

            String basePath = f.getAbsolutePath();
            String url = urlList.get(i++);

            UpdateLLDir obj = new UpdateLLDir(basePath, url);
            boolean success = obj.updateFileNames();
            if (success) {
                System.out.println(f.getName() + " Updated Successfully. [" + obj.fileCount + " Files]");
                totalFileCount += obj.fileCount;
            }

        }

        System.out.println("\n-------------------------------");
        System.out.println("Total LinkedIn Learning Playlist Directories Updated: " + rootDir.size());
        System.out.println("Total Files Updated: " + totalFileCount);
    }

    private static void complete2() {

        Scanner scanner = new Scanner(System.in);
        ArrayList<File> dirList = new ArrayList<>();
        ArrayList<String> urlList = new ArrayList<>();

        System.out.println("Enter LinkedIn Learning Playlist Root Directory Path: ");
        String rootPath = scanner.nextLine();
        System.out.println("Enter File Path that contains LinkedIn Learning Playlist URLs: ");
        String urlFilePath = scanner.nextLine();


        dirList = initializeDirList(rootPath);
        System.out.println(dirList);
        urlList = initializeUrlList(urlFilePath);
        System.out.println(urlList);

        sortMultipleLLDirs(dirList, urlList);

    }

    private static ArrayList<String> initializeUrlList(String urlFilePath) {

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


    private static void complete() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter LinkedIn Learning Playlist Directory Path: ");
        String basePath = scanner.nextLine();
        System.out.println("Enter LinkedIn Learning Playlist URL: ");
        String url = scanner.nextLine();

        sortSingleDir(basePath, url);

    }

    private static boolean sortSingleDir(String basePath, String url) {
        UpdateLLDir obj = new UpdateLLDir(basePath, url);
        return obj.updateFileNames();
    }

    public static void main(String[] args) {
        complete2();
    }

}

/*
 *  Time Stamp: 26th March 2K19, 01:47 PM..!!
 *
 *  Latest Updates:
 *  1. sortSingleDir working fine.
 *  2. Pending: sortMultipleLLDirs.
 *
 */




