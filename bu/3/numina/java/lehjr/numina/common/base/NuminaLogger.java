/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package lehjr.numina.common.base;

import com.mojang.logging.LogUtils;
import lehjr.numina.client.config.ClientConfig;
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
//    public static final Logger logger = LogUtils.getLogger();

    public static void logDebug(String string) {
        boolean debugging = true;
        try {
//            if (!NuminaSettings.enableDebugging()) {
              if(ClientConfig.enableDebugging()) {
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