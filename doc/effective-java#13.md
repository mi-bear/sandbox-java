# Effective Java#13
see: https://github.com/jbloch/effective-java-3e-source-code/tree/master/src/effectivejava

## wait と notify よりも並行処理ユーティリティを選ぶ
- 開始時間を for 文の前に書いてしまうと、スレッドがはじまったとき、for の中に書くべし。
    - for の前だとスレッドがはじまったかどうかが分からない。
    