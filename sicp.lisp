;; ----- Exercise 2.2 -----
(define (make-point x y) (cons x y))
(define (x-coord p) (car p))
(define (y-coord p) (cdr p))
(define (print-point p)
	(newline)
	(display "(")
	(display (x-coord p))
	(display ",")
	(display (y-coord p))
	(display ")")
)

(define (avg s proc) 
		( \ ( + (proc (start-segment s)) (proc (end-segment s))) 2.0 )
)


(define (make-segment p1 p2) (cons p1 p2))
(define (start-segment s) (car s))
(define (end-segment s) (cdr s))
(define (midpoint-segment s) 
	(cons 
		(avg s x-coord)
		(avg s y-coord)
	)
)

;; ----- Exercise 2.17 -----
(define (last-pair li)
	(if (null? (cdr li))
		car li
		(list-pair (cdr li))
	)
)

;; item -> pair -> list (mapping) -> trees (mapping)

(define (map proc items)
	(if (null? items)
		nil
		(cons (proc (car items))
			  (map proc (cdr items)))))

(define (count-leaves x)
	(cond ((null? x) 0)
		  ((not (pair? x)) 1)
		  ( else (+ (count-leaves (car x))
					(count-leaves (cdr x))))))

;; Enumerate, filter, map, accumulate

;; Enumerate
(define (enumerate tree)
	(cond ((null? tree) nil)
		  ((not (pair? tree)) (list tree))
		  (else (append (enumerate (car tree))
						(enumerate (cdr tree)))))


;; Filter - return list of items in sequence that meets the predicate
(define (filter predicate sequence)
	(cond ((null? sequence) nil)
		  ((predicate (car sequence))
		   (cons (car sequence)
				 (filter predicate (cdr sequence))))
		  (else (filter predicate (cdr sequence)))))

;; Accumulator
(define (accumulate op initial sequence)
	(if (null? sequence)
		initial
		(op (car sequence)
			(accumulate op initial (cdr sequence)))))




;; Section 2.3 Nested Mappings























