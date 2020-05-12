from python.test_utils import test_solution, assert_equals

all = []


def palindrome(n):
    s = str(n)
    l = len(s)
    for i in range(l // 2):
        if s[i] != s[l - i - 1]:
            return False
    return True


def find_by_digits(digits):
    if digits == 1:
        return [x for x in range(1, 10)]

    palindromes = []
    side_length = digits // 2
    start = int('0' + '1' * side_length)
    end = int('0' + '9' * side_length)
    for i in range(start, end + 1):
        left = str(i)
        right = left[::-1]
        if side_length * 2 == digits:
            p = left + right
            palindromes.append(int(p))
        else:
            for middle in range(10):
                p = left + str(middle) + right
                palindromes.append(int(p))

    return palindromes


def find_all():
    palindromes = []
    for digits in range(1, 15):
        palindromes.extend(find_by_digits(digits))
    return palindromes


def get_min(x):
    global all
    if len(all) == 0:
        all = find_all()
    for p in all:
        if p % x == 0:
            y = p / x
            if y > 1e9:
                return -1
            return p / x
    return -1


def run_line(match):
    x = int(match.group(1))
    expected = int(match.group(2))
    actual = get_min(x)
    assert_equals(actual, expected)


test_solution(__file__, '(\\S+)\\s+(\\S+)', run_line)
