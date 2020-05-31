from python.test_utils import test_solution, assert_equals, get_ints


def is_valid(positions):
    n = len(positions)
    if n == 1:
        return True

    odd = 0
    even = 0
    for i in range(n):
        if positions[i] % 2 == 0:
            even += 1
        else:
            odd += 1
    if odd > 1 and even > 1:
        return False

    positions = sorted(positions)

    for i in range(n - 3):
        if positions[i + 3] - positions[i] < 4:
            return False

    for i in range(n - 1):
        for j in range(i + 1, n):
            if positions[i] == positions[j]:
                return False

    return True


def is_valid_list(positions):
    if is_valid(positions):
        return "Valid"
    return "Invalid"


def run_line(match):
    positions = get_ints(match.group(1))
    expected = match.group(2)
    actual = is_valid_list(positions)
    assert_equals(actual, expected)


test_solution(__file__, '{(.*)}\\s+"(.*)"', run_line)
