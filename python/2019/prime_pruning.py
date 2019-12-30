from python.test_utils import test_solution, assert_equals


def generate_primes():
    numbers = [True] * 200000
    numbers[0] = False
    numbers[1] = False
    for i in range(2, len(numbers)):
        if numbers[i]:
            multi = i * 2
            while multi < len(numbers):
                numbers[multi] = False
                multi += i
    p = []
    for i in range(2, len(numbers)):
        if numbers[i]:
            p.append(i)
    return p


def generate_prime_alphabet():
    alphabet = [0] * 26
    for i in range(26):
        alphabet[i] = chr(ord('a') + i)
    p = generate_primes()
    return [alphabet[x % 26] for x in p]


def lexical_delete(start, s, e):
    if start > 999 or start >= len(s):
        for i in range(e):
            s.remove(min(s))
        return s, 0

    deleted = s[start:start + e + 1]

    max_item = max(deleted)
    max_index = deleted.index(max_item)

    e = e - max_index
    before = s[0:start]
    after = s[start + max_index:]
    s = before + after

    return s, e


def maximize(n, e):
    p = generate_prime_alphabet()

    final_len = n - e
    if n > 17000:
        s = ['z'] * final_len
    else:
        s = p[0:n]

        start = 0
        while e > 0:
            s, e = lexical_delete(start, s, e)
            start += 1

    return ''.join(s)


def run_line(match):
    n = int(match.group(1))
    e = int(match.group(2))
    expected = match.group(3)
    actual = maximize(n, e)
    assert_equals(actual, expected)


test_solution(__file__, '(\\d+), (\\d+)\\s+\"(.*)\"', run_line)
