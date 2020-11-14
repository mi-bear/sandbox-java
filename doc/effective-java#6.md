# Effective Java#6
see: https://github.com/jbloch/effective-java-3e-source-code/tree/master/src/effectivejava

ついに、`5. ジェネリック` がはじまった...
2004年くらいから入ったらしい。

## ジェネリック
警告なしでコンパイルできれば、正しい型である。
そういうコードを生成する。

```
List.java

interface List<E> {
    void add(E e) {...}
...
}
```

List.java をコンパイルすると、List.class ができる。

```List.class
interface
List {
    void add (Object e) {...}
}
```

型安全の話:

```
Object
↑ (継承)
String
```
上記は継承が成り立つ。しかし、次のケースは継承関係が存在しない。
```
List<Object>
↑ 
✕ (継承関係は存在しない)
↑
List<String>
```

`Set<?>` が引数、このような継承関係が成り立っている。
```
Set<?>
↑ (継承)
Set<String>
```

既存なレガシーなコードをジェネリクス化するのは、難易度が高い。

しないほうがいいかもしれない...

```
interface Delayed implements Comparable<Delayed> {
}

interface ScheduledFuture<E> extends Delayed<E>
```

以下は、ダメな例。(E がパラ複数使われていること)
```
bar(List<?> list) {
    foot(list, list);
}

// Question が別々の型であると想定されてしまう
<E> void foo(List<E> list1, List<E> list2) {
...
}
```

### ワイルドカードキャプチャについて
http://www012.upp.so-net.ne.jp/eshibata/pdfs/tiger.pdf  

- 1章のところを読むと良い。
- 8章の部分は内容が古いので、書き直そうと思っている。
- Java5 でメモリモデルが整備されたので、それについても書いてある。

## その他
- C++ は使い方が色々ある。めちゃ難しい。
- Go の 1.18 で Generics が登場する。