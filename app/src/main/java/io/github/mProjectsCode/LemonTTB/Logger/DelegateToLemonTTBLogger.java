package io.github.mProjectsCode.LemonTTB.Logger;

import org.apache.juli.logging.Log;


/**
 * The type Delegate to lemon ttb logger.
 *
 * TODO: This thing dose not work atm. no clue why. has something to do with "commons-logging.properties". It changes stuff but only from java.util.logging to spring boot logging.
 */
public class DelegateToLemonTTBLogger implements Log {
    private final LoggerAdapter logger;

    /**
     * Instantiates a new Delegate to lemon ttb logger.
     */
// constructor required by ServiceLoader
    public DelegateToLemonTTBLogger() {
        logger = null;
    }

    /**
     * Instantiates a new Delegate to lemon ttb logger.
     *
     * @param name the name
     */
    public DelegateToLemonTTBLogger(String name) {
        logger = new LoggerAdapter(name);
        logger.info("Hello from delegate logger");
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    @Override
    public boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }

    @Override
    public boolean isFatalEnabled() {
        return logger.isErrorEnabled();
    }

    @Override
    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    @Override
    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    @Override
    public boolean isWarnEnabled() {
        return logger.isWarnEnabled();
    }

    @Override
    public void trace(Object message) {
        logger.debug(String.valueOf(message));
    }

    @Override
    public void trace(Object message, Throwable t) {
        logger.debug(String.valueOf(message), t);
    }

    @Override
    public void debug(Object message) {
        logger.debug(String.valueOf(message));
    }

    @Override
    public void debug(Object message, Throwable t) {
        logger.debug(String.valueOf(message), t);
    }

    @Override
    public void info(Object message) {
        logger.info(String.valueOf(message));
    }

    @Override
    public void info(Object message, Throwable t) {
        logger.info(String.valueOf(message), t);
    }

    @Override
    public void warn(Object message) {
        logger.warn(String.valueOf(message));
    }

    @Override
    public void warn(Object message, Throwable t) {
        logger.warn(String.valueOf(message), t);
    }

    @Override
    public void error(Object message) {
        logger.error(String.valueOf(message));
    }

    @Override
    public void error(Object message, Throwable t) {
        logger.error(String.valueOf(message), t);
    }

    @Override
    public void fatal(Object message) {
        logger.error(String.valueOf(message));
    }

    @Override
    public void fatal(Object message, Throwable t) {
        logger.error(String.valueOf(message), t);
    }
}
