(import jess_test.Sala)
(import java.lang.Boolean)

(defclass Sala Sala)

(bind ?true (new java.lang.Boolean TRUE)) /*because jess*/
(bind ?false (new java.lang.Boolean FALSE))

(defrule baixa
	?s <- (Sala) (Sala  {temperatura <= 15}) => (aqueceQuarto ?s)
)


(defrule alta
	?s <- (Sala) (Sala {temperatura >= 30}) => (modify ?s (aquecedor ?false) (janela ?true) )
)


(deffunction aqueceQuarto (?s)
	(if (neq ?s.aquecedor nil) then
		(modify ?s (aquecedor ?true)))
	(if (neq ?s.janela nil) then
		(modify ?s (janela ?false)))
)