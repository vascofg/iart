(import jess_test.Sala)

(defclass Sala Sala)

(defrule baixa
	?s <- (Sala) (Sala  {temperatura <= 15} {ac != nil}) => (modify ?s  (janela FALSE) (aquecedor TRUE) )
)

(defrule baixa
	?s <- (Sala) (Sala  {temperatura <= 15} ) => (modify ?s  (janela FALSE) (aquecedor TRUE) )
)

(defrule alta
	?s <- (Sala) (Sala {temperatura >= 30}) => (modify ?s (aquecedor FALSE) (janela TRUE) )
)


