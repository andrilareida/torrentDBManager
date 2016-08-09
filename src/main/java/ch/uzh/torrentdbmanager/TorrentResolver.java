/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.uzh.torrentdbmanager;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sun.net.www.http.HttpClient;

/**
 *
 * @author lareida
 */
public class TorrentResolver {

    
    private final String userAgent = "Mozilla/5.0";
    
    //example "2016-07-14T18:17:04+00:00"
    private DateFormat timeAddedFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
    
    private String[] mirrors = {"dxtorrent.com"};
    private int activeMirror = 0;
    public TorrentResolver() throws ClassNotFoundException {
       
    }

    public TorrentDAO resolveTorrent(String infoHash) throws IOException {
//        String url = "https://kickass.ag/usearch/" + infoHash + "/";
        String url = "https://" + mirrors[activeMirror] + "/usearch/" + infoHash + "/";
        
        System.out.println("Get to URL: " + url);
        
//        Document doc = Jsoup.parse(new URL(url).openStream(), "UTF-8", "https://kat.cr");
        Document doc = Jsoup.connect(url)
                .userAgent(userAgent)
                .ignoreHttpErrors(true)
                .followRedirects(true)
                .timeout(30000)
                .get();
        TorrentDAO torrent = new TorrentDAO();
        torrent.setInfoHash(infoHash);
        
        Element errorPage = doc.select("div.errorpage").first();
        if(errorPage != null){
            torrent.setComment(errorPage.text());
            System.out.println("Torrent was deleted");
            return torrent;
        }
        
        
        //arvels Daredevil S02 Season 2 Complete 720p WEBRip x265 AAC E-Subs [GWC] (Size: 2.34 GB)
        String regexp = "^(.*)\\s\\(Size: (\\d+\\.\\d*)\\s([A-Z]{1,2}).*\\)$";
        Elements folderOpen = doc.select("span.folderopen");
        
        Matcher matched = Pattern.compile(regexp).matcher(folderOpen.first().text());

        if (matched.find()) {
            torrent.setTitle(matched.group(1));
            torrent.setSize(Double.parseDouble(matched.group(2)));
            torrent.setUnit(matched.group(3));
        }

        Element comment = doc.select("div[id='summary']").first();
        torrent.setComment((comment != null) ? comment.text() : "N/A");
        Element magnet = doc.select("a[title='Torrent magnet link']").first();
        torrent.setMagnetUri((magnet!=null) ? magnet.attr("href") : "N/A");
        Element link = doc.select("a[title='Download torrent file']").first();
        torrent.setLink((link!=null) ? link.attr("href") : "N/A");
        
        try {
            Element datepublished = doc.getElementsByClass("font11px lightgrey line160perc").get(0).getElementsByTag("time").get(0);
            Date published = timeAddedFormat.parse(datepublished.attributes().get("datetime"));
            torrent.setPublishDate(published.getTime());
        } catch (Exception ex) {
            System.err.println("Failed to parse date: "+infoHash);
            ex.printStackTrace();
        }
        torrent.setDateAdded(System.currentTimeMillis());

        //Iterate over list entries of: <div class="dataList">
        for (Element e : doc.select("div.dataList").select("li")) {
            Element a;
            switch (e.select("strong").first().text()) {
                
                case "IMDb link:":
                    a = e.select("a").first();
                    torrent.setiMDBLlink(a.attr("href"));
                    torrent.setiMDBiD(e.text().split(": ")[1]);
                    break;
                case "TVmaze link:":
                    a = e.select("a").first();
                    torrent.setTVmazeLink(a.attr("href"));
                    break;
                case "IMDb rating:":
                    Matcher m = Pattern.compile("^.*(\\d{1,2}\\.\\d{1,2})\\s\\(([0-9,]+)\\svotes\\)$").matcher(e.text());
                    if (m.find()) {
                        torrent.setiMDBrating(Double.parseDouble(m.group(1)));
                        torrent.setiMDBvotes(Integer.parseInt(m.group(2).replace(",", "")));
                    }else {
                        System.out.println("No match!");
                    }
                    break;
                case "Detected quality:":
                    torrent.setQuality(e.select("span").first().text());
                    break;
                case "Language:":
                    StringBuilder langs = new StringBuilder();
                    e.select("span").stream().forEach((span) -> {
                        langs.append(span.text()).append(",");
                    });
                    torrent.setLanguage(langs.toString());
                    break;
                case "Subtitles:":
                    StringBuilder subs = new StringBuilder();
                    e.select("span").stream().forEach((span) -> {
                        subs.append(span.text()).append(",");
                    });
                    torrent.setSubtitles(subs.toString());
                    break;
                case "Genres:":
                    StringBuilder genr = new StringBuilder();
                    e.select("a").stream().forEach((span) -> {
                        genr.append(span.text()).append(",");
                    });
                    torrent.setGenres(genr.toString());
                    break;
            }
        }

        return torrent;
    }
    
    public int nextMirror(){
        activeMirror = (activeMirror+1)%mirrors.length;
        return activeMirror;
    }

}
