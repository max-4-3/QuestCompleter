package org.maxim.core.session;

import org.maxim.core.models.response.JsonObject;

public interface Session {
    /* Interface for interacting with the api */ 
    public JsonObject get(String path);
    public JsonObject post(String path, Object body);
}
