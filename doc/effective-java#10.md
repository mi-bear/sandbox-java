# Effective Java#10
see: https://github.com/jbloch/effective-java-3e-source-code/tree/master/src/effectivejava

## パラメータの正当性を検査する
JavaDoc を見ると良い。

```BigInteger.java
NullPointerException を throw する
```
`-ea` は起動時に指定しないと、バイトコードから削除されてしまう。(Enable Assertions)

標準パッケージの `assert` を利用する場合は、`-ea` では有効にならない。`-esa` である必要がある。(Enable System Assertions)

コンストラクタで return したときは、成功 + こわれた Object となり、大変罪深い。

メソッドの検査は、経験を積まないと書かないことが多い。

### Sortの仕組み
つぎの流れになるため、Exception を throw したときは、リストの中身は変わっていない。

1. リストから配列にコピーする
2. 配列をソートする
3. 配列をリストに戻す

## 防御的なコピー
`LocalDateTime` を使ったほうがいい。たしかに。

