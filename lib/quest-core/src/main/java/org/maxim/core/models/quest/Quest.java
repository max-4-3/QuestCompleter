package org.maxim.core.models.quest;

import java.util.HashSet;
import java.util.Set;

import org.maxim.core.models.quest.datatypes.QuestProgress;
import org.maxim.core.models.quest.datatypes.Snowflake;
import org.maxim.core.models.quest.types.QuestType;
import org.jetbrains.annotations.Nullable;

/*  
 *  if error using jetbrains annotations:
 *      @interface Nullable {}
 * */

public class Quest {
    public Snowflake id;
    public Boolean preview;
    public QuestConfig config;

    @Nullable
    public QuestUserStatus userStatus;

    public Set<String> getQuestRewards() {
        Set<String> rewards = new HashSet<>();
        if (this.config.rewardsConfig != null && this.config.rewardsConfig.rewards != null) {
            for (QuestReward reward : this.config.rewardsConfig.rewards) {
                if (reward == null || reward.messages == null)
                    continue;
                rewards.add(reward.messages.nameWithArticle);
            }
        }
        return rewards;
    }

    public QuestProgress getQuestProgress() {
        QuestBaseTaskConfig taskConfig = this.config.getTaskConfig();
        if (taskConfig == null)
            return null;

        String taskName = "";
        int done = 0, total = 100;

        if (this.userStatus != null && !this.userStatus.progress.isEmpty()) {
            QuestTaskProgress prev = null;
            for (QuestTaskProgress progress : this.userStatus.progress.values()) {
                if (progress == null)
                    continue;

                if (prev == null) {
                    prev = progress;
                    continue;
                }

                if (prev.value < progress.value) {
                    prev = progress;
                }
            }

            if (prev != null && !taskConfig.tasks.isEmpty()) {
                taskName = prev.eventName;
                QuestTask task = taskConfig.tasks.get(taskName);
                if (task != null) {
                    done = prev.value;
                    total = task.target;
                } else {
                    taskName = "";
                }
            }
        } else {
            QuestTask prev = null;
            for (QuestTask task : taskConfig.tasks.values()) {
                if (task == null)
                    continue;
                if (prev == null) {
                    prev = task;
                    continue;
                }

                if (prev.target > task.target) {
                    prev = task;
                }
            }
            if (prev != null) {
                taskName = prev.eventName;
                QuestTask task = taskConfig.tasks.get(taskName);
                if (task != null) {
                    done = 0;
                    total = task.target;
                } else {
                    taskName = "";
                }
            }
        }
        if (taskName == null || taskName.isBlank())
            return null;

        return new QuestProgress(taskName, done, total);
    }

    public String getQuestName(QuestType questType) {
        if (questType != null && questType == QuestType.Watch) {
            if (this.config.videoMetadata != null) {
                if (this.config.videoMetadata.messages != null
                        && this.config.videoMetadata.messages.videoTitle != null
                        && !this.config.videoMetadata.messages.videoTitle.isBlank()) {
                    return this.config.videoMetadata.messages.videoTitle;
                }
            } else if (this.config.application != null && !this.config.application.name.isBlank()) {
                return String.format("Watch video by %s", this.config.application.name);
            }
        }
        return this.config.messages.questName;
    }

    public QuestType getQuestType() {
        QuestBaseTaskConfig taskConfig = this.config.getTaskConfig();
        if (taskConfig == null)
            return QuestType.Unknown;
        for (String name : taskConfig.tasks.keySet()) {
            if (name == null || name.isBlank() || name.split("_").length < 1)
                continue;
            if (name.equalsIgnoreCase("play_activity"))
                return QuestType.Activity;
            String prefix = name.split("_")[0];
            switch (prefix.toLowerCase()) {
                case "watch":
                    return QuestType.Watch;
                case "play":
                    return QuestType.Play;
                case "stream":
                    return QuestType.Stream;
                case "achievement":
                    return QuestType.Achievement;
            }
        }
        return QuestType.Unknown;
    }
}
