from python.test_utils import test_solution, assert_equals, get_ints


def min_effort(need_mean, need_median, d):
    grades = [0] * len(d)
    mid = (len(d) + 1) // 2
    for i in range(mid):
        grades[i] = need_median

    while sum(grades) / len(grades) < need_mean:
        i = 0
        while grades[i] == 10:
            i += 1
        grades[i] += 1

    d = sorted(d)
    total = 0
    for i, g in enumerate(grades):
        total += g * d[i]
    return total


def run_line(match):
    need_mean = int(match.group(1))
    need_median = int(match.group(2))
    d = get_ints(match.group(3))
    expected = int(match.group(4))
    actual = min_effort(need_mean, need_median, d)
    assert_equals(actual, expected)


test_solution(__file__, '(\\d+), (\\d+), {(.*)}\\s+(\\d+)', run_line)
