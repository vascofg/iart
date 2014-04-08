(import jess_test.Sala)

(defclass Sala Sala)

(defrule alta
	?s <- (Sala) (Sala {temperatura > 90} {temperatura < 120}) => (modify ?s (temperatura 500))
)