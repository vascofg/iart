(import jess_test.Sala)
(import java.lang.Boolean)

(defclass Sala Sala)

(reset)

(bind ?true (new java.lang.Boolean TRUE)) /*because jess*/
(bind ?false (new java.lang.Boolean FALSE))

(defrule baixa
	?s <- (Sala  {temperatura <= 15}) => (aqueceQuarto ?s)
)

/*(defrule ideal
	?s <- (Sala  {temperatura > 15 && temperatura < 30}) => (mantem ?s)
)*/


(defrule alta
	?s <- (Sala {temperatura >= 30}) => (arrefeceQuarto ?s)
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