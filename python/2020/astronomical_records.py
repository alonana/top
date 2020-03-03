from python.test_utils import test_solution, assert_equals, get_ints


def connect(a, b):
    dp = [[0 for _ in range(len(b) + 1)] for _ in range(len(a) + 1)]
    for i in range(len(a)):
        for j in range(len(b)):
            if a[i] == b[j]:
                dp[i + 1][j + 1] = dp[i][j] + 1
            else:
                dp[i + 1][j + 1] = max(dp[i][j + 1], dp[i + 1][j])
    return dp[len(a)][len(b)]


def minimal_planets(a, b):
    best = len(a) + len(b)
    for i in a:
        for j in b:
            a_factored = [x * j for x in a]
            b_factored = [x * i for x in b]
            best = min(best, len(a) + len(b) - connect(a_factored, b_factored))
    return best


def run_line(match):
    a = get_ints(match.group(1))
    b = get_ints(match.group(2))
    expected = int(match.group(3))
    actual = minimal_planets(a, b)
    assert_equals(actual, expected)


test_solution(__file__, '{(.*)}, {(.*)}\\s+(\\d+)', run_line)
