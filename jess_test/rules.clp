(import jess_test.Sala)
(import jess_test.World)
(import java.lang.Boolean)

(defclass Sala Sala)
(defclass World World)

(reset)

(bind ?true (new java.lang.Boolean TRUE)) /*because jess*/
(bind ?false (new java.lang.Boolean FALSE))

(defrule baixa
	?s <- (Sala  {temperatura < (get-member World tempIdeal)}) => (aqueceQuarto ?s)
)

(defrule ideal
	?s <- (Sala  {temperatura == (get-member World tempIdeal)}) => (mantem ?s)
)


(defrule alta
	?s <- (Sala {temperatura > (get-member World tempIdeal)}) => (arrefeceQuarto ?s)
)


(deffunction aqueceQuarto (?s)
	(if (neq ?s.aquecedor nil) then
		(modify ?s (aquecedor ?true)))
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