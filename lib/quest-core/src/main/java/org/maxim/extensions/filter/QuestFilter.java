package org.maxim.extensions.filter;

import java.util.ArrayList;
import java.util.List;

import org.maxim.core.helper.StringHelper;
import org.maxim.core.helper.TimeHelper;
import org.maxim.core.models.quest.Quest;
import org.maxim.core.models.quest.QuestUserStatus;
import org.maxim.core.models.quest.config.reward.QuestReward;
import org.maxim.core.models.quest.config.reward.QuestRewardsConfig;
import org.maxim.extensions.ExtensionHelper;

public final class QuestFilter extends ExtensionHelper {
    private static final long EXCLUDED_Id = 1248385850622869556L;
    // 3, 4 => Collectables, Orbs
    private static final int[] SPECIAL_REWARD_TYPES = { 3, 4 };

    private final TimeHelper timer;
    private Quest quest;
    private boolean expired;

    public QuestFilter(TimeHelper timer, Quest quest) {
        this.timer = timer;
        this.setQuest(quest);
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
        this.expired = isExpired(quest, timer);
    }

    public boolean isEnrollable() {
        return isEnrollable(this.quest, this.expired);
    }

    public boolean isCompleteable() {
        return isCompleteable(this.quest, this.expired);
    }

    public boolean isClaimable() {
        return isClaimable(this.quest, this.expired, this.timer);
    }

    public boolean isWorthy() {
        return isWorthy(this.quest, this.expired);
    }

    public boolean isExpired() {
        return this.expired;
    }

    public String getPreety(StringHelper strHelper) {
        List<String> strings = new ArrayList<>();
        strings.add(strHelper.format("isEnrollable: %s", this.isEnrollable()));
        strings.add(strHelper.format("isCompleteable: %s", this.isCompleteable()));
        strings.add(strHelper.format("isClaimable: %s", this.isClaimable()));
        strings.add(strHelper.format("isWorthy: %s", this.isWorthy()));
        strings.add(strHelper.format("isExpired: %s", this.isExpired()));

        return String.join("; ", strings);
    }

    public static boolean isEnrollable(Quest quest, boolean expired) {
        return isObjectNull(quest.userStatus) && !expired;
    }

    public static boolean isCompleteable(Quest quest, boolean expired) {
        if (quest.Id == EXCLUDED_Id || isObjectNull(quest.userStatus) || expired)
            return false;

        QuestUserStatus status = quest.userStatus;
        return !isStringEmpty(status.enrolledAt) &&
                isStringEmpty(status.completedAt); // Not yet completed
    }

    public static boolean isClaimable(Quest quest, boolean expired, TimeHelper timer) {
        if (isObjectNull(quest.userStatus) || expired)
            return false;

        QuestUserStatus status = quest.userStatus;
        QuestRewardsConfig rewards = quest.config.rewardsConfig;

        return !isStringEmpty(status.completedAt) &&
                isStringEmpty(status.claimedAt) && // Not yet claimed
                !isObjectNull(rewards) &&
                !isTimeExpired(rewards.rewardsExpireAt, timer);
    }

    public static boolean isWorthy(Quest quest, boolean expired) {
        if (expired || isObjectNull(quest.config.rewardsConfig))
            return false;

        List<QuestReward> rewards = quest.config.rewardsConfig.rewards;
        if (isListEmpty(rewards))
            return false;

        for (QuestReward reward : rewards) {
            if (isSpecialReward(reward.type))
                return true;
        }

        return false;
    }

    public static boolean isExpired(Quest quest, TimeHelper timer) {
        return isTimeExpired(quest.config.expiresAt, timer);
    }

    public static boolean isSpecialReward(int rewardType) {
        for (int t : SPECIAL_REWARD_TYPES) {
            if (t == rewardType)
                return true;
        }
        return false;
    }

    public static boolean isTimeExpired(String timeStr, TimeHelper timer) {
        return isStringEmpty(timeStr) || timer.timeInPast(timeStr);
    }
}
