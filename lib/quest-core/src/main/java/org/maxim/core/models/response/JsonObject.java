package org.maxim.core.models.response;

public interface JsonObject {
    // Get the objcets
    public JsonObject get(String key);

    // Convert from this to the required type
    public <T> T convert();

    // Check whether the curr obj is null or empty
    public boolean isEmptyOrNull();
}
