from python.test_utils import assert_equals, get_ints, test_solution


def expected_deliciousness(d):
    n = len(d)
    if n == 1:
        return d[0]

    first_row = [d[0]] * n
    dp = [first_row]
    for width in range(1, n):
        current_width_row = []
        for bigger_pans in range(n - width):
            selected_scores = sum(dp[i][bigger_pans + width - i - 1] for i in range(width))
            current_width_row.append(d[width] + selected_scores / (width + bigger_pans))
        dp.append(current_width_row)

    return sum([dp[i][n - i - 1] for i in range(n)]) / n


def run_line(match):
    d = get_ints(match.group(1))
    expected = float(match.group(2))
    actual = expected_deliciousness(d)
    assert_equals(actual, expected)


test_solution(__file__, '{(.*)}\\s+(\\S+)', run_line)
