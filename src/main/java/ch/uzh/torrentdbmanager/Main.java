/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.uzh.torrentdbmanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lareida
 */
public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        String file = (args.length > 1) ? args[1] : "torrents-new.db";

        DataBaseAdapter db = new DataBaseAdapter(file);
        TorrentResolver res = new TorrentResolver();

        Path input = Paths.get(args[0]);
        int retry = 0;
        try (BufferedReader reader = Files.newBufferedReader(input)) {
            System.out.println("Reading from: " + input.toAbsolutePath());
            String line = reader.readLine().toLowerCase();

            while (line != null) {
                try {
                    if (line.isEmpty() || db.exists(line)) {
                        System.out.println("Skipped: " + line);
                        line = reader.readLine();
                        continue;
                    }
                    System.out.println("Resolving torrent: " + line);
                    db.storeTorrent(res.resolveTorrent(line));
                    Thread.sleep(500);

                    line = reader.readLine();

                } catch (InterruptedException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SocketTimeoutException se) {
                    se.printStackTrace();
                    res.nextMirror();
//                    try {
//                        Thread.sleep(2 * 60 * 1000);
//                    } catch (InterruptedException ex) {
//                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//                    }
                } catch (IOException iox) {
                    System.out.println("ERROR with TORRENT: " + line);
                    iox.printStackTrace();
                   line = "";
                } catch (Exception exc){
                    res.nextMirror();
                    if(retry > 2){
                        System.out.println("Retried torrent 3 times skipping");
                        exc.printStackTrace();
                        line="";
                        retry=0;
                    }else {
                        retry ++;
                    }
                }
            }
        }

    }

}
