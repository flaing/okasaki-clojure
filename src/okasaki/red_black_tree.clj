(ns okasaki.red-black-tree
    (:use datatype.core))

(defdatatype
    ::Color
    Red
    Black)

(defdatatype
    ::Red-Black-Tree
    Empty
    (Node color left root right))

(defun member
    ;;[elem tree]
    [_ Empty] false
    [x [Node _ a y b]] (cond (< x y) (recur x a)
                             (> x y) (recur x b)
                             :else true))

(defun ^:private balance
    ;;[color tree1 elem
    (:or [Black [Node Red [Node Red a x b] y c] z d] 
         [Black [Node Red a x [Node Red b y c]] z d] 
         [Black a x [Node Red [Node Red b y c] z d]] 
         [Black a x [Node Red b y [Node Red c z d]]]) (->Node Red (->Node Black a x b) y (->Node Black c z d))
    [c a x b]                                         (->Node c a x b))

(defn insert
    [x s]
    (let [ins (fn ins [s]
                  (caseof [s]
                          [Empty] (->Node Red Empty x Empty)
                          [[Node color a y b]] (cond (< x y) (balance color (ins a) y b)
                                                     (> x y) (balance color a y (ins b))
                                                     :else   s)))]
        (caseof [(ins s)]
                [[Node _ left root right]] (->Node Black left root right))))