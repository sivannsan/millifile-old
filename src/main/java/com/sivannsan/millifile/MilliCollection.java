package com.sivannsan.millifile;

import com.sivannsan.foundation.annotation.Nonnull;

import java.util.List;

/**
 * The instance of this class does not in-memory work with the data.
 * It always communicates with the storage.
 */
public interface MilliCollection extends MilliFile, Iterable<MilliFile> {
    /**
     * Get all MilliFiles from files that are compatible with MilliFile
     */
    @Nonnull
    List<MilliFile> getFiles();

    /**
     * Get and automatically create if the MilliDocument does not exist
     */
    @Nonnull
    MilliDocument getDocument(@Nonnull String name);

    /**
     * Get and automatically create if the MilliCollection does not exist
     */
    @Nonnull
    MilliCollection getCollection(@Nonnull String name);
}
