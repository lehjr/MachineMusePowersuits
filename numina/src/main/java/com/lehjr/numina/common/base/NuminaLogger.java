package com.lehjr.numina.common.base;

import com.lehjr.numina.client.config.NuminaClientConfig;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

/**
 * Logger access class. May become more fleshed out in the future.
 *
 * @author MachineMuse
 * <p>
 * <p>
 * Ported to Java by lehjr on 10/23/16.
 */
public final class NuminaLogger {
    public static final Logger logger = LogUtils.getLogger();//LogManager.getLogger("MachineMuse");

    public static void logDebug(String string) {
        boolean debugging = true;
        try {
//            if (!NuminaSettings.enableDebugging()) {
              if(NuminaClientConfig.enableDebugging()) {
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