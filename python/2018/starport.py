import math

from python.test_utils import test_solution, assert_equals


def get_expected_time(n, m):
    return (n - math.gcd(n, m)) / 2
    limit = n * m // math.gcd(n, m)
    print('gcd', limit)
    total = 0
    for i in range(0, limit, m):
        wait = i % n
        if wait > 0:
            wait = n - wait
        total += wait
    return total / (limit / m)


def run_line(match):
    n = int(match.group(1))
    m = int(match.group(2))
    expected = float(match.group(3))
    actual = get_expected_time(n, m)
    assert_equals(actual, expected)


test_solution(__file__, '(\\d+), (\\d+)\\s+(\\S+)', run_line)
