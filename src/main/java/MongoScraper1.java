import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.HttpHost;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MongoScraper1 {

    private String url;
    private Document document;

    public static void main(String[] args) throws UnirestException {

        MongoScraper1 obj = new MongoScraper1();
//        obj.f1();
        obj.f3();
//        obj.f2();

//        obj.major();
//        obj.googleTop10();


    }

    private void f3() {
        final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36";
        try {
            Connection.Response loginResponse = Jsoup.connect("https://www.linkedin.com/learning/login")
                    .userAgent(USER_AGENT)
                    .data("username", "kohbtc02@email.com")
                    .data("password", "hitendra123")
                    .method(Connection.Method.POST)
                    .execute();

            System.out.println(loginResponse.body());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void f2() {
        final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:67.0) Gecko/20100101 Firefox/67.0";
        Unirest.setDefaultHeader("User-Agent", USER_AGENT);
        Unirest.setProxy(new HttpHost("217.23.3.15", 11100));


        String firstName = "John";
        String lastName = "Smith";
        String city = "New York";
        String state = "New York";
        HttpResponse<String> response = null;
        try {

            response = Unirest
                    .get("https://www.peoplefinders.com/Widget/GetWidgets?resultType=multiple")
                    .queryString("fn", firstName)
                    .queryString("ln", lastName)
                    .queryString("city", city)
                    .queryString("state", state)
                    .asString();

            System.out.println(response.getBody());

        } catch (UnirestException e) {
            e.printStackTrace();
        }


    }

    private void f1() {

        final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:67.0) Gecko/20100101 Firefox/67.0";
        url = "http://httpbin.org";
        try {
//            HttpResponse<String> response = Unirest.get(url).asString();

//            String body = response.getBody();
//            System.out.println(body);
            Unirest.setDefaultHeader("User-Agent", USER_AGENT);
            Unirest.setProxy(new HttpHost("62.8.65.33", 3128));

            HttpResponse<JsonNode> getResponse = Unirest.get(url + "/get").asJson();
            JsonNode jsonResponseBody = getResponse.getBody();

            System.out.println("User-Agent: " +
                    jsonResponseBody
                            .getObject()
                            .getJSONObject("headers")
                            .getString("User-Agent"));
            System.out.println("IP-Address: " + jsonResponseBody.getObject().getString("origin"));

            System.out.println("\n----------------\n");

            HttpResponse<String> postResponse1 = Unirest
                    .post(url + "/post")
                    .field("myField", 123)
                    .asString();
            System.out.println(postResponse1.getBody());

            System.out.println("\n----------------\n");

            JSONObject jsonObject = new JSONObject().put("newField", 3.14);
            HttpResponse<String> postResponse2 = Unirest
                    .post(url + "/post")
                    .body(jsonObject)
                    .asString();
            System.out.println(postResponse2.getBody());


        } catch (UnirestException | JSONException e) {
            e.printStackTrace();
        }

    }

    private void major() {

        //  Set the url
        this.url = "https://university.mongodb.com/mercury/M001/2019_May/" +
                "chapter/Chapter_1_Introduction/lesson/594d88952fbb2cd3ae1081b7/lecture";

        Document document = initializeDocument();
        int count = 0;  //  Total Number of Videos Count.

        System.out.println(document.outerHtml());
//        scrapMongoDBUniversity();

    }

    private void scrapMongoDBUniversity() {

        String titleCssSelectorQuery = "h3.LC20lb";
        String urlCssSelectorQuery = "div.rc > div.r > a";

        Elements titles = document.select(titleCssSelectorQuery);

        for (Element e : titles)
            System.out.println(e.text());

    }

    private void googleTop10() {

        String query = "apples";
        try {
            url = "https://www.google.com/search?q=" + URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        document = initializeDocument();

//        System.out.println(document.outerHtml());
        scrapGoogleSearch();


    }

    private void scrapGoogleSearch() {

        String titleCssSelectorQuery = "h3.LC20lb";
        String urlCssSelectorQuery = "div.rc > div.r > a";

        Elements titles = document.select(titleCssSelectorQuery);

        for (Element e : titles)
            System.out.println(e.text());

        Elements urls = document.select(urlCssSelectorQuery);

        for (Element e : urls)
            System.out.println(e.attr("href"));

    }

    private Document initializeDocument() {

        Document document = null;
        try {

            System.out.println("Establishing Connection.");

            //  Establish Connection to the HTML Page of the url.
            document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:67.0) Gecko/20100101 Firefox/67.0")
                    .get();
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
        final String mongoDBVideoTitleCssSelectorQuery = "a.pl-video-title-link";
        final String linkedinLearningVideoTitleCssSelectorQuery = "div.toc__sublist__item__content__title";

        return titleCssSelectorQuery;
    }

}
