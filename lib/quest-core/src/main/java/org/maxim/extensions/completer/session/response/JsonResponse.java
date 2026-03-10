package org.maxim.extensions.completer.session.response;

public interface JsonResponse {
    public JsonResponse get(String key);

    public JsonConversionMapper as();

    public boolean isEmptyOrNull();
}
