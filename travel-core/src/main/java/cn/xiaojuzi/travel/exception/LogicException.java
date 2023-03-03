package cn.xiaojuzi.travel.exception;

/**
 *  自定义逻辑异常
 *  author:xiaojuzi
 */
public class LogicException extends RuntimeException {
    public LogicException(String message) {
        super(message);
    }
}
