package nl.pieterleek.festivalmuntjes.repository;

public interface EntityRepository<E> {

    E get(String uid);
    String add(E e);
    void update(E e);
    void delete(E e);
    void delete(int id);
    E[] getAll();

}
