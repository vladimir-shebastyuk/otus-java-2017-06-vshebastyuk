package ru.otus.frontend.web.websocket.messages;

/**
 * Ответ с ошибкой запроса
 */
public class WsErrorResponse extends WsMessage {
    private final static String ERROR_MESSAGE_TYPE ="error";

    public enum Reason{
        BAD_FORMAT(400),
        UNKNOWN_MESSAGE_TYPE(405),
        UNKNOWN_COMMAND(405),
        GATEWAY_ERROR(500);

        private final int code;
        Reason(int code) { this.code = code; }
        public int getCode() { return code; }
    }

    public WsErrorResponse() {
        this.setReplyTo(-1);
        this.setOk(false);
        this.setType(ERROR_MESSAGE_TYPE);
        this.setReason(Reason.BAD_FORMAT);
    }

    public WsErrorResponse(Reason reason){
        this();
        this.setReason(reason);
    }

    public WsErrorResponse(WsMessage badRequest, Reason reason){
        this();
        this.setReplyTo(badRequest.getId());
        this.setReason(reason);
    }

    public WsErrorResponse(WsMessage badRequest, Reason reason, String message){
        this();
        this.setReplyTo(badRequest.getId());
        this.setReason(reason);
        this.getData().put("msg",message);
    }

    public void setReason(Reason reason){
        this.getData().put("msg",reason.toString());
        this.getData().put("code",reason.getCode());
    }
}
