package org.maxim.extensions.completer;

import java.util.HashMap;
import java.util.Map;

import org.maxim.extensions.helper.RandomHelper;
import org.maxim.extensions.helper.SleepHelper;
import org.maxim.extensions.helper.StringHelper;
import org.maxim.extensions.helper.TimeHelper;
import org.maxim.core.models.quest.Quest;
import org.maxim.core.models.quest.datatypes.QuestProgress;
import org.maxim.extensions.completer.result.CompletionResult;
import org.maxim.extensions.completer.session.Session;
import org.maxim.extensions.completer.session.response.JsonResponse;
import org.maxim.extensions.completer.status.CompletionStatus;
import org.maxim.extensions.helper.QuestHelper;

public class WatchQuestCompleter implements Completer {
    private final StringHelper stringer;
    private final TimeHelper timer;
    private final SleepHelper sleeper;
    private final RandomHelper rand;
    private final Session session;

    private final Quest quest;
    private final CompletionResult result;
    private final CompletionStatus status;

    public WatchQuestCompleter(
            Quest quest,
            TimeHelper timer,
            SleepHelper sleeper,
            StringHelper stringer,
            RandomHelper rand,
            Session session) {
        this.rand = rand;
        this.stringer = stringer;
        this.timer = timer;
        this.sleeper = sleeper;
        this.session = session;
        this.quest = quest;

        this.result = new CompletionResult();
        this.status = new CompletionStatus();

        this.initQuest();
    }

    public synchronized CompletionResult completeQuest() {
        String endpoint = stringer.format("quests/%d/video-progress", quest.Id);

        long maxFuture = 10, speed = 7, interval = 1;
        long maxAllowed, difference, next;

        while (!status.completed && status.done < status.total) {
            maxAllowed = timer.timeDiffNow(quest.userStatus.enrolledAt).plusSeconds(maxFuture).getSeconds();
            difference = maxAllowed - status.done;
            next = status.done + speed;

            if (difference >= speed) {
                JsonResponse response = this.makeRequest(endpoint,
                        Math.min(status.total, (double) next + this.rand.random()));

                status.completed = response.get("completed_at").isNotEmpty();
                status.done = Math.min(status.total, next);
            }

            sleeper.sleep(interval);
        }

        if (!status.completed) {
            this.makeRequest(endpoint, this.status.total);
        }

        finishWork();
        return this.result;
    }

    public synchronized CompletionStatus currentStatus() {
        return this.status;
    }

    private JsonResponse makeRequest(String endpoint, double timeStamp) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", timeStamp);
        return this.session.post(endpoint, body);
    }

    private void finishWork() {
        this.result.completed = this.status.completed;
    }

    private void initQuest() {
        QuestProgress progress = QuestHelper.getQuestProgress(quest);

        this.status.total = progress.total;
        this.status.done = progress.done;
        this.status.completed = false;
        this.result.completed = this.status.completed;
    }
}
