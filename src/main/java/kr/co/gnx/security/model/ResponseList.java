package kr.co.gnx.security.model;

public class ResponseList<T> extends ResponseDefault{

	private T list;

	public T getList() {
		return list;
	}

	public void setList(T list) {
		this.list = list;
	}


}
