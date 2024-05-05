% Facts about symptoms
has_symptom(john, fever).
has_symptom(john, cough).
has_symptom(mary, headache).
has_symptom(mary, fever).

% Facts about possible diagnoses
possible_diagnosis(fever, flu).
possible_diagnosis(cough, cold).
possible_diagnosis(headache, stress).


has_diagnosis(Person, Diagnosis) :-
    has_symptom(Person, Symptom),
    possible_diagnosis(Symptom, Diagnosis).

has_serious_condition(Person) :-
    has_symptom(Person, Symptom1),
    has_symptom(Person, Symptom2),
    Symptom1 \= Symptom2.

suggest_lifestyle_change(Person) :-
    has_diagnosis(Person, Diagnosis),
    Diagnosis == stress,
    write('Consider reducing stress through lifestyle changes.').

