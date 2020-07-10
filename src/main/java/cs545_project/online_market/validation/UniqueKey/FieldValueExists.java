package cs545_project.online_market.validation.UniqueKey;

public interface FieldValueExists {
    boolean fieldValueExists(Object value, String fieldName) throws UnsupportedOperationException;
}