from python.test_utils import test_solution, assert_equals, get_ints


def replace(reels, from_index, moves):
    # print("moving from index ", from_index)
    empty = reels.index(0)
    reels[empty] = -reels[from_index]
    reels[from_index] = 0
    moves.append(from_index)
    # print(reels)
    return empty


def move_another(reels, avoid_index, moves):
    # print("move another, avoid ", avoid_index)
    for i in range(len(reels)):
        if i != avoid_index and reels[i] != 0:
            replace(reels, i, moves)
            return True

    return False


def empty_last(reels, avoid_index, moves):
    # print("empty last, avoid ", avoid_index)
    last_index = len(reels) - 1
    if reels[last_index] == 0:
        return True

    if avoid_index == last_index:
        return False

    replace(reels, last_index, moves)
    return True


def reels_iteration(reels):
    moves = []
    # print("iteration", reels)
    if len(reels) == 1:
        if reels[0] == 0:
            return moves, True
        else:
            return moves, False

    movie = reels[0]
    movie_index = 0
    for i, m in enumerate(reels):
        if abs(m) > abs(movie):
            movie = m
            movie_index = i

    while True:
        # print("working on", movie, "index", movie_index, "reels", reels)
        if movie_index == len(reels) - 1:
            if movie > 0:
                return moves, True
            else:
                movie_index = replace(reels, movie_index, moves)
                movie = -movie
        else:
            if movie > 0:
                if reels[-1] == 0:
                    ok = move_another(reels, movie_index, moves)
                    if not ok:
                        return moves, False
                else:
                    movie_index = replace(reels, movie_index, moves)
                    movie = -movie
            else:
                ok = empty_last(reels, movie_index, moves)
                if not ok:
                    return moves, False

                # print("move to last")
                movie_index = replace(reels, movie_index, moves)
                movie = -movie


def reels_sort(reels):
    all_moves = []
    for i in range(len(reels)):
        segment = reels[:len(reels) - i]
        moves, ok = reels_iteration(segment)
        reels[:len(reels) - i] = segment
        # print("iteration solve", moves, ok)
        if not ok:
            return [-1]
        all_moves.extend(moves)
    return all_moves


def run_line(match):
    reels = get_ints(match.group(1))
    expected = get_ints(match.group(2))
    actual = reels_sort(reels)
    print("actual:", actual)
    if len(expected) == 1 and expected[0] == -1:
        assert_equals(actual, expected)
    if len(actual) == 1 and actual[0] == -1:
        assert_equals(actual, expected)


test_solution(__file__, '{(.*)}\\s+{(.*)}', run_line)
