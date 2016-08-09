/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.uzh.torrentdbmanager;

import java.util.Date;

/**
 *
 * @author lareida
 */
public class TorrentDAO {
    
    //db.storeTorrent(infoHash, title, size, comment, publishDate, magnetUri, link, dateAdded);
    private String infoHash = "";
    private String title = "";
    private String comment = "";
    private double size = 0.0;
    private String unit;
    private long publishDate = 0L;
    private long dateAdded = 0L;
    private String magnetUri = "";
    private String link = "";
    
    private String iMDBLlink = "";
    private String iMDBiD = "";
    private double iMDBrating = 0.0;
    private int iMDBvotes = 0;
    private String TVmazeLink = "";
    
    private String language = "";
    private String quality = "";
    private String subtitles = "";
    private String genres = "";
    
    
    
    public String prettyPrint(){
        return new StringBuilder().
                append("Info Hash: ").append(infoHash).
                append("\n Title: ").append(title).
                append("\n Comment: ").append(comment).
                append("\n Size: ").append(size).append(" ").append(unit).
                append("\n Publish Date: ").append(new Date(publishDate)).
                append("\n Date added: ").append(new Date(dateAdded)).
                append("\n Magnet link: ").append(magnetUri).
                append("\n Link: ").append(link).
                append("\n IMDB Link: ").append(iMDBLlink).
                append("\n IMDB ID: ").append(iMDBiD).
                append("\n IMDB Rating: ").append(iMDBrating).
                append("\n IMDB Votes: ").append(iMDBvotes).
                append("\n TV MAze Link: ").append(TVmazeLink).
                append("\n Language: ").append(language).
                append("\n Quality: ").append(quality).
                append("\n Subtitles: ").append(subtitles).
                append("\n Genres: ").append(genres).
                toString();
    }

    /**
     * @return the infoHash
     */
    public String getInfoHash() {
        return infoHash;
    }

    /**
     * @param infoHash the infoHash to set
     */
    public void setInfoHash(String infoHash) {
        this.infoHash = infoHash;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the size
     */
    public double getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(double size) {
        this.size = size;
    }

    /**
     * @return the unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * @param unit the unit to set
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * @return the publishDate
     */
    public long getPublishDate() {
        return publishDate;
    }

    /**
     * @param publishDate the publishDate to set
     */
    public void setPublishDate(long publishDate) {
        this.publishDate = publishDate;
    }

    /**
     * @return the dateAdded
     */
    public long getDateAdded() {
        return dateAdded;
    }

    /**
     * @param dateAdded the dateAdded to set
     */
    public void setDateAdded(long dateAdded) {
        this.dateAdded = dateAdded;
    }

    /**
     * @return the magnetUri
     */
    public String getMagnetUri() {
        return magnetUri;
    }

    /**
     * @param magnetUri the magnetUri to set
     */
    public void setMagnetUri(String magnetUri) {
        this.magnetUri = magnetUri;
    }

    /**
     * @return the link
     */
    public String getLink() {
        return link;
    }

    /**
     * @param link the link to set
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * @return the iMDBLlink
     */
    public String getiMDBLlink() {
        return iMDBLlink;
    }

    /**
     * @return the TVmazeLink
     */
    public String getTVmazeLink() {
        return TVmazeLink;
    }

    /**
     * @return the iMDBiD
     */
    public String getiMDBiD() {
        return iMDBiD;
    }
    

    /**
     * @param iMDBLlink the iMDBLlink to set
     */
    public void setiMDBLlink(String iMDBLlink) {
        this.iMDBLlink = iMDBLlink;
    }

    /**
     * @param TVmazeLink the TVmazeLink to set
     */
    public void setTVmazeLink(String TVmazeLink) {
        this.TVmazeLink = TVmazeLink;
    }

    /**
     * @param iMDBiD the iMDBiD to set
     */
    public void setiMDBiD(String iMDBiD) {
        this.iMDBiD = iMDBiD;
    }

    /**
     * @return the iMDBrating
     */
    public double getiMDBrating() {
        return iMDBrating;
    }

    /**
     * @param iMDBrating the iMDBrating to set
     */
    public void setiMDBrating(double iMDBrating) {
        this.iMDBrating = iMDBrating;
    }

    /**
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @return the quality
     */
    public String getQuality() {
        return quality;
    }

    /**
     * @param quality the quality to set
     */
    public void setQuality(String quality) {
        this.quality = quality;
    }

    /**
     * @return the subtitles
     */
    public String getSubtitles() {
        return subtitles;
    }

    /**
     * @param subtitles the subtitles to set
     */
    public void setSubtitles(String subtitles) {
        this.subtitles = subtitles;
    }

    /**
     * @return the genres
     */
    public String getGenres() {
        return genres;
    }

    /**
     * @param genres the genres to set
     */
    public void setGenres(String genres) {
        this.genres = genres;
    }

    /**
     * @return the iMDBvotes
     */
    public int getiMDBvotes() {
        return iMDBvotes;
    }

    /**
     * @param iMDBvotes the iMDBvotes to set
     */
    public void setiMDBvotes(int iMDBvotes) {
        this.iMDBvotes = iMDBvotes;
    }
    
}
