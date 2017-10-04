package kinoview.commonjdbc.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by alexfomin on 30.06.17.
 */
public class LocalPropertiesTest {

    @Test
    public void shouldReturnDBProperty() throws Exception{
        LocalProperties properties = new LocalProperties();
        String actual = properties.get("db.url");

        Assert.assertEquals("jdbc:mysql://localhost:3306/kino_view?useUnicode=true&characterEncoding=utf-8", actual);
    }
}
