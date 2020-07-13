package cs545_project.online_market.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No address found")
public class AddressNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 3935230281455340039L;

	private long id;

	public AddressNotFoundException(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

}
