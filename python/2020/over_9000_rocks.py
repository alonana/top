from python.test_utils import test_solution, assert_equals, get_ints


class Range:
    def __init__(self, lower, upper):
        self.lower = lower
        self.upper = upper

    def __repr__(self):
        return "{}-{}".format(self.lower, self.upper)

    def add(self, other):
        self.lower += other.lower
        self.upper += other.upper


def count_possibilities(lower_bound, upper_bound):
    ranges = []
    n = len(lower_bound)
    limit = 1 << n
    for boxes_bits in range(limit):
        r = Range(0, 0)
        for box in range(n):
            if (1 << box) & boxes_bits:
                r.add(Range(lower_bound[box], upper_bound[box]))
        ranges.append(r)

    ranges = sorted(ranges, key=lambda x: x.lower)

    highest = 0
    total = 0
    for r in ranges:
        current_low = max([r.lower, 9001, highest])
        if current_low <= r.upper:
            total += r.upper - current_low + 1
        highest = max(r.upper + 1, highest)
    return total


def run_line(match):
    lower_bound = get_ints(match.group(1))
    upper_bound = get_ints(match.group(2))
    expected = int(match.group(3))
    actual = count_possibilities(lower_bound, upper_bound)
    assert_equals(actual, expected)


test_solution(__file__, '{(.*)}, {(.*)}\\s+(\\d+)', run_line)
