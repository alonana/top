from python.test_utils import test_solution, assert_equals, get_ints, get_strings

MODULO = 1e9 + 7


class Edge:
    def __init__(self, n1, n2, cost):
        self.n1 = n1
        self.n2 = n2
        self.cost = cost

    def join(self, trees):
        i1, t1 = self.find_tree(trees, self.n1)
        i2, t2 = self.find_tree(trees, self.n2)
        if i1 != i2:
            t1.join(t2, self.cost)
            del trees[i2]

    @staticmethod
    def find_tree(trees, n):
        for i, t in enumerate(trees):
            if t.houses[n]:
                return i, t


class Tree:
    def __init__(self, houses, cost):
        self.houses = houses
        self.cost = cost

    def join(self, t, edge_cost):
        for i, h in enumerate(t.houses):
            if h:
                self.houses[i] = True
        self.cost += t.cost + edge_cost

    def __repr__(self):
        key = ''.join(['1' if h else '0' for h in self.houses])
        return "{} = {}".format(key, self.cost)


def min_tower_distance(t, a_value, b_value):
    i = 0
    res = MODULO
    while i < t:
        val = (a_value * i + b_value) % MODULO
        res = min(res, val)
        step = (MODULO - val + a_value - 1) // a_value
        i += step
    return res


def direct_distance(direct_cost, i, j):
    c = direct_cost[i][j]
    return ord(c)


def pop_min_edge(edges):
    min_index = 0
    min_edge = edges[min_index]
    for i, edge in enumerate(edges):
        if edge.cost < min_edge.cost:
            min_edge = edge
            min_index = i
    del edges[min_index]
    return min_edge


# kruskal algorithm
def minimize_cost(t, a, b, direct_cost):
    n = len(direct_cost)
    edges = []
    for i in range(n):
        cost = min_tower_distance(t, a[i], b[i])
        edges.append(Edge(0, i + 1, cost))

    for i in range(n - 1):
        for j in range(i + 1, n):
            cost = direct_distance(direct_cost, i, j)
            edges.append(Edge(i + 1, j + 1, cost))

    trees = []
    for i in range(n + 1):
        houses = [False] * (n + 1)
        houses[i] = True
        trees.append(Tree(houses, 0))

    while len(trees) > 1:
        min_edge = pop_min_edge(edges)
        min_edge.join(trees)

    return trees[0].cost


def run_line(match):
    t = int(match.group(1))
    a = get_ints(match.group(2))
    b = get_ints(match.group(3))
    direct_cost = get_strings(match.group(4))
    expected = int(match.group(5))
    actual = minimize_cost(t, a, b, direct_cost)
    assert_equals(actual, expected)


test_solution(__file__, '(\\d+), {(.*)}, {(.*)}, {(.*)}\\s+(\\d+)', run_line)
