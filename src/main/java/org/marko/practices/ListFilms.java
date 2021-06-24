package org.marko.practices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;

import javax.net.ssl.HttpsURLConnection;

public class ListFilms {
    public long page;
    public String HTTPS_URL;
    private long totalPages;
    private URL url;
    private HttpsURLConnection connection;

    ListFilms(){
        page = 1;
        totalPages = 0;
        url = null;
        connection = null;
    }

    public void setTotalPages(long totalPages){
        this.totalPages = totalPages;
    }

    public long getTotalPages(){
        return totalPages;
    }

    public void makeUrlConnection(String httpURL) {
        try {
            url = new URL(httpURL);
            connection = (HttpsURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void listing(){
        int ind = 1;
        do{
            HTTPS_URL = "https://jsonmock.hackerrank.com/api/movies/search/?Title=spiderman&page="+page;
            makeUrlConnection(HTTPS_URL);
            try (InputStream inputStream = connection.getInputStream();
                 InputStreamReader isr = new InputStreamReader(inputStream);
                 BufferedReader br = new BufferedReader(isr)) {

                JSONParser parser = new JSONParser();
                Object jsonObj = parser.parse(br);

                JSONObject jsonObject = (JSONObject) jsonObj;

                setTotalPages((long) jsonObject.get("total_pages"));

                JSONArray data = (JSONArray) jsonObject.get("data");
                JSONObject title;

                Iterator<JSONObject> it = data.iterator();
                while (it.hasNext()) {
                    title = it.next();
                    System.out.println(ind +".- " + title.get("Title"));
                    ind++;
                }
                page ++;
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }while(page<=getTotalPages());
    }

    public static void main(String[] args){

        ListFilms lf = new ListFilms();
        lf.listing();

    }

}

