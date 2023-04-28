# Raport przedstawiający wyniki porównania metod obliczania dyskretnej dwuwymiarowej funkcji splotu

Dla macierzy 1000x1000:
- Czas działania metody w Javie: 24 ms
- Czas działania metody natywnej: 44 ms.

Dla macierzy 500x500:
- Czas działania metody w Javie: 14 ms
- Czas działania metody natywnej: 12 ms.

Dla macierzy 100x100:
- Czas działania metody w Javie: 8 ms
- Czas działania metody natywnej: <1 ms.

Przy bardzo dużych problemach, JVM najwyraźniej optymalizuje wykonywanie się metody. Widać więc działanie JIT.
