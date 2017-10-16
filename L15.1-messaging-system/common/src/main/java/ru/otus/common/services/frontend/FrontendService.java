package ru.otus.common.services.frontend;

import ru.otus.common.messagesystem.Addressee;
import ru.otus.common.messagesystem.messages.FrontendConnectionId;

/**
 * Интерфейс фронтенда
 */
public interface FrontendService extends Addressee {
    /**
     * Метод для отправки произвольного сообщения клиенту
     * @param connectionId Идентификатор клиента на фронтэнде
     * @param message Сообщение в произвольном формате
     */
    void sendToClient(FrontendConnectionId connectionId, String message);
}
