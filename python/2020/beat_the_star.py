from python.test_utils import test_solution, assert_equals


def add_points(p, k, v):
    if k in p:
        p[k] += v
    else:
        p[k] = v


def does_it_matter(n, g):
    p = {0: 1}
    total = 0
    for i in range(n):
        points = i + 1
        total += points
        if points == g:
            continue
        new_p = {}
        for k, v in p.items():
            win_k = k + points
            loose_k = k
            add_points(new_p, win_k, v / 2)
            add_points(new_p, loose_k, v / 2)
        p = new_p

    mid = total / 2
    matter = 0
    for points, probability in p.items():
        if points <= mid <= points + g:
            matter += probability

    return matter


def run_line(match):
    n = int(match.group(1))
    g = int(match.group(2))
    expected = float(match.group(3))
    actual = does_it_matter(n, g)
    assert_equals(actual, expected)


test_solution(__file__, '(\\d+), (\\d+)\\s+(\\S+)', run_line)
