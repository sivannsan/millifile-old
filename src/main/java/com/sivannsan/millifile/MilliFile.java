package com.sivannsan.millifile;

import com.sivannsan.foundation.annotation.Nonnull;

import java.io.File;

/**
 * The instance of this class does not in-memory work with the data.
 * It always communicates with the storage.
 */
public interface MilliFile {
    /**
     * @return  null if this MilliDatabase is root
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
     * Deletion is not available for MilliNone
     */
    void delete();
}
