from python.test_utils import test_solution, assert_equals, get_ints


def gcd(a, b):
    while b != 0:
        a, b = b, a % b
    return a


def count_ways_copied(S, goal):
    dp = [0] * 1001
    dp[0] = 1
    for i in S:
        dp2 = [0] * 1001
        for j in range(1001):
            dp2[gcd(j, i)] += dp[j]
            dp2[j] += dp[j]
        for j in range(1001):
            dp[j] = dp2[j] % 1000000007
        print(dp)
    return dp[goal]


def recurse(collected, remaining, goal):
    if len(collected) >= 2:
        g = gcd(collected[-1], collected[-2])
        if g > goal:
            return 0

    if len(remaining) == 0:
        if len(collected) == 1:
            if collected[0] == goal:
                return 1
            else:
                return 0
        if gcd(collected[-1], collected[-2]) == goal:
            return 1
        else:
            return 0

    next_remaining = remaining[1:]
    next_collected = collected.copy()
    next_collected.append(remaining[0])
    return recurse(next_collected, next_remaining, goal) + recurse(collected, next_remaining, goal)


def count_ways(S, goal):
    return recurse([], S, goal)


def run_line(match):
    S = get_ints(match.group(1))
    goal = int(match.group(2))
    expected = int(match.group(3))
    actual = count_ways(S, goal)
    assert_equals(actual, expected)


test_solution(__file__, '{(.*)}, (\\d+)\\s+(\\d+)', run_line)
