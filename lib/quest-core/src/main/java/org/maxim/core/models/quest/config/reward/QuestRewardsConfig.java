package org.maxim.core.models.quest.config.reward;

import java.util.List;

import org.maxim.core.models.quest.config.Nullable;

public class QuestRewardsConfig {
    public Integer assignmentMethod;
    public List<QuestReward> rewards;
    @Nullable
    public String rewardsExpireAt;
    public List<Integer> platforms;
}

