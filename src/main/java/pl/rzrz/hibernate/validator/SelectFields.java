package pl.rzrz.hibernate.validator;

public interface SelectFields<T> {

    Object field(T obj);

    Object confirmedField(T obj);

    String getConfirmationFieldName();
}
