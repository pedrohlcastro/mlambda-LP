load("Entre com tamanho: ") : s;
new zero [s] : array;
0 : i;
array.apply(n -> i + 1 : i; load("Valor ",i,": ") : n;);
array.show();
# Count the odds and even.
0 : par, impar;
array.each(n -> if n % 2 == 0 {
  par + 1 : par;
} else {
  impar + 1 : impar;
});


# Print the result.
println("Voce entrou com " , par , " par(es) e " , impar , " impar(es)");
