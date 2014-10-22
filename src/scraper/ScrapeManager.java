package scraper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ScrapeManager {
    private Map<String, Integer> totalWordsMap;
    private long totalCharCount;
    private Options options;
    private List<URL> urlList;
    private String[] words;

    /**
     * Know who is String.
     * @param str web URL or path to file
     */
    public void setUrl(String str) throws IOException {
        this.urlList = new ArrayList<>();
        File file = new File(str);

        if ( str.startsWith("http://") || str.startsWith("https://")) {
            this.urlList.add(new URL(str));
        } else if ( file.exists()) {
            try {
                BufferedReader in = new BufferedReader( new FileReader( str));
                String url;
                while (( url = in.readLine()) != null) {
                    this.urlList.add(new URL(url));
                }
                in.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ScrapeManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("There is no url or path.");
        }
    }

    public ScrapeManager(String[] args) {
        try {
            this.setUrl(args[0]);
        } catch (IOException ex) {
            Logger.getLogger(ScrapeManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.words = args[1].split(",");
        this.options = new Options( Arrays.copyOfRange(args, 2, args.length));
    }

    public void ScrapeAll() {
        long startTime = System.nanoTime();
        PageScraper pageScraper;
        this.totalCharCount = 0;
        this.totalWordsMap = new HashMap<>();
        for (URL url : urlList) {
            pageScraper = new PageScraper( url, words, options);
            pageScraper.Scrape();
            this.totalCharCount += pageScraper.GetCharCount();
            this.SaveCurrentWordCount(pageScraper.GetWordsMap());
        }
        ShowTotalResults(startTime);
    }

    private void ShowTotalResults(long startTime) {
        System.out.println("---------------");
        System.out.println("Total results :");
        if (options.IsWord_number()) {
            System.out.println("Total Number of word occurrences - " + this.totalWordsMap.toString());
        }
        if (options.IsChar_number()) {
            System.out.println("Total charCount - " + this.totalCharCount);
        }
        if (options.IsVerbose()) {
            long endTime = System.nanoTime();
            System.out.println("Total spentTime - " + (endTime-startTime)/1e9);
        }
    }

    private void SaveCurrentWordCount(Map<String, Integer> wordsMap) {
        String word;
        Integer wordCount;
        Integer totalWordCount;
        for (Map.Entry<String, Integer> entry : wordsMap.entrySet()) {
            word = entry.getKey();
            wordCount = entry.getValue();
            if ( wordCount > 0) {
                totalWordCount = this.totalWordsMap.get(word) == null ? 0 : this.totalWordsMap.get(word);
                this.totalWordsMap.put( word, wordCount + totalWordCount);
            }
        }
    }
}
