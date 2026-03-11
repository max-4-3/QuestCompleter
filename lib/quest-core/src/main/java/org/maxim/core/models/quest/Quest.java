package org.maxim.core.models.quest;

import org.maxim.core.models.quest.config.Nullable;

/**
 *  if error using JetBrains annotations:
 *      @interface Nullable {}
 * */

public class Quest {
    public Long Id;
    public Boolean preview;
    public QuestConfig config;

    @Nullable
    public QuestUserStatus userStatus;
}
