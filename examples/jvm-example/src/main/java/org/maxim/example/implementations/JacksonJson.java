package org.maxim.example.implementations;

import org.maxim.extensions.completer.session.response.JsonResponse;

import tools.jackson.core.json.JsonReadFeature;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;

public class JacksonJson implements JsonResponse {
    // Jackson mapper that is pretty good
    // it can read: some_field as someField (useful in naming property in classes)
    // it only access public fields
    // it doesn't take account of 'getSomething' methods
    public static final ObjectMapper MAPPER = JsonMapper.builder()
            .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .enable(JsonReadFeature.ALLOW_SINGLE_QUOTES)
            .enable(JsonReadFeature.ALLOW_TRAILING_COMMA)
            .changeDefaultVisibility(
                    vc -> vc.withVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.PUBLIC_ONLY)
                            .withVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE)
                            .withVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE))
            .build();

    private final JsonNode node;

    public JacksonJson(JsonNode value) {
        this.node = value;
    }

    /* Implementations */
    public JacksonJson get(String key) {
        if (this.node == null)
            return new JacksonJson(null);
        JsonNode child = this.node.get(key);
        return new JacksonJson(child);
    }

    @SuppressWarnings("unchecked")
    public <T> T convert() {
        try {
            return MAPPER.treeToValue(node, (Class<T>) Object.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isEmptyOrNull() {
        return node == null || node.isNull() || node.isEmpty();
    }
    /* Implementations ends */

    public static JacksonJson parse(String json) {
        try {
            return new JacksonJson(MAPPER.readTree(json));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String parseObject(Object object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public JsonNode raw() {
        return this.node;
    }
}
