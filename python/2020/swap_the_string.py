from python.test_utils import test_solution, assert_equals


def generate(p, a0, n, x, y):
    a = [a0]
    for i in range(1, n):
        curr = a[i - 1] * x + y
        a.append(curr % 1812447359)

    s = p
    for i in range(len(p), n):
        c = a[i] % 26 + ord('a')
        s = s[:i] + chr(c) + s[i + 1:]

    return s


def replace_count(s):
    moves = 0
    ord_a = ord('a')
    letters = [0] * 26
    for i in range(0, len(s)):
        curr_ord = ord(s[i]) - ord_a
        moves += sum(letters[:curr_ord])
        letters[curr_ord] += 1
    return moves


def find_number_of_swaps(p, a0, x, y, n, k):
    s = generate(p, a0, n, x, y)

    score = 0
    for i in range(k):
        part = ""
        while i < len(s):
            part += s[i]
            i += k
        score += replace_count(part)
    return score


def run_line(match):
    p = match.group(1)
    a0 = int(match.group(2))
    x = int(match.group(3))
    y = int(match.group(4))
    n = int(match.group(5))
    k = int(match.group(6))
    expected = int(match.group(7))
    actual = find_number_of_swaps(p, a0, x, y, n, k)
    assert_equals(actual, expected)


test_solution(__file__, '"(.*)", (\\d+), (\\d+), (\\d+), (\\d+), (\\d+)\\s+(\\d+)', run_line)
