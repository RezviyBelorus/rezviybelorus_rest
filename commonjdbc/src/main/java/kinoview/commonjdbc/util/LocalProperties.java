package kinoview.commonjdbc.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by alexfomin on 30.06.17.
 */
public class LocalProperties {
    private Properties properties;

    public LocalProperties() {
        load();
    }

    private void load() {
        String path = LocalProperties.class.getClassLoader().getResource("local.properties").getPath();

        try (FileInputStream fis = new FileInputStream(path)){
            properties = new Properties();
            properties.load(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String get(String name) {
        return properties.getProperty(name);
    }
}
