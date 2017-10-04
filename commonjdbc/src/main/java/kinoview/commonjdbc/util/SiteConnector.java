package kinoview.commonjdbc.util;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;


public class SiteConnector {
    private static final String SITE_URL = "site.url";
    private String pageToGet;
    private static final Logger logger = Logger.getLogger(SiteConnector.class);
    private static LocalProperties properties = new LocalProperties();

    public SiteConnector(int page) {
        pageToGet = properties.get(SITE_URL) + page;
    }

    public Document getPage() {
        try {
            return Jsoup.connect(pageToGet).get();

        } catch (IOException e) {
            logger.info("Page not found: " + pageToGet);
            return null;
        }
    }
}
