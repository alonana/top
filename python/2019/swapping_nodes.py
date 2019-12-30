from python.test_utils import test_solution, assert_equals, get_ints


def swap_nodes(leaves):
    if len(leaves) <= 1:
        return leaves
    size = 1
    while size < len(leaves):
        i = 0
        while i < len(leaves):
            next_index = i + size
            current_first_element = leaves[i]
            next_first_element = leaves[next_index]
            if current_first_element > next_first_element:
                temp = leaves[i: i + size]
                leaves[i: i + size] = leaves[next_index: next_index + size]
                leaves[next_index: next_index + size] = temp
            i += 2 * size
        size *= 2

    return leaves


def run_line(match):
    leaves = get_ints(match.group(1))
    expected = get_ints(match.group(3))
    actual = swap_nodes(leaves)
    assert_equals(actual, expected)


test_solution(__file__, '{(.*)}, (\\d+)\\s+{(.*)}', run_line)
