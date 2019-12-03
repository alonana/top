import math

from python.test_utils import test_solution, assert_equals


class SizeCombinations:
    def __init__(self):
        self.combinations = {}

    @staticmethod
    def get_key(combination):
        key = ",".join([str(count) for count in combination])
        return key

    def add(self, combination, count):
        key = self.get_key(combination)
        if key in self.combinations:
            self.combinations[key] += count
        else:
            self.combinations[key] = count

    def count(self, combination):
        key = self.get_key(combination)
        if key in self.combinations:
            return self.combinations[key]
        return 0

    def get_combinations(self):
        return [[int(digit) for digit in combination.split(",")] for combination in self.combinations]

    def __repr__(self):
        return str(self.combinations)


def find_even(n):
    digits_count = 1 + int(math.log(n, 10))

    combinations_for_zero_size = SizeCombinations()
    all_even = [0] * 10
    combinations_for_zero_size.add(all_even, 1)
    combinations = [combinations_for_zero_size]
    for size in range(digits_count):
        current_size_combinations = SizeCombinations()
        prev_combinations = combinations[-1]
        for combination in prev_combinations.get_combinations():
            count = prev_combinations.count(combination)
            for digit in range(10):
                new_combination = combination.copy()
                new_combination[digit] = 0 if new_combination[digit] == 1 else 1
                current_size_combinations.add(new_combination, count)
        combinations.append(current_size_combinations)
    print(combinations)

    total = 0
    for combination in combinations[1:]:
        total += combination.count(all_even)
    return total


def count_in_range(lo, hi):
    return find_even(hi) - find_even(lo)


def run_line(match):
    lo = int(match.group(1))
    hi = int(match.group(2))
    expected = int(match.group(3))
    actual = count_in_range(lo, hi)
    assert_equals(actual, expected)


test_solution(__file__, '(\\d+), (\\d+)\\s+(\\d+)', run_line)
