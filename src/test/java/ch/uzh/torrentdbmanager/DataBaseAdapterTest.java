/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.uzh.torrentdbmanager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author lareida
 */
public class DataBaseAdapterTest {

    public String file = "torrent.db";
    private DataBaseAdapter db;

    public DataBaseAdapterTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws ClassNotFoundException {
        db = new DataBaseAdapter(file);
    }

    @After
    public void tearDown() {
        try {
            db.close();
            Files.delete(Paths.get(file));
        } catch (IOException ex) {
            Logger.getLogger(DataBaseAdapterTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void test() throws ClassNotFoundException, IOException {
        DataBaseAdapter dab = new DataBaseAdapter(file);
        dab.close();
    }

    /**
     * Test of storeTorrent method, of class DataBaseAdaptor.
     */
    @Test
    public void testStoreTorrent() throws ClassNotFoundException, IOException {
        System.out.println("storeTorrent");

        TorrentDAO t = new TorrentDAO();
        t.setInfoHash("Blah blah blah");
        t.setDateAdded(System.currentTimeMillis());
        t.setPublishDate(System.currentTimeMillis());
        
        db.storeTorrent(t);

    }
}
