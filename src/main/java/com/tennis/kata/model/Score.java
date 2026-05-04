package com.tennis.kata.model;

public enum Score {
    LOVE(0), FIFTEEN(15), THIRTY(30), FORTY(40), ADVANTAGE(40);

    private final int value;

    Score(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public Score next() {
        Score[] values = values();
        int nextOrdinal = this.ordinal() + 1;
        if (nextOrdinal >= values.length) {
            throw new IllegalStateException("Cannot advance beyond ADVANTAGE");
        }
        return values[nextOrdinal];
    }
}
