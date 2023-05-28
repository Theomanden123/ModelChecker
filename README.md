# Model Checker

## How to use the program
When you run the java program it should write the following in your terminal: "Choose frame: ".
You have the option choosing between several prebuilt frames or specifying your own.

You choose a prebuilt frame by typing the following commands:
- "Alphabet" 
- "Distinct" 
- "Muddy"  
- "Poker"  
- "Sunshine" 
- "Serial" 
- "Reflexive"
- "Transitive"
- "Symmetric"
- "System4"
- "System5"

You can view the frames graphically in the project folder frameImages.
*Note*: 
- In the Muddy frame inorder to write M_a you have to write "a" instead, M_b = "b" and M_c = "c".  
- In the Poker frame the upper-case "A", "Q" and "J" represent the card of Alice have and the lower-case is Bob's card.

To create a new frame you type "New". After this you be asked to specify what worlds exists, which you do by typing strings with spaces inbetween them. An example of a frame with 4 worlds could be: "W1 W2 W3 W4". Then you are asked, how many agents there exists, which you define with a single integer. When there are >= 2 agents, you are given the following message "What is the name of agent number 0 ?" here you specify a name for every agent. After that you are asked to specify which propositions are true at each world. An example could be: "p q r". All the negations are added to the worlds automatically. Then you are asked to define the outgoing relations for each world and what agents have access to it.

After you have successfully chosen or built a frame the program prompts you to input a formula.
Here is the syntax for writing a formula in our program:

- Proposition: "p"
- Negation: "Not(p)"
- Logical And: "And(p,q)"
- Logical Or: "Or(p,q)"
- Implication: "Imp(p,q)"
- Equivalence: "Equiv(p,q)"
- Box operator: "Box(p)"
- Diamond operator: "Diamond(p)"
- Knowledge: "Ki(p)"  (Where i is the name of the agent)
- Everybody knows: "E{a,b,c}(p)" (Where {a,b,c} is the list of agents)
- Common knowledge: "C{a,b,c}(p)"
- Distributed knowledge: "D{a,b,c}(p)"
- Public announcement: "\[!p\](p)"

You can nest these formulas: "Not(And(Or(p,q), Imp(q,p)))"

Typing "Exit" will terminate the program.

## Authorship

The majority of the code was written with the use of pair programming. But Victor was responsible for most of the code for the axiomatic functionality and Theo was mostly responsible for the Parser code. Other than that, we worked on everything else together.
