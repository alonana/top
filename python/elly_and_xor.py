from python.test_utils import test_solution, assert_equals, get_ints


def get_max_recursive(n, numbers):
    if len(numbers) == 0:
        return n

    copy = numbers.copy()
    m = copy.pop(0)
    return max(get_max_recursive(n & m, copy), get_max_recursive(n ^ m, copy))


def get_max(numbers):
    n = numbers.pop(0)
    return get_max_recursive(n, numbers)


def run_line(match):
    numbers = get_ints(match.group(1))
    expected = int(match.group(2))
    actual = get_max(numbers)
    assert_equals(actual, expected)


test_solution(__file__, '{(.*)}\\s+(\\d+)', run_line)
