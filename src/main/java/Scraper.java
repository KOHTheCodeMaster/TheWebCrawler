import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Scraper {

    private String url;
    private ArrayList<VideoTracker> videoList;

    public Scraper(String url) {
        this.url = url;
        this.videoList = new ArrayList<>();
    }

    static boolean validateLinkedInLearningUrl(String url) {
        return url.startsWith("https://www.linkedin.com/");

//        System.out.println("URL doesn't belongs to LinkedInLearning Playlist!");
//        System.exit(-105);
    }

    public static void main(String[] args) throws IOException {

//        majorScrap("a");
        String url = "https://www.linkedin.com/learning/gradle-for-java-developers";
        new Scraper(url).majorScrap();

    }

    boolean scrapLinkedInLearning() {

//        String url = "https://www.linkedin.com/learning/gradle-for-java-developers";
        boolean success = validateLinkedInLearningUrl(this.url);
        if (!success)
            return false;

//        this.videoList = scrapLinkedInLearningPlaylist(url);
        return scrapLinkedInLearningPlaylist(url, this.videoList);
    }

    private boolean scrapLinkedInLearningPlaylist(String url, ArrayList<VideoTracker> videoList) {
        /*
            Time Stamp: 21st March 2K19, 12:04 PM..!!

              scrapLinkedInLearningPlaylist takes in 2 parameters:
              1. String url => URL of LinkedIn Learning Playlist.
              2. ArrayList<VideoTracker> videoList =>
                    List of VideoTracker to store following attributes of each video file:
                    a. Sr. No.
                    b. Video Name
                    c. Time Length

              This overloaded method takes empty videoList of type VideoTracker
              & updates it accordingly by scraping off the url.
         */

//        String url = "https://www.linkedin.com/learning/gradle-for-java-developers";
        Document document;

        try {

            System.out.println("Establishing Connection.");

            //  Establish Connection to the HTML Page of the url.
            document = Jsoup.connect(url).get();

            System.out.println("Connection Established Successfully.");

        } catch (IOException e) {
            System.out.println("Connection Failed.");
            e.printStackTrace();
            return false;
        }

        int count = 0;  //  Total Number of Videos Count.

        //  CSS Selector Queries for extracting particular segment off the document.
        final String htmlTagVideoName = "div.toc__sublist__item__content__title";
        final String htmlTagTimeLength = "div.toc__sublist__item__content__length";

        //  Instantiating Lists for keeping track of name, time length & serial number of each video.
        ArrayList<String> videoNameList = new ArrayList<>();
        ArrayList<String> timeLengthList = new ArrayList<>();
        ArrayList<Integer> serialNumberList = new ArrayList<>();

        //  htmlVideoNames => List of All Video Names of LinkedIn Learning Playlist of particular url.
        Elements htmlVideoNames = document.select(htmlTagVideoName);

        //  htmlTimeLength => List of Time Length (e.g.: 1m. 23 sec.) of each Video of
        //                    LinkedIn Learning Playlist of particular url.
        Elements htmlTimeLength = document.select(htmlTagTimeLength);

        /*
              Extract Video File Names & add 'em to the videoNameList.
              Also, increment the count value by 1 & add it to serialNumberList to keep track of Sr. No. of each video.
         */
        for (Element element : htmlVideoNames) {
            count++;
            videoNameList.add(element.text());
            serialNumberList.add(count);
        }

        //  Extract Time Length for each video & add 'em to the timeLengthList.
        for (Element element : htmlTimeLength) {
            timeLengthList.add(element.text());
        }

        /*
              Iterate videoNameList, timeLengthList, serialNumberList & add each one of 'em
              to new videoHelper instance & add it to the videoList.
         */
        for (int i = 0; i < count; i++) {
            videoList.add(new VideoTracker(videoNameList.get(i), timeLengthList.get(i), serialNumberList.get(i)));
        }
        return true;
    }

    private ArrayList<VideoTracker> scrapLinkedInLearningPlaylist(String url) {
        /*
            Time Stamp: 21st March 2K19, 12:04 PM..!!

              scrapLinkedInLearningPlaylist takes in only 1 parameter:
              1. String url => URL of LinkedIn Learning Playlist.

              This overloaded method creates videoList of type VideoTracker locally,
              updates it accordingly by scraping off the url
              & returns that videoList.
         */

//        String url = "https://www.linkedin.com/learning/gradle-for-java-developers";
        Document document;

        try {

            System.out.println("Establishing Connection.");

            //  Establish Connection to the HTML Page of the url.
            document = Jsoup.connect(url).get();

            System.out.println("Connection Established Successfully.");

        } catch (IOException e) {
            System.out.println("Connection Failed.");
            e.printStackTrace();
            return null;
        }

        int count = 0;  //  Total Number of Videos Count.

        //  CSS Selector Queries for extracting particular segment off the document.
        final String htmlTagVideoName = "div.toc__sublist__item__content__title";
        final String htmlTagTimeLength = "div.toc__sublist__item__content__length";

        videoList = new ArrayList<>();

        //  Instantiating Lists for keeping track of name, time length & serial number of each video.
        ArrayList<String> videoNameList = new ArrayList<>();
        ArrayList<String> timeLengthList = new ArrayList<>();
        ArrayList<Integer> serialNumberList = new ArrayList<>();

        //  htmlVideoNames => List of All Video Names of LinkedIn Learning Playlist of particular url.
        Elements htmlVideoNames = document.select(htmlTagVideoName);

        //  htmlTimeLength => List of Time Length (e.g.: 1m. 23 sec.) of each Video of
        //                    LinkedIn Learning Playlist of particular url.
        Elements htmlTimeLength = document.select(htmlTagTimeLength);

        //  Extract Video File Names & add 'em to the videoNameList.
        //  Also, increment the count value by 1 & add it to serialNumberList to keep track of Sr. No. of each video.
        for (Element element : htmlVideoNames) {
            count++;
            videoNameList.add(element.text());
            serialNumberList.add(count);
        }

        //  Extract Time Length for each video & add 'em to the timeLengthList.
        for (Element element : htmlTimeLength) {
            timeLengthList.add(element.text());
        }

        //  Iterate videoNameList, timeLengthList, serialNumberList & add each one of 'em
        //  to new videoHelper instance & add it to the videoList.
        for (int i = 0; i < count; i++) {
            videoList.add(new VideoTracker(videoNameList.get(i), timeLengthList.get(i), serialNumberList.get(i)));
        }
        return videoList;
    }

    void majorScrap() {

//        String url = "https://www.linkedin.com/learning/gradle-for-java-developers";
//        String url = "https://www.youtube.com/playlist?list=PLoJSah60cTP5Hulr9HxLyv-JVXFZycdmw";

        boolean b = this.scrapLinkedInLearning();
        if (!b)
            System.out.println("Invalid URL...");
//        else
//            System.out.println(this.videoList);

    }

    public ArrayList<VideoTracker> getVideoList() {
        return videoList;
    }
}

