package com.sivannsan.millifile;

import com.sivannsan.foundation.annotation.Nonnull;

import java.util.List;

/**
 * The instance of this class does not in-memory work with the data.
 * It always communicates with the storage.
 */
public interface MilliCollection extends MilliFile, Iterable<MilliFile> {
    /**
     * <p>This will also override the incorrect type file.
     * <p>A file that is considered as incorrect type means:
     * <p>- A file with .mll extension but existing as a directory
     * <p>- A file without .mll extension but existing as a file
     *
     * @param name  with .mll extension for MilliDocument; without .mll extension for MilliCollection
     */
    void createIfNotExists(@Nonnull String name);

    /**
     * <p>A file that is considered as incorrect type means:
     * <p>- A file with .mll extension but existing as a directory
     * <p>- A file without .mll extension but existing as a file
     *
     * @param name  with .mll extension for MilliDocument; without .mll extension for MilliCollection
     * @return  MilliNone if the file does not exist or incorrect type
     */
    @Nonnull
    MilliFile get(@Nonnull String name);
}
