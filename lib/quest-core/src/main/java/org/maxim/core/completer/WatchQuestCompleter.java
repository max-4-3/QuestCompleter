package org.maxim.core.completer;

import java.util.HashMap;
import java.util.Map;

import org.maxim.core.helper.RandomHelper;
import org.maxim.core.helper.SleepHelper;
import org.maxim.core.helper.StringHelper;
import org.maxim.core.helper.TimeHelper;
import org.maxim.core.models.completion.QuestCompletionResult;
import org.maxim.core.models.completion.QuestCompletionStatus;
import org.maxim.core.models.quest.Quest;
import org.maxim.core.models.quest.datatypes.QuestProgress;
import org.maxim.core.models.response.JsonObject;
import org.maxim.core.session.Session;
import org.maxim.extensions.helper.QuestHelper;

public class WatchQuestCompleter implements Completer {
    private final StringHelper stringHelper;
    private final TimeHelper timeHelper;
    private final SleepHelper sleepHelper;
    private final RandomHelper randomHelper;
    private final Session session;

    private final QuestCompletionResult result;
    private final QuestCompletionStatus status;
    private QuestHelper questHelper;

    public WatchQuestCompleter(
            SleepHelper sleepHelper,
            StringHelper stringHelper,
            RandomHelper randomHelper,
            TimeHelper timeHelper,
            Session session) {

        this.randomHelper = randomHelper;
        this.timeHelper = timeHelper;
        this.stringHelper = stringHelper;
        this.sleepHelper = sleepHelper;
        this.session = session;

        this.result = new QuestCompletionResult();
        this.status = new QuestCompletionStatus();
    }

    public QuestCompletionResult completeQuest(Quest quest) {
        this.questHelper = new QuestHelper(quest);
        this.init(quest);

        long maxFuture = 10, speed = 7, interval = 1;
        long maxAllowed, diffrence, next;
        String endpoint = stringHelper.format("quests/%d/video-progress", quest.Id);

        while (!result.completed && status.done < status.total) {
            maxAllowed = timeHelper.timeDiffNow(quest.userStatus.enrolledAt).plusSeconds(maxFuture).getSeconds();
            diffrence = maxAllowed - status.done;
            next = status.done + speed;

            if (diffrence >= speed) {
                // Use random insted of `1`
                JsonObject response = this.makeRequest(endpoint,
                        Math.min(status.total, (double) next + this.randomHelper.random()));

                result.completed = !response.get("completed_at").isEmptyOrNull();
                status.done = Math.min(status.total, next);
            }

            sleepHelper.sleep(interval);
        }

        if (!result.completed) {
            this.makeRequest(endpoint, this.status.total);
        }

        finish();
        return this.result;
    }

    public QuestCompletionStatus currentStatus() {
        return this.status;
    }

    private JsonObject makeRequest(String endpoint, double timeStamp) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", timeStamp);
        return this.session.post(endpoint, body);
    }

    private void finish() {
        this.status.completed = true;
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
        this.status.completed = false;
        this.result.completed = false;
    }
}
