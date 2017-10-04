package kinoview.commonjdbc.util;

import org.apache.log4j.Logger;

/**
 * Created by alexfomin on 06.07.17.
 */
public final class Validator {
    private static Logger logger = Logger.getLogger(Validator.class);

    private Validator() {
    }

    public static Integer validateInt(String potentialInt) {
        assert potentialInt != null;
        return Integer.parseInt(potentialInt);
    }

    public static float validateFloat(String potentialFLoat) {
        assert potentialFLoat != null;
        return Float.parseFloat(potentialFLoat);
    }
}
