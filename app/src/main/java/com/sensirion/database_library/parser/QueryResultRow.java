package com.sensirion.database_library.parser;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class QueryResultRow {

    private static final String TAG = QueryResultRow.class.getSimpleName();

    @NonNull
    private final QueryResult mParent;
    @NonNull
    private final Object[] mResultRow;

    QueryResultRow(@NonNull final QueryResult result, @NonNull final Object[] databaseRow) {
        mParent = result;
        mResultRow = databaseRow;
    }

    /**
     * Obtain a row Integer value using the index.
     *
     * @param index of the column.
     * @return {@link java.lang.Integer} with the Integer value of the row column.
     */
    public Integer getInt(final int index) {
        return getLong(index).intValue();
    }

    /**
     * Obtains a row Integer value using the column name.
     *
     * @param columnName name of the column.
     * @return {@link java.lang.Integer} with the Integer value of the row column.
     */
    @SuppressWarnings("unused")
    public Integer getInt(@NonNull final String columnName) {
        return getDouble(columnName).intValue();
    }

    /**
     * Obtain a row Integer value using the index.
     *
     * @param index of the column.
     * @return {@link java.lang.Long} with the Long value of the row column.
     */
    @Nullable
    public Long getLong(final int index) {
        if (mResultRow[index] == null) {
            return null;
        } else if (mResultRow[index] instanceof Long) {
            return (Long) mResultRow[index];
        }
        throw new ClassCastException(String.format("%s: getLong -> The column %d it's not an integer.", TAG, index));
    }

    /**
     * Obtains a row Integer value using the column name.
     *
     * @param columnName name of the column.
     * @return {@link java.lang.Long} with the Long value of the row column.
     */
    @Nullable
    public Long getLong(@NonNull final String columnName) {
        final Integer indexColumn = mParent.getIndexColumn(columnName);
        if (indexColumn == null) {
            throw new IllegalArgumentException(String.format("%s: getLong -> The introduced String '%s' it's not a column name of the query.", TAG, columnName));
        }
        if (mResultRow[indexColumn] == null) {
            return null;
        } else if (mResultRow[indexColumn] instanceof Long) {
            return (Long) mResultRow[indexColumn];
        } else if (mResultRow[indexColumn] instanceof Double) {
            Log.w(TAG, String.format("getLong -> The column with name '%s' was not a long, it's a double. The result has been rounded.", columnName));
            return Math.round((Double) mResultRow[indexColumn]);
        }
        throw new ClassCastException(String.format("%s: getLong -> The column %s it's not a long it's a %s.", TAG, columnName, mResultRow[indexColumn].getClass().getSimpleName()));
    }

    /**
     * Obtain a row String value using the index.
     *
     * @param index of the column.
     * @return {@link java.lang.String} with the position of the String in the table.
     */
    @Nullable
    public String getString(final int index) {
        if (mResultRow[index] == null) {
            return null;
        } else if (mResultRow[index] instanceof String) {
            return (String) mResultRow[index];
        }
        throw new ClassCastException(String.format("%s: getString -> The column it's not a String.", TAG));
    }

    /**
     * Obtains a row String value using the column name.
     *
     * @param columnName name of the column.
     * @return {@link java.lang.String} with the String value of the row column.
     */
    @Nullable
    public String getString(@NonNull final String columnName) {
        final Integer indexColumn = mParent.getIndexColumn(columnName);
        if (indexColumn == null) {
            throw new IllegalArgumentException(String.format("%s: getString -> The introduced String '%s' it's not a column name of the query.", TAG, columnName));
        }
        if (mResultRow[indexColumn] == null) {
            return null;
        } else if (mResultRow[indexColumn] instanceof String) {
            return (String) mResultRow[indexColumn];
        }
        throw new ClassCastException(String.format("%s: getString -> The column %s it's not a String.", TAG, columnName));
    }


    /**
     * Obtain a row String value using the index.
     *
     * @param index of the column.
     * @return {@link java.lang.Double} with the float value in the table.
     */
    @SuppressWarnings("unused")
    public Float getFloat(final int index) {
        return getDouble(index).floatValue();
    }

    /**
     * Obtains a row Float value using the column name.
     *
     * @param columnName name of the column.
     * @return {@link java.lang.Float} with the Float value of the row column.
     */
    public Float getFloat(@NonNull final String columnName) {
        return getDouble(columnName).floatValue();
    }

    /**
     * Obtain a row String value using the index.
     *
     * @param index of the column.
     * @return {@link java.lang.Double} with the double value in the table.
     */
    @Nullable
    public Double getDouble(final int index) {
        if (mResultRow[index] == null) {
            return null;
        } else if (mResultRow[index] instanceof Double) {
            return (Double) mResultRow[index];
        }
        throw new ClassCastException(String.format("%s: getDouble -> The column with index %d it's not a double it's a %s.", TAG, index, mResultRow[index].getClass().getSimpleName()));
    }

    /**
     * Obtains a row Float value using the column name.
     *
     * @param columnName name of the column.
     * @return {@link java.lang.Double} with the double value of the row column.
     */
    @Nullable
    public Double getDouble(@NonNull final String columnName) {
        final Integer indexColumn = mParent.getIndexColumn(columnName);
        if (indexColumn == null) {
            throw new IllegalArgumentException(String.format("%s: getDouble -> The introduced String '%s' it's not a column name of the query.", TAG, columnName));
        }
        if (mResultRow[indexColumn] == null) {
            return null;
        } else if (mResultRow[indexColumn] instanceof Double) {
            return (Double) mResultRow[indexColumn];
        }
        throw new ClassCastException(String.format("%s: getDouble -> The column %s it's not a double. It's a %s", TAG, columnName, mResultRow[indexColumn].getClass().getSimpleName()));
    }

    /**
     * Obtain a row Blob value using the index.
     *
     * @param index of the column.
     * @return array of {@link java.lang.Byte} with the position of the blob in the table.
     */
    @Nullable
    @SuppressWarnings("unused")
    public Byte[] getBlob(final int index) {
        if (mResultRow[index] == null) {
            return null;
        } else if (mResultRow[index] instanceof Byte[]) {
            return (Byte[]) mResultRow[index];
        }
        throw new ClassCastException(String.format("%s: getBlob -> The column it's not a Blob.", TAG));
    }


    /**
     * Obtains a row Float value using the column name.
     *
     * @param columnName name of the column.
     * @return {@link java.lang.Float} with the Float value of the row column.
     */
    @Nullable
    @SuppressWarnings("unused")
    public Byte[] getBlob(@NonNull final String columnName) {
        final Integer indexColumn = mParent.getIndexColumn(columnName);
        if (indexColumn == null) {
            throw new IllegalArgumentException(String.format("%s: getBlob -> The introduced String '%s' it's not a column name of the query.", TAG, columnName));
        }
        if (mResultRow[indexColumn] == null) {
            return null;
        } else if (mResultRow[indexColumn] instanceof Byte[]) {
            return (Byte[]) mResultRow[indexColumn];
        }
        throw new ClassCastException(String.format("%s: getBlob -> The column %s it's not a blob.", TAG, columnName));
    }
}