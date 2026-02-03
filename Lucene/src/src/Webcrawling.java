package src;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.*;

public class Webcrawling {

    static class Node {
        String url;
        int depth;

        Node(String url, int depth) {
            this.url = url;
            this.depth = depth;
        }
    }
    private static void bfsCrawl(String seedUrl, int maxDepth) {
        Queue<Node> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        BufferedWriter writer;
        try {
        writer = new BufferedWriter(new FileWriter("C:/Users/nsarj/Desktop/Lucene/src/pages.txt",true));
        } catch (Exception e) {
            System.out.println("Failed to open file");
            return;
        }

        queue.add(new Node(seedUrl, 0));
        visited.add(seedUrl);

        try {
            writer.write(seedUrl);
            writer.newLine();
        } catch (Exception e) {
            System.out.println("Failed to write seed URL");
        }

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            System.out.println("Depth " + current.depth + " -> " + current.url);

            if (current.depth == maxDepth) continue;

            try {
                Document doc = Jsoup.connect(current.url).userAgent("Mozilla/5.0").timeout(10_000).get();
                Elements links = doc.select("a[href]");

                for (Element link : links) {
                    String absUrl = link.absUrl("href");

                    if (absUrl.startsWith(current.url) && !absUrl.contains("#") && !visited.contains(absUrl)) {

                        visited.add(absUrl);
                        queue.add(new Node(absUrl, current.depth + 1));

                        writer.write(absUrl);
                        writer.newLine();
                    }
                }

            } catch (Exception e) {
                System.out.println("Failed to fetch: " + current.url);
            }
        }

        try {
            writer.close();
        } catch (Exception e) {
            System.out.println("Failed to close file");
        }
    }
    public static void main(String[] args) {

        String[] aerospace={"https://www.lockheedmartin.com/","https://www.boeing.com/",
        "https://www.boeing.com/defense/","https://www.northropgrumman.com/","https://www.rtx.com/",
        "https://en.wikipedia.org/wiki/Lockheed_Martin","https://en.wikipedia.org/wiki/Fifth-generation_fighter",
        "https://en.wikipedia.org/wiki/Aviation","https://en.wikipedia.org/wiki/Fighter_aircraft","https://en.wikipedia.org/wiki/Aerospace",
        "https://en.wikipedia.org/wiki/Lockheed_Martin_F-22_Raptor","https://en.wikipedia.org/wiki/F-35_Lightning_II","https://en.wikipedia.org/wiki/Boeing_F-15EX",
        "https://en.wikipedia.org/wiki/Northrop_Grumman_B-21_Raider","https://en.wikipedia.org/wiki/Northrop_B-2_Spirit","https://en.wikipedia.org/wiki/Rockwell_B-1_Lancer",
        "https://en.wikipedia.org/wiki/Concorde"};

        for(String seed : aerospace){
            bfsCrawl(seed, 3);
        }   
    }
    
}
