package org.maxim.extensions.helper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.maxim.core.helper.StringHelper;
import org.maxim.core.models.quest.Quest;
import org.maxim.core.models.quest.config.reward.QuestReward;
import org.maxim.core.models.quest.config.task.QuestTask;
import org.maxim.core.models.quest.config.task.QuestTaskBase;
import org.maxim.core.models.quest.config.task.QuestTaskV2;
import org.maxim.core.models.quest.config.task.config.QuestBaseTaskConfig;
import org.maxim.core.models.quest.datatypes.QuestProgress;
import org.maxim.core.models.quest.types.QuestType;
import org.maxim.core.models.quest.userstatus.task.QuestUserTaskProgress;
import org.maxim.extensions.ExtensionHelper;

/* Works fine 😭 */
public final class QuestHelper extends ExtensionHelper {
    private Quest quest;
    private QuestType questType;

    public QuestHelper(Quest quest) {
        this.setQuest(quest);
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
        this.questType = getQuestType(quest);
    }

    public Set<String> getQuestRewards() {
        return getQuestRewards(this.quest);
    }

    public QuestProgress getQuestProgress() {
        return getQuestProgress(this.quest);
    }

    public String getQuestName() {
        return getQuestName(this.quest, this.questType);
    }

    public QuestType getQuestType() {
        return this.questType;
    }

    public String getPreety(StringHelper strHelper) {
        QuestProgress progress = this.getQuestProgress();
        if (progress == null) {
            progress = new QuestProgress("Unknown", 0, 0);
        }
        return strHelper.format("[%s] %s: %d/%d (%s) [%s]", this.getQuestType(), this.getQuestName(), progress.done,
                progress.total, this.getQuestRewards(), progress.taskName);
    }

    public static QuestBaseTaskConfig getTaskConfig(Quest quest) {
        if (quest.config.taskConfig == null || quest.config.taskConfigV2 == null) {
            return null;
        }

        QuestBaseTaskConfig config = new QuestBaseTaskConfig();
        Map<String, QuestTaskBase> tasks = new HashMap<>();

        if (quest.config.taskConfig != null) {
            for (Entry<String, QuestTask> e : quest.config.taskConfig.tasks.entrySet()) {
                QuestTaskBase t = new QuestTaskBase();
                t.target = e.getValue().target;
                t.externalIds = e.getValue().externalIds;
                tasks.put(e.getKey(), t);
            } 

            config.joinOperator = quest.config.taskConfig.joinOperator;
            config.enrollmentUrl = quest.config.taskConfig.enrollmentUrl;
            config.developerApplicationId = quest.config.taskConfig.developerApplicationId;

        } else if (quest.config.taskConfigV2 != null) {
            for (Entry<String, QuestTaskV2> e : quest.config.taskConfigV2.tasks.entrySet()) {
                QuestTaskBase t = new QuestTaskBase();
                t.target = e.getValue().target;
                t.externalIds = e.getValue().externalIds;

                tasks.put(e.getKey(), t);
            } 

            config.joinOperator = quest.config.taskConfigV2.joinOperator;
            config.enrollmentUrl = quest.config.taskConfigV2.enrollmentUrl;
            config.developerApplicationId = quest.config.taskConfigV2.developerApplicationId;
        }

        config.tasks = tasks;
        return config;
    }

    public static String getQuestName(Quest quest, QuestType questType) {
        if (!isObjectNull(questType) && questType == QuestType.Watch) {
            if (!isObjectNull(quest.config.videoMetadata)) {
                if (!isObjectNull(quest.config.videoMetadata.messages)
                        && !isStringEmpty(quest.config.videoMetadata.messages.videoTitle)) {
                    return quest.config.videoMetadata.messages.videoTitle;
                }
            } else if (!isObjectNull(quest.config.application) && !isStringEmpty(quest.config.application.name)) {
                return String.format("Watch video by %s", title(quest.config.application.name));
            }
        }
        return quest.config.messages.questName;
    }

    public static Set<String> getQuestRewards(Quest quest) {
        Set<String> rewards = new HashSet<>();
        if (isObjectNull(quest.config.rewardsConfig) || isListEmpty(quest.config.rewardsConfig.rewards)) {
            return rewards;
        }

        for (QuestReward reward : quest.config.rewardsConfig.rewards) {
            if (isObjectNull(reward) || isObjectNull(reward.messages))
                continue;

            if (!isStringEmpty(reward.messages.nameWithArticle)) {
                rewards.add(title(reward.messages.nameWithArticle));
            } else if (!isStringEmpty(reward.messages.name)) {
                rewards.add(title(reward.messages.name));
            }
        }

        return rewards;
    }

    public static QuestType getQuestType(Quest quest) {
        QuestBaseTaskConfig taskConfig = getTaskConfig(quest);
        if (isObjectNull(taskConfig) || isMapEmpty(taskConfig.tasks)) {
            System.out.println("taskConfig(.tasks) is null");
            return QuestType.Unknown;
        }

        for (String name : taskConfig.tasks.keySet()) {
            if (isStringEmpty(name) || !name.contains("_"))
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

    public static QuestProgress getQuestProgress(Quest quest) {
        QuestBaseTaskConfig taskConfig = getTaskConfig(quest);
        if (isObjectNull(taskConfig))
            return null;

        String taskName = "";
        int done = 0, total = 100;

        if (!isObjectNull(quest.userStatus) && !isMapEmpty(quest.userStatus.progress)) {
            QuestUserTaskProgress prev = null;
            for (QuestUserTaskProgress progress : quest.userStatus.progress.values()) {
                if (isObjectNull(progress))
                    continue;

                if (isObjectNull(prev)) {
                    prev = progress;
                    continue;
                }

                if (prev.value < progress.value) {
                    prev = progress;
                }
            }

            if (!isObjectNull(prev) && !isMapEmpty(taskConfig.tasks)) {
                taskName = prev.eventName;
                QuestTaskBase task = taskConfig.tasks.get(taskName);

                if (!isObjectNull(task)) {
                    done = prev.value;
                    total = task.target;
                } else {
                    taskName = "";
                }
            }
        } else {
            String prevTaskName = null;
            QuestTaskBase prev = null;

            for (Entry<String, QuestTaskBase> entry : taskConfig.tasks.entrySet()) {
                prevTaskName = entry.getKey();
                QuestTaskBase task = entry.getValue();

                if (isObjectNull(task))
                    continue;
                if (isObjectNull(prev)) {
                    prev = task;
                    continue;
                }

                if (prev.target > task.target) {
                    prev = task;
                }
            }

            if (!isObjectNull(prev) && !isStringEmpty(prevTaskName)) {
                taskName = prevTaskName;
                QuestTaskBase task = taskConfig.tasks.get(taskName);
                if (!isObjectNull(task)) {
                    done = 0;
                    total = task.target;
                } else {
                    taskName = "";
                }
            }
        }

        if (isStringEmpty(taskName))
            return null;

        return new QuestProgress(taskName.toUpperCase(), done, total);
    }

}
