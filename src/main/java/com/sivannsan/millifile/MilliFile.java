package com.sivannsan.millifile;

import com.sivannsan.foundation.annotation.Nonnull;

import java.io.File;

public interface MilliFile {
    /**
     * @return  null if this MilliFile is root
     */
    MilliCollection getParent();

    @Nonnull
    File getFile();

    boolean isMilliNone();

    boolean isMilliDocument();

    boolean isMilliCollection();

    @Nonnull
    MilliDocument asMilliDocument() throws ClassCastException;

    @Nonnull
    MilliCollection asMilliCollection() throws ClassCastException;

    /**
     * Deletion is not available (ignored) for MilliNone
     */
    void delete();
}
