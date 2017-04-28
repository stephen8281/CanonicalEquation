# CanonicalEquation

An application for transforming equation into canonical form. An equation can be of any order. It may contain any amount of variables and brackets.

The equation will be given in the following form:

P1 + P2 + ... = ... + PN

where P1..PN - summands, which look like: 

ax^k

where a - floating point value;

k - integer value;

x - variable (each summand can have many variables).

 
For example:

"x^2 + 3.5xy + y = y^2 - xy + y"     should be transformed into:     "x^2 - y^2 + 4.5xy = 0"

"x = 1" => "x - 1 = 0"

"x - (y^2 - x) = 0" => "2x - y^2 = 0"

"x - (0 - (0 - x)) = 0" => "0 = 0"

etc
