package org.maxim.extensions.completer;

import java.util.HashMap;
import java.util.Map;

import org.maxim.core.helper.RandomHelper;
import org.maxim.core.helper.SleepHelper;
import org.maxim.core.helper.StringHelper;
import org.maxim.core.models.quest.Quest;
import org.maxim.core.models.quest.datatypes.QuestProgress;
import org.maxim.extensions.completer.result.CompletionResult;
import org.maxim.extensions.completer.session.Session;
import org.maxim.extensions.completer.session.response.JsonResponse;
import org.maxim.extensions.completer.status.CompletionStatus;
import org.maxim.extensions.helper.QuestHelper;

public class PlayQuestCompleter implements Completer {
    private final StringHelper stringer;
    private final SleepHelper sleeper;
    private final RandomHelper rand;
    private final Session session;

    private final Quest quest;
    private final CompletionResult result;
    private final CompletionStatus status;

    public PlayQuestCompleter(
            Quest quest,
            SleepHelper sleeper,
            StringHelper stringer,
            RandomHelper rand,
            Session session) {
        this.rand = rand;
        this.stringer = stringer;
        this.sleeper = sleeper;
        this.session = session;
        this.quest = quest;

        this.result = new CompletionResult();
        this.status = new CompletionStatus();

        this.initQuest();
    }

    public synchronized CompletionResult completeQuest() {
        Long applicationId = quest.Id;
        String endpoint = stringer.format("quests/%d/heartbeat", applicationId);

        long interval; // [55, 70]

        while (!status.completed && status.done < status.total) {
            JsonResponse response = makeRequest(endpoint, applicationId);

            status.done = (long) Math.floor(parseResponse(response));
            status.completed = !response.get("completed_at").isEmptyOrNull();

            // Change interval randomly
            if (status.done > status.total * 0.8) {
                interval = this.rand.uniform(30, 45);
            } else {
                interval = this.rand.uniform(55, 70);
            }

            sleeper.sleep(interval);
        }

        if (!status.completed) {
            this.makeRequest(endpoint, applicationId);
        }

        this.finishWork();
        return this.result;
    }

    public synchronized CompletionStatus currentStatus() {
        return this.status;
    }

    private void finishWork() {
        result.completed = status.completed;
    }

    private void initQuest() {
        QuestProgress progress = QuestHelper.getQuestProgress(this.quest);
        this.status.total = progress.total;
        this.status.done = progress.done;
        this.status.completed = false;

        this.result.completed = this.status.completed;
    }

    private double parseResponse(JsonResponse response) {
        if (this.quest.config != null && quest.config.configVersion == 1) {
            return response.get("streamProgressSeconds").as().d64();
        } else {
            return response.get("progress").get("PLAY_ON_DESKTOP").get("value").as().d64();
        }
    }

    private JsonResponse makeRequest(String endpoint, Long applicationId) {
        Map<String, Object> body = new HashMap<>();
        body.put("terminal", false);
        body.put("application_id", applicationId);

        return this.session.post(endpoint, body);
    }
}
