package scraper;


public class Scraper {

    public static void main(String[] args) {
        System.out.println("Web Scraper");
        
        String[] myArgs = new String[6];
//        myArgs[0] = "http://www.cnn.com";
        myArgs[0] = "./src/data/urls.txt";
        myArgs[1] = "Greece,Football,default,Ebola";
        myArgs[2] = "-v";
        myArgs[3] = "-w";
        myArgs[4] = "-c";
        myArgs[5] = "-e";
        
        if ( args.length>2)
        {
//            ScrapeManager manager = new ScrapeManager(myArgs);
            ScrapeManager manager = new ScrapeManager(args);
            manager.ScrapeAll();
        } else {
            System.out.println("Parameters is required!");
        }
    }
}
