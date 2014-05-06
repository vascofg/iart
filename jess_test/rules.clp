(import jess_test.Sala)

(defclass Sala Sala)

(defrule baixa
	?s <- (Sala) (Sala  {temperatura < 15}) => (modify ?s  (janela 0) (aquecedor 1) )
)

(defrule alta
	?s <- (Sala) (Sala {temperatura > 30}) => (modify ?s (aquecedor 0) (janela 1) )
)


