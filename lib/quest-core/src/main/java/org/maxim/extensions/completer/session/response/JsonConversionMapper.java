package org.maxim.extensions.completer.session.response;

public interface JsonConversionMapper {
    String text();

    long i64();
    int i32();
    boolean bool();
    double d64();

    <T> T map(Class<T> type);
}
