from python.test_utils import test_solution, assert_equals, get_ints

MAX_SIDE = 50


def win(number):
    if len(number) < 3:
        return False
    more_than_one = sum([1 if x > 1 else 0 for x in number])
    if more_than_one == 0:
        return False
    return len(number) % 2 == 1


def win_or_lose(number):
    if win(number):
        return "WIN"
    return "LOSE"


def run_line(match):
    number = get_ints(match.group(1))
    expected = match.group(2)
    actual = win_or_lose(number)
    assert_equals(actual, expected)


test_solution(__file__, '{(.*)}\\s+"(.*)"', run_line)
