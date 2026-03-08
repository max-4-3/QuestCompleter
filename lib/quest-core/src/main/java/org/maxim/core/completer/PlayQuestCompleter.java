package org.maxim.core.completer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.maxim.core.helper.RandomHelper;
import org.maxim.core.helper.SleepHelper;
import org.maxim.core.helper.StringHelper;
import org.maxim.core.models.completion.QuestCompletionResult;
import org.maxim.core.models.completion.QuestCompletionStatus;
import org.maxim.core.models.quest.Quest;
import org.maxim.core.models.quest.datatypes.QuestProgress;
import org.maxim.core.models.response.JsonObject;
import org.maxim.core.session.Session;
import org.maxim.extensions.helper.QuestHelper;

public class PlayQuestCompleter implements Completer {
    private final StringHelper stringHelper;
    private final SleepHelper sleepHelper;
    private final RandomHelper randomHelper;
    private final Session session;
    private final QuestCompletionResult result;
    private final QuestCompletionStatus status;

    private QuestHelper questHelper;

    public PlayQuestCompleter(
            SleepHelper sleepHelper,
            StringHelper stringHelper,
            RandomHelper randomHelper,
            Session session) {

        this.randomHelper = randomHelper;
        this.stringHelper = stringHelper;
        this.sleepHelper = sleepHelper;
        this.session = session;
        this.result = new QuestCompletionResult();
        this.status = new QuestCompletionStatus();
    }

    public QuestCompletionResult completeQuest(Quest quest) {
        this.questHelper = new QuestHelper(quest);

        this.init(quest);
        Long applicationId = quest.Id;

        long interval; // [55, 70]
        String endpoint = stringHelper.format("quests/%d/heartbeat", applicationId);

        Function<JsonObject, Float> parseResponse = (JsonObject response) -> {
            if (quest.config.configVersion != null && quest.config.configVersion == 1) {
                return response.get("streamProgressSeconds").<Float>convert();
            } else {
                return response.get("progress").get("PLAY_ON_DESKTOP").get("value").<Float>convert();
            }
        };

        while (!result.completed && status.done < status.total) {
            JsonObject response = makeRequest(endpoint, applicationId);

            status.done = (long) Math.floor((double) parseResponse.apply(response));
            result.completed = !response.get("completed_at").isEmptyOrNull();

            // Change interval randomly
            if (status.done > status.total * 0.8) {
                interval = this.randomHelper.uniform(30, 45);
            } else {
                interval = this.randomHelper.uniform(55, 70);
            }

            sleepHelper.sleep(interval);
        }

        if (!result.completed) {
            this.makeRequest(endpoint, applicationId);
        }

        finish();
        return this.result;
    }

    public QuestCompletionStatus currentStatus() {
        return this.status;
    }

    private JsonObject makeRequest(String endpoint, Long applicationId) {
        Map<String, Object> body = new HashMap<>();
        body.put("terminal", false);
        body.put("application_id", applicationId);

        return this.session.post(endpoint, body);
    }

    private void finish() {
        this.result.completed = this.status.done >= this.status.total;
        if (this.result.completed) {
            this.status.done = this.status.total;
        }
    }

    private void init(Quest quest) {
        this.status.type = this.questHelper.getQuestType();
        QuestProgress progress = this.questHelper.getQuestProgress();

        this.status.total = progress.total;
        this.status.done = progress.done;
        this.result.completed = false;
    }
}
