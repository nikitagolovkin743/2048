package com.golovkin;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Game2048 implements Game {
    public static final int GAME_SIZE = 4;
    private final int TILE_INIT_VALUE = 2;
    private final Board<Key, Integer> board = new SquareBoard<>(GAME_SIZE);
    private final int width;
    private final int height;
    private GameHelper helper = new GameHelper();
    private Random random = new Random();

    public Game2048() {
        width = board.getWidth();
        height = board.getHeight();
    }

    @Override
    public void init() {
        initWithNulls();

        try {
            addItem();
            addItem();
        } catch (GameOverException e) {
            throw new RuntimeException("Ошибка инициализации приложения");
        }
    }

    private void initWithNulls() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Key key = new Key(i, j);
                board.addItem(key, null);
            }
        }
    }

    @Override
    public boolean canMove() {
        return !board.availableSpace().isEmpty();
    }

    @Override
    public void move(Direction direction) throws GameOverException {
        if (direction == Direction.UP) {
            moveUp();
        } else if (direction == Direction.DOWN) {
            moveDown();
        } else if (direction == Direction.LEFT) {
            moveLeft();
        } else if (direction == Direction.RIGHT) {
            moveRight();
        }

        addItem();
    }

    private void moveRight() {
        for (int i = 0; i < height; i++) {
            List<Key> row = board.getRow(i);
            List<Integer> values = board.getValues(row);
            Collections.reverse(values);

            List<Integer> mergedValues = helper.moveAndMergeEqual(values);
            Collections.reverse(mergedValues);

            for (int j = 0; j < row.size(); j++) {
                board.addItem(row.get(j), mergedValues.get(j));
            }
        }
    }

    private void moveLeft() {
        for (int i = 0; i < height; i++) {
            List<Key> row = board.getRow(i);
            List<Integer> values = board.getValues(row);

            List<Integer> mergedValues = helper.moveAndMergeEqual(values);

            for (int j = 0; j < row.size(); j++) {
                board.addItem(row.get(j), mergedValues.get(j));
            }
        }
    }

    private void moveDown() {
        for (int i = 0; i < width; i++) {
            List<Key> column = board.getColumn(i);
            List<Integer> values = board.getValues(column);
            Collections.reverse(values);

            List<Integer> mergedValues = helper.moveAndMergeEqual(values);
            Collections.reverse(mergedValues);

            for (int j = 0; j < column.size(); j++) {
                board.addItem(column.get(j), mergedValues.get(j));
            }
        }
    }

    private void moveUp() {
        for (int i = 0; i < width; i++) {
            List<Key> column = board.getColumn(i);
            List<Integer> values = board.getValues(column);

            List<Integer> mergedValues = helper.moveAndMergeEqual(values);

            for (int j = 0; j < column.size(); j++) {
                board.addItem(column.get(j), mergedValues.get(j));
            }
        }
    }

    @Override
    public void addItem() throws GameOverException {
        List<Key> availableSpace = board.availableSpace();

        if (availableSpace.size() == 0) {
            throw new GameOverException();
        }

        int randomPosition = random.nextInt(availableSpace.size());
        Key randomKey = availableSpace.get(randomPosition);

        board.addItem(randomKey, TILE_INIT_VALUE);
    }

    @Override
    public Board getGameBoard() {
        return board;
    }

    @Override
    public boolean hasWin() {
        return board.hasValue(2048);
    }
}
