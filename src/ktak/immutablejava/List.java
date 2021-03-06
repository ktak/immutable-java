package ktak.immutablejava;

import java.util.Comparator;

public abstract class List<T> {
    
    public abstract <R> R visit(Visitor<R,T> visitor);
    
    public abstract <R> R match(
            Function<Unit,R> nilCase,
            Function<Tuple<T,List<T>>,R> consCase);
    
    public interface Visitor<R,T> {
        public R visitNil();
        public R visitCons(T head, List<T> tail);
    }
    
    public static final class Nil<T> extends List<T> {

        @Override
        public <R> R visit(Visitor<R, T> visitor) {
            return visitor.visitNil();
        }
        
        @Override
        public <R> R match(
                Function<Unit, R> nilCase,
                Function<Tuple<T, List<T>>, R> consCase) {
            return nilCase.apply(Unit.unit);
        }
        
    }
    
    private static class Cons<T> extends List<T> {
        
        public final T head;
        public final List<T> tail;
        
        public Cons(T head, List<T> tail) {
            this.head = head;
            this.tail = tail;
        }

        @Override
        public <R> R visit(Visitor<R, T> visitor) {
            return visitor.visitCons(head, tail);
        }
        
        @Override
        public <R> R match(
                Function<Unit, R> nilCase,
                Function<Tuple<T, List<T>>, R> consCase) {
            return consCase.apply(Tuple.create(head, tail));
        }
        
    }
    
    public List<T> cons(T value) {
        return new Cons<T>(value, this);
    }
    
    public Long length() {
        return this.visit(new ListLength<T>());
    }
    
    public Boolean equalTo(List<T> other, Eq<T> eq) {
        return this.visit(new ListEqualTo<T>(other, eq));
    }
    
    public List<T> reverse() {
        return this.visit(new ListReverse<T>());
    }
    
    public List<T> append(List<T> end) {
        return this.visit(new ListAppend<T>(end));
    }
    
    public <U> List<U> map(Function<T,U> f) {
        return this.visit(new ListMap<T,U>(f));
    }
    
    public <U> List<U> mapcat(Function<T,List<U>> f) {
        return this.visit(new ListMapCat<T,U>(f));
    }
    
    public Boolean isEmpty() {
        return this.visit(new ListEmpty<T>());
    }
    
    public int compareTo(List<T> other, Comparator<T> cmp) {
        return this.visit(new ListCompareTo<T>(other, cmp));
    }
    
    public <U> U foldRight(U init, Function<T,Function<U,U>> f) {
        return this.visit(new ListFoldRight<T,U>(init, f));
    }
    
}
