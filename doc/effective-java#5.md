# Effective Java#5
see: https://github.com/jbloch/effective-java-3e-source-code/tree/master/src/effectivejava

## 誤りがある
P45: Set.of で変えてってくるのは HashSet ではない。"セット"

## 継承よりもコンポジション
1990 年頃、オブジェクト指向が流行って、継承だらけだった時代がある。

そのため、脆弱なコードがたくさん生まれてしまった。

- List.of: Java9 から
- Arrays.asList: Java9 よりも前

super クラスの処理を再実装することは、困難である。(場合によっては)
パフォーマンスが低下することもある。

「困難」と言っているのは、例の `addAll` の話のことではない。

super クラスが変わったからと言って、サブクラスが毎回コンパイルされるかは、わからない。

- Binary Compatibility
- Source Compatibility
- Functional Compatibility (機能の互換性がない)

### SELF問題
なにか別のフレームワークがある場合、Wrapper を通らずに自分自身を返してしまう。

論文: SELF Problem.pdf

`is-a`: 'イズ ア' と読む。(英語の、a name みたいなやつ。)

### 本来はコンポジションであるべき
- Stack と Vector
- Properties と Hashtable: Properties はみんなつかう。よくない。

## 継承のために設計及び文書化する
文書化できないのならば、継承を禁止せよ。

Instant is なに？ = java.Time パッケージに存在する。

Java はサブクラスができるクラスを書いてしまう傾向にある。

サブクラスが山程ある。ことがある。ありがちなのが、Super クラスを書いたときに Override されると思っていなかった。というケース。

とある不幸な話:
> - とあるクラスがあった。フレームワーク用。
> - Super クラスには Override しないといけないメソッドがいくつかあった。
> - サブクラスがたくさんいた。
> - このフレームワークにはクラスがわんさか。
> - フレームワークに機能が足りないとき、別のメソッドを並行して使いたい。お！Final って書いていない！ドキュメント宣言もされていない。
> - サブクラスが都合の良いように Override してしまった。
> - Super クラスがある日対応したところ、サブクラスに浸透していない...

きちんと設計されないまま、継承可能なメソッドなどを作るとよろしくない。かなりレガシー

```
public class Super {
    public final void foo() {
        // ...
        bar();
        // ... 
    }

    // Override 可能
    public void bar() {
        // ...処理A
    }
}

class Sub extends Super {
   public void bar() {
        // ...異なる処理
   } 
}
```

上記のようにすると、foo の実装が正しくなくなってしまう。  
大丈夫な状態にするためには、次のようにする。

```
public class Super {
    public final void foo() {
        // ...
        barImple();
        // ... 
    }

    // Override 可能
    public void bar() {
        barImple();
    }

    private void barImple() {
        // ...処理 A
    }
}

class Sub extends Super {
   public void bar() {
        // ...異なる処理
   } 
}
```

Java で　Class を書くときは、継承をしない場合は Final を利用するべし。

## 抽象クラスよりも Interface を選ぶ

- ボクシング: 値型の暗黙的な変換。

## 将来のためにインタフェースを設計する

- while よりも for を使えという話がある。(Java の世界では)

## 非 static のメンバクラスよりも static のメンバクラスを選ぶ
```
class X {
    class Y {
    }

    static class Z {
    }
}
```

- Z は単独でオブジェクトを作れる。
- Y は単独ではオブジェクトを作ることができない。かならず X が存在している。

```
// コンパイルエラーになる
X.Y y = new X.Y();
```

Y のインスタンスにひもづく X が存在しないから。

```
class X {
    Y createY () {
        return new Y();
    }

    class Y {
    }

    static class Z {
    }
}

// このようにすると Y が手に入る
X.Y y = new X().createY();
```

static を書けるのに書かないコードがよくある。

しかし、Y が生きている限り、X は消えない。

### static の文脈内 とは

```
class X {
    static {
        // 初期化
    }

    static void foo() {
        Runnable r = new Rannable() {  // static の文脈
            public void run() {...}
        }
    }
    
    void bar() {
        Runnable r = new Rannable() {  // 参照を持つ
            public void run() {...}
        }
    }
}
```

### ローカルクラスとは

あまり使っているところを見ない。

```
class X {
    void foo() {
        // このなかで宣言した Class = ローカルクラス
    }
}
```

こんな感じ。

```
class X {
    Runnable foo() {
        class Y implements Runnable {
            public void run() {...}
        }
        return new Y();
    }
}
```
