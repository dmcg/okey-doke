package org.rococoa.okeydoke.util;

import java.util.*;

public class Tabulator {

    public String tableOf(Collection<?> data) {
        if (data.isEmpty())
            return "";
        return tableOf(data, columnSizes(data));
    }

    public String headedTableOf(Collection<?> data) {
        if (data.isEmpty())
            return "";
        int[] columnSizes = columnSizes(data);
        return tableOf(withDivider(data, columnSizes), columnSizes);
    }

    private int[] columnSizes(Collection<?> data) {
        int[] result = null;
        for (Object row : data) {
            Collection<?> rowCollection = collectionOf(row);
            if (result == null)
                result = new int[rowCollection.size()];
            int c = 0;
            for (Object cell : rowCollection) {
                int cellLength = String.valueOf(cell).length();
                result[c] = Math.max(result[c], cellLength);
                ++c;
            }
        }
        return result;
    }

    private Collection<?> collectionOf(Object row) {
        return row instanceof Collection ? (Collection<?>) row : Collections.singleton(row);
    }

    private String tableOf(Collection<?> data, int[] columnSizes) {
        if (columnSizes.length == 0)
            return "";
        String[][] formatted = formattedCells(data, columnSizes);
        StringBuffer result = new StringBuffer();
        for (String[] row: formatted) {
            formatRowInto(result, row);
        }
        return result.toString();
    }

    private void formatRowInto(StringBuffer result, String[] row) {
        result.append('|');
        for (String cell : row) {
            result.append(cell).append('|');
        }
        result.append('\n');
    }

    private String[][] formattedCells(Collection<?> data, int[] columnSizes) {
        String[][] result = null;
        int r = 0;
        for (Object row : data) {
            Collection<?> rowCollection = collectionOf(row);
            if (result == null)
                result = new String[data.size()][rowCollection.size()];
            int c = 0;
            for (Object cell : rowCollection) {
                result[r][c] = formatCell(String.valueOf(cell), columnSizes[c]);
                ++c;
            }
            ++r;
        }
        return result;
    }

    private String formatCell(String s, int columnSize) {
        if (s.length() == columnSize)
            return s;
        StringBuffer result = new StringBuffer(columnSize);
        result.append(s);
        while (result.length() < columnSize) {
            result.append(' ');
        }
        return result.toString();
    }

    private Collection<?> withDivider(final Collection<?> data, final int[] columnSizes) {
        return new Collection<Object>() {
            @Override public int size() {
                return data.size() + 1;
            }

            @Override public Iterator<Object> iterator() {
                return new Iterator<Object>() {
                    final Iterator<?> underlying = data.iterator();
                    int i = 0;
                    @Override
                    public boolean hasNext() {
                        return underlying.hasNext() || i < 2;
                    }

                    @Override
                    public Object next() {
                        try {
                            return i == 1 ? dividerData(columnSizes) : underlying.next();
                        } finally {
                            i++;
                        }
                    }

                    @Override public void remove() { throw new UnsupportedOperationException(); }
                };
            }

            @Override public boolean isEmpty() { throw new UnsupportedOperationException(); }
            @Override public boolean contains(Object o) { throw new UnsupportedOperationException(); }
            @Override public Object[] toArray() { throw new UnsupportedOperationException(); }
            @Override public Object[] toArray(Object[] a) { throw new UnsupportedOperationException(); }
            @Override public boolean add(Object o) { throw new UnsupportedOperationException(); }
            @Override public boolean remove(Object o) { throw new UnsupportedOperationException(); }
            @Override public boolean containsAll(Collection<?> c) { throw new UnsupportedOperationException(); }
            @Override public boolean addAll(Collection<?> c) { throw new UnsupportedOperationException(); }
            @Override public boolean removeAll(Collection<?> c) { throw new UnsupportedOperationException(); }
            @Override public boolean retainAll(Collection<?> c) { throw new UnsupportedOperationException(); }
            @Override public void clear() { throw new UnsupportedOperationException(); }
        };
    }

    private Collection<?> dividerData(int[] columnSizes) {
        List<String> result = new ArrayList<String>(columnSizes.length);
        for (int i = 0; i < columnSizes.length; i++) {
            result.add(times('-', columnSizes[i]));
        }
        return result;
    }

    private String times(char ch, int repeat) {
        StringBuffer result = new StringBuffer(repeat);
        for (int i = 0; i < repeat; i++) {
            result.append(ch);
        }
        return result.toString();
    }
}
