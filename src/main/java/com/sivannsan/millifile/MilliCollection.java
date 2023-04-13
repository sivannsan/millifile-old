package com.sivannsan.millifile;

import com.sivannsan.foundation.annotation.Nonnull;

import java.util.List;

@SuppressWarnings("unused")
public interface MilliCollection extends MilliFile {
    @Nonnull
    List<MilliFile> list();

    /**
     * @param name  empty string will return a MilliNone
     */
    @Nonnull
    MilliFile get(@Nonnull String name);

    /**
     * @param name  the conventional name for MilliDocument ends with the .mll extension; empty string will be ignored the creation
     * @param type  MilliDocument.class or MilliCollection.class; MilliNone will be ignored the creation
     * @param force if false, it won't create if the provided name already exists; if true, it will delete the old and create a new if the type is different
     */
    void create(@Nonnull String name, @Nonnull Class<? extends MilliFile> type, boolean force);
}
