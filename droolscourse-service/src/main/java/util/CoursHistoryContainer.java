package util;

import org.chtijbug.drools.entity.history.HistoryEvent;
import org.chtijbug.drools.runtime.DroolsChtijbugException;
import org.chtijbug.drools.runtime.listener.HistoryListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * Date: 14/02/14
 * Time: 11:47
 * To change this template use File | Settings | File Templates.
 */
public class CoursHistoryContainer implements HistoryListener {
    /**
     * Class Logger
     */
    private static Logger logger = LoggerFactory.getLogger(CoursHistoryContainer.class);

    @Override
    public void fireEvent(HistoryEvent historyEvent) throws DroolsChtijbugException {
        logger.info(historyEvent.toString());

    }
}
