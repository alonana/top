from python.test_utils import test_solution, assert_equals


def most(width, height, size, max_allowed):
    counts = {}
    for r in range(height):
        for c in range(width):
            key = "{}_{}".format(r % size, c % size)
            if key in counts:
                counts[key] += 1
            else:
                counts[key] = 1

    ordered = list(counts.values())
    ordered.sort()
    ordered = list(reversed(ordered))

    print(ordered)

    total = 0
    while max_allowed > 0 and len(ordered) > 0:
        total += ordered.pop(0)
        max_allowed -= 1
    return total


def run_line(match):
    width = int(match.group(1))
    height = int(match.group(2))
    size = int(match.group(3))
    max_allowed = int(match.group(4))
    expected = int(match.group(5))
    actual = most(width, height, size, max_allowed)
    assert_equals(actual, expected)


test_solution(__file__, '(\\d+), (\\d+), (\\d+), (\\d+)\\s+(\\d+)', run_line)
