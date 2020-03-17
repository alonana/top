from python.test_utils import test_solution, assert_equals, get_ints


def overall_score(scores):
    scores.sort()
    section = len(scores) // 3
    low = scores[:section]
    high = scores[len(scores) - section:]
    mid = scores[section:len(scores) - section]
    return sum(low) / len(low) + sum(high) / len(high) + sum(mid) / len(mid)


def run_line(match):
    scores = get_ints(match.group(1))
    expected = float(match.group(2))
    actual = overall_score(scores)
    assert_equals(actual, expected)


test_solution(__file__, '{(.*)}\\s+(\\S+)', run_line)
