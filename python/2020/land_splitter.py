from python.test_utils import test_solution, assert_equals


def single_split(area, a, b):
    if a == b or area % b == 0:
        if area % b != 0:
            print('similar bounds - not dividable')
            return []
        parts = area / b
        if parts % 2 == 0:
            print('similar bounds - even split')
            return [area / 2, area / 2]
        print('similar bounds - odd split')
        return [(parts // 2) * b, (parts // 2 + 1) * b]

    parts = area

    return []


def cheapest(n, a, b):
    cost = 0
    to_split = [n]
    while len(to_split) > 0:
        area = to_split.pop()
        items = single_split(area, a, b)
        if len(items) == 0:
            return -1
        if len(items) == 2:
            cost += items[0] * items[1]
        else:
            cost += items[0] * items[1] + items[1] * items[2] + items[0] * items[2]
        for i in items:
            if i > b:
                to_split.append(i)
    return cost


def run_line(match):
    n = int(match.group(1))
    a = int(match.group(2))
    b = int(match.group(3))
    expected = int(match.group(4))
    actual = cheapest(n, a, b)
    assert_equals(actual, expected)


test_solution(__file__, '(\\d+), (\\d+), (\\d+)\\s+(\\S+)', run_line)
