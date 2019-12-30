import math

from python.test_utils import test_solution, assert_equals


def count_maximal_chains(n):
    return math.pow(2, n - 1)


def run_line(match):
    n = int(match.group(1))
    expected = int(match.group(2))
    actual = count_maximal_chains(n)
    assert_equals(actual, expected)


test_solution(__file__, '(\\d+)\\s+(\\d+)', run_line)
