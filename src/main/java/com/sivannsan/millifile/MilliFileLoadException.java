package com.sivannsan.millifile;

import com.sivannsan.foundation.annotation.Nonnull;

public class MilliFileLoadException extends RuntimeException {
    public MilliFileLoadException() {
        super();
    }

    public MilliFileLoadException(@Nonnull String message) {
        super(message);
    }
}
