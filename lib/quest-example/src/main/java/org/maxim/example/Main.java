package org.maxim.example;

import java.util.Map;
import java.time.Duration;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;
import java.util.Base64.Encoder;

import org.maxim.core.helper.SleepHelper;
import org.maxim.core.helper.StringHelper;
import org.maxim.core.models.quest.Quest;

import org.maxim.example.implementations.HeaderProvider;
import org.maxim.example.implementations.DefaultSleepHelper;
import org.maxim.example.implementations.DefaultStringHelper;
import org.maxim.example.implementations.HttpSession;
import org.maxim.example.implementations.RotatingHeaderProvider;
import org.maxim.example.implementations.JacksonJson;

public class Main {
    public static final Encoder base64 = Base64.getEncoder();
    public static final SleepHelper sleeper = new DefaultSleepHelper();
    public static final StringHelper stringer = new DefaultStringHelper();
    public static final HeaderProvider headerProvider = new RotatingHeaderProvider(
            Duration.ofMinutes(30), Main::getHeaders);

    public static void main(String[] args) {
        HttpSession session = new HttpSession("https://discord.com/api/v9", headerProvider);

        // Prints all quests
        for (Quest quest : session.getQuests()) {
            System.out.println(
                    stringer.format(
                            "[%d] (%s) Quest: %s (%s)",
                            quest.id.toLong(), quest.getQuestType().name(),
                            quest.getQuestName(quest.getQuestType()), quest.getQuestRewards()));
        }
    }

    public static Map<String, String> getHeaders() {
        Map<String, String> map = new HashMap<>();
        Map<String, Object> superProp = new HashMap<>();

        String TOKEN = getToken();
        String CLIENT_LAUNCH_ID = genId();
        String LAUNCH_SIGNATURE = genId();
        String CLIENT_HEARTBEAT_SESSION_ID = genId();
        String USERAGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) discord/0.0.78 Chrome/118.0.5993.159 Electron/26.2.1 Safari/537.36";

        superProp.put("os", "Linux");
        superProp.put("browser", "Discord Client");
        superProp.put("release_channel", "stable");
        superProp.put("client_version", "0.0.118");
        superProp.put("os_version", "6.12.58-1-lts");
        superProp.put("os_arch", "x64");
        superProp.put("app_arch", "x64");
        superProp.put("system_locale", "en-US");
        superProp.put("has_client_mods", false);
        superProp.put("client_launch_id", CLIENT_LAUNCH_ID);
        superProp.put("browser_user_agent", USERAGENT);
        superProp.put("browser_version", "37.6.0");
        superProp.put("window_manager", "KDE,unknown");
        superProp.put("distro", "Arch Linux");
        superProp.put("runtime_environment", "native");
        superProp.put("display_server", "wayland");
        superProp.put("client_build_number", 479793);
        superProp.put("native_build_number", null);
        superProp.put("client_event_source", null);
        superProp.put("launch_signature", LAUNCH_SIGNATURE);
        superProp.put("client_heartbeat_session_id", CLIENT_HEARTBEAT_SESSION_ID);
        superProp.put("client_app_state", "focused");

        map.put("Referrer", "https://discord.com/quest-home");
        map.put("Authorization", TOKEN);
        map.put("User-Agent", USERAGENT);
        map.put("X-Discord-Locale", "all");
        map.put("X-Super-Properties", base64Encode(JacksonJson.parseObject(superProp)));

        return map;
    }

    public static String base64Encode(String data) {
        return base64.encodeToString(data.getBytes());
    }

    public static String genId() {
        return UUID.randomUUID().toString();
    }

    public static String getToken() {
        return Secrets.getToken();
    }
}
