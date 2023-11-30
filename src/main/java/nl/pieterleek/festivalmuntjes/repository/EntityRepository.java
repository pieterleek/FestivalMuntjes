package nl.pieterleek.festivalmuntjes.repository;

public interface EntityRepository<E> {

    public E get(int id);
    public void add(E e);
    public void update(E e);
    public void delete(E e);
    public void delete(int id);
    public E[] getAll();

}
