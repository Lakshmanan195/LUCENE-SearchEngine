🔍 Search Engine Using Apache Lucene
📌 Project Description

This project implements a basic search engine that crawls web pages, extracts textual content, indexes it using Apache Lucene, and supports efficient keyword-based search. It demonstrates core information retrieval concepts such as web crawling, indexing, text analysis, and ranked search.

🚀 Features

Web crawling using predefined seed URLs

HTML parsing and text extraction

Storage of crawled data in JSONL format

Indexing using Apache Lucene

Keyword-based search with relevance ranking

Multi-field search (URL, title, content)

🛠 Technologies Used

Java

Apache Lucene

Jsoup

File I/O

Data Structures (BFS, HashSet)

📁 Project Structure

Lucene/
├── src/
│ ├── Webcrawling.java
│ ├── ContentExtractor.java
│ ├── LuceneIndexing.java
│ └── LuceneSearching.java
│
├── lib/
│ ├── jsoup-.jar
│ ├── lucene-core-.jar
│ ├── lucene-analysis-common-.jar
│ └── lucene-queryparser-.jar
│
├── paths/
│ └── extracted_pages.jsonl
│
├── index/
│ └── (Lucene index files)
│
└── README.md

▶️ How to Run
Compile

javac -cp "lib/" -d bin src/.java

Run Web Crawler

java -cp "bin;lib/*" src.Webcrawling

Index the Data

java -cp "bin;lib/*" src.LuceneIndexing

Search

java -cp "bin;lib/*" src.LuceneSearching

⚠️ Limitations

Limited crawling depth

No duplicate content detection

No stemming or synonym handling

Console-based interface only

🚀 Future Enhancements

PageRank-based ranking

Duplicate content detection

Fuzzy and phrase search

Web-based user interface

Parallel crawling

👤 Author

Lakshmanan S
B.Tech – Artificial Intelligence and Data Science

📜 License

This project is for educational purposes only.
