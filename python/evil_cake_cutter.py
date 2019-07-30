from python.test_utils import test_solution, assert_equals


def calc_failed(full_length, part_length):
    if full_length == part_length:
        return 1

    possible = full_length - part_length
    valid = full_length - 2 * part_length
    if valid >= part_length:
        return 0

    p = 1 - valid * 2 / possible
    if p < 0:
        return 0
    if p > 1:
        return 1
    return p


def success_probability(w, h, w1, h1):
    w_p = calc_failed(w, w1)
    h_p = calc_failed(h, h1)
    print(w_p, h_p)
    return 1 - w_p * h_p


def run_line(match):
    w = int(match.group(1))
    h = int(match.group(2))
    w1 = int(match.group(3))
    h1 = int(match.group(4))
    expected = float(match.group(5))
    actual = success_probability(w, h, w1, h1)
    assert_equals(actual, expected)


test_solution(__file__, '(\\d+), (\\d+), (\\d+), (\\d+)\\s+(\\S+)', run_line)
