package pl.rzrz.hibernate.validator;

public interface FieldMap<T> {

    Object field(T obj);

    Object confirmedField(T obj);

    String getConfirmationFieldName();
}
