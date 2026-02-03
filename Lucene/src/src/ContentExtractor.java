package src;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;

public class ContentExtractor {

    public static void main(String[] args) {
        String inputFile = "C:/Users/nsarj/Desktop/Lucene/src/pages.txt";
        String outputFile = "C:/Users/nsarj/Desktop/Lucene/paths/ex_pages.jsonl";

        try (
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true))
        ) {
            String url;
            while ((url = br.readLine()) != null) {
                extractAndSave(url, writer);
            }
        } catch (Exception e) {
            System.out.println("File processing error");
        }
    }

    private static void extractAndSave(String url, BufferedWriter writer) {

        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(10000)
                    .get();

            String title = doc.title();

            Elements article = doc.select("article");
            Elements headings = doc.select("h1, h2, h3");
            Elements paragraphs = doc.select("p");

            StringBuilder content = new StringBuilder();

            if (!article.isEmpty()) {
                content.append(article.text());
            } else {
                content.append(headings.text()).append(" ");
                content.append(paragraphs.text());
            }

            if (content.length() < 150) return;

            String json = "{"
                    + "\"url\":\"" + escape(url) + "\","
                    + "\"title\":\"" + escape(title) + "\","
                    + "\"content\":\"" + escape(content.toString()) + "\""
                    + "}";

            writer.write(json);
            writer.newLine();
            writer.flush();

        } catch (Exception e) {
            System.out.println("Failed to extract: " + url);
        }
    }

    private static String escape(String text) {
        return text
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", " ")
            .replace("\r", " ")
            .replace("\u2028", " ")
            .replace("\u2029", " ");
    }
}
