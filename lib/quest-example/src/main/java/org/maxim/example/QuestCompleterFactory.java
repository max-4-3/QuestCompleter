package org.maxim.example;

import org.maxim.core.completer.Completer;
import org.maxim.core.completer.PlayQuestCompleter;
import org.maxim.core.completer.WatchQuestCompleter;
import org.maxim.core.helper.*;
import org.maxim.core.models.quest.types.QuestType;
import org.maxim.core.session.Session;
import org.maxim.example.implementations.*;

public final class QuestCompleterFactory {
    private final static TimeHelper timer = new DefaultTimeHelper();
    private final static StringHelper stringer = new DefaultStringHelper();
    private final static RandomHelper random = new DefaultRandomHelper();
    private final static SleepHelper sleeper = new DefaultSleepHelper();

    private final Session session;

    public QuestCompleterFactory(Session session) {
        this.session = session;
    }

    public Completer getCompleter(QuestType type) {
        switch (type) {
            case Watch:
                return new WatchQuestCompleter(sleeper, stringer, random, timer, session);
            case Play:
                return new PlayQuestCompleter(sleeper, stringer, random, session);
            default:
                return null;
        }
    }

    public static boolean isSupported(QuestType type) {
        switch (type) {
            case Watch, Play:
                return true;
            default:
                return false;
        }
    }
}
