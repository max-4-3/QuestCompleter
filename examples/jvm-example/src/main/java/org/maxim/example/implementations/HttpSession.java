package org.maxim.example.implementations;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.maxim.core.helper.StringHelper;
import org.maxim.core.models.quest.Quest;
import org.maxim.extensions.completer.session.Session;

import tools.jackson.databind.JsonNode;

public class HttpSession implements Session {
    private final StringHelper stringHelper = new DefaultStringHelper();
    private final HeaderProvider headers;
    private final HttpClient client;
    private final URI baseUrl;

    public HttpSession(String baseUrl, HeaderProvider headers) {
        this.baseUrl = URI.create(baseUrl);
        this.headers = headers;
        this.client = HttpClient.newHttpClient();
    }

    public JacksonJson get(String path) {
        try {
            HttpRequest req = baseRequest(path)
                    .GET()
                    .build();

            HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
            String response = resp.body();

            return JacksonJson.parse(response);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public JacksonJson post(String path, Object body) {
        try {
            String json = JacksonJson.parseObject(body);

            HttpRequest req = baseRequest(path)
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());

            return JacksonJson.parse(resp.body());

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpRequest.Builder baseRequest(String path) {
        HttpRequest.Builder builder = HttpRequest
                .newBuilder()
                .uri(this.baseUrl.resolve(path));

        if (headers != null) {
            headers.headers().forEach(builder::headers);
        }

        return builder;
    }

    public List<Quest> getQuests() {
        try {
            JacksonJson response = this.get("quests/@me");

            JacksonJson blocked = response.get("quest_enrollment_blocked_until");
            if (!blocked.isEmptyOrNull()) {
                throw new RuntimeException(
                        stringHelper.format("ERROR: Blocked for quests: %s",
                                blocked.as().text()));
            }

            List<Quest> quests = new ArrayList<>();
            JsonNode questNode = response.get("quests").raw();

            if (questNode != null && questNode.isArray()) {
                for (JsonNode o : questNode) {
                    quests.add(JacksonJson.MAPPER.treeToValue(o, Quest.class));
                }
            } else {
                throw new RuntimeException(
                        stringHelper.format("ERROR: Invalid response:\n%s", response.raw()));
            }

            return quests;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
