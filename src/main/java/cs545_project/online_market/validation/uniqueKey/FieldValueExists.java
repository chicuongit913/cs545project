package cs545_project.online_market.validation.uniqueKey;

public interface FieldValueExists {
    boolean fieldValueExists(Object value, String fieldName) throws UnsupportedOperationException;
}