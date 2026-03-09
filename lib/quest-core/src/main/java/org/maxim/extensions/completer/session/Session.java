package org.maxim.extensions.completer.session;

import org.maxim.extensions.completer.session.response.JsonResponse;

public interface Session {
    public JsonResponse get(String path);
    public JsonResponse post(String path, Object body);
}
