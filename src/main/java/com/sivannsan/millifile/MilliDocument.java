package com.sivannsan.millifile;

import com.sivannsan.foundation.annotation.Nonnegative;
import com.sivannsan.foundation.annotation.Nonnull;
import com.sivannsan.millidata.MilliData;
import com.sivannsan.millidata.MilliDataParseException;

public interface MilliDocument extends MilliFile {
    @Nonnull
    MilliData getContent() throws MilliDataParseException;

    void setContent(@Nonnull MilliData value);

    void setContent(@Nonnull MilliData value, @Nonnegative int indent);
}
