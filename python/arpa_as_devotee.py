from python.test_utils import test_solution, assert_equals, get_ints, get_strings


def is_colliding(current, value):
    return current != 'Not Sure' and current != value


def solve(a, last_seen, t):
    reports = {}
    for i in range(len(a)):
        at = a[i]
        seen = last_seen[i]
        if seen > at:
            return []
        if at in reports:
            if reports[at] != seen:
                return []
        reports[at] = seen

    times = ['Not Sure'] * (1 + max(max(t), max(a)))
    prev_time = 0
    for new_time in sorted(list(reports.keys())):
        last_seen_time = reports[new_time]
        for i in range(prev_time, last_seen_time):
            times[i] = 'Not Sure'
        if is_colliding(times[last_seen_time], 'Yes'):
            return []
        times[last_seen_time] = 'Yes'
        for i in range(last_seen_time + 1, new_time + 1):
            if is_colliding(times[i], 'No'):
                return []
            times[i] = 'No'

        prev_time = new_time + 1
    print(times)
    queries = []
    for query in t:
        queries.append(times[query])
    return queries


def run_line(match):
    a = get_ints(match.group(3))
    last_seen = get_ints(match.group(4))
    t = get_ints(match.group(5))
    expected = get_strings(match.group(6))
    actual = solve(a, last_seen, t)
    assert_equals(actual, expected)


test_solution(__file__, '(\\d+), (\\d+), {(.*)}, {(.*)}, {(.*)}\\s+{(.*)}', run_line)
