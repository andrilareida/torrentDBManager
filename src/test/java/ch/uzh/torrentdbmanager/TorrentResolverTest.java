/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.uzh.torrentdbmanager;

import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lareida
 */
public class TorrentResolverTest {
    
    public TorrentResolverTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void resolveTest() throws Exception{
        TorrentResolver tr = new TorrentResolver();
        TorrentDAO t = tr.resolveTorrent("27422106A6902D6617CA8ED3EEA4EA10AC03B4F2");
        System.out.println(t.prettyPrint());
    }
}
