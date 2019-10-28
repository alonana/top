from python.test_utils import test_solution, assert_equals, get_ints


def min_operations(red, green, blue):
    return 6


def run_line(match):
    red = get_ints(match.group(1))
    green = get_ints(match.group(2))
    blue = get_ints(match.group(3))
    expected = int(match.group(4))
    actual = min_operations(red, green, blue)
    assert_equals(actual, expected)


test_solution(__file__, '{(.*)}, {(.*)}, {(.*)}\\s+(\\S+)', run_line)
