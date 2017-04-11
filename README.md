## McCluskey
Resolve algorithm of [Quine McCluskey](https://en.wikipedia.org/wiki/Quine%E2%80%93McCluskey_algorithm). 
This is useful for simplify your boolean functions, like [Karnaugh tables](https://en.wikipedia.org/wiki/Karnaugh_map), with a better algorithm, which is used to reduce the cost of logical ports of your logical circuit.

# Boolean values
```
a = a
~a = A
```

# Getting Started

Firstly you select how do you prefer to introuduce the main function.

### MAXTERMS (option 1)
Example: 
```
f(A,B,C) = (A + b)(a + B + c)
```
### MINTERMS (option 2)
Example: 
```
f(A, B, C) = Ab + aBc
```

## INTRODUCE YOUR 'd' terms
Before you introuduce your function, you can introduce some 'd' terms which will be used only if they were needed to simplify your logical results.

## FINALLY
When execution is finisihed, it returns the solution of apply Quine McCluskey to your function.
