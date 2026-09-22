package kr.co.gnx.exception;

public interface IMessageCodeable {
	String getMessageCode();
	String getMessage(String... args);
}