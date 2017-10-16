package ru.otus.frontend.web.websocket.messages;

import com.google.gson.*;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.lang.reflect.Type;

/**
 * Сообщение для обмена по WebSocket
 * Пример ообщения для вызова команды GetCacheStats
 * <code>
 * {
 *     id: 1,
 *     type: "command"
 *     data: {
 *         name: "GetCacheStats"
 *         params: {}
 *     }
 * }
 * </code><br/>
 * Пример ответа:
 * <code>
 * {
 *     "id": 0,
 *     "type": "result",
 *     "ok": false,
 *     "replyTo": 1,
 *     "data": {
 *         "hit": 203,
 *         "miss": 255,
 *         "ratio": 44.32314410480349
 *     }
 * }
 * </code>
 */
public class WsMessage {
    public enum Type{
        Command("command"),
        Result("result");

        private String text;
        Type(String text){ this.text = text;};

        @Override
        public String toString() {
            return text;
        }
    }

    protected Long id = null;
    protected String type;
    protected Boolean ok;
    protected Long replyTo = null;
    protected FluentMap data = new FluentMap();

    public Long getId() {
        return id;
    }

    public WsMessage setId(Long id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return type;
    }

    public WsMessage setType(String type) {
        this.type = type;
        return this;
    }

    public WsMessage setType(Type type){
        this.type = type.toString();
        return this;
    }

    public Boolean isOk() {
        return ok;
    }

    public WsMessage setOk(boolean ok) {
        this.ok = ok;
        return this;
    }

    public Long getReplyTo() {
        return replyTo;
    }

    public WsMessage setReplyTo(long replyTo) {
        this.replyTo = replyTo;
        return this;
    }

    public FluentMap getData() {
        return data;
    }

    public WsMessage setData(FluentMap data) {
        this.data = data;
        return this;
    }

    private class WsMessageSerializer implements JsonSerializer<WsMessage> {
        @Override
        public JsonElement serialize(WsMessage message, java.lang.reflect.Type type, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();

            if(message.id != null) jsonObject.addProperty("id", message.id);
            if(message.ok != null) jsonObject.addProperty("ok", message.ok);
            jsonObject.addProperty("type", message.type);
            if(message.replyTo != null) jsonObject.addProperty("replyTo",message.replyTo);
            jsonObject.add("data",context.serialize(message.data));

            return jsonObject;
        }
    }

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(WsMessage.class,new WsMessageSerializer());

        return gsonBuilder.create().toJson(this);

        //return (new Gson()).toJson(this);
    }

    public static WsMessage fromJson(String json) throws InvalidMessageException {
        try {
            return (new Gson()).fromJson(json, WsMessage.class);
        }catch (JsonSyntaxException e){
            throw new InvalidMessageException("Error on type parsing",e);
        }
    }
}
