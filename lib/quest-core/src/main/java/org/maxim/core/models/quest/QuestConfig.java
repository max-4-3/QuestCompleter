package org.maxim.core.models.quest;

import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.Nullable;
import org.maxim.core.models.quest.datatypes.Snowflake;

public class QuestConfig {
    public Snowflake id;
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

    @Nullable
    public QuestBaseTaskConfig getTaskConfig() {
        if (this.taskConfig != null)
            return (QuestBaseTaskConfig) this.taskConfig;
        else if (this.taskConfigV2 != null)
            return (QuestBaseTaskConfig) this.taskConfigV2;
        else
            return null;
    }
}

class QuestVideoMetadata {
    public QuestVideoMessages messages;
    public QuestVideoAssets assets;
}

class QuestVideoMessages {
    public String videoTitle;
    public String videoEndCtaTitle;
    public String videoEndCtaSubtitle;
    public String videoEndCtaButtonLabel;
}

class QuestVideoAssets {
    public String videoPlayerVideo;
    public String videoPlayerVideoLowRes;
    public String videoPlayerCaption;
    public String videoPlayerTranscript;

    @Nullable
    public String videoPlayerVideoHls;
    @Nullable
    public String videoPlayerThumbnail;
    @Nullable
    public String questBarPreviewVideo;
    @Nullable
    public String questBarPreviewThumbnail;
    @Nullable
    public String questHomeVideo;
}

class QuestCosponsorMetadata {
    public String name;
    public String logotype;
    public String redemptionInstructions;
}

class QuestRewardsConfig {
    public Integer assignmentMethod;
    public List<QuestReward> rewards;
    @Nullable
    public String rewardsExpireAt;
    public List<Integer> platforms;
}

class QuestReward {
    public Integer type;
    public Snowflake skuID;
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
    public Integer quantity;
}

class QuestRewardMessages {
    public String name;
    public String nameWithArticle;
    @Nullable
    public Map<Integer, String> rewardRedemptionInstructionsByPlatform;
}

class QuestBaseTaskConfig {
    public String joinOperator;
    public Map<String, QuestTask> tasks;

    @Nullable
    public String enrollmentUrl;
    @Nullable
    public Snowflake developerApplicationID;
}

class QuestTaskConfig extends QuestBaseTaskConfig {
    public Integer type;
}

class QuestTaskConfigV2 extends QuestBaseTaskConfig {

}

class QuestTask {
    public String eventName;
    public Integer target;
    @Nullable
    public List<String> externalIds;
    @Nullable
    public String title;
    @Nullable
    public String description;
}

class QuestMessages {
    public String questName;
    public String gameTitle;
    public String gamePublisher;
}

class QuestGradient {
    public String primary;
    public String secondary;
}

class QuestAssets {
    public String hero;
    public String questBarHero;
    public String gameTile;
    public String logotype;

    public String gameTileDark;
    public String gameTileLight;
    public String logotypeDark;
    public String logotypeLight;
    public String questBarHeroBlurhash;

    @Nullable
    public String heroVideo;
    @Nullable
    public String questBarHeroVideo;
}

class QuestApplication {
    public Snowflake id;
    public String name;
    public String link;
}
