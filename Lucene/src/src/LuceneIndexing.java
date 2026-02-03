package src;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.store.*;
import java.io.*;
import java.nio.file.*;

public class LuceneIndexing {

    private static final String DATA_FILE = "paths/ex_pages.jsonl";
    private static final String INDEX_DIR = "index";

    public static void main(String[] args) {
        try {
            Directory directory = FSDirectory.open(Paths.get(INDEX_DIR));
            StandardAnalyzer analyzer = new StandardAnalyzer();

            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

            IndexWriter writer = new IndexWriter(directory, config);

            BufferedReader br = new BufferedReader(new FileReader(DATA_FILE));
            String line;
            int count = 0;

            while ((line = br.readLine()) != null) {
                Document doc = jsonToDocument(line);
                if (doc != null) {
                    writer.addDocument(doc);
                    count++;
                }
            }

            br.close();
            writer.close();

            System.out.println("Indexing completed.");
            System.out.println("Total documents indexed: " + count);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Document jsonToDocument(String json) {
        try {
            String url = extract(json, "url");
            String title = extract(json, "title");
            String content = extract(json, "content");

            Document doc = new Document();

            doc.add(new StringField("url", url, Field.Store.YES));
            doc.add(new TextField("title", title, Field.Store.YES));
            doc.add(new TextField("content", content, Field.Store.NO));

            return doc;

        } catch (Exception e) {
            return null;
        }
    }

    private static String extract(String json, String key) {
        String pattern = "\"" + key + "\":\"";
        int start = json.indexOf(pattern);
        if (start == -1) return "";

        start += pattern.length();
        int end = json.indexOf("\"", start);
        return json.substring(start, end);
    }
}
