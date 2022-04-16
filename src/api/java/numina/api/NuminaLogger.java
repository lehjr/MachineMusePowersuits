package numina.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NuminaLogger {
    public static final Logger logger = LogManager.getLogger("MachineMuse");

        public static void logDebug(String string) {
            boolean debugging = true;
            try {
                if (!NuminaSettings.enableDebugging()) {
                    debugging = false;
                }
            } catch (Exception ignored) {
            }
            if (debugging) logger.info(string);
        }

        public static void logError(String string) {
            logger.warn(string);
        }

        public static Logger getLogger() {
            return logger;
        }

        public static void logException(String string, Throwable exception) {
            logger.warn(string);
            exception.printStackTrace();
        }
}