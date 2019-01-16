from python.test_utils import test_solution

products = []


class Product:
    def __init__(self, product1, product2, parallel):
        self.product1 = product1
        self.product2 = product2
        self.parallel = parallel
        value1 = 1000000000 if product1 == -1 else products[product1].value
        value2 = 1000000000 if product2 == -1 else products[product2].value
        if parallel:
            self.value = 1 / (1 / value1 + 1 / value2)
        else:
            self.value = value1 + value2

        print(self)

    def __repr__(self):
        return "{},{},{} = {:,}".format(
            self.product1,
            self.product2,
            "Parallel" if self.parallel else "Serial",
            self.value
        )


def construct(nanoOhms):
    print("{:,}".format(nanoOhms))
    products.append(Product(-1, -1, False))
    products.append(Product(-1, -1, False))

    while (products[-1].value != nanoOhms):
        if len(products) > 1000:
            raise Exception("Failed!")
        if products[-1].value < nanoOhms:
            last = len(products) - 1
            products.append(Product(last, last - 1, False))
        else:
            last = len(products) - 1
            products.append(Product(last, last - 1, True))


def run_line(match):
    nanoOhms = int(match.group(1))
    construct(nanoOhms)


test_solution(__file__, '(\\d+)', run_line)
