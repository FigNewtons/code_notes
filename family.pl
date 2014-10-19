% Use consult('~/path/to/file'). to load the data

female(mary).
female(sandra).
female(juliet).
female(lisa).

male(peter).
male(paul).
male(dick).
male(bob).
male(harry).

parent(bob, lisa).
parent(bob, paul).
parent(bob, mary).

parent(juliet, lisa).
parent(juliet, paul).
parent(juliet, mary).

parent(peter, harry).

parent(lisa, harry).

parent(mary, dick).
parent(mary, sandra).


sibling(X,Y) :- parent(Z,X), parent(Z,Y), X \= Y.
sister(X,Y) :- female(X), sibling(X,Y).
brother(X,Y) :- male(X), sibling(X,Y).

mother(X,Y) :- female(X), parent(X,Y).
father(X,Y) :- male(X), parent(X,Y).

grandmother(X,Y) :- female(X), parent(X, Z), parent(Z, Y).
grandfather(X,Y) :- male(X), parent(X,Z), parent(Z,Y).

cousin(X,Y) :- parent(A,X), parent(B,Y), sibling(A,B).

