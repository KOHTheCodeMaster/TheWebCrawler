import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class A1 {

    private final String baseUrl = "https://www.youtube.com/playlist?list=PLlyCyjh2pUe9wv-hU4my-Nen_SvXIzxGB";
//            private final String baseUrl = "https://www.linkedin.com/learning/learning-sql-server-2017";
//            private final String baseUrl = "https://www.linkedin.com/learning/building-a-paid-membership-site-with-django";

    //  10+ Hrs. Videos in YT Playlist.
//    private final String baseUrl = "https://www.youtube.com/playlist?list=PLs6u2t8Zq4tmyNvN_pOzTFW-N9an9nrsz";

//    private final String baseUrl = "https://www.youtube.com/results";
//    private final String baseUrl = "https://www.google.com";
//    private final String baseUrl = "https://www.tutorialspoint.com/convert-string-to-utf-8-bytes-in-java";

    private Document doc;
    private String mozillaUserAgent = "Mozilla/5.0 (Windows; U; Windows NT 6.1; rv:2.2) Gecko/20110201";

    public static void main(String[] args) {

        System.out.println("Begin.\n");

        A1 obj = new A1();
        obj.major();

        System.out.println("\nEnd.\n");

    }

    private void major() {

        //  Initialize doc
        initializeDocument();

//        System.out.println(doc.html());


//        ll1();


        yt1();

//        searchResults();
//        f2();

//        extractTimeStamp();

    }

    private void ll1() {

        Elements elements = doc.select("a.toc__item__link");
        Elements elementsN = doc.select("div.toc__sublist__item__content__title");
        Elements elementsT = doc.select("div.toc__sublist__item__content__length");

        int i = 0;
        for (Element e : elements) {
            i++;
            String str = e.attr("href");
            int x = str.indexOf("?autoplay");
            str = str.substring(0, x);
            System.out.println(str);
        }
        System.out.println("Count : " + i);
        i = 0;
/*
for (Element e : elementsN){
            i++;
            System.out.println(e.text());
        }
        System.out.println("Count : "+ i);
        i=0;
for (Element e : elementsT){
            i++;
            System.out.println(e.text());
        }
        System.out.println("Count : "+ i);
*/


    }

    private void yt1() {
        Elements elements = doc.select("a.pl-video-title-link");
//        Elements elements = doc.select("div.timestamp > span[aria-label]");

        int i = 0;
        String[] link = new String[100];
        for (Element e : elements) {
            i++;
            System.out.println(e.text());
            link[i] = "https://www.youtube.com" + e.attr("href");
            System.out.println(e.attr("href"));

        }
        System.out.println("Count : " + i);

        for (String str : link) {
//            System.out.println(str);
        }

    }

    private void initializeDocument() {
        try {
            doc = Jsoup.connect(baseUrl)
                    .userAgent(mozillaUserAgent)
//                    .ignoreContentType(true)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void extractTimeStamp() {

        String timeStampCssQuery = "div.timestamp > span[aria-label]";
        Elements elements = doc.select(timeStampCssQuery);
//        String timeStamp = extractElementsText(elements);

    }

    private void f2() throws IOException {

        doc = Jsoup.connect(baseUrl)
                .userAgent("Mozilla/5.0 (Windows; U; Windows NT 6.1; rv:2.2) Gecko/20110201")
//                .ignoreContentType(true)
                .get();

//        System.out.println(doc.html());
        parseDoc5();

    }

    /*
     <li role="menuitem" class="overflow-menu-choice addto-watch-queue-menu-choice addto-watch-queue-play-now yt-uix-button-menu-item" data-action="play-now" onclick=";return false;" data-video-ids="xNPkXGdVw7E"><span class="addto-watch-queue-menu-text">Play now</span></li>
                      </ul></button> </span> <button class="yt-uix-button yt-uix-button-size-small yt-uix-button-default yt-uix-button-empty yt-uix-button-has-icon no-icon-markup addto-button video-actions spf-nolink hide-until-delayloaded addto-watch-later-button-sign-in yt-uix-tooltip" type="button" onclick=";return false;" role="button" title="Watch later" data-video-ids="xNPkXGdVw7E" data-button-menu-id="shared-addto-watch-later-login"><span class="yt-uix-button-arrow yt-sprite"></span></button> <button class="yt-uix-button yt-uix-button-size-small yt-uix-button-default yt-uix-button-empty yt-uix-button-has-icon no-icon-markup addto-button addto-queue-button video-actions spf-nolink hide-until-delayloaded addto-tv-queue-button yt-uix-tooltip" type="button" onclick=";return false;" title="Queue" data-video-ids="xNPkXGdVw7E" data-style="tv-queue"></button> </span></td>
                  <td class="pl-video-title"> <a class="pl-video-title-link yt-uix-tile-link yt-uix-sessionlink  spf-link " dir="ltr" href="/watch?v=xNPkXGdVw7E&amp;list=PLlyCyjh2pUe9wv-hU4my-Nen_SvXIzxGB&amp;index=3&amp;t=0s" data-sessionlink="ei=cb7NXK3iA8KP3LUPoMWcyAw&amp;feature=plpp_video&amp;ved=CAgQxjQYASITCO3eormmguICFcIHtwAdoCIHySj6LA"> Intro to Android (Android Developer Fundamentals, Unit 1: Lesson 1.0) </a>
                   <div class="pl-video-owner">
                     by
                    <a href="/channel/UC8QMvQrV1bsK7WO37QpSxSg" class=" yt-uix-sessionlink      spf-link " data-sessionlink="ei=cb7NXK3iA8KP3LUPoMWcyAw&amp;feature=playlist&amp;ved=CAgQxjQYASITCO3eormmguICFcIHtwAdoCIHySj6LA">Google Developers India</a>
                   </div>

     */
    private void parseDoc5() {

//        System.out.println(doc.html());

//        Elements elements = doc.select("span[title]");
//        Elements elements = doc.select("a.pl-video-title-link");
        Elements elements = doc.select(".pl-video-title");
        int i = 0;
        for (Element e : elements) {
            i++;
            System.out.println(e.text());
//            System.out.println(e.attr("href"));
            System.out.println(e);
//            System.out.println(e.outerHtml());
        }
        System.out.println("Count : " + i);

    }

    private void searchResults() throws IOException {

        //  Download the HTML Doc.
        doc = Jsoup.connect(baseUrl)
                .data("search_query", "jsoup parsing youtube page")
                .get();

        //  Display Title
        System.out.println("Title: " + doc.title());

        //  Parse the doc according to CSS Selector Query.
        parseDoc4();
//            parseDoc(doc);


    }

    private void parseDoc4() {

//        System.out.println(doc.html());

        Elements elements = doc.select("h3 > a[title]");

        for (Element e : elements) {
            System.out.println(e.text());
//            System.out.println(e.outerHtml());
        }

    }

    private void parseDoc3(Document doc) {

        Elements elements = doc.select(".style-scope");

        for (Element e : elements) {
            System.out.println(e);
            System.out.println(e.outerHtml());
        }

    }

    private void parseDoc2(Document doc) {

        Elements elements = doc.select("div#ansModalDivVDl5YnV5dmtHTVJmTjVYK2lsUHVidz09 p");

        for (Element e : elements) {
            System.out.println(e.text());
        }

    }

    private void parseDoc(Document doc) {

        Elements elements = doc.select("#SIvCob a");
        display1(elements);

    }

    private void displayUsingCssSelectorQuery(Document doc) {
        Elements elements = doc.select("div#SIvCob");
        display1(elements);
    }

    private void displayUsingElementById(Document doc) {
        //            Element element = doc.getElementById("video-title");
    }

    private void display1(Elements elements) {

        //  Print the extracted data.
        for (Element e : elements) {
//            String str = new String(e.text().getBytes(), StandardCharsets.UTF_8);

//            System.out.println(e.text());
            System.out.println(e);

        }
    }

    private void extractElementsText(Elements elements) {

        StringBuilder sb = new StringBuilder(100);

        //  Extract the textual data of each element.
        for (Element e : elements)
            System.out.println(e.text());
    }

}
