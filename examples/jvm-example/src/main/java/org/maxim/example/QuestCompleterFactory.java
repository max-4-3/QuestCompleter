package org.maxim.example;

import org.maxim.extensions.completer.session.Session;
import org.maxim.extensions.completer.Completer;
import org.maxim.extensions.completer.PlayQuestCompleter;
import org.maxim.extensions.completer.WatchQuestCompleter;
import org.maxim.core.helper.*;
import org.maxim.core.models.quest.Quest;
import org.maxim.core.models.quest.types.QuestType;
import org.maxim.example.implementations.*;

public final class QuestCompleterFactory {
    private final static TimeHelper timer = new DefaultTimeHelper();
    private final static StringHelper stringer = new DefaultStringHelper();
    private final static RandomHelper rand = new DefaultRandomHelper();
    private final static SleepHelper sleeper = new DefaultSleepHelper();

    private final Session session;

    public QuestCompleterFactory(Session session) {
        this.session = session;
    }

    public Completer getCompleter(Quest quest, QuestType type) {
        switch (type) {
            case Watch:
                return new WatchQuestCompleter(quest, timer, sleeper, stringer, rand, session);
            case Play:
                return new PlayQuestCompleter(quest, sleeper, stringer, rand, session);
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
