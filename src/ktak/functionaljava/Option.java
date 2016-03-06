package ktak.functionaljava;

public abstract class Option<T> {
    
    public abstract <R> R visit(Visitor<R,T> visitor);
    
    public static interface Visitor<R,T> {
        public R visitNone();
        public R visitSome(T value);
    }
    
    public static class None<T> extends Option<T> {
        
        @Override
        public <R> R visit(ktak.functionaljava.Option.Visitor<R, T> visitor) {
            return visitor.visitNone();
        }
        
    }
    
    public static class Some<T> extends Option<T> {
        
        private final T val;
        
        public Some(T val) {
            this.val = val;
        }
        
        @Override
        public <R> R visit(ktak.functionaljava.Option.Visitor<R, T> visitor) {
            return visitor.visitSome(val);
        }
        
    }
    
}