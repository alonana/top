from python.test_utils import test_solution, assert_equals, get_strings

MAX_SIDE = 50


def build_lines(a):
    if a % 2 == 1:
        return None
    squares = a // 2
    lines = []
    current_line_index = 1
    current_line_length = 1
    grow = True
    while current_line_length > 0:
        if current_line_length >= squares:
            lines.append(squares)
            return lines
        lines.append(current_line_length)
        squares -= current_line_length
        if squares == 0:
            return lines
        if current_line_index * 2 == MAX_SIDE:
            grow = False
        current_line_index += 1
        if grow:
            current_line_length += 2
        else:
            current_line_length -= 2

    return None


def update_board(board, row, col, char):
    line = list(board[row])
    line[col] = char
    board[row] = "".join(line)


def merge_board(board, row, col, char):
    current = board[row][col]
    if current == '.':
        update_board(board, row, col, char)
    else:
        update_board(board, row, col, '.')


def draw_square(board, row, col):
    merge_board(board, row, col, '/')
    merge_board(board, row, col + 1, '\\')
    merge_board(board, row + 1, col, '\\')
    merge_board(board, row + 1, col + 1, '/')


def draw_line(board, line_index, line):
    offset = line // 2
    row = line_index + offset
    col = line_index - offset
    for i in range(line):
        draw_square(board, row, col)
        row -= 1
        col += 1


def draw(lines):
    if lines == [1]:
        return ['/\\', '\\/']

    size = len(lines) * 2
    if size > MAX_SIDE:
        size = MAX_SIDE
    board = ['.' * size] * size

    for i, line in enumerate(lines):
        draw_line(board, i, line)

    return board


def enclose(a):
    lines = build_lines(a)
    print(lines)
    if lines is None:
        return []

    return draw(lines)


def print_board(board):
    print("==================")
    for line in board:
        print(line)


def area(board):
    s = 0
    for line in board:
        inside = False
        for c in line:
            if c == '.':
                if inside:
                    s += 1
            else:
                s += 0.5
                inside = not inside
    print_board(board)
    return s


def run_line(match):
    a = int(match.group(1))
    expected = get_strings(match.group(2))
    actual = enclose(a)
    assert_equals(area(actual), area(expected))


test_solution(__file__, '(\\d+)\\s+{(.*)}', run_line)
