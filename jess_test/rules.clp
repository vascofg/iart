(import jess_test.Sala)
(import jess_test.World)
(import java.lang.Boolean)

(defclass Sala Sala)
(defclass World World)

(reset)

(bind ?true (new java.lang.Boolean TRUE)) /*because jess*/
(bind ?false (new java.lang.Boolean FALSE))
(defrule ideal
	?s <- (Sala  {temperatura < (+ (get-member World tempIdeal) 1) 
	&& temperatura > (- (get-member World tempIdeal) 1)}) => (mantem ?s)
)

(defrule baixa
	?s <- (Sala  {temperatura < (- (get-member World tempIdeal) 1)}) => (aqueceQuarto ?s)
)




(defrule alta
	?s <- (Sala {temperatura > (+ (get-member World tempIdeal) 1)}) => (arrefeceQuarto ?s)
)


(deffunction aqueceQuarto (?s)
	(if (neq ?s.aquecedor nil) then
		(modify ?s (aquecedor ?true))
		(modify ?s (janela ?false)))
	(if (neq ?s.janela nil) then
		(modify ?s (janela ?false)))
)

(deffunction arrefeceQuarto (?s)
	(if (neq ?s.aquecedor nil) then
		(modify ?s (aquecedor ?false)))
	(if (neq ?s.janela nil) then
		(modify ?s (janela ?true)))
)

(deffunction mantem (?s)
	(if (neq ?s.aquecedor nil) then
		(modify ?s (aquecedor ?false)))
	(if (neq ?s.janela nil) then
		(modify ?s (janela ?false)))
)