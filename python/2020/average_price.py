from python.test_utils import test_solution, assert_equals, get_ints


def non_duplicated_average(prices):
    unique = {}
    for p in prices:
        unique[p] = True
    keys = unique.keys()
    return sum(keys) / len(keys)


def run_line(match):
    prices = get_ints(match.group(1))
    expected = float(match.group(2))
    actual = non_duplicated_average(prices)
    assert_equals(actual, expected)


test_solution(__file__, '{(.*)}\\s+(\\S+)', run_line)