/*
 * The Web Crawler..!!
 *
 * Time Stamp: 21st March 2K19, 12:41 PM..!!
 *
 * Latest Updates:
 *  1. Classes Added:
 *      Scraper, VideoTracker
 *  2. Provided URL of particular LinkedIn Learning Playlist is crawled over web & extracted out
 *     the Video Title, Time Length along with Serial Number with the help of VideoTracker class.
 *
 *
 * Code Developed By,
 * ~K.O.H..!! ^__^
 *
 */


    /*

        static void scrapGoogle(String url){

            //  Incomplete & Invalid
            Document document = null;
            String url2 = "https://www.google.com/search?q=";

    //        String userAgent2 = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36";
            try {
                document = Jsoup.connect(url2 + URLEncoder.encode("apple", "UTF-8"))
                        .get();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            Elements elements = document.select("span.st");
            for (Element element : elements)
                System.out.println(document.text());
        }
    */

    /*
    //  Extract Youtube Video ID i.e.
    //          url = "https://www.youtube.com/watch?v=X5XC5b75tus"
    //          ytdVideoID = X5XC5b75tus
    public static String extractVideoId(String youtubeUrl) {
        String videoId = null;

        try {
            Document videoPage = Jsoup.connect(youtubeUrl).get();

            Element videoIdMeta = videoPage.select("div[itemtype=http://schema.org/VideoObject] meta[itemprop=videoId]").first();
            if (videoIdMeta == null) {
                throw new IOException("Unable to find videoId in HTML content.");
            }

            videoId = videoIdMeta.attr("content");
        } catch (Exception e) {
            e.printStackTrace(); // alternatively you may log this exception...
        }
        System.out.println(videoId);
        return videoId;
    }

    public static String extractVideoId2(String ytUrl) {
        String vId = null;
        Pattern pattern = Pattern.compile(".*(?:youtu.be\\/|v\\/|u\\/\\w\\/|embed\\/|watch\\?v=)([^#\\&\\?]*).*");
        Matcher matcher = pattern.matcher(ytUrl);
        if (matcher.matches()){
            vId = matcher.group(1);
        }
        System.out.println(vId);
        return vId;
    }
    */

