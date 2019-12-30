from python.test_utils import test_solution, assert_equals, get_ints

DIGITS = 5
primes_numbers = None
primes_digits = None
index = None
sums = None


def find_primes_numbers():
    global primes_numbers
    if primes_numbers is not None:
        return

    limit = 10 ** DIGITS
    n = [True] * limit
    for i in range(2, limit // 2):
        if n[i]:
            multiplier = 2 * i
            while multiplier < limit:
                n[multiplier] = False
                multiplier += i

    primes_numbers = []
    for i in range(limit // 10, limit):
        if n[i]:
            primes_numbers.append(i)

    print(primes_numbers)


def find_primes_digits():
    global primes_digits
    if primes_digits is not None:
        return

    primes_digits = []
    for prime in primes_numbers:
        prime_str = str(prime)
        t = []
        for d in range(0, DIGITS):
            digit = int(prime_str[d])
            t.insert(0, digit)
        primes_digits.append(t)

    print(primes_digits)


def index_primes():
    global index
    if index is not None:
        return

    index = []
    for d in range(DIGITS):
        digits = []
        for i in range(10):
            digits.append([])
        index.append(digits)

    for i, prime in enumerate(primes_digits):
        for d in range(DIGITS):
            index[d][prime[d]].append(i)

    print(index)
    return index


def recursive_find(digit_index, possible_first, possible_second, possible_third):
    if len(possible_first) == 0 or len(possible_second) == 0 or len(possible_third) == 0:
        return None

    if digit_index == len(sums):
        return possible_first, possible_second, possible_third

    digit_sum = sums[digit_index]
    for first_digit in range(0, min(digit_sum + 1, 10)):
        for second_digit in range(0, min(digit_sum + 1 - first_digit, 10)):
            third_digit = digit_sum - first_digit - second_digit
            if third_digit > 9:
                continue

            first_options = index[digit_index][first_digit]
            possible_first_next = [v for v in possible_first if v in first_options]
            second_options = index[digit_index][second_digit]
            possible_second_next = [v for v in possible_second if v in second_options]
            third_options = index[digit_index][third_digit]
            possible_third_next = [v for v in possible_third if v in third_options]
            result = recursive_find(digit_index + 1, possible_first_next, possible_second_next, possible_third_next)
            if result is not None:
                return result
    return None


def get_primes(sums_input):
    global sums
    sums = sums_input
    find_primes_numbers()
    find_primes_digits()
    index_primes()

    possible_first = [i for i in range(len(primes_digits))]
    possible_second = [i for i in range(len(primes_digits))]
    possible_third = [i for i in range(len(primes_digits))]
    result = recursive_find(0, possible_first, possible_second, possible_third)
    print(result)
    if result is None:
        return []

    result = [primes_numbers[result[0][0]], primes_numbers[result[1][0]], primes_numbers[result[2][0]]]
    print(result)
    return result


def run_line(match):
    sums = get_ints(match.group(1))
    expected = get_ints(match.group(2))
    actual = get_primes(sums)
    assert_equals(len(actual), len(expected))


test_solution(__file__, '{(.*)}\\s+{(.*)}', run_line)
