import math

from python.test_utils import assert_equals, get_ints, test_solution


def count_ways(a, k):
    total = 1
    for children in a:
        total = total * math.pow(k, children)

    # all parents together in each table
    total = total * k

    # parents are separated

    return total


def run_line(match):
    a = get_ints(match.group(1))
    k = int(match.group(2))
    expected = int(match.group(3))
    actual = count_ways(a, k)
    assert_equals(actual, expected)


test_solution(__file__, '{(.*)}, (\\d+)\\s+(\\d+)', run_line)
