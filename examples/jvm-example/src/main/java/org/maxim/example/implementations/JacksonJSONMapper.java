package org.maxim.example.implementations;

import org.maxim.extensions.completer.session.response.JsonConversionMapper;

import tools.jackson.databind.JsonNode;

public class JacksonJSONMapper implements JsonConversionMapper {
    private final JsonNode element;

    public JacksonJSONMapper(JsonNode element) {
        this.element = element;
    }

    @Override
    public String text() {
        return element.asString();
    }

    @Override
    public long i64() {
        return element.asLong();
    }

    @Override
    public int i32() {
        return element.asInt();
    }

    @Override
    public boolean bool() {
        return element.asBoolean();
    }

    @Override
    public double d64() {
        return element.asDouble();
    }

    @Override
    public <T> T map(Class<T> type) {
        return JacksonJson.MAPPER.convertValue(this.element, type);
    }
}
