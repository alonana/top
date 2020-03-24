from python.test_utils import test_solution, assert_equals


class Recurse:
    def __init__(self, n):
        self.a_sum = 0
        self.b_sum = 0
        self.c_sum = 0
        self.a_elements = 0
        self.b_elements = 0
        self.c_elements = 0

        self.series_len = 6 * n
        self.series_sum = self.series_len * (1 + self.series_len) / 2
        self.unit_sum = self.series_sum / 4
        self.unit_size = 2 * n

    def solve(self):
        if self.series_sum % 4 != 0:
            return ""
        result = self.recurse("")
        if result is None:
            return ""
        return result

    def recurse(self, s):
        # print(self.a_elements, self.b_elements, self.c_elements, s)
        if len(s) == self.series_len:
            if self.a_sum == self.b_sum and self.c_sum == 2 * self.a_sum:
                return s
            return None

        element_value = len(s) + 1

        if self.a_elements < self.unit_size and self.a_sum + element_value > self.unit_sum:
            return None

        if self.b_elements < self.unit_size and self.b_sum + element_value > self.unit_sum:
            return None

        if self.c_elements < self.unit_size and self.c_sum + element_value > 2 * self.unit_sum:
            return None

        if self.a_elements == self.unit_size and self.a_sum != self.unit_sum:
            return None

        if self.b_elements == self.unit_size and self.b_sum != self.unit_sum:
            return None

        if self.c_elements == self.unit_size and self.c_sum != self.unit_sum * 2:
            return None

        if self.a_sum + element_value <= self.unit_sum and self.a_elements < self.unit_size:
            self.a_sum += element_value
            self.a_elements += 1
            result = self.recurse(s + 'a')
            if result is not None:
                return result
            self.a_sum -= element_value
            self.a_elements -= 1

        if self.b_sum + element_value <= self.unit_sum and self.b_elements < self.unit_size:
            self.b_sum += element_value
            self.b_elements += 1
            result = self.recurse(s + 'b')
            if result is not None:
                return result
            self.b_sum -= element_value
            self.b_elements -= 1

        if self.c_sum + element_value <= self.unit_sum * 2 and self.c_elements < self.unit_size:
            self.c_sum += element_value
            self.c_elements += 1
            result = self.recurse(s + 'c')
            if result is not None:
                return result
            self.c_sum -= element_value
            self.c_elements -= 1


def create_partition(n):
    return Recurse(n).solve()


def run_line(match):
    n = int(match.group(1))
    expected = match.group(2)
    actual = create_partition(n)
    assert_equals(len(actual), len(expected))


test_solution(__file__, '(\\d+)\\s+"(.*)"', run_line)
