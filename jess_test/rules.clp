(import jess_test.Sala)
(import jess_test.World)
(import java.lang.Boolean)

(defclass Sala Sala)
(defclass World World)

(reset)

(defglobal ?*true* = (new java.lang.Boolean TRUE)) /*because jess*/
(defglobal ?*false* = (new java.lang.Boolean FALSE))

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


(defrule sensorMon
	?s <- (Sala {movimento == TRUE}) =>(peopleOn ?s)
)

(defrule sensorMoff
	?s <- (Sala {movimento == FALSE}) =>(peopleOff ?s)
)

(deffunction peopleOn (?s)
	(if (> (get-member World luzIdeal) 2000) then /*quer luz*/
		(if (> (get-member World luminosidade) 2000) then
			(if (neq ?s.persiana nil) then
				(modify ?s (persiana ?*true*))
				(if (neq ?s.lampada nil) then
					(modify ?s (lampada ?*false*)))
			else
			(if (neq ?s.lampada nil) then
				(modify ?s (lampada ?*true*))))
		else
			(if (neq ?s.lampada nil) then
				(modify ?s (lampada ?*true*))
				(if (neq ?s.persiana nil) then
				(modify ?s (persiana ?*false*)))))
		else
			(if (neq ?s.persiana nil) then
				(modify ?s (persiana ?*false*)))
			(if (neq ?s.lampada nil) then
				(modify ?s (lampada ?*false*)))
	)
)
		
(deffunction peopleOff (?s)
	(if (neq ?s.lampada nil) then
		(if (eq ?s.lampada ?*true*) then
			(modify ?s (lampada ?*false*))
		else
		(if (neq ?s.persiana nil) then
			(if (< (get-member World luminosidade) 2000) then
				(modify ?s (persiana ?*false*)))))
	else
		(if (neq ?s.persiana nil) then
			(if (< (get-member World luminosidade) 2000) then
				(modify ?s (persiana ?*false*))))))
				
(deffunction aqueceQuarto (?s)
	(if (neq (get-member World poupanca) ?*true*)
		(if (neq ?s.aquecedor nil) then
			(if(neq ?s.janela nil) then
				(modify ?s (janela ?*false*))
				(modify ?s (aquecedor ?*true*))
			else
				(modify ?s (aquecedor ?*true*)))
		else
			(if (neq ?s.janela nil) then
				 (if (> (- (get-member World temperatura) ?s.temperatura) 5) then
				 	(if (< (get-member World humidade) 85) then
					(modify ?s (janela ?*true*))
				else	
					(modify ?s (janela ?*false*))))))
	else
		(if(neq ?s.janela nil) then
			(if (> (- (get-member World temperatura) ?s.temperatura) 5) then
				(if (< (get-member World humidade) 85) then
				(modify ?s (janela ?*true*)))
			else
				(if (neq ?s.aquecedor nil) then
				(modify ?s (aquecedor ?*true*))))))


)

(deffunction arrefeceQuarto (?s)
	(if (neq ?s.ac nil) then
		(modify ?s (ac ?*true*))
		(if (neq ?s.janela nil) then
			(modify ?s (janela ?*false*)))
	else
		(if (neq ?s.janela nil) then
			(if (> (- ?s.temperatura (get-member World temperatura)) 5) then
				(modify ?s (janela ?*true*))
			else
				(modify ?s (janela ?*false*))))))
	
	
	

(deffunction mantem (?s)
	(if (neq ?s.ac nil) then
		(modify ?s (ac ?*false*)))
	(if (neq ?s.aquecedor nil) then
		(modify ?s (aquecedor ?*false*)))
	(if (neq ?s.janela nil) then
		(modify ?s (janela ?*false*)))
)