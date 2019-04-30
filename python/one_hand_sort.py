from python.test_utils import test_solution, get_ints

EMPTY = -1


def is_sorted(target):
    for i, n in enumerate(target):
        if i != n and i != len(target) - 1:
            return False
    return True


def get_move(target, empty_location):
    if empty_location == len(target) - 1:
        for i, n in enumerate(target):
            if i != n:
                return i
        assert "problem 1"
    else:
        for i, n in enumerate(target):
            if n == empty_location:
                return i
        assert "problem 2"


def sort_shelf(target):
    empty_location = len(target)
    target.append(EMPTY)

    moves = []
    while not is_sorted(target):
        move_location = get_move(target, empty_location)
        moves.append(move_location)
        target[move_location], target[empty_location] = target[empty_location], target[move_location]
        empty_location = move_location
    return moves


def run_line(match):
    target = get_ints(match.group(1))
    actual = sort_shelf(target)
    print(actual)


test_solution(__file__, '{(.*)}\\s+{(.*)}', run_line)
