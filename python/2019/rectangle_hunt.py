from python.test_utils import assert_equals, get_ints, test_solution

x = []
y = []


def rectangle_size(p1, p2, p3, p4):
    dx21 = abs(x[p2] - x[p1])
    dy21 = abs(y[p2] - y[p1])
    dx32 = abs(x[p3] - x[p2])
    dy32 = abs(y[p3] - y[p2])
    dx24 = abs(x[p4] - x[p2])
    dy13 = abs(y[p3] - y[p1])

    area1 = abs(dx24 * dy13 - dx21 * dy21 - dx32 * dy32)
    return area1


def slope(p1, p2):
    delta_x = x[p2] - x[p1]
    if delta_x == 0:
        return None

    delta_y = y[p2] - y[p1]
    return delta_y / delta_x


def is_rectangle(p1, p2, p3, p4):
    delta21_x = x[p2] - x[p1]
    delta21_y = y[p2] - y[p1]

    if delta21_x != x[p3] - x[p4]:
        return False
    if delta21_y != y[p3] - y[p4]:
        return False

    s1 = slope(p1, p2)
    s2 = slope(p2, p3)
    if s1 is None:
        return s2 == 0
    if s2 is None:
        return s1 == 0

    return s1 * s2 == -1


def square_size(p1, p2, p3, p4):
    if is_rectangle(p1, p2, p3, p4):
        return rectangle_size(p1, p2, p3, p4)
    return -1


def largest(x, y):
    n = len(x)
    biggest = -1
    for p1 in range(n - 3):
        for p2 in range(p1 + 1, n - 2):
            for p3 in range(p2 + 1, n - 1):
                for p4 in range(p3 + 1, n):
                    biggest = max(biggest, square_size(p1, p2, p3, p4))
                    biggest = max(biggest, square_size(p1, p2, p4, p3))
                    biggest = max(biggest, square_size(p1, p3, p2, p4))
    return biggest


def run_line(match):
    global x
    global y
    x = get_ints(match.group(1))
    y = get_ints(match.group(2))
    expected = float(match.group(3))
    actual = largest(x, y)
    assert_equals(actual, expected)


test_solution(__file__, '{(.*)}, {(.*)}\\s+(\\S+)', run_line)
