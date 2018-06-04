package ru.perm.kefir.bbcode;

import java.io.InputStream;
import java.util.UUID;

/**
 * Util class
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
final class Util {
    private Util() {
    }

    /**
     * Open the resource stream for named resource.
     * Stream must be closed by user after usage.
     *
     * @param resourceName resource name
     * @return input stream
     */
    static InputStream openResourceStream(String resourceName) {
        InputStream stream = null;
        ClassLoader classLoader = Util.class.getClassLoader();
        if (classLoader != null) {
            stream = classLoader.getResourceAsStream(resourceName);
        }

        if (stream == null) {
            stream = ClassLoader.getSystemResourceAsStream(resourceName);
        }
        return stream;
    }

    static String generateRandomName() {
        return UUID.randomUUID().toString();
    }
}
