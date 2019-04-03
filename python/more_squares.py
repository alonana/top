from collections import defaultdict

from python.test_utils import test_solution, assert_equals, get_ints


class Points:
    def __init__(self):
        self.points_dict = defaultdict(list)
        self.points_list = []

    def add_point(self, p):
        x = p[0]
        y = p[1]
        if x in self.points_dict:
            axis = self.points_dict[x]
        else:
            axis = {}
            self.points_dict[x] = axis
        if not y in axis:
            axis[y] = True
            self.points_list.append((x, y))

    def includes(self, p):
        x = p[0]
        y = p[1]
        if not x in self.points_dict:
            return False
        axis = self.points_dict[x]
        return y in axis

    def size(self):
        return len(self.points_list)


def create_points(N, SX, SY, Xprefix, Yprefix):
    X = []
    Y = []
    L = len(Xprefix)
    # print("N {}, SX {}, SY {}, Xprefix {}, Yprefix {}, L {}".format(N, SX, SY, Xprefix, Yprefix, L))

    for i in range(L):
        X.append(Xprefix[i])
        Y.append(Yprefix[i])

    print("X {}, Y {}".format(X, Y))

    for i in range(L, N):
        X.append((X[i - 1] * 47 + 42) % SX)
        Y.append((Y[i - 1] * 47 + 42) % SY)

    S = Points()
    for i in range(N):
        S.add_point((X[i], Y[i]))
    return S


def add_possible_point(S, p3, p4, possible_points):
    p3_included = S.includes(p3)
    p4_included = S.includes(p4)
    if p3_included:
        if not p4_included:
            if not possible_points.includes(p4):
                possible_points.add_point(p4)
    elif p4_included:
        if not possible_points.includes(p3):
            possible_points.add_point(p3)


def find_square(S, possible_points, p1, p2):
    x = p1[0] - p2[0]
    y = p2[1] - p1[1]
    p3 = (p1[0] + y, p1[1] + x)
    p4 = (p3[0] - x, p3[1] + y)

    add_possible_point(S, p3, p4, possible_points)

    p3 = (p1[0] - y, p1[1] - x)
    p4 = (p3[0] - x, p3[1] + y)

    add_possible_point(S, p3, p4, possible_points)


def count_locations(N, SX, SY, Xprefix, Yprefix):
    S = create_points(N, SX, SY, Xprefix, Yprefix)
    # print("len: {} ==> {}".format(S.size(), S))
    if S.size() < 3:
        return 0

    possible_points = Points()
    for p1 in range(len(S.points_list) - 1):
        # print(p1)
        for p2 in range(p1 + 1, len(S.points_list)):
            find_square(S, possible_points, S.points_list[p1], S.points_list[p2])
    return possible_points.size()


def run_line(match):
    N = int(match.group(1))
    SX = int(match.group(2))
    SY = int(match.group(3))
    Xprefix = get_ints(match.group(4))
    Yprefix = get_ints(match.group(5))
    expected = int(match.group(6))
    actual = count_locations(N, SX, SY, Xprefix, Yprefix)
    assert_equals(actual, expected)


test_solution(__file__, '(\\d+), (\\d+), (\\d+), {(.*)}, {(.*)}\\s+(\\d+)', run_line)
