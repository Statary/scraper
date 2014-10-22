package scraper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PageScraper {
    private URL url;
    private Options options;
    private Map<String, Integer> wordsMap;
    private long charCount;
    private double spentTime;

    public Map<String, Integer> GetWordsMap() {
        return wordsMap;
    }
    public long GetCharCount() {
        return charCount;
    }
    
    public PageScraper(URL url, String[] words, Options options) {
        this.url = url;
        this.options = options;
        this.wordsMap = new HashMap<>();
        for (String word : words) {
            this.wordsMap.put(word, 0);
        }
    }
    
    public void Scrape() {
        long startTime = System.nanoTime();
        try {
            URLConnection conn = this.url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            this.charCount = 0;
            
            while ((line = in.readLine()) != null) {
                if (options.IsWord_number()) {
                    ParseWords( line);
                }
                if (options.IsChar_number()) {
                    this.charCount += line.length();
                }
            }
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(Scraper.class.getName()).log(Level.SEVERE, null, ex);
        }
        ShowResults(startTime);
    }

    private void ParseWords( String line) {
        String word;
        Integer wordCount;
        for (Map.Entry<String, Integer> entry : this.wordsMap.entrySet()) {
            word = entry.getKey();
            wordCount = WordCount(word, line);
            if ( wordCount > 0)
                entry.setValue( entry.getValue() + wordCount);
        }
    }
    
    /**
     *
     * @param word
     * @param string
     * @return Count of Word in specified String
     */
    public static Integer WordCount(String word, String string) {
        Integer stringLenght = string.length();
        string = string.replaceAll(word, "");
        Integer count = (stringLenght - string.length()) / word.length();
        return count;
    }

    private void ShowResults(long startTime) {
        System.out.println(this.url + " : ");
        if (options.IsWord_number()) {
            System.out.println("Number of word occurrences - " + this.wordsMap.toString());
        }
        if (options.IsChar_number()) {
            System.out.println("charCount - " + this.charCount);
        }
        if (options.IsVerbose()) {
            long endTime = System.nanoTime();
            this.spentTime = (endTime-startTime)/1e9;
            System.out.println("spentTime - " + this.spentTime);
        }
    }
}
