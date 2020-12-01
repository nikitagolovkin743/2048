package com.golovkin;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SquareBoard<V> extends Board<Key, V> {
    public SquareBoard(int size) {
        super(size, size);
    }

    @Override
    public void fillBoard(List<V> list) {
        int size = getWidth();

        if (list.size() != size * size) {
            throw new RuntimeException("Ошибка инициализации приложения");
        }

        board.clear();

        int counter = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Key key = new Key(i, j);
                V value = list.get(counter);
                board.put(key, value);
                counter++;
            }
        }
    }

    @Override
    public List<Key> availableSpace() {
        return board.entrySet().stream()
                .filter(x -> x.getValue() == null)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public void addItem(Key key, V value) {
        board.put(key, value);
    }

    @Override
    public Key getKey(int i, int j) {
        return board.keySet().stream()
                .filter(x -> x.getI() == i && x.getJ() == j)
                .findFirst().orElse(null);
    }

    @Override
    public V getValue(Key key) {
        return board.get(key);
    }

    @Override
    public List<Key> getColumn(int j) {
        return board.keySet().stream()
                .filter(x -> x.getJ() == j)
                .sorted(Comparator.comparingInt(Key::getI))
                .collect(Collectors.toList());
    }

    @Override
    public List<Key> getRow(int i) {
        return board.keySet().stream()
                .filter(x -> x.getI() == i)
                .sorted(Comparator.comparingInt(Key::getJ))
                .collect(Collectors.toList());
    }

    @Override
    public boolean hasValue(V value) {
        return board.containsValue(value);
    }

    @Override
    public List<V> getValues(List<Key> keys) {
        List<V> values = new ArrayList<>();

        for (Key key : keys) {
            values.add(board.get(key));
        }

        return values;
    }
}
