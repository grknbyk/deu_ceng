import math


def binomial_probability(n, k, p):
    """
    Calculate the probability of getting k successful outcomes in n trials,
    given a probability of success p in each trial.
    """
    # use the formula for binomial probability
    prob = (p**k) * ((1-p)**(n-k)) * (math.factorial(n) / (math.factorial(k) * math.factorial(n-k)))
    return prob

def poisson_probability(k, lamda):
    """
    Calculate the probability of getting k occurrences of an event
    in a certain amount of time, given the average number of occurrences
    lamda in that amount of time.
    """
    # use the formula for Poisson probability
    prob = (lamda**k * math.exp(-lamda)) / math.factorial(k)
    return prob

def hypergeometric_probability(n, N, k, K):
    """
    Calculate the probability of getting k successes in a sample of size n
    from a population of size N with K successes.
    """
    # use the formula for hypergeometric probability
    prob = (math.comb(K, k) * math.comb(N - K, n - k)) / math.comb(N, n)
    return prob

tot = 0
for i in range(0,4):
    tot += hypergeometric_probability(3,10,i,5)

print(tot)