package org.maxim.extensions.completer.session.response;

public interface JsonResponse {
    JsonResponse get(String key);

    JsonConversionMapper as();

    boolean isNotEmpty();
}
