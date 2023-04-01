package com.sivannsan.millifile;

import com.sivannsan.foundation.annotation.Nonnull;

import java.util.List;

/**
 * The instance of this class does not in-memory work with the data.
 * It always communicates with the storage.
 */
public interface MilliCollection extends MilliFile, Iterable<MilliFile> {
    /**
     * @param name  with .mll extension for MilliDocument; without .mll extension for MilliCollection
     */
    void createIfNotExists(@Nonnull String name);

    /**
     * @param name  with .mll extension for MilliDocument; without .mll extension for MilliCollection
     * @return  MilliNone if the file does not exist
     */
    @Nonnull
    MilliFile get(@Nonnull String name);
}
