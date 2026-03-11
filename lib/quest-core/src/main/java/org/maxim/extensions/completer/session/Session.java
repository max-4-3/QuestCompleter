package org.maxim.extensions.completer.session;

import org.maxim.extensions.completer.session.response.JsonResponse;

public interface Session {
    JsonResponse get(String path);
    JsonResponse post(String path, Object body);
}
