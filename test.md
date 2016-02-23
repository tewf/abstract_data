# Entropy


## Bayes Rule

$$
\begin{align*}
  P(a,b)&=P(a│b)P(b) \\
  P(a,b)&=P(b,a) \\
  P(a│b)&=\frac{P(b│a)P(a)}{P(b)}
\end{align*}
$$

Definition of *independence*: $P(a,b)=P(a│b)P(b)=P(a)P(b)$

## Information
The *information* we obtain observing the occurrence of an event with probability $p$.
We define information in terms of the probability of $$p$$.

1. $I(p)≥0$
2. $I(1)=0$
3. $I(p_1*p_2)=I(p_1)+I(p_2)$
  * Two independent events occur, then the *information* obtained from
  observing is the sum of the two informations.
4. $I(p^n)=n*I(p) \space; where \space 0 < p <= 1 \space and \space n > 0$
5. $I(p) = -log_b(p)=log_b(1/p)$ for base $b$ determining the units we're using.

*Example*:
Flipping a fair coin:    $I(1/2)=-log_2(1/2)=-(-1)=1$ bit of information.  
Flipping $n$ fair coins: $nI(1/2)=-log_2((1/2)^n)=log_2(2^n)=n$ bit of information.

$hthht$ in bit encoding $10110$

### Entropy Theory
Suppose a source is providing us a stream of symbols $\{s_1, s_2, ..., s_k\}$ with probabilities $\{p_1, p_2, ..., p_k\}$.

What is the average *information* we get from each symbol in the stream?

Symbol $s_i$ gives us $log(1/p_i)$ *information*. In the long run we observe $N$ so we see $N*p_i$ of $s_i$.  Thus $N$ independent observations the total information is:
$$ I = \sum_{i=1}^k(N*p_i)*log(1/p_i) $$

With average *information* dividing $I$ by $N$:
$$ I = \sum_{i=1}^kp_i*log(1/p_i) $$

We have defined *information* strictly in terms of probabilities of events. Say we have a set of probabilities $P= \{p_1, p_2, ..., p_k\}$ (a probability distribution). We define the entropy of the distribution $P$ by:

$$ H(P) = \sum_{i=1}^kp_i*log(1/p_i)$$

$H(P)$ is the Shannon TODO.

The *entropy* of a probability distribution is just the *expected value* of the *information* of the distribution.
$$ H(P) = < I(p) > $$

$Min: H(P) = 0$ when exactly one of the $p_i$'s is 1 and the rest are 0.  
$Max: H(P) = 1$ when all of the events have the same probability $\frac{1}{k}$.

The max of the entropy is the log of the number of possible events, and maxes when all the events are equally likely.  Remember, $I(1)=0$. A two headed coin provides zero entropy but a fair coin provides the most entropy.

#### Kullback-Leibler Information Measure
Suppose you have a true distribution of a process given by $P=p(x)$ and a proposed distribution $Q=q(x)$. The Kullback-Leibler is a measure of how well $Q$ fits $P$:

$$ KL(P;Q)=\left<log⁡\left(\frac{p(x)}{q(x)}\right)\right>_P=
\large\sum_{x}p(x)log\left(\frac{p(x)}{q(x)}\right) $$

This is the $P$ expected value of the difference of the logs.

These properties hold true for $KL$:   
 - $KL(P;Q)≥0$     
 - $KL(P;Q)=0⇔p(x)=q(x)$

## TODO: Cross entropy

[An introduction to information theory and entropy](http://astarte.csustan.edu/~tom/SFI-CSSS/info-theory/info-lec.pdf)
