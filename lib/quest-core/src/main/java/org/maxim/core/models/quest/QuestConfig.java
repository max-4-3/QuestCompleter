package org.maxim.core.models.quest;

import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.maxim.core.models.quest.config.QuestAssets;
import org.maxim.core.models.quest.config.QuestGradient;
import org.maxim.core.models.quest.config.QuestMessages;
import org.maxim.core.models.quest.config.application.QuestApplication;
import org.maxim.core.models.quest.config.reward.QuestRewardsConfig;
import org.maxim.core.models.quest.config.task.config.QuestTaskConfig;
import org.maxim.core.models.quest.config.task.config.QuestTaskConfigV2;
import org.maxim.core.models.quest.config.video.QuestCosponsorMetadata;
import org.maxim.core.models.quest.config.video.QuestVideoMetadata;

public class QuestConfig {
    public Long Id;
    public Integer configVersion;
    public String startsAt;
    public String expiresAt;

    public List<Integer> features;
    public QuestApplication application;
    public QuestAssets assets;
    public QuestGradient colors;
    public QuestMessages messages;

    @Nullable
    public QuestTaskConfig taskConfig;
    @Nullable
    public QuestTaskConfigV2 taskConfigV2;

    public QuestRewardsConfig rewardsConfig;

    @Nullable
    public QuestVideoMetadata videoMetadata;
    @Nullable
    public QuestCosponsorMetadata cosponsorMetadata;
}
