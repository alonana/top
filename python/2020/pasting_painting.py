from python.test_utils import test_solution, assert_equals, get_strings, get_ints

EMPTY = '.'


def paste_color(board, row, col, color):
    if color != EMPTY:
        board[row][col] = color


def paste(board, clipboard, position):
    for row_index, row in enumerate(clipboard):
        for col_index, color in enumerate(row):
            paste_color(board, row_index + position, col_index + position, color)


def counts(board):
    counters = {
        'R': 0,
        'G': 0,
        'B': 0,
    }

    for row in board:
        for color in row:
            if color != EMPTY:
                counters[color] += 1

    return [counters['R'], counters['G'], counters['B']]


def count_colors(clipboard, t):
    size = 105
    board = [[EMPTY for i in range(size)] for i in range(size)]
    initial = 51

    if t <= initial:
        for i in range(t):
            paste(board, clipboard, i)
        return counts(board)
    else:
        for i in range(initial):
            paste(board, clipboard, i)
        c1 = counts(board)
        paste(board, clipboard, initial)
        c2 = counts(board)
        result = []
        for i in range(len(c1)):
            result.append(c1[i] + (t - initial) * (c2[i] - c1[i]))
        return result


def run_line(match):
    clipboard = get_strings(match.group(1))
    t = int(match.group(2))
    expected = get_ints(match.group(3))
    actual = count_colors(clipboard, t)
    assert_equals(actual, expected)


test_solution(__file__, '{(.*)}, (\\d+)\\s+{(.*)}', run_line)
