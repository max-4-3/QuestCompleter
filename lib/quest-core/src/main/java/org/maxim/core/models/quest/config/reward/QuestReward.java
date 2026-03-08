package org.maxim.core.models.quest.config.reward;

import org.maxim.core.models.quest.config.Nullable;

public class QuestReward {
    public Integer type;
    public Long skuId;
    public QuestRewardMessages messages;

    @Nullable
    public String asset;
    @Nullable
    public String assetVideo;
    @Nullable
    public Integer approximateCount;
    @Nullable
    public String redemptionLink;
    @Nullable
    public String expiresAt;
    @Nullable
    public String expiresAtPremium;
    @Nullable
    public Integer expirationMode;
    @Nullable
    public Integer orbQuantity;
    @Nullable
    public Integer premiumOrbQuantity;
    @Nullable
    public Integer quantity;
}

