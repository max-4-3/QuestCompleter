package org.maxim.example.implementations;

import java.util.Locale;

import org.maxim.extensions.helper.StringHelper;

public class DefaultStringHelper implements StringHelper {

    public String format(String format, Object... args) {
        return String.format(Locale.getDefault(), format, args);
    }
}
