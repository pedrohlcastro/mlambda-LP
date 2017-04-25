5 : s;
4 : f;
new zero [s] : array;
array.show();
new rand [s] : b;
b.show();
println(b.at(s-2));
println(b.at(1));
println(b.size());
array.set(1,s-1); #com set(s-1,s-2) nao ta fucionando, primeiro paramentro com erro
array.show();
b.sort();
b.show();
new fill[s-1,s-3] : dois; #cria vetor de 2
dois.show();


#nao funciona
b.add(array);
b.show();
b.add(13);
b.show();
b.filter(n -> n>0).size() : amen;
println(amen);