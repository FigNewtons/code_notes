'''
				Numerical Analysis 
			  Created by: Fig Newtons

A collection of algoritms useful for numerical analysis.


'''

'''
Horner's Method: 
Evaluates a polynomial at value x. 

Algorithmic Complexity: O(n) (as compared to O(n^2) multiplications and O(n) additions)

Given a list of coefficients [a_0, a_1, ..., a_n]
and a value x for the polynomial f(x) = a_0 + a_1x + a_2x^2 + ... + a_nx^n,
this algorithm computes the value at x using n additions and n 
multiplications.

This is done by treating the polynomial as such:

a_0 + x(a_1 + x(a_2 + x(....x(a_n + 0x))))

And so, we iterate backwards through the coefficient
list, each time computing a new total:

total = a_i + (total * x) 

'''
def horner(co, x):
	total = 0
	for i in range(len(co) - 1, -1, -1):
		total = co[i] + (total * x)
	return total


'''
Strassen's Algorithm:
Matrix Multiplication

T(n) = 7T(n/2) + O(n^2)

a = 3
b = 2

O(n^2) = O(n^(lg(7-3)) 

Thus, by Master Theorem, 
T(n) is in O(n^lg 7) = O(n^2.81)

Algorithmic Complexity: O(n^2.81) (as compared to O(n^3))

'''

'''
Karatsuba's Algorithm:
Multiplying Large Numbers

T(n) = 3T(n/2) + O(n)

a = 3
b = 2

O(n) = O(n^lg(3 - 1)) 

Thus, by Master Theorem,
T(n) is in O(n^lg 3) = O(n^1.585)

Algorithmic Complexity: O(n^lg 3) (as compared to O(n^2))

'''

'''
Dynamic Programming

Rod-cutting problem

Given a list of prices p[] of size i, determine what cuts
should be made to a rod of size n to maximize profit. There
are 2^(n-1) ways to cut said rod, but this algorithm runs
in 0(n^2), precomputing solutions for smaller rod sizes and
saving them in a list to avoid redundancy. 

s[] is list of sizes the first cut should be

'''
price = [ 0, 1, 5, 8, 9, 10, 17, 17, 20, 24, 30]


def cut_rod(p, n):
	r = [0] * (n + 1)
	s = [0] * (n + 1)
	for j in range(1, n + 1):
		q = float("-inf") 
		for i in range(1, j + 1):
			if q < p[i] + r[j - i]:
				q = p[i] + r[j - i]
				s[j] = i
		r[j] = q

	return [ r[n], s[n] ]

print(cut_rod(price, 7))









