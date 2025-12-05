package scrapper;

import java.io.*;
import java.net.*;
import java.nio.charset.*;
import java.util.*;
import java.util.regex.*;

public class WordScrapper {
    private static final String RESOURCES_PATH = "src/main/resources/scrapper/";
    private static final String BASE_URL = "https://www.listesdemots.net/";
    private static final List<Integer> pagesByIndex = List.of(0, 1, 1, 5, 17, 43, 87, 146, 202, 234);
    private int size;

    public void scrap(int size) {
        if (size < 2 || size > 10) {
            System.out.println("Size not available now");
            return;
        }
        this.size = size;
        int pages = pagesByIndex.get(size - 1);
        String base = "mots" + size + "lettres";
        // Build the URIs
        List<String> uriStrList = new ArrayList<>();
        for (int i = 1; i < pages + 1; i++) {
            if (i == 1) {
                uriStrList.add(base + ".htm");
            } else {
                uriStrList.add(base + "page" + i + ".htm");
            }
        }

        // Scrap the URIs
        List<String> words = new ArrayList<>();
        for (String urlStr : uriStrList) {
            words.addAll(scrapPage(urlStr));
        }
        writeAllInFile(base + ".txt", words);
    }

    private List<String> scrapPage(String uriStr) {
        try {
            URI uri = new URI(BASE_URL + uriStr);
            HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
            conn.setRequestProperty(
                    "User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));

            StringBuilder html = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                html.append(line).append("\n");
            }
            reader.close();

            Pattern pattern = Pattern.compile(">([A-Z]{" + size + "}| )+<");
            Matcher matcher = pattern.matcher(html.toString());

            if (matcher.find()) {
                String[] s = matcher.group(0).replace("<", "").replace(">", "").split(" ");
                return new ArrayList<>(Arrays.stream(s).toList());
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private void writeAllInFile(String outputFile, List<String> words) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(RESOURCES_PATH + outputFile));
            for (String word : words) {
                writer.write(word);
                writer.newLine();
            }
            writer.close();
            System.out.println("Fichier généré : " + outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
