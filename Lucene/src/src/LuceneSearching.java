package src;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.*;

import java.nio.file.*;
import java.util.Scanner;

public class LuceneSearching {

    private static final String INDEX_DIR = "index";

    public static void main(String[] args) {
        try {
            Directory directory = FSDirectory.open(Paths.get(INDEX_DIR));
            DirectoryReader reader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(reader);

            StandardAnalyzer analyzer = new StandardAnalyzer();
            String[] fields = {"title", "body"};

            MultiFieldQueryParser parser = new MultiFieldQueryParser(fields,analyzer,
                java.util.Map.of(
                        "title", 3.0f,
                        "body", 1.0f
                ));
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.print("\nEnter search query (or 'exit'): ");
                String input = sc.nextLine();

                if (input.equalsIgnoreCase("exit")) {
                    break;
                }

                Query query = parser.parse(input);

                TopDocs results = searcher.search(query, 20);

                System.out.println("Total results: " + results.totalHits);
                System.out.println("----------------------------------");

                for (ScoreDoc sd : results.scoreDocs) {

                    StoredFields storedFields = searcher.storedFields();
                    Document d = storedFields.document(sd.doc);

                    System.out.println("Title : " + d.get("title"));
                    System.out.println("URL   : " + d.get("url"));
                    System.out.println("Score : " + sd.score);
                    System.out.println("----------------------------------");
                }
            }

            reader.close();
            sc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
