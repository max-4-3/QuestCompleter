package org.maxim.extensions.completer;

import org.maxim.extensions.completer.result.CompletionResult;
import org.maxim.extensions.completer.status.CompletionStatus;

// This could be used with factory pattern
public interface Completer {
    // This method should be long-running task to complete the Quest
    // Retrieving quest is up to the implementor
    CompletionResult completeQuest();

    // This should get the current status of the progress
    CompletionStatus currentStatus();
}
