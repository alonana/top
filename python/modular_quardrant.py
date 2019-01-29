from python.test_utils import test_solution, assert_equals


def quardrant_sum(r1, r2, c1, c2):
    s = 0
    for row in range(r1, r2 + 1):
        row_sum = 0
        if c1 <= row:
            row_control_amount = min(row, c2) - c1 + 1
            # print("row {}, row control amount {}".format(row, row_control_amount))
            row_control_value = row % 3
            row_sum += row_control_amount * row_control_value
        if c2 > row:
            col_control_start = max(row + 1, c1)
            col_control_amount = c2 - col_control_start + 1
            # print("row {}, col control amount {}".format(row, col_control_amount))
            three_cells = col_control_amount // 3
            row_sum += three_cells * 3
            other_cells = col_control_amount - three_cells * 3
            if other_cells > 0:
                row_sum += col_control_start % 3
            if other_cells > 1:
                row_sum += (col_control_start + 1) % 3
        s += row_sum
        # print("row {}, sum {}".format(row, row_sum))
    return s


def run_line(match):
    r1 = int(match.group(1))
    r2 = int(match.group(2))
    c1 = int(match.group(3))
    c2 = int(match.group(4))
    expected = int(match.group(5))
    actual = quardrant_sum(r1, r2, c1, c2)
    assert_equals(actual, expected)


test_solution(__file__, '(\\d+), (\\d+), (\\d+), (\\d+)\\s+(\\d+)', run_line)
