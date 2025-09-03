package lotto.model;

import java.util.List;

public class Lotto {
    public static final int MIN_VALUE = 1;
    public static final int MAX_VALUE = 45;
    public static final int SIZE = 6;

    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = numbers;
    }

    public Lotto(Lotto lotto) {
        this(lotto.numbers);
    }

    private void validate(List<Integer> numbers) {
        validateSize(numbers);
        validateRange(numbers);
        validateDuplicate(numbers);
    }

    private void validateDuplicate(List<Integer> numbers) {
        if (distinctSize(numbers) != numbers.size()) {
            throw new IllegalArgumentException(LottoErrorStatus.DUPLICATE.getMessage());
        }
    }

    private int distinctSize(List<Integer> numbers) {
        return numbers.stream()
                .distinct()
                .toList()
                .size();
    }

    private void validateRange(List<Integer> numbers) {
        numbers.forEach(this::validateRange);
    }

    private void validateRange(Integer number) {
        if (number < MIN_VALUE || number > MAX_VALUE) {
            throw new IllegalArgumentException(LottoErrorStatus.INVALID_RANGE.getMessage());
        }
    }

    private void validateSize(List<Integer> numbers) {
        if (numbers.size() != SIZE) {
            throw new IllegalArgumentException(LottoErrorStatus.INVALID_SIZE.getMessage());
        }
    }

    protected boolean contains(int number) {
        return numbers.contains(number);
    }

    public int matchCount(Lotto otherLotto) {
        return (int)numbers.stream()
                .filter(otherLotto::contains)
                .count();
    }

    @Override
    public String toString() {
        return numbers.toString();
    }
}
