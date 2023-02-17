package com.sivannsan.millifile;

import com.sivannsan.foundation.annotation.Nonnull;
import com.sivannsan.millidata.MilliData;
import com.sivannsan.millidata.MilliDataParseException;

/**
 * The instance of this class does not in-memory work with the data.
 * It always communicates with the storage.
 */
public interface MilliDocument extends MilliFile {
    @Nonnull
    MilliData getContent() throws MilliDataParseException;

    void setContent(@Nonnull MilliData value);

    void setContent(@Nonnull MilliData value, int indent);
}
