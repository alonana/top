import copy

from python.test_utils import test_solution, assert_equals, get_ints


def add_edge_single_direction(edges, v1, v2):
    if v1 in edges:
        nexts = edges[v1]
    else:
        nexts = []
        edges[v1] = nexts

    if not (v2 in nexts):
        nexts.append(v2)


def remove_edge_single_direction(edges, v1, v2):
    nexts = edges[v1]
    nexts.remove(v2)


def add_edge(edges, v1, v2):
    add_edge_single_direction(edges, v1, v2)
    add_edge_single_direction(edges, v2, v1)


def remove_edge(edges, v1, v2):
    remove_edge_single_direction(edges, v1, v2)
    remove_edge_single_direction(edges, v2, v1)


def edge_from(edges, v1):
    if v1 in edges:
        return edges[v1]
    return []


def connected(edges, v1, v2):
    if v1 in edges:
        if v2 in edges[v1]:
            return True
    return False


def paint_component(edges, i, components_color, color):
    to_check = [i]
    while len(to_check) > 0:
        current = to_check.pop()
        components_color[current] = color
        for next in edge_from(edges, current):
            if not (next in components_color):
                to_check.append(next)


def find_components_amount(edges, n):
    components_color = {}
    color = 0
    for i in range(n):
        if not (i in components_color):
            color += 1
            paint_component(edges, i, components_color, color)

    # for i in range(n):
    #     print("{}: {}".format(i, components_color[i]))
    # print("total colors: {}".format(color))
    return color


def build_edges(a, b):
    edges = {}
    for i in range(len(a)):
        add_edge(edges, a[i], b[i])
    return edges


def locate_bridges(a, b, edges, n):
    components_amount = find_components_amount(edges, n)
    bridges = {}
    for i in range(len(a)):
        lesser_edges = copy.deepcopy(edges)
        remove_edge(lesser_edges, a[i], b[i])
        if find_components_amount(lesser_edges, n) > components_amount:
            add_edge(bridges, a[i], b[i])
    print(bridges)
    return bridges


def recursive_solve(n, bridges, included, current):
    if current == n:
        return len(included)

    can_add_current = True
    for v in included:
        if connected(bridges, v, current):
            can_add_current = False
            break

    best = 0
    if can_add_current:
        updated_included = copy.deepcopy(included)
        updated_included.append(current)
        best = recursive_solve(n, bridges, updated_included, current + 1)

    return max(best, recursive_solve(n, bridges, included, current + 1))


def solve(n, a, b):
    edges = build_edges(a, b)
    bridges = locate_bridges(a, b, edges, n)
    return recursive_solve(n, bridges, [], 0)


def run_line(match):
    n = int(match.group(1))
    a = get_ints(match.group(2))
    b = get_ints(match.group(3))
    expected = int(match.group(4))
    actual = solve(n, a, b)
    assert_equals(actual, expected)


test_solution(__file__, '(\\d+), {(.*)}, {(.*)}\\s+(\\d+)', run_line)
