from python.test_utils import test_solution, assert_equals, get_ints


def get_max_direction(height):
    best = 1
    passed = 1
    prev = height[0]
    for h in height[1:]:
        if h <= prev:
            passed += 1
            best = max(best, passed)
        else:
            passed = 1
        prev = h

    return best


def get_max(height):
    if len(height) == 1:
        return 1

    return max(get_max_direction(height), get_max_direction(list(reversed(height))))


def run_line(match):
    height = get_ints(match.group(1))
    expected = int(match.group(2))
    actual = get_max(height)
    assert_equals(actual, expected)


test_solution(__file__, '{(.*)}\\s+(\\d+)', run_line)
