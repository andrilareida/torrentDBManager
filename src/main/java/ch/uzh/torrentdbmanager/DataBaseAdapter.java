/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.uzh.torrentdbmanager;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.IIOException;

/**
 *
 * @author lareida
 */
public class DataBaseAdapter implements Closeable {
    
    	public static final String TORRENTS ="TORRENTS";
	public static final String INFO_HASH = "INFO_HASH";
	public static final String TORRENT_TITLE = "TORRENT_TITLE";
        public static final String TORRENT_COMMENT = "TORRENT_COMMENT";
        
	public static final String TORRENT_SIZE = "TORRENT_SIZE";
        public static final String SIZE_UNIT = "SIZE_UNIT";
        public static final String LANGUAGE = "LANGUAGE";
	public static final String QUALITY = "QUALITY";
	public static final String SUBTITLES = "SUBTITLES";
	public static final String GENRES = "GENRES";
        
        public static final String PUBLISH_DATE = "PUBLISH_DATE";
        public static final String TIME_ADDED = "TIME_ADDED";
        
        public static final String MAGNET_URI = "MAGNET_URI";
	public static final String TORRENT_LINK = "TORRENT_LINK";
	
	public static final String IMDB_LINK = "IMDB_LINK";
	public static final String IMDB_ID = "IMDB_ID";
	public static final String IMDB_RATING = "IMDB_RATING";
	public static final String IMDB_VOTES = "IMDB_VOTES";
	public static final String TV_MAZE_LINK = "TV_MAZE_LINK";

        
        
        public static final String createTableSQL = "CREATE TABLE IF NOT EXISTS "+TORRENTS+" ( " +
						INFO_HASH +" BINARY(20) NOT NULL, " +
						TORRENT_TITLE + " VARCHAR(255) NOT NULL, " +
						TORRENT_COMMENT + " TEXT, " +
                                                
                                                TORRENT_SIZE + " REAL, " +
                                                SIZE_UNIT + " VARCHAR(4), " +
                                                LANGUAGE + " VARCHAR(20), " +
                                                QUALITY + " VARCHAR(20), " +
                                                SUBTITLES + " VARCHAR(256), " +
                                                GENRES + " VARCHAR(256), " +
                                                PUBLISH_DATE + " TIMESTAMP NOT NULL, " +
                                                TIME_ADDED + " TIMESTAMP NOT NULL, " +
                                                											
						MAGNET_URI + " VARCHAR(512), " +
						TORRENT_LINK + " VARCHAR(255), " +
                                                
                                                IMDB_LINK + " VARCHAR(255), " +
                                                IMDB_ID + " VARCHAR(20), " +
                                                IMDB_RATING + " REAL, " +
                                                IMDB_VOTES + " INTEGER " +
                                                TV_MAZE_LINK + " VARCHAR(255), " +
						"PRIMARY KEY ( " + INFO_HASH + "))";
    
        public static final String sqlStatement = "INSERT OR IGNORE INTO " + TORRENTS + " ( " + 
				INFO_HASH + ", " + 
				TORRENT_TITLE + ", " + 
				TORRENT_COMMENT + "," +
                
				TORRENT_SIZE + "," +
                                SIZE_UNIT + "," +
                                LANGUAGE + "," +
                                QUALITY + "," +
                                SUBTITLES + "," +
                                GENRES + "," +
				PUBLISH_DATE + ", " + 
				TIME_ADDED + ", " + 

                                MAGNET_URI + ", " + 
				TORRENT_LINK + ", " +
				
                                IMDB_LINK + "," +
                                IMDB_ID + "," +
                                IMDB_RATING + "," + 
                                IMDB_VOTES + " ) " +
                                "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        private Connection connection;
        
        public DataBaseAdapter(String file) throws ClassNotFoundException{
        Class.forName("org.sqlite.JDBC");
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:"+file);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate(createTableSQL);
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseAdapter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void storeTorrent(TorrentDAO t){
        
                try {
                    PreparedStatement stmt = connection.prepareStatement(sqlStatement);
                    stmt.setString(1, t.getInfoHash());
                    stmt.setString(2, t.getTitle());
                    stmt.setString(3, t.getComment());
                    
                    stmt.setDouble(4, t.getSize());
                    stmt.setString(5, t.getUnit());
                    stmt.setString(6, t.getLanguage());
                    stmt.setString(7, t.getQuality());
                    stmt.setString(8, t.getSubtitles());
                    stmt.setString(9, t.getGenres());
                    stmt.setDate(10, new Date(t.getPublishDate()));
                    stmt.setDate(11, new Date(t.getDateAdded()));


                    stmt.setString(12, t.getMagnetUri());
                    stmt.setString(13, t.getLink());
                    stmt.setString(14, t.getiMDBLlink());
                    stmt.setString(15, t.getiMDBiD());
                    stmt.setDouble(16, t.getiMDBrating());
                    stmt.setInt(17, t.getiMDBvotes());
                    stmt.execute();
                    
                } catch (SQLException ex) {
                    Logger.getLogger(DataBaseAdapter.class.getName()).log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                }
        
    }
    
    
    public boolean exists(String torrent){
        String query = "SELECT " + INFO_HASH + " FROM " + TORRENTS + " WHERE " + INFO_HASH + "=?";
        boolean result = false;
                try {
                    PreparedStatement pst = connection.prepareStatement(query);
                    pst.setString(1, torrent);
                    ResultSet rs = pst.executeQuery();
                    result = rs.next();
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DataBaseAdapter.class.getName()).log(Level.SEVERE, null, ex);
                }
        return result;
    }

    @Override
    public void close() throws IOException {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    throw new IIOException("Failed to close JDBC connection", ex);
                }
    }
}
