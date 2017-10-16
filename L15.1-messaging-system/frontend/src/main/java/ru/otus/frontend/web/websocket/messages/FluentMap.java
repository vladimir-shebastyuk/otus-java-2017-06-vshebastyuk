package ru.otus.frontend.web.websocket.messages;

import java.util.HashMap;

/**
 * Карта с гибкой возможнотью добавения элементов для удобного формирования произвольного ответа на фронтэнд
 */
public class FluentMap extends HashMap<String,Object>{
   public FluentMap add(String key, Object value){
       this.put(key,value);
       return this;
   }
}
