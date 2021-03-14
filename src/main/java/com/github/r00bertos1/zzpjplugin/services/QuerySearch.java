package com.github.r00bertos1.zzpjplugin.services;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class QuerySearch {

    public static final String GOOGLE_SEARCH_URL = "https://www.google.com/search";

    public static String createSearchQuery(String queryString) throws IOException {

        String searchURL = GOOGLE_SEARCH_URL + "?q=" + queryString;
        return searchURL;
    }
    public static void search(String searchURL) throws IOException {

        Desktop d = Desktop.getDesktop();
        d.browse(URI.create(searchURL));
    }
}