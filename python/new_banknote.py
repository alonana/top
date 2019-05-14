from python.test_utils import test_solution, assert_equals, get_ints

coins = [1, 2, 5, 10, 20, 50, 100, 200, 500, 1000, 2000, 5000, 10000, 20000, 50000]


def get_coins_amount(cash):
    split_coins = []
    for coin in reversed(coins):
        coin_amount = cash // coin
        split_coins = [coin_amount] + split_coins
        cash -= coin_amount * coin
    return sum(split_coins)


def find_fewest(cash, new_banknote):
    coins_amount = get_coins_amount(cash)
    best = coins_amount
    for i in range(1, coins_amount):
        cash_without_i_banknote = cash - new_banknote * i
        if cash_without_i_banknote >= 0:
            best = min(best, i + get_coins_amount(cash_without_i_banknote))
    return best


def fewest_pieces(new_banknote, amounts_to_pay):
    result = []

    for cash in amounts_to_pay:
        result.append(find_fewest(cash, new_banknote))
    return result


def run_line(match):
    new_banknote = int(match.group(1))
    amounts_to_pay = get_ints(match.group(2))
    expected = get_ints(match.group(3))
    actual = fewest_pieces(new_banknote, amounts_to_pay)
    assert_equals(actual, expected)


test_solution(__file__, '(\\d+), {(.*)}\\s+{(.*)}', run_line)
