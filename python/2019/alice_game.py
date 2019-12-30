from math import sqrt, trunc

from python.test_utils import test_solution, assert_equals

alice_required = 0
kirito_required = 0
total_turns = 0


def recurse_play(turn_points, alice_points, kirito_points, alice_won_turns, played_turns):
    # print("check turn_points {}, alice_points {}, kirito_points {}, alice_won_turns {}, played_turns {}"
    #       .format(turn_points, alice_points, kirito_points, alice_won_turns, played_turns))

    if alice_points > alice_required or kirito_points > kirito_required:
        return -1
    if alice_points == alice_required and kirito_points == kirito_required:
        return alice_won_turns
    if played_turns == total_turns:
        return -1

    alice_loose = recurse_play(turn_points + 2, alice_points, kirito_points + turn_points, alice_won_turns,
                               played_turns + 1)

    if alice_loose != -1:
        return alice_loose

    alice_win = recurse_play(turn_points + 2, alice_points + turn_points, kirito_points, alice_won_turns + 1,
                             played_turns + 1)

    # print("alice loose {}, alice win {}".format(alice_loose, alice_win))

    return alice_win


def find_minimum_value(x, y):
    global alice_required
    global kirito_required
    global total_turns
    alice_required = x
    kirito_required = y
    total_turns = sqrt(x + y)
    print("total_turns {}".format(total_turns))
    if total_turns != trunc(total_turns):
        return -1
    return recurse_play(1, 0, 0, 0, 0)


def run_line(match):
    x = int(match.group(1))
    y = int(match.group(2))
    expected = int(match.group(3))
    actual = find_minimum_value(x, y)
    assert_equals(actual, expected)


test_solution(__file__, '(\\d+), (\\d+)\\s+(-?\\d+)', run_line)
