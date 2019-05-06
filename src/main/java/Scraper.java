import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Scraper {

    private String url;
    private String urlCategory;
    private ArrayList<VideoTracker> videoList;

    public Scraper(String url) {
        this.url = url;
        this.urlCategory = Scraper.extractUrlCategory(url);
        this.videoList = new ArrayList<>();
    }

    public Scraper(String url, String urlCategory) {
        this.url = url;
        this.urlCategory = urlCategory;
        this.videoList = new ArrayList<>();
    }

    static String extractUrlCategory(String url) {

        String urlCategory;

        //  Extract urlCategory on the basis of initial substring of the url
        urlCategory = (url.startsWith("https://www.youtube.com/"))
                ? "youtube"
                : (url.startsWith("https://www.linkedin.com/") || url.startsWith("https://www.lynda.com/"))
                ? "linkedinlearning"
                : null;
        return urlCategory;
    }

    public boolean scrapPlaylist() {

        /*
            Time Stamp: 6th May 2K19, 09:46 AM..!!
              This method creates videoList of type VideoTracker locally,
              updates it accordingly by scraping off the url
              & returns that true only if videoList attribute of Scraper is successfully updated.
         */

        Document document = initializeDocument();
        int count = 0;  //  Total Number of Videos Count.

        //  Instantiating Lists for keeping track of title, time length, url & serial number of each video.
        ArrayList<String> titleList = new ArrayList<>();
        ArrayList<String> timeLengthList = new ArrayList<>();
        ArrayList<String> urlList = new ArrayList<>();
        ArrayList<Integer> serialNumberList = new ArrayList<>();

        /*
            Time Stamp: 6th May 2K19, 05:35 PM..!!
            Acquire CSS Selector Queries for extracting particular
            section off the document for each video found in the playlist.

            titleElements => List of All the Titles of videos in Playlist
            timeLengthElements => List of Time Length (e.g.: 01:23 i.e. 1 min. 23 sec.)

            Css Selector Query for url of video is same as its title. It just
            requires element.attr("href") property to extract url rather than element.text()

         */

        Elements titleElements = document.select(acquireVideoTitleCssSelectorQuery());
        Elements timeLengthElements = document.select(acquireVideoTimeLengthCssSelectorQuery());
        Elements urlElements = document.select((acquireVideoTitleCssSelectorQuery()));

        //  Extract Video Titles & Time Length of each video & add 'em to their respective Lists.
        updateListWithText(titleElements, titleList);
        updateListWithText(timeLengthElements, timeLengthList);

        //  Extract URL for each video & add 'em to urlList. Also, increment the
        //  count value by 1 & add it to serialNumberList to keep track of Sr. No. of each video.
        //  updateListWithHref method returns the value of total count.
        count = updateListWithHref(urlElements, urlList, serialNumberList);

        //  Iterate titleList, timeLengthList, urlList, serialNumberList,
        //  instantiate new videoHelper instance & add it to the videoList.
        for (int i = 0; i < count; i++) {
            this.videoList.add(new VideoTracker(titleList.get(i),
                    timeLengthList.get(i),
                    urlList.get(i),
                    serialNumberList.get(i)));
        }

        return true;
    }

    private Document initializeDocument() {

        Document document = null;
        try {

            System.out.println("Establishing Connection.");

            //  Establish Connection to the HTML Page of the url.
            document = Jsoup.connect(url).get();
            System.out.println("Connection Established Successfully.");

        } catch (IOException e) {
            System.out.println("Connection Failed.");
            System.exit(-204);
        }

        return document;

    }

    private String acquireVideoTitleCssSelectorQuery() {
        //  Return valid CSS Selector Query for extracting video title off the document.

        String titleCssSelectorQuery = null;
        final String youtubeVideoTitleCssSelectorQuery = "a.pl-video-title-link";
        final String linkedinLearningVideoTitleCssSelectorQuery = "div.toc__sublist__item__content__title";

        if (this.urlCategory.equals("youtube"))
            titleCssSelectorQuery = youtubeVideoTitleCssSelectorQuery;
        else if (this.urlCategory.equals("linkedinlearning"))
            titleCssSelectorQuery = linkedinLearningVideoTitleCssSelectorQuery;
        else {
            System.out.println("Invalid URL Category");
            System.exit(-123);
        }

        return titleCssSelectorQuery;
    }

    private String acquireVideoTimeLengthCssSelectorQuery() {
        //  Return valid CSS Selector Query for extracting video title off the document.

        String timeLengthCssSelectorQuery = null;
        final String youtubeTimeLengthCssSelectorQuery = "div.timestamp > span[aria-label]";
        final String linkedinLearningTimeLengthCssSelectorQuery = "div.toc__sublist__item__content__length";

        if (this.urlCategory.equals("youtube"))
            timeLengthCssSelectorQuery = youtubeTimeLengthCssSelectorQuery;
        else if (this.urlCategory.equals("linkedinlearning"))
            timeLengthCssSelectorQuery = linkedinLearningTimeLengthCssSelectorQuery;
        else {
            System.out.println("Invalid URL Category");
            System.exit(-123);
        }

        return timeLengthCssSelectorQuery;
    }

    private void updateListWithText(Elements elements, ArrayList<String> arrayList) {

        //  Extract Video File Names & add 'em to the videoNameList.
        //  Also, increment the count value by 1 & add it to serialNumberList to keep track of Sr. No. of each video.
        for (Element element : elements) arrayList.add(element.text());

    }

    private int updateListWithHref(Elements elements, ArrayList<String> urlList, ArrayList<Integer> serialNumberList) {
        int count = 0;
        for (Element element : elements) {
            count++;
            serialNumberList.add(count);
            urlList.add(element.attr("href"));
        }
        return count;
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

