package kinoview.commonjdbc.util;


import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;

/**
 * Created by alexfomin on 30.06.17.
 */
public class JDBCFactoryTest {

    @Test
    public void getConnection() throws Exception{
        Connection connection = JDBCFactory.createConnection();

        Assert.assertNotNull(connection);

    }
}
