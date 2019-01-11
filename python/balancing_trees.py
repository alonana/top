from python.test_utils import test_solution, get_ints, assert_equals


class Node:
    def __init__(self, weight, index):
        self.weight = weight
        self.index = index
        self.children = []
        self.width = 0
        self.indent = 0

    def add_child(self, node):
        self.children.append(node)

    def __repr__(self):
        return "{}({})".format(self.index, self.weight)

    def print(self):
        self.calc_width()
        self.calc_indent()
        depth = self.depth()
        for d in range(depth):
            self.print_level(d)
            print()

    def depth(self):
        max_depth = 0
        for x in self.children:
            max_depth = max(max_depth, x.depth())
        return 1 + max_depth

    def calc_width(self):
        self.width = 0
        for c in self.children:
            self.width += c.calc_width() + 1
        self.width = max(self.width, 1 + len(self.__repr__()))
        return self.width

    def calc_indent(self, prefix=0):
        self.indent = prefix

        for c in self.children:
            c.calc_indent(prefix)
            prefix += c.width

    def print_level(self, depth, already_indented=0):
        if depth == 0:
            node = '{}{} '.format(''.ljust(self.indent - already_indented), str(self))
            print(node, end='')
            return len(node)

        depth = depth - 1
        for c in self.children:
            already_indented += c.print_level(depth, already_indented)
        return already_indented


def min_cost(p, w):
    w.pop(0)
    print("parents {} weights {}".format(p, w))
    nodes = {0: Node(0, 0)}
    for i, weight in enumerate(w):
        nodes[i + 1] = Node(weight, i + 1)
    for i, parent in enumerate(p):
        nodes[parent].add_child(nodes[i + 1])
    nodes[0].print()
    return 1


def run_line(match):
    p = get_ints(match.group(1))
    w = get_ints(match.group(2))
    expected = float(match.group(3))
    actual = min_cost(p, w)
    assert_equals(actual, expected)


test_solution(__file__, '{(.*)}, {(.*)}\\s+(\\S+)', run_line)
