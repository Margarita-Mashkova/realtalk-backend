package realtalk.bot;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class SimpleBot {
    final String[] COMMON_PHRASES = {
            "Нет ничего ценнее слов, сказанных к месту и ко времени.",
            "Порой молчание может сказать больше, нежели уйма слов.",
            "Перед тем как писать/говорить всегда лучше подумать.",
            "Вежливая и грамотная речь говорит о величии души.",
            "Приятно когда текст без орфографических ошибок.",
            "Многословие есть признак неупорядоченного ума.",
            "Слова могут ранить, но могут и исцелять.",
            "Записывая слова, мы удваиваем их силу.",
            "Кто ясно мыслит, тот ясно излагает.",
            "Боюсь Вы что-то не договариваете."};
    final String[] ELUSIVE_ANSWERS = {
            "Вопрос непростой, прошу тайм-аут на раздумья.",
            "Не уверен, что располагаю такой информацией.",
            "Может лучше поговорим о чём-то другом?",
            "Простите, но это очень личный вопрос.",
            "Не уверен, что Вам понравится ответ.",
            "Поверьте, я сам хотел бы это знать.",
            "Вы действительно хотите это знать?",
            "Уверен, Вы уже догадались сами.",
            "Зачем Вам такая информация?",
            "Давайте сохраним интригу?"};
    final Map<String, String> PATTERNS_FOR_ANALYSIS = new HashMap<String, String>() {{
        // hello
        put("хай", "hello");
        put("привет", "hello");
        put("здорово", "hello");
        put("здравствуй", "hello");
        // who
        put("кто\\s.*ты", "who");
        put("ты\\s.*кто", "who");
        // name
        put("как\\s.*зовут", "name");
        put("как\\s.*имя", "name");
        put("есть\\s.*имя", "name");
        put("какое\\s.*имя", "name");
        // howareyou
        put("как\\s.*дела", "howareyou");
        put("как\\s.*жизнь", "howareyou");
        // whatdoyoudoing
        put("зачем\\s.*тут", "whatdoyoudoing");
        put("зачем\\s.*здесь", "whatdoyoudoing");
        put("что\\s.*делаешь", "whatdoyoudoing");
        put("чем\\s.*занимаешься", "whatdoyoudoing");
        // whatdoyoulike
        put("что\\s.*нравится", "whatdoyoulike");
        put("что\\s.*любишь", "whatdoyoulike");
        // iamfeelling
        put("кажется", "iamfeelling");
        put("чувствую", "iamfeelling");
        put("испытываю", "iamfeelling");
        // yes
        put("^да", "yes");
        put("согласен", "yes");
        // whattime
        put("который\\s.*час", "whattime");
        put("сколько\\s.*время", "whattime");
        // bye
        put("прощай", "bye");
        put("увидимся", "bye");
        put("до\\s.*свидания", "bye");

        // best
        put("лучшая\\s.*сеть", "best");

    }};
    final Map<String, String> ANSWERS_BY_PATTERNS = new HashMap<String, String>() {{
        put("hello", "Здравствуйте, рад Вас видеть.");
        put("who", "Я обычный чат-бот.");
        put("name", "Зовите меня Чаттер :)");
        put("howareyou", "Спасибо, что интересуетесь. У меня всё хорошо.");
        put("whatdoyoudoing", "Я пробую общаться с людьми.");
        put("whatdoyoulike", "Мне нравиться думать что я не просто программа.");
        put("iamfeelling", "Как давно это началось? Расскажите чуть подробнее.");
        put("yes", "Согласие есть продукт при полном непротивлении сторон.");
        put("bye", "До свидания. Надеюсь, ещё увидимся.");
        put("best", "Конечно realtalk!");
    }};
    Pattern pattern; // for regexp
    Random random; // for random answers
    Date date; // for date and time

    public SimpleBot() {
        random = new Random();
        date = new Date();
    }

    public String sayInReturn(String msg, boolean ai) {
        String say = (msg.trim().endsWith("?"))?
                ELUSIVE_ANSWERS[random.nextInt(ELUSIVE_ANSWERS.length)]:
                COMMON_PHRASES[random.nextInt(COMMON_PHRASES.length)];
        if (ai) {
            String message =
                    String.join(" ", msg.toLowerCase().split("[ {,|.}?]+"));
            for (Map.Entry<String, String> o : PATTERNS_FOR_ANALYSIS.entrySet()) {
                pattern = Pattern.compile(o.getKey());
                if (pattern.matcher(message).find())
                    if (o.getValue().equals("whattime")) return date.toString();
                    else return ANSWERS_BY_PATTERNS.get(o.getValue());
            }
        }
        String finalSay = say;
        if(Arrays.stream(ELUSIVE_ANSWERS).filter(ans->ans.equals(finalSay)).collect(Collectors.toSet()).size()!=0){
            String search = googleRequest(msg);
            if(!search.equals("")){
                say = search;
            }
        }
        return say;
    }

    public String googleRequest(String query){
        try {
            // Set the search query
            String api_key = "AIzaSyDY69QCX9S6kYojR8MD9w6X_wAgKwwKo2k";
            query = query.replaceAll("\\s+", "+").toLowerCase();

            // Construct the URL for the Google Search API
            String urlString = "https://www.googleapis.com/customsearch/v1?key="+api_key+"&cx=440ee895be8394ede" +"&q="+query;
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(urlString))
                    .header("content-type", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = null;
            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());

            } catch (IOException | InterruptedException e) {
                System.out.println("err");
            }

            JsonObject json =  new Gson().fromJson(String.valueOf(response.body()), JsonObject.class).getAsJsonArray("items").get(0).getAsJsonObject();
            // Print the response from the server
            String link = json.get("formattedUrl").toString();
            link = link.substring(1, link.length()-1);
            String snippet = json.get("snippet").toString();
            snippet = snippet.substring(1, snippet.length()-1);
            return snippet+"\n"+link;

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }
}

