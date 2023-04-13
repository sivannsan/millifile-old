package com.sivannsan.millifile;

import com.sivannsan.foundation.common.Ensure;
import com.sivannsan.foundation.common.Validate;
import com.sivannsan.foundation.annotation.Nonnegative;
import com.sivannsan.foundation.annotation.Nonnull;
import com.sivannsan.foundation.utility.FileUtility;
import com.sivannsan.millidata.MilliData;
import com.sivannsan.millidata.MilliDataParseException;
import com.sivannsan.millidata.MilliNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * TODO: Rule for filename
 */
@SuppressWarnings("unused")
public final class MilliFiles {
    private MilliFiles() {
    }

    /**
     * The loaded MilliFile is considered as root.
     */
    @Nonnull
    public static MilliFile load(@Nonnull String pathname) {
        return load(new File(pathname));
    }

    /**
     * The loaded MilliFile is considered as root.
     */
    @Nonnull
    public static MilliFile load(@Nonnull File file) {
        if (!file.exists()) {
            return new IMilliNone(null, file);
        } else if (file.isFile()) {
            return new IMilliDocument(null, file);
        } else if (file.isDirectory()) {
            return new IMilliCollection(null, file);
        } else {
            throw new IllegalStateException("Unexpected error while loading a MilliFile!");
        }
    }

    /**
     * @param file  the conventional name for MilliDocument ends with the .mll extension
     * @param type  MilliDocument.class or MilliCollection.class; MilliNone will be ignored the creation
     * @param force if false, it won't create if the provided file already exists; if true, it will delete the old and create a new if the type is different
     */
    public static void create(@Nonnull File file, @Nonnull Class<? extends MilliFile> type, boolean force) {
        if (Validate.nonnull(type) == MilliNone.class) return;
        if (Validate.nonnull(file).exists() && !force) return;
        if (type == MilliDocument.class) {
            if (file.isFile()) return;
            FileUtility.delete(file);
            FileUtility.createFile(file);
            FileUtility.writeLines(file, MilliNull.INSTANCE.toString());
        }
        if (type == MilliCollection.class) {
            if (file.isDirectory()) return;
            FileUtility.delete(file);
            FileUtility.createDirectory(file);
        }
    }

    private static abstract class IMilliFile implements MilliFile {
        protected final MilliCollection parent;
        @Nonnull
        protected final File file;

        protected IMilliFile(MilliCollection parent, @Nonnull File file) {
            this.parent = parent;
            this.file = Validate.nonnull(file);
        }

        @Override
        public final MilliCollection getParent() {
            return parent;
        }

        @Override
        @Nonnull
        public final File getFile() {
            return file;
        }

        @Override
        public final boolean isMilliNone() {
            return this instanceof MilliNone;
        }

        @Override
        public final boolean isMilliDocument() {
            return this instanceof MilliDocument;
        }

        @Override
        public final boolean isMilliCollection() {
            return this instanceof MilliCollection;
        }

        @Override
        @Nonnull
        public final MilliDocument asMilliDocument() throws ClassCastException {
            if (isMilliDocument()) return (MilliDocument) this;
            throw new ClassCastException("Not a MilliDocument");
        }

        @Override
        @Nonnull
        public final MilliCollection asMilliCollection() throws ClassCastException {
            if (isMilliCollection()) return (MilliCollection) this;
            throw new ClassCastException("Not a MilliCollection");
        }

        @Override
        public final void delete() {
            FileUtility.delete(file);
        }
    }

    private static final class IMilliNone extends IMilliFile implements MilliNone {
        private IMilliNone(MilliCollection parent, @Nonnull File file) {
            super(parent, file);
        }
    }

    private static final class IMilliDocument extends IMilliFile implements MilliDocument {
        private IMilliDocument(MilliCollection parent, @Nonnull File file) {
            super(parent, file);
        }

        @Override
        @Nonnull
        public MilliData getContent() throws MilliDataParseException {
            if (!file.exists()) throw new IllegalStateException("The file does not exists!");
            StringBuilder sb = new StringBuilder();
            try {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) sb.append(scanner.nextLine().trim());
                scanner.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return MilliData.Parser.parse(sb.toString());
        }

        @Override
        public void setContent(@Nonnull MilliData value) {
            setContent(value, 0);
        }

        @Override
        public void setContent(@Nonnull MilliData value, @Nonnegative int indent) {
            if (!file.exists()) throw new IllegalStateException("The file does not exists!");
            FileUtility.writeLines(file, Validate.nonnull(value).toString(Validate.nonnegative(indent)));
        }
    }

    private static final class IMilliCollection extends IMilliFile implements MilliCollection {
        private IMilliCollection(MilliCollection parent, @Nonnull File file) {
            super(parent, file);
        }

        @Override
        @Nonnull
        public List<MilliFile> list() {
            List<MilliFile> files = new ArrayList<>();
            for (File child : Ensure.ifNull(file.listFiles(), new File[]{})) {
                if (child.isFile()) {
                    files.add(new IMilliDocument(this, child));
                } else if (child.isDirectory()) {
                    files.add(new IMilliCollection(this, child));
                } else {
                    throw new IllegalStateException("Unexpected error while listing children MilliFiles!");
                }
            }
            return files;
        }

        @Override
        @Nonnull
        public MilliFile get(@Nonnull String name) {
            File child = new File(file, Validate.nonnull(name));
            if (!child.exists()) {
                return new IMilliNone(this, child);
            } else if (child.isFile()) {
                return new IMilliDocument(this, child);
            } else if (child.isDirectory()) {
                return new IMilliCollection(this, child);
            } else {
                throw new IllegalStateException("Unexpected error while getting a child MilliFile!");
            }
        }

        @Override
        public void create(@Nonnull String name, @Nonnull Class<? extends MilliFile> type, boolean force) {
            if (Validate.nonnull(name).equals("")) return;
            if (Validate.nonnull(type) == MilliNone.class) return;
            if (type == MilliDocument.class) {
                File child = new File(file, name);
                if (!child.exists()) FileUtility.createFile(child);
                if (child.isFile()) return;
                if (force) {
                    FileUtility.delete(child);
                    FileUtility.createFile(child);
                    FileUtility.writeLines(child, MilliNull.INSTANCE.toString());
                }
            }
            if (type == MilliCollection.class) {
                File child = new File(file, name);
                if (!child.exists()) FileUtility.createDirectory(child);
                if (child.isDirectory()) return;
                if (force) {
                    FileUtility.delete(child);
                    FileUtility.createDirectory(child);
                }
            }
        }
    }
}
