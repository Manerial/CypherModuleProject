package launcher;

import scrapper.*;

public class Launcher {

    public static void main(String[] args) {
        WordScrapper wordScrapper = new WordScrapper();
        wordScrapper.scrap(9);
    }
}
