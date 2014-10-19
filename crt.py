import functools as f

'''
    Chinese Remainder Theorem

    Given a system of linear congruence equations:

        x = a_1 (mod m_1)
        x = a_2 (mod m_2)
        ...
        x = a_n (mod m_n)

    where m_1, m_2, ... , m_n are pairwise relatively
    prime, a unique solution exists in the form:

    x = sum(from i=0 to n) (a_i * (M_i) * inv(M_i) mod m_i) mod M

    where M = m1 * m2 * ... * m_n
    and M_i = M / m_i

    and inv(M_i) is the inverse of the value M_i in mod m_i.

    Example problem: https://files.nyu.edu/jl860/public/crt.htm

'''

a = [2, 3, 2]
m = [3, 5, 7]

M_total = f.reduce(lambda x, y: x * y, m)

# The inverse of a, u = a^-1 solves the equation: au = 1 mod n
def inv(a, n):
    for i in range(n):
        if a * i % n == 1:
            return i

x = 0
for i in range(len(a)):
    M_i = M_total / m[i]
    x += a[i] * M_i * inv(M_i, m[i])

print(x % M_total)
