package ru.otus.frontend.web.websocket.messages;

import com.google.gson.Gson;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 *
 */
public class WsMessageTest {
    @Test
    public void testToString() throws Exception {
        WsMessage message = new WsMessage()
                .setId(1L)
                .setType("command")
                .setData(
                        new FluentMap()
                        .add("name","GetCacheStats")
                        .add("arg1","someArg")

                );

        assertEquals(
                "{\"id\":1,\"type\":\"command\",\"data\":{\"arg1\":\"someArg\",\"name\":\"GetCacheStats\"}}",
                message.toString()
        );
    }

    @Test
    public void testFromJson() throws Exception {
        String json = "{\"id\":1,\"type\":\"command\",\"data\":{\"name\":\"GetCacheStats\",\"arg1\":\"someArg\"}}";

        WsMessage message = WsMessage.fromJson(json);

        assertEquals(new Long(1L),message.getId());
        assertEquals("command",message.getType());

        assertEquals(
                new FluentMap()
                        .add("name","GetCacheStats")
                        .add("arg1","someArg")
                ,
                message.getData()
        );
    }
}