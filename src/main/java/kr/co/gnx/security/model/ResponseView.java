package kr.co.gnx.security.model;


public abstract class ResponseView<T> extends ResponseDefault{

	private T view;
	
	public T getView() {
		return view;
	}

	public void setView(T view) {
		this.view = view;
	}

}
