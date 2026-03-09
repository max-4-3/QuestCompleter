package org.maxim.extensions.completer.session.response;

public interface JsonResponse {
    public JsonResponse get(String key);
    public <T> T convert();
    public boolean isEmptyOrNull();
}
