package io.webfolder.cdp.logger;

import static io.webfolder.cdp.logger.MessageFormatter.arrayFormat;
import static org.apache.log4j.Level.ERROR;
import static org.apache.log4j.Level.WARN;

import org.apache.log4j.Logger;

public class CdpLog4jLogger implements CdpLogger {

    private final Logger logger;

    public CdpLog4jLogger(final String name) {
        logger = Logger.getLogger(name);
    }

    @Override
    public void info(String message, Object... args) {
        if (logger.isInfoEnabled()) {
            FormattingTuple tuple = arrayFormat(message, args);
            logger.info(tuple.getMessage());
        }
    }

    @Override
    public void debug(String message, Object... args) {
        if (logger.isDebugEnabled()) {
            FormattingTuple tuple = arrayFormat(message, args);
            logger.debug(tuple.getMessage());
        }
    }

    @Override
    public void warning(String message, Object... args) {
        if (logger.isEnabledFor(WARN)) {
            FormattingTuple tuple = arrayFormat(message, args);
            logger.log(WARN, tuple.getMessage());
        }
    }

    @Override
    public void error(String message, Object... args) {
        if (logger.isEnabledFor(ERROR)) {
            FormattingTuple tuple = arrayFormat(message, args);
            logger.error(tuple.getMessage());
        }
    }

    @Override
    public void error(String message, Throwable t) {
        if (logger.isEnabledFor(ERROR)) {
            logger.error(message, t);
        }
    }
}
