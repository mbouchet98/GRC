package DAO;

public abstract class DAO<T> {
    public abstract void insert(T obj);
    public abstract boolean update(T obj);
    public abstract boolean delete(T obj);
}
