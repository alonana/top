from python.test_utils import test_solution, assert_equals


def debug(m):
    if False:
        print(m)


class Node:
    def __init__(self, possible):
        self.possible = possible
        self.visit = False

    def __repr__(self):
        return str(self.possible)

    def remove_first_possible(self):
        if self.visit:
            p = self.possible[0]
            del self.possible[0]
            return p
        else:
            self.visit = True
            return None

    def is_possible(self):
        return len(self.possible) > 0

    def first(self):
        self.visit = True
        return self.possible[0]


def build_nodes(nodes):
    result = ""

    for n in nodes:
        result += str(n.first())

    return result


def get_possible(n, compare):
    if compare == '=':
        return [n]
    if compare == '!':
        return [x for x in range(0, 10) if x != n]
    if compare == '>':
        return [x for x in range(0, 10) if x < n]
    if compare == '<':
        return [x for x in range(0, 10) if x > n]
    raise Exception("NO")


def remove_by_checked(possible, checkedOptions):
    return [x for x in possible if not checkedOptions[x]]


def smallest(comparison):
    digits_amount = len(comparison) + 1
    checked = [[False for y in range(0, 10)] for x in range(digits_amount)]
    nodes = [None for i in range(digits_amount)]
    nodes[0] = Node([x for x in range(1, 10)])
    i = 0
    while i < len(comparison):
        compare = comparison[i]
        debug("{} {} {}".format(i, compare, nodes))
        current = nodes[i]
        not_possible = current.remove_first_possible()
        if not_possible is not None:
            checked[i][not_possible] = True
        if not current.is_possible():
            debug("current not possible")
            if i == 0:
                return ""
            i -= 1
        else:
            possible = get_possible(current.first(), compare)
            possible = remove_by_checked(possible, checked[i + 1])
            debug("get possible {} {} {}".format(current.first(), compare, possible))
            if len(possible) > 0:
                nodes[i + 1] = Node(possible)
                i += 1
    return build_nodes(nodes)


def run_line(match):
    comparision = match.group(1)
    expected = match.group(2)
    actual = smallest(comparision)
    assert_equals(actual, expected)


test_solution(__file__, '"(.*)"\\s+"(.*)"', run_line)
