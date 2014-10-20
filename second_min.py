'''
      Algorithms (MIT - CLRS)
      Section 9.1 - Exercise 1

      Find the second smallest element
      in an array of n distinct integers
      within n + lg n - 2 comparisons

      Intuition: We can find the minimum element
      in n-1 comparisons. If we keep track of the
      numbers the minimum was compared to, then this
      set will contain lg n elements, and must include
      the second smallest element (think of it like a
      tournament - the only element that can beat the
      second smallest element is the minimum). Hence,
      we do another lg n - 1 comparisons in this smaller
      set to find the second smallest integer.

'''
import random as r

n = 10000

winners = [ i for i in range(n)]
losers = [[] for i in range(n)]

r.shuffle(winners)

def tournament(w, l):

    win = []
    lose = []

    if len(w) == 1:
        return l

    for i in range(1, len(w), 2):
        if w[i] > w[i-1]:
            win.append(w[i-1])
            lose.append([l[i-1], w[i]])
        else:
            win.append(w[i])
            lose.append([l[i], w[i-1]])

    if len(w) % 2 == 1:
        win.append(w[len(w) - 1])

    return tournament(win, lose)


comp = tournament(winners,losers)
comp = comp[0]

li = []
while comp:
	li.append(comp[1])
	comp = comp[0]

print(min(li))

'''
# Quick way:

winners.remove(min(winners))
w = winners
print(min(w))
'''





