# Parametry symulacji
n_points <- 200000  # Liczba losowanych punktów

# Definicja funkcji opisującej górną krawędź obszaru (interpolacja liniowa)
f_area <- function(x) {
  approx(x = c(0, 1, 2.5, 3.5, 5, 6, 10), 
         y = c(0, 2, 2, 4, 4, 5, 0), 
         xout = x)$y
}

# 1. Losowanie punktów w prostokącie ograniczającym (X: 0-10, Y: 0-5)
x_rand <- runif(n_points, min = 0, max = 10)
y_rand <- runif(n_points, min = 0, max = 5)

# 2. Sprawdzenie, czy punkty znajdują się pod wykresem funkcji
is_inside <- y_rand <= f_area(x_rand)

# 3. Obliczenie pola powierzchni
# Pole prostokąta * (punkty wewnątrz / wszystkie punkty)
area_rectangle <- 10 * 5
estimated_area <- area_rectangle * mean(is_inside)

# Wyniki
cat("Szacowane pole powierzchni:", estimated_area, "\n")

# Wizualizacja symulacji
plot(x_rand[1:5000], y_rand[1:5000], col = ifelse(is_inside[1:5000], "deepskyblue", "lightgrey"),
     pch = 20, cex = 0.5, main = "Symulacja Monte Carlo", xlab = "x", ylab = "y")
curve(f_area(x), from = 0, to = 10, add = TRUE, col = "black", lwd = 2)