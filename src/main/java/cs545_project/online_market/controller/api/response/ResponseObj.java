package cs545_project.online_market.controller.api.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author knguyen93
 */
@Data
public class ResponseObj<T> {
    private T data;
    private String message;
    private List<String> errors = new ArrayList<>();

    public ResponseObj data(T data) {
        this.data = data;
        return this;
    }

    public ResponseObj message(String message) {
        this.message = message;
        return this;
    }

    public ResponseObj error(String error) {
        this.errors.add(error);
        return this;
    }

    public ResponseObj errors(List<String> errors) {
        this.errors.addAll(errors);
        return this;
    }
}
