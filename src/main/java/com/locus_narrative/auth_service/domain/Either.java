package com.locus_narrative.auth_service.domain;

public class Either<L, R> {
    private final L left;
    private final boolean isLeft;

    private final R right;
    private final boolean isRight;

    private Either(L left, R right) {
        if (left == null && right == null)
            throw new IllegalStateException("Left and right cannot be null at the same time.");

        this.left = left;
        this.right = right;

        isLeft = left != null;
        isRight = !isLeft;
    }

    public static <L, R> Either<L, R> left(L value) {
        return new Either<>(value, null);
    }

    public static <L, R> Either<L, R> right(R value) {
        return new Either<>(null, value);
    }

    public boolean isLeft() {
        return isLeft;
    }

    public boolean isRight() {
        return isRight;
    }

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }
}
