from python.test_utils import test_solution, assert_equals


def recurse_calculate(game_index,
                      wins_needed,
                      pa,
                      pb,
                      a_wins_strike,
                      b_wins_strike,
                      max_matches,
                      a_win_so_far,
                      b_win_so_far):
    # print('recurse index {}'.format(game_index))

    if max_matches == 0:
        return 0

    if a_win_so_far >= wins_needed:
        return 1
    if b_win_so_far >= wins_needed:
        return 0

    if (game_index // 2) % 2 == 0:
        use_p = pa
    else:
        use_p = 1 - pb

    if a_wins_strike > 0:
        use_p = min(use_p + 5 * a_wins_strike, 1)
    if b_wins_strike > 0:
        use_p = max(use_p - 5 * b_wins_strike, 0)

    total_p = 0
    if use_p > 0:
        a_will_win_this = recurse_calculate(game_index + 1, wins_needed, pa, pb, a_wins_strike + 1, 0, max_matches - 1,
                                            a_win_so_far + 1, b_win_so_far)
        total_p += use_p * a_will_win_this
    if use_p < 1:
        b_will_win_this = recurse_calculate(game_index + 1, wins_needed, pa, pb, 0, b_wins_strike + 1, max_matches - 1,
                                            a_win_so_far, b_win_so_far + 1)
        total_p += (1 - use_p) * b_will_win_this

    return total_p


def win_probability(wins_needed, a_wins_home, b_wins_home):
    max_matches = 2 * wins_needed - 1
    modulo = 10 ** 9 + 7
    pa = a_wins_home / 100
    pb = b_wins_home / 100
    p = recurse_calculate(0, wins_needed, pa, pb, 0, 0, max_matches, 0, 0)
    x = p * 100 ** max_matches
    return x % modulo


def run_line(match):
    wins_needed = int(match.group(1))
    a_wins_home = int(match.group(2))
    b_wins_home = int(match.group(3))
    expected = int(match.group(4))
    actual = win_probability(wins_needed, a_wins_home, b_wins_home)
    assert_equals(actual, expected)


test_solution(__file__, '(\\d+), (\\d+), (\\d+)\\s+(\\d+)', run_line)
