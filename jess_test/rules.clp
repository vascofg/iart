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

(defrule escuro
	?s <- (Sala { luz < 1000}) => (iluminaQuarto ?s)
)

(defrule claro
	?s <- (Sala { luz > 8000}) => (escuraQuarto ?s)
)

(deffunction iluminaQuarto (?s)
	(if (neq ?s.persiana) then
		(modify ?s (persiana ?true)))
	(if (neq ?s.lampada) then
		(modify ?s (lampada ?true)))
)

(deffunction escuraQuarto (?s)
	(if (neq ?s.persiana) then
		(modify ?s (persiana ?false)))
	(if (neq ?s.lampada) then
		(modify ?s (lampada ?false)))
)

(deffunction aqueceQuarto (?s)
	(if (neq ?s.aquecedor nil) then
		(if(neq ?s.janela nil) then
			(modify ?s (janela ?false))
			(modify ?s (aquecedor ?true))
		else
			(modify ?s (aquecedor ?true)))
	else
		(if (neq ?s.janela nil) then
			 (if (> (- (get-member World temperatura) ?s.temperatura) 5) then
				(modify ?s (janela ?true))
			else	
				(modify ?s (janela ?false)))))
		
	
)

(deffunction arrefeceQuarto (?s)
	(if (neq ?s.ac nil) then
		(modify ?s (ac ?true))
		(if (neq ?s.janela nil) then
			(modify ?s (janela ?false)))
	else
		(if (neq ?s.janela nil) then
			(if (> (- ?s.temperatura (get-member World temperatura)) 5) then
				(modify ?s (janela ?true))
			else
				(modify ?s (janela ?false))))))
	
	
	

(deffunction mantem (?s)
	(if (neq ?s.ac nil) then
		(modify ?s (ac ?false)))
	(if (neq ?s.aquecedor nil) then
		(modify ?s (aquecedor ?false)))
	(if (neq ?s.janela nil) then
		(modify ?s (janela ?false)))
)